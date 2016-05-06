package com.mnzn.ecode.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;



/**
 * @author Administrator
 */

@Entity
@Table(name = "com_mn_mnpay")
public class MNPay implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private Integer id;
	private String traceNo;
	private Float money;
	private String applyId;
	private String applyName;
	private String status;
	private Date mnPayInsertTime;
	private Date operationTime;
	private Long operaterId;
	private String operaterName;
	private String bankInfo;
	private String payResult;
	private String category;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Column(name = "t_id") 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@Column(name="t_traceno")
	public String getTraceNo() {
		return traceNo;
	}
	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}
	@Column(name = "t_money") 
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	
	
	@Column(name = "t_status") 
	public String getStatus() {
		return status;
	}
	
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="t_apply_id")
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	@Column(name="t_apply_name")
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	
	@Column(name="t_insert_time")
	public Date getMnPayInsertTime() {
		return mnPayInsertTime;
	}
	public void setMnPayInsertTime(Date mnPayInsertTime) {
		this.mnPayInsertTime = mnPayInsertTime;
	}
	@Column(name="t_operation_time")
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	
	@Column(name="t_operator_id")
	public Long getOperaterId() {
		return operaterId;
	}
	public void setOperaterId(Long operaterId) {
		this.operaterId = operaterId;
	}
	@Column(name="t_operator_name")
	public String getOperaterName() {
		return operaterName;
	}
	public void setOperaterName(String operaterName) {
		this.operaterName = operaterName;
	}
	
	@Column(name="t_bank_info")
	public String getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	
	@Column(name="t_pay_result")
	public String getPayResult() {
		return payResult;
	}
	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}
	
	@Column(name="t_category")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	

}
