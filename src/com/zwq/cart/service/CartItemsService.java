package com.zwq.cart.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zwq.cart.dao.CartItemsDao;
import com.zwq.cart.domain.CartItems;
import com.zwq.cart.service.exception.CartException;

import cn.itcast.commons.CommonUtils;


/**
 * 购物车模块
 */
public class CartItemsService {
	private CartItemsDao cartItemsDao = new CartItemsDao();

	/**
	 * 添加条目
	 * @param cartItem
	 * @throws SQLException 
	 */
	public void add(CartItems cartItems) {
		/*
		 * 1.使用uid和id去数据库中查询这个条目是否存在
		 */
		try {
			CartItems datebase_cartItem = cartItemsDao.findByUidAndid(cartItems.getUser().getUid(),cartItems.getItems().getId());
			//如果原来没有这个条目，那么就添加条目
			if(datebase_cartItem == null) {
				//补全数据，即为CartItemId设值，这一步后，CartItems对象中只有orderBy没有值
				cartItems.setCartItemId(CommonUtils.uuid());
				//调用dao层addCartItems方法将信息存入数据库
				cartItemsDao.addCartItems(cartItems);
			} 
			//如果原来有该条目，修改数量
			else {
				//原有数量和新添加条目数量之和，作为新的总数量
				int totalQuantity = datebase_cartItem.getItemQuantity() + cartItems.getItemQuantity();
				//修改原有数量
				cartItemsDao.updateItemQuantity(datebase_cartItem.getCartItemId(), totalQuantity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 我的购物车功能
	 * @param uid
	 * @return
	 */
	public List<CartItems>myCart(String uid) {
		try {
			return cartItemsDao.findAllInfoByUser(uid);
		} catch (SQLException e) {
		throw new RuntimeException(e);
		}
	}	
	
	/*
	 * 删除购物车中商品条目
	 */
	public boolean deleteCartItems(CartItems cartItems) throws CartException {
		try {
			 int affectRowNum = cartItemsDao.deleteCartItemsByCartItemId(cartItems.getCartItemId());
			 if (affectRowNum > 0) {
				return true;
			}
		} catch (SQLException e) {
			throw new CartException("删除失败！");
		}
		return false;
	}
	
	
	public boolean deleteAllCartItemsByuid(String uid) throws CartException {
		try {
			int affectRowNum = cartItemsDao.deleteAllCartItemsByuid(uid);
			if (affectRowNum > 0) {
				return true;
			}
		} catch (SQLException e) {
			throw new CartException("删除失败");
		}
		return false;	
	}
	
	
	/**
	 * 点击加号
	 * @param request
	 * @param response
	 * @throws CartException 
	 */
	public boolean addNum(String cartItemId,int itemQuantity) throws CartException {
		try {
			int affectRowNum = cartItemsDao.updateItemQuantity(cartItemId, itemQuantity);
			if (affectRowNum > 0) {
				System.out.println("受影响的行数："+affectRowNum);
				return true;
			}
		} catch (SQLException e) {
			throw new CartException("添加失败");
		}
		return false;	
	}
	
	
	/**
	 * 点击减号
	 * @param request
	 * @param response
	 * @throws CartException 
	 */
	public boolean reduceNum(String cartItemId,int itemQuantity) throws CartException {
		try {
			int affectRowNum = cartItemsDao.updateItemQuantity(cartItemId, itemQuantity);
			if (affectRowNum > 0) {
				System.out.println("受影响的行数："+affectRowNum);
				return true;
			}
		} catch (SQLException e) {
			throw new CartException("删除失败");
		}
		return false;	
	}
	
	
	
	
	
}
