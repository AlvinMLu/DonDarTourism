package com.cy.pj.sys.controller;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	@RequestMapping("doUserEditUI")
	public String doUserEditUI() {
		return "sys/user_edit";
	}
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(
			String username,
			String password,
			boolean isRememberMe) {
		//1.封装用户信息
		UsernamePasswordToken token=
		new UsernamePasswordToken(username,password);
		//2.提交token对象(传递给SecurityManager)
		//2.1获取subject对象
		Subject subject=
		SecurityUtils.getSubject();
		//2.2执行登录认证
		if(isRememberMe) {//记住我
			token.setRememberMe(true);
		}
		subject.login(token);
		return new JsonResult("login ok");
	}
	
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(
			SysUser entity,
			Integer[] roleIds) {
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("insert ok");
	}

	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(
			Integer id,Integer valid) {
		//获取登录用户
		SysUser user=(SysUser)
		SecurityUtils.getSubject().getPrincipal();
		sysUserService.validById(id,
				valid,user.getUsername());
		return new JsonResult("update ok");
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(
			String username,
			Integer pageCurrent) {
		return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(
			Integer id){
		Map<String,Object> map=
				sysUserService.findObjectById(id);
		return new JsonResult(map);
	}

	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(
			SysUser entity,Integer[] roleIds){
		sysUserService.updateObject(entity,
				roleIds);
		return new JsonResult("update ok");
	}


}
