package com.cy.pj.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserRoleDao {

	
	@Select("select role_id from sys_user_roles where user_id=#{id}")
	List<Integer> findRoleIdsByUserId(Integer id);
	 /**
	  * 保存用户和角色的关系数据
	  * @param userId
	  * @param roleIds
	  * @return
	  */
	 int insertObjects(
			 @Param("userId")Integer userId,
			 @Param("roleIds")Integer[] roleIds);
	 @Delete("delete from sys_user_roles where role_id=#{roleId}")
	 int deleteObjectsByRoleId(Integer roleId);
	 @Delete("delete from sys_user_roles where user_id=#{userId}")
	 int deleteObjectsByUserId(Integer userId);
}
