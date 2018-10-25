package com.zwq.page.service;
import java.sql.SQLException;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zwq.cart.domain.Items;
import com.zwq.page.dao.PageDao;
import com.zwq.page.domain.PageBean;
import com.zwq.page.domain.PageException;

public class PageService {
	private PageDao pageDao = new PageDao();
	
	public PageBean<Items> getLimitRowAllItems(int currentPageNum) throws PageException {	
		try {
			return pageDao.getLimitRowAllItems(currentPageNum);
		} catch (SQLException e) {
			throw new PageException();
		}	
	}
	
}
