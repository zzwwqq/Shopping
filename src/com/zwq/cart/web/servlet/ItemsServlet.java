package com.zwq.cart.web.servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zwq.cart.domain.Items;
import com.zwq.cart.service.ItemsService;
import cn.itcast.servlet.BaseServlet;

/**
 * Servlet implementation class ItemsServlet
 */
@WebServlet("/ItemsServlet")
public class ItemsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private ItemsService itemsService = new ItemsService();
	
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO 自动生成的方法存根
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		returnHome(req,resp);
	}




	public void returnHome(HttpServletRequest request, HttpServletResponse response) {
    	try {
			request.getRequestDispatcher("/jsps/main.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }
	
	
	public void showItemsDetail(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException {
	     String viewIdList = "";
		/*获取Cookie,并得到其中的viewIdList*/
		Cookie[]cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for(Cookie c : cookies) {
				if(c.getName().equals("getViewList")){ 
				 viewIdList = c.getValue();
				}
			}
		}	
		
	     viewIdList += (String)request.getParameter("id") + "-";
		 /*浏览商品数量大于50时，清空浏览记录*/
		 String[]idArray = viewIdList.split("-");
		 if( idArray != null && idArray.length > 50) {
			viewIdList="";
		}
	     
	    /*浏览商品数量小于50时，保存id到cookie，显示浏览记录
	     * 注意：Cookie中不能包含空格和逗号，不然会报错
	     * */
		Cookie cookie = new Cookie("getViewList",viewIdList);
		response.addCookie(cookie);
					 
		ArrayList<Items> itemlist = itemsService.getViewItemsList(viewIdList);
        Items item = itemsService.getItemsById(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("itemlist", itemlist);
		request.setAttribute("item", item);
		request.getRequestDispatcher("/jsps/cart/detail.jsp").forward(request, response);
	}	
}
