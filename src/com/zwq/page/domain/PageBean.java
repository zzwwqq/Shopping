package com.zwq.page.domain;

import java.util.List;

public class PageBean<T> {
	private int totalRecords;//总记录数
	private int perPageRecordsNum;//每页显示的记录数
	private int totalPage; //总页数是根据总记录数和每页显示记录数算出来的
	private int currentPageNum;//当前页号
	private List<T>beanList;   //每页显示的数据
	
	public int getTotalPage() {
		
		this.totalPage = this.totalRecords / this.perPageRecordsNum;
		return this.totalRecords % this.perPageRecordsNum == 0 ? this.totalPage:this.totalPage + 1;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getPerPageRecordsNum() {
		return perPageRecordsNum;
	}
	public void setPerPageRecordsNum(int perPageRecordsNum) {
		this.perPageRecordsNum = perPageRecordsNum;
	}
	public int getCurrentPageNum() {
		return currentPageNum;
	}
	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	
}
