package com.dj.boot.manager.impl;

import com.dj.boot.manager.SequenceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * ID序列生成Manager实现
 * 
 * @author ext.wangjia
 * @Date 2014-07-24 下午 14:20:39
 */
public abstract class AbstractSequenceManagerImpl implements SequenceManager {

	private static final Logger log = LogManager.getLogger(AbstractSequenceManagerImpl.class);

	//批量生成ID最大数量
	public static final int BATCH_MAX_NUM=10000;

	/**
	 * 获取当前ID序列值
	 * 
	 * @param tableName
	 * @return
	 */
	@Override
	public long insertAndGetCurrentId(String tableName) {
		long currentId = 0;
		if (tableName != null && !tableName.equals("")) {
			try {
				currentId = getId(tableName);
			} catch (Exception e) {
				log.error("SequenceManagerImpl--->获取表" + tableName + "的主键出现异常:" + e);
				throw new RuntimeException("SequenceManagerImpl--->获取表" + tableName
						+ "的主键出现异常:" + e);
			}
		} else {
			log.error("SequenceManagerImpl--->表名不能为空值");
			throw new RuntimeException("SequenceManagerImpl--->表名不能为空值");
		}
		return currentId;
	}

	/**
	 * 批量获取ID序列值
	 * 
	 * @param tableName
	 * @param num
	 * @return
	 */
	@Override
	public List<String> insertAndGetIdLists(String tableName, int num) {
		List<String> idLists = new ArrayList<String>();
		int maxNum = BATCH_MAX_NUM;
		if (tableName != null && !tableName.equals("")) {
			if (num > maxNum) {
				log.error("SequenceManagerImpl--->批量生成ID的数量不能大于" + maxNum);
				throw new RuntimeException("SequenceManagerImpl--->批量生成ID的数量不能大于"
						+ maxNum);
			} else {
				try {
					for (int i = 0; i < num; i++) {
						idLists.add(String.valueOf(getId(tableName)));
					}
				} catch (Exception e) {
					log.error("com.jd.clps.btp.pk.manager.impl.SequenceManagerImpl--->批量获取表" + tableName + "的主键出现异常:"
							+ e);
					throw new RuntimeException("com.jd.clps.btp.pk.manager.impl.SequenceManagerImpl--->批量获取表"
							+ tableName + "的主键出现异常:" + e);
				}
			}
		} else {
			log.error("com.jd.clps.btp.pk.manager.impl.SequenceManagerImpl--->表名不能为空值");
			throw new RuntimeException("com.jd.clps.btp.pk.manager.impl.SequenceManagerImpl--->表名不能为空值");
		}
		return idLists;
	}

	/**
	 * 获取当前ID序列值
	 * 
	 * @param tableName
	 * @return
	 */
	protected abstract long getId(String tableName) throws Exception;

}
