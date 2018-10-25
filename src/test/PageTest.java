package test;

import java.sql.SQLException;

import org.junit.Test;

import com.zwq.cart.domain.Items;
import com.zwq.page.dao.PageDao;
import com.zwq.page.domain.PageBean;

public class PageTest {
	@Test
	public void test1() {
		try {
			PageBean<Items> pageBean = new PageDao().getLimitRowAllItems(1);
			if (pageBean.getBeanList() != null && pageBean.getBeanList().size() > 0) {
				for(Items item:pageBean.getBeanList()) {				
					System.out.println("商品名："+ item.getName());
				}
			}
			System.out.println("当前页："+pageBean.getCurrentPageNum());
			System.out.println("每页记录数："+ pageBean.getPerPageRecordsNum());
			System.out.println("总页数："+ pageBean.getTotalPage());
			System.out.println("总记录数："+ pageBean.getTotalRecords());					
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
