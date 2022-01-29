package com.github.biyanwen.api;

import java.util.List;

/**
 * @Author byw
 * @Date 2022/1/7 10:48
 */
public interface ObjParser {

	/**
	 * 解析
	 *
	 * @param objects 对象
	 * @param csvWriteContext
	 * @return {@link List}<{@link String}>
	 */
	String doParse(List<Object> objects, CsvWriteContext csvWriteContext);
}
