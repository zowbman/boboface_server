package net.zowbman.base.model.vo;

import com.github.pagehelper.PageInfo;


public class PageInfoCustom {
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //当前页的数量
    private int size;
    //总记录数
    private long total;
    //总页数
    private int pages;
    
    //第一页
    private int firstPage;
    //前一页
    private int prePage;
    //下一页
    private int nextPage;
    //最后一页
    private int lastPage;

    //是否为第一页
    private boolean hasFirstPage = false;
    //是否为最后一页
    private boolean hasLastPage = false;
    //是否有前一页
    private boolean hasPreviousPage = false;
    //是否有下一页
    private boolean hasNextPage = false;
    //导航页码数
    private int navigatePages;
    //所有导航页号
    private int[] navigatepageNums;
    
    public PageInfoCustom(PageInfo<?> pageInfo){
    	this.pageNum = pageInfo.getPageNum();
    	this.pageSize = pageInfo.getPageSize();
    	this.size = pageInfo.getSize();
    	this.total = pageInfo.getTotal();
    	this.pages = pageInfo.getPages();
    	this.firstPage = pageInfo.getFirstPage();
    	this.prePage = pageInfo.getPrePage();
    	this.nextPage = pageInfo.getNextPage();
    	this.lastPage = pageInfo.getLastPage();
    	this.hasFirstPage = pageInfo.isIsFirstPage();
    	this.hasLastPage = pageInfo.isIsLastPage();
    	this.hasPreviousPage = pageInfo.isHasPreviousPage();
    	this.hasNextPage = pageInfo.isHasNextPage();
    	this.navigatePages = pageInfo.getNavigatePages();
    	this.navigatepageNums = pageInfo.getNavigatepageNums();
    }
    
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public boolean isHasFirstPage() {
		return hasFirstPage;
	}
	public void setHasFirstPage(boolean hasFirstPage) {
		this.hasFirstPage = hasFirstPage;
	}
	public boolean isHasLastPage() {
		return hasLastPage;
	}
	public void setHasLastPage(boolean hasLastPage) {
		this.hasLastPage = hasLastPage;
	}
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public int getNavigatePages() {
		return navigatePages;
	}
	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}
	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}
	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}   
}
