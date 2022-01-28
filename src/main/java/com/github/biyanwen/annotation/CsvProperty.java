package com.github.biyanwen.annotation;

import com.github.biyanwen.bean.None;

import java.lang.annotation.*;

/**
 * @Author byw
 * @Date 2022/1/11 14:13
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CsvProperty {

	/**
	 * 索引, 从 0 开始
	 *
	 * @return int
	 */
	int index() default -1;

	/**
	 * 数组使用这个字段，可以快速收集数据到数组中，不用自己一个一个添加
	 *
	 * @return int
	 */
	int size() default 1;

	/**
	 * 表头名字
	 *
	 * @return {@link String}
	 */
	String name() default "";

	/**
	 * 类型，集合必须配置此属性，因为类型擦除无法拿到集合元素的类型
	 *
	 * @return {@link Class}<{@link ?}>
	 */
	Class<?> clazz() default None.class;


}
