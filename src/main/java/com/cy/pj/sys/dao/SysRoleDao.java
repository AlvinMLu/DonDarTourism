package com.cy.pj.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;
@Mapper
public interface SysRoleDao {
	  /**查询所有角色信息*/
	  @Select("select id,name from sys_roles")
	  List<CheckBox> findObjects();
	  /**更新角色自身信息*/
	  int updateObject(SysRole entity);
	  /**
	   * 基于角色id获取角色以及对应的菜单信息
	   * @param id
	   * @return
	   */
	  SysRoleMenuVo findObjectById(Integer id);
	  /**保存角色自身信息*/
	  int insertObject(SysRole entity);
	  /**基于id执行角色id删除*/
	  @Delete("delete from sys_roles where id=#{id}")
	  int deleteObject(Integer id);
	
	  int getRowCount(@Param("name")String name);
	  List<SysRole> findPageObjects(
			  @Param("name")String name,
			  @Param("startIndex")Integer startIndex,
			  @Param("pageSize")Integer pageSize);
}
