/**
 * 
 */
package com.mnzn.ecode.domain;

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
 * @author cxy
 */

public class BoxWashCount implements Serializable{
	
	/**
	 * 
	 */
	private String agencyid;
	private String merchname;
	private String companyid;
	private String deviceno;
	private String date;
	private String dt;
	private String kx;
	private String bz;
	private String dw;
	private String devicetype;
	private String money;
	private String address;
	
	public String getAgencyid() {
		return agencyid;
	}
	public void setAgencyid(String agencyid) {
		this.agencyid = agencyid;
	}
	public String getMerchname() {
		return merchname;
	}
	public void setMerchname(String merchname) {
		this.merchname = merchname;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getDeviceno() {
		return deviceno;
	}
	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getKx() {
		return kx;
	}
	public void setKx(String kx) {
		this.kx = kx;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
