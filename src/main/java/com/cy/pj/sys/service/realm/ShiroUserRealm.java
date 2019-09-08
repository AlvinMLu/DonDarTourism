package com.cy.pj.sys.service.realm;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
/**借助此realm完成认证和授权信息的获取及封装*/
@Service
public class ShiroUserRealm extends AuthorizingRealm{
	@Autowired
	private SysUserDao sysUserDao;
	/**设置凭证匹配器*/
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		//构建凭证匹配对象
		HashedCredentialsMatcher cMatcher=
				new HashedCredentialsMatcher();
		//设置加密算法
		cMatcher.setHashAlgorithmName("MD5");
		//设置加密次数
		cMatcher.setHashIterations(1);
		super.setCredentialsMatcher(cMatcher);
	}
	/**负责认证信息的获取及封装*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//1.从token获取用户信息
		UsernamePasswordToken upToken=
		(UsernamePasswordToken)token;
		String username=upToken.getUsername();
		//2.基于用户名查询用户信息
		SysUser user=
		sysUserDao.findUserByUserName(username);
		//3.校验用户信息(用户是否存在,是否禁用)
		if(user==null)
		throw new UnknownAccountException();
		if(user.getValid()==0)
		throw new LockedAccountException();
		//4.封装数据并返回
		ByteSource credentialsSalt=
		ByteSource.Util.bytes(user.getSalt());
		
		SimpleAuthenticationInfo info=
		new SimpleAuthenticationInfo(
				user,//principal 身份 
				user.getPassword(),//hashedCredentials 已加密密码 
				credentialsSalt,//credentialsSalt 加密盐
				getName());//realmName
		return info;//返回给认证管理器
	}
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
    /**此方法负责授权信息的获取及封装*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
		 PrincipalCollection principals) {
		//1.获取用户id
		SysUser sysUser=(SysUser)
		principals.getPrimaryPrincipal();
		//2.基于用户id查询角色id
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(sysUser.getId());
		if(roleIds==null||roleIds.isEmpty())
		throw new AuthorizationException();
		//3.基于角色id获取菜单id
		Integer[] array={};
		List<Integer> menuIds=
		sysRoleMenuDao.findMenuIdsByRoleIds(
		roleIds.toArray(array));
		if(menuIds==null||menuIds.isEmpty())
		throw new AuthorizationException();
		//4.基于菜单id获取权限标识
		List<String> permissionsList=
		sysMenuDao.findPermissions(
		menuIds.toArray(array));
		if(permissionsList==null||permissionsList.isEmpty())
		throw new AuthorizationException();
		//5.封装数据并返回
		SimpleAuthorizationInfo info=
		new SimpleAuthorizationInfo();
		Set<String> permisssionSet=new HashSet<>();
		for(String permisssion:permissionsList) {
			 if(!StringUtils.isEmpty(permisssion)) {
				 permisssionSet.add(permisssion);
			 } 
		}
		System.out.println(permisssionSet);
		info.setStringPermissions(permisssionSet);
		return info;
	}

}








