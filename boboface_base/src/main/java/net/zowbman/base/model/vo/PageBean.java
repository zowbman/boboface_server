package net.zowbman.base.model.vo;

/**
 * 
 * Title:PageBean
 * Description:分页包装类
 * @author    zwb
 * @date      2016年7月27日 下午2:04:13
 *
 */
public class PageBean {
	private Integer pageNum = 1;//页码
	private Integer pageSize = 8;//一页数据量（默认8）
	private String orderByColumn;//排序列
	private OrderStyleEnum orderStyleEnum;//排序方式
	//不是用通用mapper进行分页
	private Integer start;
	private Integer end;
	
	
	public PageBean() {
		super();
	}
	public PageBean(Integer pageNum, Integer pageSize, String orderByColumn, OrderStyleEnum orderStyleEnum) {
		super();
		if(pageNum != null)
			this.pageNum = pageNum;
		if(pageSize != null)
			this.pageSize = pageSize;
		this.orderByColumn = orderByColumn;
		this.orderStyleEnum = orderStyleEnum;
		this.start = (this.pageNum - 1) * this.pageSize;
		this.end = this.pageSize;
	}
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderByColumn() {
		return orderByColumn;
	}
	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}
	public OrderStyleEnum getOrderStyleEnum() {
		return orderStyleEnum;
	}
	public void setOrderStyleEnum(OrderStyleEnum orderStyleEnum) {
		this.orderStyleEnum = orderStyleEnum;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
}
