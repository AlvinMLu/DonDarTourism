package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;

/**
  *  用户行为日志业务
 * @author Administrator
 */
public interface SysRoleService {
	/**
	 * 查询所有角色id,name
	 * @return
	 */
	List<CheckBox> findObjects();
	
	/**
	 * 基于角色id查询角色以及对应的菜单信息
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	
	/**
	 * 更新角色以及对应的菜单信息
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int updateObject(SysRole entity,Integer[] menuIds);
	
	/**
	 * 保存角色以及对应的菜单信息
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int saveObject(SysRole entity,Integer[] menuIds);
	
	/**
	 * 基于角色id删除角色以及对应的关系数据
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
	/**
	 * 执行分页查询
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysRole> findPageObjects(
			 String name,
			 Integer pageCurrent);
	
}





