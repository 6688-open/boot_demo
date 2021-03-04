package com.dj.boot.pojo.pk;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * ID序列生成
 * @author ext.wangjia
 * @Date 2014-07-24 下午 14:20:39
 */
@Data
@Accessors(chain = true)
@TableName("sequence")
@AllArgsConstructor
@NoArgsConstructor
public class Sequence {

	/**
	 * 表名
	 */
	private String tname;
	
	/**
	 * id
	 */
	private long id;

	
	
	
}
