package com.zwq.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.zwq.cart.domain.CartItems;
import com.zwq.cart.domain.Items;
import com.zwq.user.domain.User;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class CartItemsDao {
	private QueryRunner qr = new TxQueryRunner();	
	/**
	 * 购物车模块mycart.jsp使用
	 */
	/**
	 * 查询某个用户的商品条目是否存在	
	 * 购物车中的每件商品一定属于特定的用户，所以需要知道用户的uid
	 * @param uid
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public CartItems findByUidAndid(String uid,int id) throws SQLException {
		String sql = "select * from t_cartitem where uid = ? and id = ?";
		Map<String, Object>map = qr.query(sql, new MapHandler(),uid,id);
		CartItems cartItem = toCartItem(map);
		return cartItem;
	}

	/**
	 * 修改指定条目的数量
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException
	 */
	public int updateItemQuantity (int cartItemId,int itemQuantity) throws SQLException {
		String sql = "update t_cartitem set itemQuantity=? where cartItemId=?";
		return qr.update(sql,itemQuantity,cartItemId);
	}
		
	/**
	 * 添加条目	
	 * @param cartItem
	 * @throws SQLException
	 */
	public void addCartItems(CartItems cartItems) throws SQLException {
		String sql = "insert into t_cartitem(cartItemId,uid,id,itemQuantity) values(?,?,?,?)";
		Object[]params = {cartItems.getCartItemId(),cartItems.getUser().getUid(),cartItems.getItems().getId(),cartItems.getItemQuantity()};
		int affectRow = qr.update(sql, params);
		System.out.println("============受影响的行数："+affectRow+"===========");
	}
	
		/**
		 * 把一个Map映射成一个Cartiems
		 * @param map
		 * @return
		 */
		public CartItems toCartItem(Map<String, Object>map) {
			if (map == null || map.size() == 0) {
				return null;
			}
			CartItems cartItem = CommonUtils.toBean(map, CartItems.class);
			Items item = CommonUtils.toBean(map, Items.class);
			User user = CommonUtils.toBean(map, User.class);
			cartItem.setItems(item);
			cartItem.setUser(user);
			return cartItem;	
		}
		
		/*
		 * 把List中存放的多个Map映射成多个CartItems
		 */
		public List<CartItems>toCartItemList(List<Map<String,Object>>mapList) {
			List<CartItems>cartItemList = new ArrayList<CartItems>();
			for(Map<String, Object> map : mapList) {
				CartItems cartItem = toCartItem(map);
				cartItemList.add(cartItem);			
			}
			return cartItemList;
		}
		

		/**
		 * 通过用户查询购物车条目（包括商品表，购物车表）
		 * 先将所有数据存放到多个Map，再将Map转换为CartItems
		 * @param uid
		 * @return 购物车表和商品表两个表满足查询条件的行数据的总和 List<CartItems>
		 * @throws SQLException
		 */
		public List<CartItems> findAllInfoByUser(String uid) throws SQLException{
			//String sql = "select * from t_cartitem c,items i where c.id = i.id and uid = ? order by c.orderBy";
			String sql = "select * from t_cartitem c,items i where c.id = i.id and uid = ?";
			/*
			 * 返回值是购物车表和商品表两个表满足查询条件的行数据的总和，不能单独赋值给Item对象或者CartItems对象，否则数据会丢失
			 * 这里采取的方法是将查询出的信息存到List集合中，且List中存放的是Map对象,每个Map存放的是一条记录
			 */
			List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),uid);
			return toCartItemList(mapList);
		}	
		
		/*
		 * 通过购物车主键，删除购物车条目
		 */
		public int deleteCartItemsByCartItemId(int cartItemId) throws SQLException {
			String sql = "delete from t_cartitem where cartItemId = ?";
			return qr.update(sql,cartItemId);
		}
		
		
		public int deleteAllCartItemsByuid(String uid) throws SQLException {
			String sql = "delete from t_cartitem where uid =?";
			return qr.update(sql,uid);
		}
		
		
		
		
		
		
		
		
		
		
		
}
