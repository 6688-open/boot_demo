package com.dj.boot.manager;

import java.util.List;

/**
 * ID序列生成Manager接口
 * @author ext.wangjia
 */
public interface SequenceManager {

	/**
	 * 获取当前ID序列值
	 * @param tableName
	 * @return 
	 */
	public long insertAndGetCurrentId(String tableName);
	
	/**
	 * 批量获取ID序列值
	 * @param tableName
	 * @param num
	 * @return 
	 */
	public List<String> insertAndGetIdLists(String tableName,int num);
	
	
	
}
