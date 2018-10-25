package com.zwq.cart.service;

import java.sql.SQLException;
import java.util.ArrayList;
import com.zwq.cart.dao.ItemsDao;
import com.zwq.cart.domain.Items;
import com.zwq.constant.Constant;

public class ItemsService {
	private ItemsDao itemsDao = new ItemsDao();

	/**
	 * 主页main.jsp使用 查询所有商品
	 * 
	 * @return
	 */
	public ArrayList<Items> getAllItems() {
		ArrayList<Items> itemsList = null;
		try {
			itemsList = itemsDao.getAllItems();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return itemsList;
	}

	/**
	 * detail.jsp查看商品浏览记录使用 查询一个商品的详细信息
	 * 
	 * @param id
	 * @return
	 */
	public Items getItemsById(int id) {
		Items items = null;
		try {
			items = itemsDao.getItemsById(id);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return items;
	}

	/**
	 * 获取浏览记录detail.jsp
	 * 
	 * @param list
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Items> getViewItemsList(String list) throws SQLException {
		System.out.println("list:" + list);
		ArrayList<Items> itemlist = new ArrayList<Items>();

		// 限制页面显示的浏览记录条数
		int iCount = Constant.NUMBER_OF_VIEWING_RECORDING;
		if (list != null && list.length() > 0) {
			String[] arr = list.split("-");
			System.out.println("arr.length=" + arr.length);
			// 浏览商品大于iCount条记录
			if (arr.length >= iCount) {
				/* 后浏览的记录先显示， */
				for (int i = arr.length - 1; i >= arr.length - iCount; i--) {
					try {
						itemlist.add(getItemsById(Integer.parseInt(arr[i])));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			} else {
				/* 浏览记录在五条以内，后浏览的先显示 */
				for (int i = arr.length - 1; i >= 0; i--) {
					try {
						itemlist.add(getItemsById(Integer.parseInt(arr[i])));
					} catch (NumberFormatException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
			return itemlist;
		} else {
			return null;
		}
	}
}
