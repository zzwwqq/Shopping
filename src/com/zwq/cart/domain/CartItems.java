package com.zwq.cart.domain;

import java.math.BigDecimal;

import com.zwq.user.domain.User;


public class CartItems {
	private String cartItemId;//主键
	private int itemQuantity;//购物车中对应用户的商品数量
	private Items items;//商品
	private User user;//所属用户
	
	/**
	 * 添加小计方法
	 * BigDecimal不会有误差
	 * 必须使用String类型构造器
	 * @return
	 */
	public double getSubtotal() {
		BigDecimal b1 = new BigDecimal(items.getPrice()+"");
		BigDecimal b2 = new BigDecimal(itemQuantity+"");
		BigDecimal b3 = b1.multiply(b2);//单件商品的价格乘以这件商品的数量得总价
		return b3.doubleValue(); 
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
}
