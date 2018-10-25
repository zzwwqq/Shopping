package com.zwq.page.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zwq.cart.domain.Items;
import com.zwq.page.domain.PageBean;
import com.zwq.page.domain.PageException;
import com.zwq.page.service.PageService;

import cn.itcast.servlet.BaseServlet;

public class PageServlet extends BaseServlet {

	private PageService pageService = new PageService();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the GET method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the POST method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
	}
	
	/**
	 * 获取当前页
	 * @param request
	 * @return
	 */
		public int getCurrentPage(HttpServletRequest request) {
			int currentPage = 1;
			String tempCurrentPage = request.getParameter("currentPageNum");
			if (tempCurrentPage != null && !tempCurrentPage.trim().isEmpty()) {			
				try {
					currentPage = Integer.parseInt(tempCurrentPage);
				} catch (RuntimeException e) {
					e.getMessage();
				}			
			}
			return currentPage;
		}
		
		/**
		 * 获取PageBean，返回给jsp页面
		 * @throws IOException 
		 * @throws ServletException 
		 * @throws SQLException 
		 */
		public void getPageBean(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, SQLException {		
        	//request.setAttribute("status", "");	
			int currentPage = getCurrentPage(request);
			PageBean<Items> pageBean = null;
			try {
				pageBean = pageService.getLimitRowAllItems(currentPage);
			} catch (PageException e1) {
				e1.printStackTrace();
			}
			request.setAttribute("pageBean", pageBean);			
			request.getRequestDispatcher("/jsps/main.jsp").forward(request, response);	
		}	

}
