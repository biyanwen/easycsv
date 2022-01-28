package com.github.biyanwen.factory;

import com.github.biyanwen.api.InputObjParser;
import com.github.biyanwen.impl.DefaultInputObjParser;

/**
 * 输入obj解析器工厂
 *
 * @Author byw
 * @Date 2022/1/7 10:49
 */
public class InputObjParserFactory {

	/**
	 * 获取默认解析器
	 *
	 * @return {@link InputObjParser}
	 */
	public static InputObjParser getDefaultParser() {
		return new DefaultInputObjParser();
	}
}
