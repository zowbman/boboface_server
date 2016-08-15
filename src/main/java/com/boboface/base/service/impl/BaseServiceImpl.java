package com.boboface.base.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import net.zowbman.base.model.vo.OrderStyleEnum;
import net.zowbman.base.model.vo.PageBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import com.boboface.base.service.IBaseService;
import com.boboface.exception.CustomException;
import com.boboface.wiki.mapper.TBobofaceWikiContentMapper;
import com.github.pagehelper.PageHelper;

/**
 * 
 * Title:BaseServiceImpl
 * Description:基础Service接口实现类
 * @author    zwb
 * @date      2016年7月25日 下午12:12:40
 *
 */
@Service
public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	
	//泛型类
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		super();
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	/**
	 * 注入通用mapper
	 */
	@Autowired
	private Mapper<T> mapper;
	
	/**
	 * wiki content mapper接口
	 */
	@Autowired
	protected TBobofaceWikiContentMapper tBobofaceWikiContentMapper;
	
	public void save(T entity) {
		mapper.insert(entity);
	}
	
	public void saveSeletive(T entity) {
		mapper.insertSelective(entity);
	}

	public void delete(Integer id) {
		mapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			mapper.deleteByPrimaryKey(id);
		}
	}
	public void update(T entity) {
		mapper.updateByPrimaryKey(entity);
	}

	public void updateSeletive(T entity) {
		mapper.updateByPrimaryKeySelective(entity);
	}

	public T getById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	public List<T> getByIds(Integer[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> findAll() {
		return mapper.selectAll();
	}

	@Override
	public int getTotal(T entity) {
		return mapper.selectCount(entity);
	}

	@Override
	public List<T> findAllByPageBean(PageBean pageBean) throws CustomException {
		if(pageBean == null || pageBean.getPageNum() == null || pageBean.getPageSize() == null)
			throw new CustomException("分页数据不允许为空");
		Example example = new Example(clazz);
		if(OrderStyleEnum.ACS.equals(pageBean.getOrderStyleEnum())){
			example.orderBy(pageBean.getOrderByColumn()).asc();
		}else if(OrderStyleEnum.DESC.equals(pageBean.getOrderStyleEnum())){
			example.orderBy(pageBean.getOrderByColumn()).desc();
		}
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize(), true, false);
		return mapper.selectByExample(example);
	}
}
