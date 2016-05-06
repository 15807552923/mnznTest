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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * @author Administrator
 */

@Entity
@Table(name = "box_stat_bill")
public class BillStat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private BigInteger localId;
	private String agencyId;
	private User user;
	private String agencyName;
	private String merchName;
	private String periodStart;
	private String periodEnd;
	private String billDate;
	private Float money;
	private Float billMoney;
	private Integer time;
	private String status;
	private Date insertTime;
	
	
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
	
	@Column(name="AGENCYID")
	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}
	
	@OneToOne
	@JoinColumn(name = "COMPANYID", nullable = true)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@Column(name="AGENCYNAME")
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	
	
	@Column(name="MERCHNAME")
	public String getMerchName() {
		return merchName;
	}
	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}
	
	@Column(name="PERIOD_START")
	public String getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(String periodStart) {
		this.periodStart = periodStart;
	}
	
	@Column(name="PERIOD_END")
	public String getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}
	
	@Column(name="BILLDATE")
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	@Column(name="MONEY")
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	@Column(name="BILLMONEY")
	public Float getBillMoney() {
		return billMoney;
	}
	public void setBillMoney(Float billMoney) {
		this.billMoney = billMoney;
	}
	@Column(name="TIMES")
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="INSERTTIME")
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
}
