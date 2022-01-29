package com.github.biyanwen.impl;

import cn.hutool.core.util.ReflectUtil;
import com.github.biyanwen.annotation.CsvProperty;
import com.github.biyanwen.api.CsvWriteContext;
import com.github.biyanwen.api.ObjParser;
import lombok.SneakyThrows;

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
public class DefaultObjParser implements ObjParser {

	@Override
	public String doParse(List<Object> objects, CsvWriteContext csvWriteContext) {
		List<Field> fields = null;
		StringBuffer buffer = new StringBuffer();

		if (csvWriteContext.writeByHead() && objects.size() > 0) {
			fields = Arrays.stream(ReflectUtil.getFields(objects.get(0).getClass()))
					.filter(field -> field.getAnnotation(CsvProperty.class) != null)
					.sorted(Comparator.comparingInt(f -> f.getAnnotation(CsvProperty.class).index()))
					.collect(Collectors.toList());
			appendHead(objects.get(0), fields, buffer);
		}
		List<Field> finalFields = fields;
		objects.parallelStream().forEach(t -> {
			appendData(finalFields, buffer, t);
			int index = buffer.lastIndexOf(",");
			buffer.deleteCharAt(index);
		});
		return buffer.toString();
	}

	@SneakyThrows
	private void appendHead(Object obj, List<Field> fields, StringBuffer builder) {
		for (Field field : fields) {
			field.setAccessible(true);
			String name = field.getAnnotation(CsvProperty.class).name();
			Object value = field.get(obj);
			if (value instanceof Collection) {
				Collection<Object> collection = (Collection<Object>) value;
				int size = collection.size();
				builder.append(name);
				for (int i = 1; i < size; i++) {
					builder.append(",");
				}
			} else {
				builder.append(name).append(",");
			}
		}
		builder.append(System.lineSeparator());
		int index = builder.lastIndexOf(",");
		builder.deleteCharAt(index);
	}

	/**
	 * 附加数据
	 *
	 * @param fields 字段
	 * @param buffer 构建器
	 * @param obj    obj
	 */
	private void appendData(List<Field> fields, StringBuffer buffer, Object obj) {
		fields.forEach(field -> {
			try {
				field.setAccessible(true);
				Object value = field.get(obj);
				if (value instanceof Collection) {
					Collection<Object> collection = (Collection<Object>) value;
					List<String> strings = collection.stream().map(Object::toString).collect(Collectors.toList());
					buffer.append(String.join(",", strings)).append(",");
				} else {
					buffer.append(value).append(",");
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		buffer.append(System.lineSeparator());
	}
}
