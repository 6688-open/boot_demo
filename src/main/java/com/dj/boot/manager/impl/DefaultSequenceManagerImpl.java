package com.dj.boot.manager.impl;

import com.dj.boot.dao.SequenceDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * ID序列生成Manager实现
 * 
 * @author ext.wangjia
 * @Date 2014-07-24 下午 14:20:39
 */
public class DefaultSequenceManagerImpl extends AbstractSequenceManagerImpl {

	private static final Logger log = LogManager.getLogger(DefaultSequenceManagerImpl.class);

	private SequenceDao sequenceDao;

	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}

	/**
	 * 获取当前ID序列值
	 * 
	 * @param tableName
	 * @return
	 */
	@Override
	protected long getId(String tableName) throws Exception {
		if (log.isInfoEnabled()) {
			log.info(String.format("使用默认主键生成器, tableName:%s", tableName));
		}
		long id = sequenceDao.insertAndGetCurrentId(tableName);
		if (id == 0) {
			int initResult = sequenceDao.insertAndInitTable(tableName);
			if (initResult > 0) {
				id = sequenceDao.insertAndGetCurrentId(tableName);
			} else {
				throw new Exception("初始化id生成器的tableName出错！");
			}
		}
		if (log.isInfoEnabled()) {
			log.info(String.format("使用默认主键生成器, tableName:%s, 生成主键值:%s", tableName, id));
		}
		return id;
	}
}
