package com.github.biyanwen.api;

import java.util.List;

/**
 * @Author byw
 * @Date 2022/1/7 10:48
 */
public interface InputObjParser {

	/**
	 * 解析
	 *
	 * @param objects 对象
	 * @return {@link List}<{@link String}>
	 */
	List<String> doParse(List<Object> objects);
}
