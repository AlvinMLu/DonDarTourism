package com.cy.pj.sys.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
/**
  * 菜单的数据访问层对象
 * @author Administrator
 */
@Mapper
//@CacheNamespace
public interface SysMenuDao {
	/**
	  *  基于菜单id获取权限标识
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(
			@Param("menuIds")
			Integer[] menuIds);
	
	/**将菜单信息更新到数据库*/
	int updateObject(SysMenu entity);
	/**将菜单信息写入到数据库*/
	int insertObject(SysMenu entity);
	/**查询所有菜单信息
	 * 1)一行菜单信息映射为一个map对象(key为记录中的字段名)
	 * 2)多行记录会对应多个map,然后将map存在list集合*/
	@Select("select c.*,p.name parentName from sys_menus c left join sys_menus p on c.parentId=p.id")
	List<Map<String,Object>> findObjects();
	/**查询所有菜单信息
	 * 1)一行菜单信息映射为一个Node对象(属性名建议和字段名相同)
	 * 2)多行记录会对应多个node,然后将node存在list集合*/
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();
}






