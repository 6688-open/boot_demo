///*
// *
// */
//package com.dj.boot.manager.impl;
//
//import com.jd.clps.common.exception.ClpsSystemException;
//import com.jd.coo.sa.sequence.HighAvailableSequenceGen;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
///**
// * 基于MYSQL的主键管理器实现类
//
// * @Date 2016-10-26 下午8:19:06
// */
//public class MysqlSequenceManagerImpl extends com.jd.clps.btp.pk.manager.impl.AbstractSequenceManagerImpl {
//
//	private static final Logger log = LogManager.getLogger(com.jd.clps.btp.pk.manager.impl.MysqlSequenceManagerImpl.class);
//
//	// 默认最大值(4台MYSQL最大支持200亿)
//	private final long DEFAULT_MAXIMUM_ID = 20000000000L;
//
//	/**
//	 * 高可用主鍵器，注意当前使用的是MYSQL的解决方案以及SPRING配置文件
//	 */
//	private HighAvailableSequenceGen highAvailableSequenceGen;
//
//	/**
//	 * 获取当前ID序列值
//	 *
//	 * @param tableName
//	 * @return
//	 */
//	protected long getId(String tableName) {
//		if (log.isInfoEnabled()) {
//			log.info(String.format("使用基于MYSQL的高可用主键生成器, tableName:%s", tableName));
//		}
//		long id = highAvailableSequenceGen.gen(tableName);
//		if (id > this.DEFAULT_MAXIMUM_ID) {
//			throw new ClpsSystemException("危险!危险!危险！已经超过最大主键上限200亿!");
//		}
//		if (log.isInfoEnabled()) {
//			log.info(String.format("使用基于MYSQL的高可用主键生成器, tableName:%s, 生成主键值:%s", tableName, id));
//		}
//		return id;
//	}
//
//	/**
//	 * @param highAvailableSequenceGen
//	 *            the highAvailableSequenceGen to set
//	 */
//	public void setHighAvailableSequenceGen(HighAvailableSequenceGen highAvailableSequenceGen) {
//		this.highAvailableSequenceGen = highAvailableSequenceGen;
//	}
//}
