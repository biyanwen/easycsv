package com.github.biyanwen.api;

/**
 * csv上下文
 *
 * @Author byw
 * @Date 2022/1/26 19:34
 */
public interface CsvContext {

	/**
	 * 设置字符编码
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
	 * 设置类
	 *
	 * @param clazz clazz
	 */
	void setClass(Class clazz);

	/**
	 * 得到类
	 *
	 * @return {@link Class}
	 */
	Class getClazz();

	/**
	 * 设置路径
	 *
	 * @param path 路径
	 */
	void setPath(String path);

	/**
	 * 获取路径
	 *
	 * @return {@link String}
	 */
	String getPath();

	/**
	 * 跳过多少行
	 *
	 * @param num 行数
	 */
	CsvContext skip(int num);

	/**
	 * 跳过多少行
	 *
	 * @return int
	 */
	int getSkip();

	/**
	 * 做阅读
	 */
	void doRead();
}
