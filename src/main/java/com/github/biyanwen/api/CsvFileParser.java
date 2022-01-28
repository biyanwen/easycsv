package com.github.biyanwen.api;

/**
 * 输出文件解析器
 *
 * @Author byw
 * @Date 2022/1/6 20:08
 */
public interface CsvFileParser {

	/**
	 * 解析
	 *
	 * @param csvContext csv上下文
	 */
	void doParse(CsvContext csvContext);
}
