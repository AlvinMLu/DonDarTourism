package com.cy.pj.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.annotation.RequiredCache;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
	private SysMenuDao sysMenuDao;
    @Override
    public int saveObject(SysMenu entity) {
    	//1.参数校验
    	if(entity==null)
    	throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(entity.getName()))
    	throw new IllegalArgumentException("菜单名不能为空");
    	if(StringUtils.isEmpty(entity.getPermission()))
    	throw new IllegalArgumentException("权限标识不能为空");
    	//2.持久化数据
    	int rows=sysMenuDao.insertObject(entity);
    	//3.返回结果
    	return rows;
    }
    @Override
    public int updateObject(SysMenu entity) {
    	//1.参数校验
    	if(entity==null)
    		throw new IllegalArgumentException("保存对象不能为空");
    	if(StringUtils.isEmpty(entity.getName()))
    		throw new IllegalArgumentException("菜单名不能为空");
    	if(StringUtils.isEmpty(entity.getPermission()))
    		throw new IllegalArgumentException("权限标识不能为空");
    	//2.持久化数据
    	int rows=sysMenuDao.updateObject(entity);
    	//3.返回结果
    	return rows;
    }
   
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list=
		sysMenuDao.findObjects();
		return list;
	}
	@Override
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}
}







