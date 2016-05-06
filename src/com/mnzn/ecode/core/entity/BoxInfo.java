/**
 * 
 */
package com.mnzn.ecode.core.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

/**
 * @author Administrator
 */

@Entity
@Table(name = "box_info")
public class BoxInfo {

	private Long companyid;
	private String deviceno;
	private String status;
	private String password;
	private Integer location;
	private Date insertTime;
	private Date updateTime;
	private String address;
	private Float price_601; //单脱价格
	private Float price_602; //快洗价格
	private Float price_603; //标准洗价格
	private Float price_604; //大物洗价格
	private String devicetype;
	
	private Map<String, String> customs = new HashMap<String, String>(0);
	
	
	@ElementCollection
	@CollectionTable(name = "box_info_custom", joinColumns = @JoinColumn(name = "DEVICENO"))
	@MapKeyColumn(name = "f_key", length = 50)
	@Column(name = "f_value", length = 2000)
	public Map<String, String> getCustoms() {
		return this.customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}
	
	
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Column(name="LOCATION")
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	@Column(name="INSERTTIME")
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	@Column(name="UPDATETIME")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="COMPANYID")
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	
	@Id
	@Column(name="DEVICENO")
	public String getDeviceno() {
		return deviceno;
	}
	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}
	
	@Column(name="ADDRESS")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="PRICE_601")
	public Float getPrice_601() {
		return price_601;
	}
	public void setPrice_601(Float price_601) {
		this.price_601 = price_601;
	}
	@Column(name="PRICE_602")
	public Float getPrice_602() {
		return price_602;
	}
	public void setPrice_602(Float price_602) {
		this.price_602 = price_602;
	}
	@Column(name="PRICE_603")
	public Float getPrice_603() {
		return price_603;
	}
	public void setPrice_603(Float price_603) {
		this.price_603 = price_603;
	}
	
	@Column(name="PRICE_604")
	public Float getPrice_604() {
		return price_604;
	}
	public void setPrice_604(Float price_604) {
		this.price_604 = price_604;
	}
	
	@Column(name="DEVICETYPE")
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
}
