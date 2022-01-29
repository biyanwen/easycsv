package com.github.biyanwen;

import cn.hutool.core.util.StrUtil;
import com.github.biyanwen.api.CsvReadContext;
import com.github.biyanwen.api.CsvFileParser;
import com.github.biyanwen.impl.DefaultCsvReadContext;

/**
 * 简单csv
 *
 * @Author byw
 * @Date 2022/1/26 17:38
 */
public class EasyCsv {
	public static CsvReadContext read(String filePath, Class clazz, CsvFileParser csvFileParser) {
		return read(filePath, clazz, csvFileParser, null);
	}

	/**
	 * 读
	 *
	 * @param clazz         clazz
	 * @param csvFileParser csv文件解析器
	 * @param encoding      编码
	 * @param filePath      文件路径
	 */
	public static CsvReadContext read(String filePath, Class clazz, CsvFileParser csvFileParser, String encoding) {
		encoding = StrUtil.isBlank(encoding) ? "GBK" : encoding;
		return DefaultCsvReadContext.createBuilder()
				.withPath(filePath)
				.withEncoding(encoding)
				.withTClass(clazz)
				.withCsvFileParser(csvFileParser)
				.build();
	}
}
