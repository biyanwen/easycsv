package com.github.biyanwen.helper;

import cn.hutool.core.util.ReflectUtil;
import com.github.biyanwen.annotation.CsvProperty;
import com.github.biyanwen.bean.None;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author byw
 * @Date 2022/1/11 14:34
 */
public class CsvParseHelper {

	@SneakyThrows
	public static <T> T getResult(String strLine, Class<T> tClass) {
		T obj = tClass.newInstance();
		List<Field> fieldList = Arrays.stream(ReflectUtil.getFields(tClass)).
				filter(t -> t.getAnnotation(CsvProperty.class) != null)
				.sorted(Comparator.comparingInt(t -> t.getAnnotation(CsvProperty.class).index()))
				.collect(Collectors.toList());

		String[] splitResult = strLine.split(",");
		assignValue(obj, fieldList, splitResult);
		return obj;
	}

	@SneakyThrows
	private static <T> void assignValue(T obj, List<Field> fieldList, String[] splitResult) {
		int offset = 0;
		for (Field t : fieldList) {
			t.setAccessible(true);
			CsvProperty csvProperty = t.getAnnotation(CsvProperty.class);
			int index = csvProperty.index() + offset;
			int size = csvProperty.size();
			if (size == 1) {
				String value = splitResult[index];
				t.set(obj, ConvertUtils.convert(value, t.getType()));
			} else {
				offset += size - 1;
				Class<?> clazz = csvProperty.clazz();
				if (clazz.equals(None.class)) {
					throw new RuntimeException("size > 1 时需要配置 @OutputField#clazz 方法");
				}
				List<String> strings = new ArrayList<>(Arrays.asList(splitResult).subList(index, index + size));
				List<Object> collect = strings.stream().map(str -> ConvertUtils.convert(str, clazz))
						.collect(Collectors.toList());
				t.set(obj, collect);
			}
		}
	}
/*

	private List<FieldWrapper> createByIndex() {

	}

*/
	@Data
	private static class FieldWrapper {

		private Field field;

		private int size;
	}
}
