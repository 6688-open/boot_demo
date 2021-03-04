package com.dj.boot.dao;

/**
 * ID序列生成Dao层接口
 * @org clps.jd.com 
 * @author jiangzhaojing
 * @Date 2014-07-24 下午 14:20:39
 */
public interface SequenceDao {
	
	/**
	 * 新插入一条记录
	 * @param 表名
	 * @return
	 */
	public long insertAndGetCurrentId(String tableName);
	
	/**
	 * 初始化一个新表
	 * @param 表名
	 * @return
	 */
	public int insertAndInitTable(String tableName);
}
