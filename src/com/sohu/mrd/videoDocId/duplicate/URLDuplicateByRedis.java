package com.sohu.mrd.videoDocId.duplicate;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sohu.mrd.videoDocId.constant.Constant;
import com.sohu.mrd.videoDocId.constant.HBaseConstant;
import com.sohu.mrd.videoDocId.constant.RedisConstant;
import com.sohu.mrd.videoDocId.redis.RedisCrud;
import com.sohu.mrd.videoDocId.utils.MD5Utils;
/**
 * @author  Jin Guopan
 * @version 2016-11-22
 */
public class URLDuplicateByRedis {
	public static final Logger LOG=Logger.getLogger(URLDuplicateByRedis.class);
	public static String duplicatebyURL(String url)
	{
		String flag=Constant.DUPLICATE_FLAG;
		String rowKey=MD5Utils.getMD5(url);
		String value=RedisCrud.get(RedisConstant.KEY_PREFIX_VIDEO_URL_INDEX_TABLE+rowKey);
		if(value!=null)
		{
			String[] values=value.split("\t", -1);
			String docId = values[0];
			String storageTime=values[1];
			String valueUrl=values[2];
		    flag = docId;
		    LOG.info("redis 通过url 排重成功"+"docId "+docId+"原始 url "+valueUrl+"现在url "+url);
		}
		return flag;
	}
}
