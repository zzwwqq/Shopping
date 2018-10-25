package com.zwq.page.dao;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.zwq.cart.domain.Items;
import com.zwq.constant.Constant;
import com.zwq.page.domain.PageBean;
import cn.itcast.jdbc.TxQueryRunner;

public class PageDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 主页main.jsp使用
	 * 限制显示商品信息行数
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Items> getLimitRowAllItems(int currentPageNum) throws SQLException {	
		String sql = "select count(*) from items";
		Number number = (Number) qr.query(sql, new ScalarHandler());
		/*
		 * 1.总记录数
		 */
		int totalRecordsNum = number.intValue();
		/*
		 * 2.每页记录数
		 */
		int perPageRecordsNum = new Constant().PERPAGERECORDS;
		int startRowNum = (currentPageNum - 1) * perPageRecordsNum;
		String sql2 = "select * from items limit ?,?";	
		PageBean<Items> pageBean = new PageBean<Items>();
		pageBean.setBeanList(qr.query(sql2, new BeanListHandler<Items>(Items.class),startRowNum,perPageRecordsNum));
		pageBean.setCurrentPageNum(currentPageNum);
		pageBean.setPerPageRecordsNum(perPageRecordsNum);
		pageBean.setTotalRecords(totalRecordsNum);
		pageBean.setTotalPage(pageBean.getTotalPage());
		return pageBean;		
	}
}
