package com.github.biyanwen.impl;

import cn.hutool.core.util.ReflectUtil;
import com.github.biyanwen.annotation.InputField;
import com.github.biyanwen.api.InputObjParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author byw
 * @Date 2022/1/7 10:49
 */
public class DefaultInputObjParser implements InputObjParser {
	private static final Logger LOGGER = LoggerFactory.getLogger(InputObjParser.class);

	@Override
	public List<String> doParse(List<Object> objects) {
		return objects.stream().parallel().map(t -> {
			Field[] fields = ReflectUtil.getFields(t.getClass());
			StringBuilder builder = new StringBuilder();
			appendData(fields, builder, t);
			int index = builder.lastIndexOf(",");
			builder.deleteCharAt(index);
			return builder.toString();
		}).collect(Collectors.toList());
	}

	/**
	 * 附加数据
	 *
	 * @param fields  字段
	 * @param builder 构建器
	 * @param obj     obj
	 */
	private void appendData(Field[] fields, StringBuilder builder, Object obj) {
		Arrays.stream(fields).filter(field -> field.getAnnotation(InputField.class) != null)
				.sorted(Comparator.comparingInt(f -> f.getAnnotation(InputField.class).order()))
				.forEach(field -> {
					try {
						field.setAccessible(true);
						Object value = field.get(obj);
						if (value instanceof Collection) {
							Collection<Object> collection = (Collection<Object>) value;
							List<String> strings = collection.stream().map(Object::toString).collect(Collectors.toList());
							builder.append(String.join(",", strings)).append(",");
						} else {
							builder.append(value).append(",");
						}
					} catch (IllegalAccessException e) {
						LOGGER.error("解析数据出现异常！");
						LOGGER.error(e.getMessage());
					}
				});
	}
}
