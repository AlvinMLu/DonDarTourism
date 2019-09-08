package com.cy.pj.sys.service;

import java.util.Map;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

public interface SysUserService 
   extends PageService<SysUserDeptVo>{
	int updateObject(SysUser entity,Integer[] roleIds);
	Map<String, Object> findObjectById(
			Integer userId);

	int saveObject(SysUser entity,
			Integer[]roleIds);
	
	int validById(Integer id,
			Integer valid,
			String modifiedUser);

}
