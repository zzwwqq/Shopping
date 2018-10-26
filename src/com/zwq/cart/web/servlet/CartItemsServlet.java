package com.zwq.cart.web.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zwq.cart.domain.CartItems;
import com.zwq.cart.domain.Items;
import com.zwq.cart.service.CartItemsService;
import com.zwq.cart.service.exception.CartException;
import com.zwq.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class CartItemsServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public CartItemsService cartItemsService = new CartItemsService();

	/**
	 * 添加购物车条目
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 得到User，判断用户是否已经登录
		 */
		User user = (User) request.getSession().getAttribute("sessionUser");
		if (user == null) {//未登录
			request.setAttribute("error", "您未登录，请先登录！");
			try {
				request.getRequestDispatcher("/jsps/cart/detail.jsp").forward(request, response);
				return;
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
				
		//用户已经登录
		/*
		 * 封装表单数据到CartItems（商品id,商品数量itemQuantity,商品Item，用户User）
		 */
		//封装要购买的商品数量itemQuantity
		CartItems cartItem = CommonUtils.toBean(request.getParameterMap(), CartItems.class);
		//封装商品id到Items
		Items items = CommonUtils.toBean(request.getParameterMap(), Items.class);		
		//将封装的商品对象和用户对象封装到购物车对象cartItem
		cartItem.setItems(items);
		cartItem.setUser(user);
		//此时cartItem中只有CartItemId和orderBy没有值
				
		/*
		 * 2.调用service完成添加
		 */
		cartItemsService.add(cartItem);
		/*
		 * 3.添加成功后，查询当前用户购物车中的所有条目，转发到myCart.jsp
		 */
		request.setAttribute("msg", "商品已加入购物车！");
		request.getRequestDispatcher("ItemsServlet?method=showItemsDetail&id="+cartItem.getItems().getId()).forward(request, response);
//		try {
//			myCart(request, response);
//		} catch (ServletException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 我的购物车
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void myCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1.得到uid
		 */
		User user = (User) request.getSession().getAttribute("sessionUser");
		if (user == null) {//未登录
			request.setAttribute("error", "您未登录，请先登录！");
			try {
				request.getRequestDispatcher("/jsps/cart/detail.jsp").forward(request, response);
				return;
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		String uid = user.getUid();
		
		/*
		 * 2.通过service得到当前用户所有购物车条目
		 */
		List<CartItems> cartItemList = cartItemsService.myCart(uid);		
		
		/*
		 * 3.保存起来，转发到/cart/myCart.jsp
		 */
		request.setAttribute("cartItemList", cartItemList);
		for(CartItems cartItems:cartItemList) {
		     Items items = cartItems.getItems();
		     System.out.println("********"+items.getName()+"********");
		}
		request.getRequestDispatcher("/jsps/cart/myCart.jsp").forward(request, response);
		return;
	}
	
	/*
	 * 删除一条购物车条目
	 */
	public void deleteCartItems(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//Items items = CommonUtils.toBean(request.getParameterMap(), Items.class);
		CartItems cartItems = CommonUtils.toBean(request.getParameterMap(), CartItems.class);
		//cartItems.setItems(items);
		
		try {
			cartItemsService.deleteCartItems(cartItems);
			myCart(request, response);
		} catch (CartException e) {
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		}			
	}
	
	/**
	 * 删除一个用户下的购物车中所有商品条目
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void deleteAllCartItems(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		try {
			boolean b = cartItemsService.deleteAllCartItemsByuid(uid);
			if (b) {
				myCart(request, response);
			}
			request.setAttribute("code", "error");
			request.setAttribute("msg", "删除失败");
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
			
		} catch (CartException e) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "删除失败");
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/**
	 * 点击加号
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void addNum(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String tempItemQuantity1 = request.getParameter("itemQuantity");
		String cartItemId1 = request.getParameter("cartItemId");
		int cartItemId = Integer.parseInt(cartItemId1);
		int tempItemQuantity2 = Integer.parseInt(tempItemQuantity1);
		int itemQuantity = ++tempItemQuantity2;
		try {
			boolean b =cartItemsService.addNum(cartItemId, itemQuantity);
			if (b) {
				myCart(request, response);
			}
			else {
				request.setAttribute("code", "error");
				request.setAttribute("msg", "添加失败");
				request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
			}
		} catch (CartException e) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		}
	}
	
	/**
	 * 点击减号
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void reduceNum(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String tempItemQuantity1 = request.getParameter("itemQuantity");
		String cartItemId1 = request.getParameter("cartItemId");
		int cartItemId = Integer.parseInt(cartItemId1);
		int tempItemQuantity2 = Integer.parseInt(tempItemQuantity1);
		int itemQuantity = 0;
		if (tempItemQuantity2 < 1 || tempItemQuantity2 == 1) {
			myCart(request, response);
			/*
			 * 注意：
			 * 这里一定要return;要不然，执行了myCart(request, response)后虽然在myCart方法转发到了myCart.jsp
			 * 但还是会执行myCart(request, response);后面的语句
			 */			
			return;
		}
		itemQuantity = --tempItemQuantity2;			
		try {
			boolean b =cartItemsService.reduceNum(cartItemId, itemQuantity);
			if (b) {
				myCart(request, response);
			}
			else {
				request.setAttribute("code", "error");
				request.setAttribute("msg", "添加失败");
				request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
			}
		} catch (CartException e) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		}
	}
	
//	/**
//	 * 点击购买按钮后，删除购物车中已购买的商品，购物车中没有下单的商品仍然保留
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	public void afterBuyDeleteCartItems(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
//		String cartItemId= request.getParameter("cartItemId");
//		if (cartItemId != null && cartItemId.length() > 0) {
//			String[] cartItem= cartItemId.split(",");
//			for(String id:cartItem) {
//				System.out.println("商品在购物车中的"+id);
//				try {
//					boolean b = cartItemsService.afterBuyDeleteCartItems(Integer.parseInt(id));
//					if (b) {
//						request.getRequestDispatcher("/jsps/order/order.jsp").forward(request, response);
//						return;
//					}
//					request.getRequestDispatcher("/jsps/order/order.jsp").forward(request, response);
//					return;
//				} catch (NumberFormatException e) {
//					request.setAttribute("msg", e.getMessage());
//					request.setAttribute("code", "error");
//					request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
//				} catch (CartException e) {
//					request.setAttribute("msg", e.getMessage());
//					request.setAttribute("code", "error");
//					request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
//				}
//			}
//		}
//	}	
	
	
	
	/**
	 * 点击购买按钮后，删除购物车中已购买的商品，购物车中没有下单的商品仍然保留
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void afterBuyDeleteCartItems(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String cartItemId= request.getParameter("cartItemId");
		if (cartItemId != null && cartItemId.length() > 0) {
			String[] cartItem= cartItemId.split(",");
			for(String id:cartItem) {
				System.out.println("商品在购物车中的"+id);				
					try {
						cartItemsService.afterBuyDeleteCartItems(Integer.parseInt(id));
					} catch (NumberFormatException | CartException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
			}	
			request.setAttribute("oneParam", "Take_the_payment");
			request.getRequestDispatcher("/jsps/order/order.jsp").forward(request, response);			
		}	
	}
}
