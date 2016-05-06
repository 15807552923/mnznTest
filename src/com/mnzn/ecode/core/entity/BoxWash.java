/**
 * 
 */
package com.mnzn.ecode.core.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Administrator
 */

@Entity
@Table(name = "box_wash")
public class BoxWash implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6906935425448361233L;
	
	private BigInteger localId;
	private String deviceNo;
	private String userMobile;
	private String passwd;
	private Float price;
	private String washType;
	private Date insertTime;
	private String status;
	private BigInteger companyId;
	private BigInteger reLocalId;
	private String reName;
	private Date reTime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Column(name = "LOCALID") 
	public BigInteger getLocalId() {
		return localId;
	}
	public void setLocalId(BigInteger localId) {
		this.localId = localId;
	}
	
	@Column(name="DEVICENO")
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	@Column(name="USERMOBILE")
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	@Column(name="PASSWD")
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@Column(name="PRICE")
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	@Column(name="WASHTYPE")
	public String getWashType() {
		return washType;
	}
	public void setWashType(String washType) {
		this.washType = washType;
	}
	
	@Column(name="INSERTTIME")
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="COMPANYID")
	public BigInteger getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigInteger companyId) {
		this.companyId = companyId;
	}
	
	@Column(name="bw_re_id")
	public BigInteger getReLocalId() {
		return reLocalId;
	}
	public void setReLocalId(BigInteger reLocalId) {
		this.reLocalId = reLocalId;
	}
	
	@Column(name="bw_re_name")
	public String getReName() {
		return reName;
	}
	public void setReName(String reName) {
		this.reName = reName;
	}
	@Column(name="bw_re_time")
	public Date getReTime() {
		return reTime;
	}
	public void setReTime(Date reTime) {
		this.reTime = reTime;
	}
	
	
	
}
