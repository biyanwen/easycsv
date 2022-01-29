package com.github.biyanwen.factory;

import com.github.biyanwen.api.ObjParser;
import com.github.biyanwen.impl.DefaultObjParser;

/**
 * 输入obj解析器工厂
 *
 * @Author byw
 * @Date 2022/1/7 10:49
 */
public class ObjParserFactory {

	/**
	 * 获取默认解析器
	 *
	 * @return {@link ObjParser}
	 */
	public static ObjParser getDefaultParser() {
		return new DefaultObjParser();
	}
}
