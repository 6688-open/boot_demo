/*
 * 
 */
package com.dj.boot.service.pk;

/**
 * 
 * @author ext.wangjia
 * @Date 2014-8-14 上午10:22:27
 */
public class IdProcessor {
	
	/**
	 * 
	 * @param sellerPart
	 * @param orderPart
	 * @return
	 */
	public static long mergeId(long sellerPart, long orderPart) {
		long result = (sellerPart << 42) | orderPart;
		return result;
	}
	
	/**
	 * 
	 * @param mergedId
	 * @return
	 */
	public static long parseSellerPart(long mergedId) {
		long orderPart = mergedId >> 42;
		return orderPart;
	}
	
	/**
	 * 
	 * @param mergedId
	 * @return
	 */
	public static long parseOrderPart(long mergedId) {
		long t = ~(63L << 42);
		long result = mergedId & t;
		
		return result;
	}

}
