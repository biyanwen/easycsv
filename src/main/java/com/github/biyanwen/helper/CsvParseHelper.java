package com.github.biyanwen.helper;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.biyanwen.annotation.CsvProperty;
import com.github.biyanwen.bean.None;
import com.github.biyanwen.exception.CsvParseException;
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
	public static <T> T getResult(String strLine, Class<T> tClass, String head, boolean useHead) {
		T obj = tClass.newInstance();
		List<FieldWrapper> wrapperList;
		List<Field> fields = Arrays.asList(ReflectUtil.getFields(tClass)).stream()
				.filter(t -> t.getAnnotation(CsvProperty.class) != null)
				.collect(Collectors.toList());
		if (useHead) {
			wrapperList = createByHead(head, fields);
		} else {
			wrapperList = createByIndex(fields);
		}
		String[] splitResult = strLine.split(",");
		assignValue(obj, wrapperList, splitResult);
		return obj;
	}

	private static List<FieldWrapper> createByHead(String head, List<Field> fields) {
		List<String> headList = Arrays.asList(head.split(","));
		List<FieldWrapper> fieldWrappers = new ArrayList<>(headList.size());
		List<String> headListNoBlank = headList.stream().filter(StrUtil::isNotBlank).collect(Collectors.toList());
		for (Field field : fields) {
			String name = field.getAnnotation(CsvProperty.class).name();
			int index = headList.indexOf(name);
			if (index == -1) {
				throw new CsvParseException("未找到表头：" + name);
			}
			int size = getSize(headList, index);
			int relativeIndex = headListNoBlank.indexOf(name);
			FieldWrapper wrapper = new FieldWrapper();
			wrapper.setField(field);
			wrapper.setClazz(field.getAnnotation(CsvProperty.class).clazz());
			wrapper.setIndex(relativeIndex);
			wrapper.setSize(size);
			fieldWrappers.add(wrapper);
		}
		fieldWrappers = fieldWrappers.stream().sorted(Comparator.comparing(FieldWrapper::getIndex))
				.collect(Collectors.toList());
		return fieldWrappers;
	}

	private static int getSize(List<String> headList, int index) {
		int size = 1;
		for (int i = index + 1; i < headList.size(); i++) {
			if (!StrUtil.isBlank(headList.get(i))) {
				break;
			}
			size += 1;
		}
		return size;
	}

	@SneakyThrows
	private static <T> void assignValue(T obj, List<FieldWrapper> fieldWrappers, String[] splitResult) {
		int offset = 0;
		for (FieldWrapper t : fieldWrappers) {
			Field field = t.getField();
			field.setAccessible(true);
			int index = t.getIndex() + offset;
			int size = t.getSize();
			if (size == 1) {
				String value = splitResult[index];
				field.set(obj, ConvertUtils.convert(value, field.getType()));
			} else {
				offset += size - 1;
				Class<?> clazz = t.getClazz();
				if (clazz.equals(None.class)) {
					throw new CsvParseException("size > 1 时需要配置 @CsvProperty#clazz 方法");
				}
				List<String> strings = new ArrayList<>(Arrays.asList(splitResult).subList(index, index + size));
				List<Object> collect = strings.stream().map(str -> ConvertUtils.convert(str, clazz))
						.collect(Collectors.toList());
				field.set(obj, collect);
			}
		}
	}

	private static List<FieldWrapper> createByIndex(List<Field> fieldList) {

		return fieldList.stream().sorted(Comparator.comparingInt(t -> t.getAnnotation(CsvProperty.class).index()))
				.map(t -> {
					FieldWrapper fieldWrapper = new FieldWrapper();
					fieldWrapper.setField(t);
					fieldWrapper.setSize(t.getAnnotation(CsvProperty.class).size());
					fieldWrapper.setClazz(t.getAnnotation(CsvProperty.class).clazz());
					fieldWrapper.setIndex(t.getAnnotation(CsvProperty.class).index());
					return fieldWrapper;
				}).collect(Collectors.toList());
	}

	@Data
	private static class FieldWrapper {

		private Field field;

		private int size;

		private Class<?> clazz;

		private int index;
	}
}
