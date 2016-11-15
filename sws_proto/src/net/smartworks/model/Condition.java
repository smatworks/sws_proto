package net.smartworks.model;

import java.util.List;

public class Condition {

	private String viewType = null;
	private String searchKey = null;
	private int pageSize = -1;
	private int pageNo = -1;
	
	private String orderColumn;
	private boolean isDescending;
	
	private List<Filter> filters;

	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getOrderColumn() {
		return orderColumn;
	}
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	public boolean isDescending() {
		return isDescending;
	}
	public void setDescending(boolean isDescending) {
		this.isDescending = isDescending;
	}
	public List<Filter> getFilters() {
		return filters;
	}
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
}
