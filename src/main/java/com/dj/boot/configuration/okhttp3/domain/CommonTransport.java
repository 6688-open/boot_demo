package com.dj.boot.configuration.okhttp3.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * 统一下传实体
 * @author wj
 */
public class CommonTransport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 合作伙伴id
	 */
	private Long partnerId;

	/**
	 * 合作伙伴名称
	 */
	private String partnerName;
	
	/**
	 * 库房类型
	 */
	private Integer warehouseType;

	/**
	 * 业务类型
	 */
	private Integer bizType;

	/**
	 * 业务单号
	 */
	private String bizNo;
	
	/**
	 * vm模板地址
	 */
	private String templateFile;
	
	/**
	 * serivice请求地址
	 */
	private String serviceUri;
	
	/**
	 * 附加参数(json)
	 */
	private CommonExtraMsg extraMsg;
	
	/**
	 * 消息实体(json)
	 */
	private String bodyMsg;

	/**
	 * 任务头
	 */
	private Map<String,Object> header;
	/**
	 * 延迟时间 min
	 */
	private Integer delay;
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
	 * @return the partnerName
	 */
	public String getPartnerName() {
		return partnerName;
	}
	
	/**
	 * @param partnerName the partnerName to set
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	
	public String getBizNo() {
		return bizNo;
	}
	
	
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	
	
	public String getTemplateFile() {
		return templateFile;
	}
	

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	
	
	public String getserviceUri() {
		return serviceUri;
	}
	
	
	public void setserviceUri(String serviceUri) {
		this.serviceUri = serviceUri;
	}
	
	
	public CommonExtraMsg getExtraMsg() {
		return extraMsg;
	}
	
	
	public void setExtraMsg(CommonExtraMsg extraMsg) {
		this.extraMsg = extraMsg;
	}
	
	
	public String getBodyMsg() {
		return bodyMsg;
	}
	
	
	public void setBodyMsg(String bodyMsg) {
		this.bodyMsg = bodyMsg;
	}

	public Map<String, Object> getHeader() {
		return header;
	}

	public void setHeader(Map<String, Object> header) {
		this.header = header;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"partnerId\":")
				.append(partnerId);
		sb.append(",\"partnerName\":\"")
				.append(partnerName).append('\"');
		sb.append(",\"warehouseType\":")
				.append(warehouseType);
		sb.append(",\"bizType\":")
				.append(bizType);
		sb.append(",\"bizNo\":\"")
				.append(bizNo).append('\"');
		sb.append(",\"templateFile\":\"")
				.append(templateFile).append('\"');
		sb.append(",\"serviceUri\":\"")
				.append(serviceUri).append('\"');
		sb.append(",\"extraMsg\":")
				.append(extraMsg);
		sb.append(",\"bodyMsg\":\"")
				.append(bodyMsg).append('\"');
		sb.append(",\"header\":")
				.append(header);
		sb.append(",\"delay\":")
				.append(delay);
		sb.append('}');
		return sb.toString();
	}
}