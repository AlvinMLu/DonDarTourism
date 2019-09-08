package com.cy.pj.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.entity.SysLog;
@Mapper
public interface SysLogDao {
	int insertObject(SysLog entity);
	/**按条件查询日志总记录数
	 * @param username 查询条件
	 */
	int getRowCount(@Param("username")String username);
	/**
	 * 基于条件执行分页查询,获取当前页记录
	 * @param username 查询条件
	 * @param startIndex 起始位置
	 * @param pageSize 页面大小(每个页面
	 * 显示多少条数据)
	 * @return
	 */
	List<SysLog> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
	/**简单sql使用注解*/
    @Delete("delete from sys_logs where id=#{id}")
	int deleteObject(Integer id);
    /**
          *  复杂SQL建议将SQL写到xml
          *  基于传入的id值执行动态删除(可能多个)操作
     * @param ids
     * @return
     */
    int deleteObjects(
        @Param("ids")Integer... ids);
}
