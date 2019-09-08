package com.cy.pj.sys.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.PageUtil;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;
@Transactional(timeout = 30,
               readOnly = false,
               isolation = Isolation.READ_COMMITTED,
               rollbackFor = Throwable.class,
               propagation = Propagation.REQUIRED)
@Service 
public class SysUserServiceImpl implements SysUserService {
	@Autowired 
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public int updateObject(SysUser entity,Integer[] roleIds) {
		//1.参数有效性验证
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("必须为其指定角色");
		//其它验证自己实现，例如用户名已经存在，密码长度，...
		//2.更新用户自身信息
		int rows=sysUserDao.updateObject(entity);
		//3.保存用户与角色关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(),
				roleIds);
	
		//4.返回结果
		return rows;
	}	

	//readOnly表示大家可以并发访问数据
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> findObjectById(
			Integer userId) {
		//1.合法性验证
		if(userId==null||userId<=0)
		throw new ServiceException("参数数据不合法,userId="+userId);
		//2.业务查询
		SysUserDeptVo user=
		sysUserDao.findObjectById(userId);
		if(user==null)
		throw new ServiceException("此用户已经不存在");
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(userId);
		//3.数据封装
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	//@Transactional //TransactionInterceptor,DataSourceTransactionManager
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		if(entity==null)
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new ServiceException("密码不能为空");
		if(roleIds==null || roleIds.length==0)
			throw new ServiceException("至少要为用户分配角色");
		//2.对密码进行加密
		String salt=UUID.randomUUID().toString();
		SimpleHash sh=
				new SimpleHash("MD5", //algorithmName算法
						entity.getPassword(),//source 未加密的密码
						salt,//salt
						1);//hashIterations加密次数
		entity.setSalt(salt);
		entity.setPassword(sh.toHex());
		//3.将对象写入到数据库
		int rows=sysUserDao.insertObject(entity);
		int count=sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//if(count>0)
	    //throw new ServiceException("关系数据保存失败");
		return rows;
	}
    /**
           * 当方法上@RequiresPermissions注解时
           * 此方法需要进行权限控制(授权以后才可以访问)
     */
	@RequiresPermissions("sys:user:valid")
	@RequiredLog("valid")
	@Override
	public int validById(Integer id, 
			Integer valid, 
			String modifiedUser) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("id值不正确");
		if(valid!=1&&valid!=0)
			throw new IllegalArgumentException("状态值不正确");
		//2.修改状态
		int rows=sysUserDao.validById(id, valid, modifiedUser);
		//3.返回结果
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	@RequiredLog("query user")
	@Transactional(readOnly = false)
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(
			String username, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码不正确");
		//2.查询总记录数并进行校验
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//3.查询当前页要呈现的记录
		//3.1页面大小,例如每页最多显示3条
		int pageSize=PageUtil.getPageSize();
		//3.2当前页起始位置
		int startIndex=PageUtil.getStartIndex(pageCurrent);
		List<SysUserDeptVo> records=
				sysUserDao.findPageObjects(username,
						startIndex,pageSize);
		//4.对查询结果进行计算和封装并返回
		return PageUtil.newPageObject(
				pageCurrent, rowCount, pageSize, records);
	}

}
