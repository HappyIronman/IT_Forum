package com.ironman.forum.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @Author: Ironman
 * @Description:
 * @Date: Created in 11:29 2017/12/31 0031
 **/
public class RegexHashMap<K, V> extends HashMap {
	private static Log logger = LogFactory.getLog(RegexHashMap.class);

	@Override
	public V get(Object key){
		Object value = super.get(key);
		if(value != null){
			return (V) value;
		} else {
			Set keySet = this.keySet();
			for (Object k : keySet) {
				Pattern pattern = Pattern.compile(k.toString());
				if(pattern.matcher(key.toString()).matches()){
					logger.info(key.toString() + " Matches --> " + k.toString());
					return (V) super.get(k);
				}
			}
		}
		return null;
	}

	@Override
	public boolean containsKey(Object key){
		if(super.containsKey(key)){
			return true;
		}
		Set keySet = this.keySet();
		for (Object k : keySet) {
			Pattern pattern = Pattern.compile(k.toString());
			if(pattern.matcher(key.toString()).matches()){
				return true;
			}
		}
		return false;
	}
}
