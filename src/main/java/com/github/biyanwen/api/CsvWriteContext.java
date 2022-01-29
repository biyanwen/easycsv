package com.github.biyanwen.api;

import com.github.biyanwen.annotation.CsvProperty;

import java.util.List;

/**
 * csv写上下文
 *
 * @Author byw
 * @Date 2022/1/29 15:01
 */
public interface CsvWriteContext {

	/**
	 * 设置编码
	 *
	 * @param encoding 编码
	 */
	void setEncoding(String encoding);

	/**
	 * 得到编码
	 *
	 * @return {@link String}
	 */
	String getEncoding();

	/**
	 * 设置写类
	 *
	 * @param writeClass 写类
	 */
	void setWriteClass(Class writeClass);

	/**
	 * 获取写类
	 *
	 * @return {@link Class}
	 */
	Class getWriteClass();

	/**
	 * 设置文件路径
	 *
	 * @param filePath 文件路径
	 */
	void setFilePath(String filePath);

	/**
	 * 获取文件路径
	 *
	 * @return {@link String}
	 */
	String getFilePath();

	/**
	 * 有表头的写; 根据是否使用了 {@link CsvProperty#name()}自动判断
	 *
	 * @return boolean
	 */
	boolean writeByHead();

	/**
	 * 写
	 *
	 * @param objects 对象
	 */
	void doWrite(List objects);
}
