
package com.zwq.cart.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.zwq.cart.domain.Items;
import cn.itcast.jdbc.TxQueryRunner;

public class ItemsDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 主页main.jsp使用
	 * 查询所有商品信息
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Items> getAllItems() throws SQLException {
		ArrayList<Items> list = new ArrayList<Items>();
		String sql = "select * from items;";
		ArrayList<Items> itemsList = (ArrayList<Items>) qr.query(sql, new BeanListHandler<Items>(Items.class));
		return itemsList;
	}

	/**
	 * detail.jsp使用
	 * 根据id查询商品详细信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Items getItemsById(int id) throws SQLException {
		String sql = "select * from items where id=?";
		Items items = qr.query(sql, new BeanHandler<Items>(Items.class), id);
		return items;
	}
}
