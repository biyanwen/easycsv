package com.github.biyanwen.impl;

import com.github.biyanwen.api.InputFileCreator;
import com.github.biyanwen.api.InputObjParser;
import com.github.biyanwen.factory.InputObjParserFactory;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 输入文件创造者
 *
 * @Author byw
 * @Date 2022/1/7 10:22
 */
public abstract class AbstractInputFileCreator implements InputFileCreator {

	@Override
	public void doCreate(String path, String caseId) {
		createDir(path);
		path = path + getFileName();
		List<Object> objects = doCreateObjects(caseId);
		List<String> strLines = parse2Str(objects);
		write2File(strLines, path);
	}

	private void createDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * write2文件
	 *
	 * @param strLines str行
	 * @param path     路径
	 */
	@SneakyThrows
	private void write2File(List<String> strLines, String path) {
		Path filePath = Paths.get(path);
		boolean exists = Files.exists(filePath, LinkOption.NOFOLLOW_LINKS);
		if (!exists) {
			Files.createFile(filePath);
		}
		Files.write(filePath, strLines, Charset.forName("GBK"));
	}

	/**
	 * 将 object 解析成字符串
	 *
	 * @param objects 对象
	 * @return {@link List}<{@link String}>
	 */
	protected List<String> parse2Str(List<Object> objects) {
		return inputObjParser().doParse(objects);
	}

	/**
	 * 输入obj解析器
	 *
	 * @return {@link InputObjParser}
	 */
	protected InputObjParser inputObjParser() {
		return InputObjParserFactory.getDefaultParser();
	}

	/**
	 * 做创建对象
	 *
	 * @param caseId 方案id
	 * @return {@link List}<{@link Object}>
	 */
	protected abstract List<Object> doCreateObjects(String caseId);

	/**
	 * 获取文件名称
	 *
	 * @return {@link String}
	 */
	protected abstract String getFileName();
}
