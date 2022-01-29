package com.github.biyanwen.impl;

import com.github.biyanwen.api.CsvContext;
import com.github.biyanwen.api.CsvFileParser;
import com.github.biyanwen.exception.CsvParseException;
import com.github.biyanwen.helper.CsvParseHelper;
import lombok.SneakyThrows;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * 输出文件解析器
 *
 * @Author byw
 * @Date 2022/1/7 16:52
 */
public abstract class AbstractCsvFileParser<T> implements CsvFileParser {

	/**
	 * csv上下文
	 */
	private CsvContext csvContext;

	/**
	 * 第一行/表头
	 */
	private String head;

	@Override
	public void doParse(CsvContext csvContext) {
		this.csvContext = csvContext;
		String path = csvContext.getPath();
		check(path);
		parse2Objects(path);
		doAfterAllAnalysed();
	}

	/**
	 * 解析完成后会调用这个方法
	 */
	protected abstract void doAfterAllAnalysed();

	/**
	 * parse2对象
	 *
	 * @param t t
	 */
	protected abstract void invoke(T t);

	/**
	 * 解析.可以返回 Null。
	 *
	 * @param path 路径
	 * @return {@link List}<{@link Object}>
	 */
	@SneakyThrows
	private void parse2Objects(String path) {
		try (Stream<String> stream = Files.lines(Paths.get(path), Charset.forName(csvContext.getEncoding()))) {
			stream.skip(csvContext.getSkip()).forEach(t -> {
				if (head == null) {
					head = t;
				}

				if (exeHead(csvContext.hasHead(), head.equals(t))) {
					T result = (T) CsvParseHelper.getResult(t, csvContext.getClazz(), head, csvContext.hasHead());
					invoke(result);
				}
			});
		}
	}

	private boolean exeHead(boolean hasHead, boolean equalsHead) {
		if (hasHead && equalsHead) {
			return false;
		}
		return true;
	}

	private void check(String path) {
		Path filePath = Paths.get(path);
		if (!Files.exists(filePath)) {
			throw new CsvParseException("File is not found：" + path);
		}
	}
}
