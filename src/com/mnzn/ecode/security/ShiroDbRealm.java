/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.mnzn.ecode.security;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.Encodes;

import com.google.common.base.Objects;
import com.mnzn.ecode.core.entity.User;

public class ShiroDbRealm extends AuthorizingRealm {

	protected AccountService accountService;
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = accountService.findMobileUser(token.getUsername());
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			return new SimpleAuthenticationInfo(new ShiroUser(user.getLocalid().longValue(), user.getMobile(), user.getName(),user.getUsertype()),
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} 
		
	   return null;
	
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = accountService.findMobileUser(shiroUser.userName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		if (user != null ) {
			Set<String> perms = user.getPerms();
			if (!CollectionUtils.isEmpty(perms)) {
				info.setStringPermissions(perms);
			}
			if (user.isSuper()) {
				info.addRole("super");
			}
		}
		//info.addRoles(user.getRoleList());
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AccountService.HASH_ALGORITHM);
		matcher.setHashIterations(AccountService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}



	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long localid;
		public String userName;
		public String name;
		public String usertype;

		public ShiroUser(Long localid, String userName, String name, String usertype) {
			this.localid = localid;
			this.userName = userName;
			this.name = name;
			this.usertype = usertype;
		}

		public String getName() {
			return name;
		}

		
		
		public Long getLocalid() {
			return localid;
		}


		public String getUserName() {
			return userName;
		}


		public String getUsertype(){
			return usertype;
		}
		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return userName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(userName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (userName == null) {
				if (other.userName != null) {
					return false;
				}
			} else if (!userName.equals(other.userName)) {
				return false;
			}
			return true;
		}
	}
}
