package com.mnzn.ecode.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * Role
 * 
 * @author cxy
 * 
 */
@Entity
@Table(name = "com_role")
public class Role implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 所有信息权限
	 */
	public static final int INFO_PERM_ALL = 1;
	/**
	 * 组织信息权限
	 */
	public static final int INFO_PERM_ORG = 2;
	/**
	 * 自身信息权限
	 */
	public static final int INFO_PERM_SELF = 3;

	

	@Transient
	public Set<User> getUsers() {
		Set<UserRole> userRoles = getUserRoles();
		if (userRoles == null) {
			return null;
		}
		Set<User> users = new HashSet<User>(userRoles.size());
		for (UserRole userRole : userRoles) {
			users.add(userRole.getUser());
		}
		return users;
	}

	// public void addUserRole(UserRole userRole) {
	// Set<UserRole> userRoles = getUserRoles();
	// if (userRoles == null) {
	// userRoles = new HashSet<UserRole>();
	// setUserRoles(userRoles);
	// }
	// userRoles.add(userRole);
	// }

	@Transient
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
		if (getAllPerm() == null) {
			setAllPerm(true);
		}
	}

	private Integer id;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	private Site site;

	private String name;
	private String description;
	private Integer seq;
	private String perms;
	private Boolean allPerm;

	@Id
	@Column(name = "f_role_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_com_role", pkColumnValue = "com_role", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_com_role")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	@JSONField(serialize=false)
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	




	@Column(name = "f_name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "f_seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Lob
	@Column(name = "f_perms")
	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	@Column(name = "f_is_all_perm", nullable = false, length = 1)
	public Boolean getAllPerm() {
		return allPerm;
	}

	public void setAllPerm(Boolean allPerm) {
		this.allPerm = allPerm;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_site_id", nullable = false)
	@JSONField(serialize=false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	

}
