///*
// *
// */
//package com.dj.boot.manager.impl;
//
//import com.jd.coo.sa.sequence.HighAvailableSequenceGen;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
///**
// * 基于JIMDB的主键管理器实现类
// *
// * @author 蔚伟
// * @author 周小皮
// * @Date 2016-10-26 下午8:19:06
// */
//public class JimdbSequenceManagerImpl extends com.jd.clps.btp.pk.manager.impl.AbstractSequenceManagerImpl {
//
//	private static final Logger log = LogManager.getLogger(com.jd.clps.btp.pk.manager.impl.JimdbSequenceManagerImpl.class);
//	/**
//	 * 高可用主鍵器，注意当前使用的是JIMDB的解决方案以及SPRING配置文件
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
//			log.info(String.format("使用基于JIMDB的高可用主键生成器, tableName:%s", tableName));
//		}
//		long id = highAvailableSequenceGen.gen(tableName);
//		if (log.isInfoEnabled()) {
//			log.info(String.format("使用基于JIMDB的高可用主键生成器, tableName:%s, 生成主键值:%s", tableName, id));
//		}
//		return id;
//	}
//
//	/**
//	 * @return the highAvailableSequenceGen
//	 */
//	public HighAvailableSequenceGen getHighAvailableSequenceGen() {
//		return highAvailableSequenceGen;
//	}
//
//	/**
//	 * @param highAvailableSequenceGen
//	 *            the highAvailableSequenceGen to set
//	 */
//	public void setHighAvailableSequenceGen(HighAvailableSequenceGen highAvailableSequenceGen) {
//		this.highAvailableSequenceGen = highAvailableSequenceGen;
//	}
//
//}
