package com.boboface.base.service;

import java.util.List;

import com.boboface.base.model.vo.PageBean;
import com.boboface.exception.CustomException;

/**
 * 
 * Title:IBaseService
 * Description:基础Service接口
 * @author    zwb
 * @date      2016年7月25日 下午12:12:24
 *
 */
public interface IBaseService<T> {
	/**
	 * 保存实体(所有字段)
	 * @param entity 
	 */
	void save(T entity);
	
	/**
	 * 保存实体
	 * @param entity
	 */
	void saveSeletive(T entity);
	
	/**
	 * 删除
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void delete(Integer[] ids);
	
	/**
	 * 更新实体（所有字段）
	 * @param entity
	 */
	void update(T entity);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	void updateSeletive(T entity);
	
	/**
	 * 根据Id查询实体
	 * @param id
	 * @return
	 */
	T getById(Integer id);
	
	/**
	 * 多个Id查询实体
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Integer[] ids);
	
	/**
	 * 查询全部
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 获取总记录数
	 * @param entity
	 * @return
	 */
	int getTotal(T entity); 
	
	/**
	 * 查询全部（分页）
	 * @param pageBean 分页包装类
	 * @return List<T>
	 * @throws CustomException
	 */
	List<T> findAllByPageBean(PageBean pageBean) throws CustomException;
}
