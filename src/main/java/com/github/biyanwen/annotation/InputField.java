package com.github.biyanwen.annotation;

import java.lang.annotation.*;

/**
 * 输入字段
 *
 * @Author byw
 * @Date 2022/1/7 11:58
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface InputField {

	/**
	 * 顺序 从 0 开始
	 *
	 * @return int
	 */
	int order();
}
