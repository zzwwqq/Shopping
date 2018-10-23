package com.zwq.cart.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.zwq.cart.dao.ItemsDao;
import com.zwq.cart.domain.Items;

public class ItemsService {
	private ItemsDao itemsDao = new ItemsDao();
	
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
	

	public ArrayList<Items> getViewItemsList(String list) throws SQLException
	{
		System.out.println("list:"+list);
		ArrayList<Items> itemlist = new ArrayList<Items>();
		int iCount = 5; 
		if(list != null && list.length() > 0)
		{
		    String[] arr = list.split("-");
		    System.out.println("arr.length="+arr.length);
		  //浏览商品大于五条记录
		    if(arr.length >= iCount) {
		    	/*后浏览的记录先显示，*/
		       for(int i=arr.length-1;i >= arr.length - iCount; i--) {
		    	  try {
					itemlist.add(getItemsById(Integer.parseInt(arr[i])));
				} catch (NumberFormatException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}  
		       }
		    }
		    else
		    {
		    	/*浏览记录在五条以内，后浏览的先显示*/
		    	for(int i = arr.length - 1; i >= 0;i--)
		    	{
		    		try {
						itemlist.add(getItemsById(Integer.parseInt(arr[i])));
					} catch (NumberFormatException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
		    	}
		    }
		    return itemlist;
		}
		else
		{
			return null;
		}
		
	}
	
	
}
