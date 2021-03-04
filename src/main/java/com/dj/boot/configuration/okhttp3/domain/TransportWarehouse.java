package com.dj.boot.configuration.okhttp3.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 下发库房配置
 * @author wj
 */
public class TransportWarehouse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 合作伙伴id
	 */
	private Long partnerId;
	
	/**
	 * 库房类型
	 */
	private Integer warehouseType;
	
	/**
	 * 业务类型
	 */
	private Integer bizType;
	
	/**
	 * 模板地址
	 */
	private String templateFile;
	
	/**
	 * 服务地址
	 */
	private String serviceUri;
	
	/**
	 * 附加参数(json)
	 */
	private String extraMsg;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 更新人
	 */
	private String updateUser;
	
	/**
	 * 删除标志
	 */
	private Integer yn;
	
	/**
	 * 预留字段1
	 */
	private String reserve1;
	
	/**
	 * 预留字段2
	 */
	private String reserve2;
	
	/**
	 * 是否为测试数据
	 */
	private Integer test;


	/**
	 * lsb 通道 无：clps  , clps_other
	 */

	private String lsbChannel;


	/**
	 * 业务类型描述
	 * @return
	 */

	private  String bizTypeDesc;

	/**
	 *配置是否执行 2舍弃
	 */
	private Integer execute;

	public String getLsbChannel() {
		return lsbChannel;
	}

	public void setLsbChannel(String lsbChannel) {
		this.lsbChannel = lsbChannel;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the partnerId
	 */
	public Long getPartnerId() {
		return partnerId;
	}
	
	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	
	/**
	 * @return the warehouseType
	 */
	public Integer getWarehouseType() {
		return warehouseType;
	}
	
	/**
	 * @param warehouseType the warehouseType to set
	 */
	public void setWarehouseType(Integer warehouseType) {
		this.warehouseType = warehouseType;
	}
	
	/**
	 * @return the bizType
	 */
	public Integer getBizType() {
		return bizType;
	}
	
	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	
	/**
	 * @return the templateFile
	 */
	public String getTemplateFile() {
		return templateFile;
	}
	
	/**
	 * @param templateFile the templateFile to set
	 */
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	
	/**
	 * @return the serviceUri
	 */
	public String getServiceUri() {
		return serviceUri;
	}
	
	/**
	 * @param serviceUri the serviceUri to set
	 */
	public void setServiceUri(String serviceUri) {
		this.serviceUri = serviceUri;
	}
	
	/**
	 * @return the extraMsg
	 */
	public String getExtraMsg() {
		return extraMsg;
	}
	
	/**
	 * @param extraMsg the extraMsg to set
	 */
	public void setExtraMsg(String extraMsg) {
		this.extraMsg = extraMsg;
	}
	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	
	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	/**
	 * @return the yn
	 */
	public Integer getYn() {
		return yn;
	}
	
	/**
	 * @param yn the yn to set
	 */
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	
	/**
	 * @return the reserve1
	 */
	public String getReserve1() {
		return reserve1;
	}
	
	/**
	 * @param reserve1 the reserve1 to set
	 */
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	
	/**
	 * @return the reserve2
	 */
	public String getReserve2() {
		return reserve2;
	}
	
	/**
	 * @param reserve2 the reserve2 to set
	 */
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	
	/**
	 * @return the test
	 */
	public Integer getTest() {
		return test;
	}
	
	/**
	 * @param test the test to set
	 */
	public void setTest(Integer test) {
		this.test = test;
	}


	public String getBizTypeDesc() {
		return bizTypeDesc;
	}

	public void setBizTypeDesc(String bizTypeDesc) {
		this.bizTypeDesc = bizTypeDesc;
	}

	public Integer getExecute() {
		return execute;
	}

	public void setExecute(Integer execute) {
		this.execute = execute;
	}
}