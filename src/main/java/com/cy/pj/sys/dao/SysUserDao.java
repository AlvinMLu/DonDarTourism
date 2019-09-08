package com.cy.pj.sys.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;
@Mapper
public interface SysUserDao {
	  /**基于用户名查询用户信息*/
	  @Select("select * from sys_users where username=#{username}")
	  SysUser findUserByUserName(String username);
	  
	  int updateObject(SysUser entity);
	  SysUserDeptVo findObjectById(Integer id);
	   /**
	    * 保存用户自身信息
	    * @param entity
	    * @return
	    */
	   int insertObject(SysUser entity);
	   /**
	    * 禁用启用用户信息
	    * @param id 用户id
	    * @param valid 状态信息(禁用,启用)
	    * @param modifiedUser 登录用户
	    * @return 更新的行数
	    */
	   @Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
	   int validById(
			   @Param("id")Integer id,
			   @Param("valid")Integer valid,
			   @Param("modifiedUser")String modifiedUser);
	   
	   int getRowCount(@Param("username")String username);
	   List<SysUserDeptVo> findPageObjects(
			   @Param("username")String username,
			   @Param("startIndex")Integer startIndex,
			   @Param("pageSize")Integer pageSize);
}
