package com.cy.pj.sys.service.impl;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.PageUtil;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

import lombok.extern.slf4j.Slf4j;
@Service //===map.put("sysLogServiceImpl",object instance)
@Slf4j
public class SysLogServiceImpl implements SysLogService {
	//当我们使用了lombok插件以后,在类上使用了
	//@Slf4j注解,此时下面的日志对象创建可省略
	//底层自动帮我们生成如下语句.
	//private static final Logger log=
	//LoggerFactory.getLogger(SysLogServiceImpl.class);
	//Spring 按属性类型查找bean,然后进行DI
	@Autowired 
	//@Qualifier("sysLogDao")
	private SysLogDao sysLogDao;
	@Override
	public PageObject<SysLog> findPageObjects(
		String username, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("页码不正确");
		//2.查询总记录数并进行校验
		int rowCount=sysLogDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("记录不存在");
		//3.查询当前页要呈现的记录
		//3.1页面大小,例如每页最多显示3条
		int pageSize=PageUtil.getPageSize();
		//3.2当前页起始位置
		int startIndex=PageUtil.getStartIndex(pageCurrent);
		List<SysLog> records=
		sysLogDao.findPageObjects(username,
				startIndex,pageSize);
		//4.对查询结果进行计算和封装并返回
		return PageUtil.newPageObject(
	    pageCurrent, rowCount, pageSize, records);
	}
    @RequiresPermissions("sys:log:delete")
	@Override
	public int deleteObjects(Integer... ids) {
		//1.参数校验(参数校验)
		//2.执行删除
		int rows=sysLogDao.deleteObjects(ids);
		if(rows>0) {
		log.info("delete ok,rows="+rows);
		}
		//3.验证并返回结果
		return rows;
	}
}
