package com.mnzn.ecode.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.mnzn.ecode.bm.constant.Constants;



@Entity
@Table(name = "box_admin")
public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public User(){
		super();
	}
	
	@Transient
	public Set<String> getPerms() {
		List<Role> roles = getRoles();
		if (roles == null) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		if (isRoot() && Constants.IS_ROOT_ALL_PERMS) {
			// root用户，拥有所有权限，直接返回
			set.add("*");
			return set;
		}
		for (Role role : roles) {
			if (role.getAllPerm()) {
				// 拥有所有权限，直接返回
				set.add("*");
				return set;
			}
		}
		for (Role role : roles) {
			String perms = role.getPerms();
			if (StringUtils.isNotBlank(perms)) {
				for (String perm : StringUtils.split(perms, ',')) {
					set.add(perm);
				}
			}
		}
		return set;
	}
	
	
	@Transient
	@JSONField(serialize=false)
	public List<Role> getRoles() {
		List<UserRole> userRoles = getUserRoles();
		if (userRoles == null) {
			return null;
		}
		List<Role> roles = new ArrayList<Role>(userRoles.size());
		Role role;
		for (UserRole userRole : userRoles) {
			role = userRole.getRole();
				roles.add(role);
		}
		return roles;
	}
	
	
	
	@Transient
	public boolean isRoot() {
		return getLocalid().equals(new Long(1));
	}
	
	@Transient
	public boolean isSuper() {
		return Integer.parseInt(getUsertype())< 1;
	}
	
	@Transient
	public void applyDefaultValue() {
		if (getRank() == null) {
			setRank(1);
		}
	}

	
	
	private	Long localid;
    private String mobile;//手机号
    private String name;//姓名
    private String address;//地址
    private String contcat;//联系人
    private String password;//密码
    private String usertype;//用户类别
    private String status;//状态
    private String paytype;//支付类别
    private String payname;//户名
    private String payaccount;//账号
    private String servicephone;//服务电话
    private String agencyid;//代理商ID
    private Date inserttime;//添加时间
    private Date updatetime;//更新时间
  
   /* private String boxcount;//模块数量
    private int mainbusi = 0;//主营业务
*/  
    private String bankname;
    private String contactnum;
    
    private List<UserRole> userRoles = new ArrayList<UserRole>(0);
	private List<UserOrg> userOrgs = new ArrayList<UserOrg>(0);
	private Org org;
    private String salt;
    private Integer rank;
  
    private Integer errorCount;
    private String loginIp;
    
    private String rawPassword;
    
    private float recharge;
    
    private Date lastLoginTime;
    
	public User(Long currentUserId) {
		// TODO Auto-generated constructor stub
		this.localid = currentUserId;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Column(name = "LOCALID") 
	public Long getLocalid() {
		return localid;
	}
	public void setLocalid(Long localid) {
		this.localid = localid;
	}
	
	@Column(name="MOBILE")
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="ADDRESS")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="CONTCAT")
	public String getContcat() {
		return contcat;
	}
	public void setContcat(String contcat) {
		this.contcat = contcat;
	}
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="USERTYPE")
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="PAYTYPE")
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	
	@Column(name="PAYNAME")
	public String getPayname() {
		return payname;
	}
	public void setPayname(String payname) {
		this.payname = payname;
	}
	
	@Column(name="PAYACCOUNT")
	public String getPayaccount() {
		return payaccount;
	}
	public void setPayaccount(String payaccount) {
		this.payaccount = payaccount;
	}
	
	@Column(name="SERVICEPHONE")
	public String getServicephone() {
		return servicephone;
	}
	public void setServicephone(String servicephone) {
		this.servicephone = servicephone;
	}
	
	@Column(name="AGENCYID")
	public String getAgencyid() {
		return agencyid;
	}
	public void setAgencyid(String agencyid) {
		this.agencyid = agencyid;
	}
	
	@Column(name="INSERTTIME")
	public Date getInserttime() {
		return inserttime;
	}
	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
	
	@Column(name="UPDATETIME")
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
	/*public String getBoxcount() {
		return boxcount;
	}
	public void setBoxcount(String boxcount) {
		this.boxcount = boxcount;
	}
	public int getMainbusi() {
		return mainbusi;
	}
	public void setMainbusi(int mainbusi) {
		this.mainbusi = mainbusi;
	}*/
	@Column(name="BANKNAME")
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	@Column(name="CONTACTNUM")
	public String getContactnum() {
		return contactnum;
	}
	public void setContactnum(String contactnum) {
		this.contactnum = contactnum;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "user")
	@OrderBy("roleIndex")
	@JSONField(serialize=false)
	public List<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "user")
	@OrderBy("orgIndex")
	public List<UserOrg> getUserOrgs() {
		return userOrgs;
	}
	public void setUserOrgs(List<UserOrg> userOrgs) {
		this.userOrgs = userOrgs;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_org_id", nullable = false)
	@JSONField(serialize=false)
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	@Column(name="f_salt")
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	@Column(name="f_rank")
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Length(max = 255)
	@Transient
	public String getRawPassword() {
		return rawPassword;
	}

	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}

	@Column(name="error_count")
	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	@Column(name="login_ip")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name="recharge")
	public float getRecharge() {
		return recharge;
	}

	public void setRecharge(float recharge) {
		this.recharge = recharge;
	}

	@Column(name="last_login_time")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
    

	
}