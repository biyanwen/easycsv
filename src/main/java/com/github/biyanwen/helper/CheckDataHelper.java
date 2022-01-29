package com.github.biyanwen.helper;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.biyanwen.annotation.CsvProperty;

import java.lang.reflect.Field;

/**
 * 检查数据助手
 *
 * @Author byw
 * @Date 2022/1/29 17:12
 */
public class CheckDataHelper {

	/**
	 * 检查是否使用索引
	 *
	 * @param tClass 类
	 * @return boolean
	 */
	public static boolean checkIfUseIndex(Class tClass) {
		for (Field field : ReflectUtil.getFields(tClass)) {
			CsvProperty annotation = field.getAnnotation(CsvProperty.class);
			if (annotation != null && annotation.index() != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查是否使用名字
	 *
	 * @param tClass t类
	 * @return boolean
	 */
	public static boolean checkIfUseName(Class tClass) {
		for (Field field : ReflectUtil.getFields(tClass)) {
			CsvProperty annotation = field.getAnnotation(CsvProperty.class);
			if (annotation != null && !StrUtil.isBlank(annotation.name())) {
				return true;
			}
		}
		return false;
	}
}
