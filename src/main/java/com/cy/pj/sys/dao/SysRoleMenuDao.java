package com.cy.pj.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**负责sys_role_menus关系表中的数据的操作*/
@Mapper
public interface SysRoleMenuDao {
	 /**
	    * 基于多个角色id获取菜单id
	  * @param roleIds
	  * @return
	  */
	 List<Integer> findMenuIdsByRoleIds(
		@Param("roleIds")Integer[] roleIds);

	 /**保存角色和菜单的关系数据
	  * @param roleId 角色id
	  * @param menuids 多个菜单id*/
	 int insertObjects(
			 @Param("roleId")Integer roleId,
			 @Param("menuIds")Integer[]menuIds);
	
	 @Delete("delete from sys_role_menus where role_id=#{role_id}")
	 int deleteObjectsByRoleId(Integer roleId);
}
