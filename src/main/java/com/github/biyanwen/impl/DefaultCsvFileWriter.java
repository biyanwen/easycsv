package com.github.biyanwen.impl;

import com.github.biyanwen.api.CsvFileWriter;
import com.github.biyanwen.api.CsvWriteContext;
import com.github.biyanwen.factory.ObjParserFactory;
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
public class DefaultCsvFileWriter implements CsvFileWriter {

	@SneakyThrows
	@Override
	public void doWrite(List<Object> objects, CsvWriteContext csvWriteContext) {
		createFile(csvWriteContext.getFilePath());
		String string = ObjParserFactory.getDefaultParser().doParse(objects, csvWriteContext);
		Files.write(Paths.get(csvWriteContext.getFilePath()), string.getBytes(csvWriteContext.getEncoding()));
	}

	@SneakyThrows
	private void createFile(String path) {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		Path filePath = Paths.get(path);
		boolean exists = Files.exists(filePath, LinkOption.NOFOLLOW_LINKS);
		if (!exists) {
			Files.createFile(filePath);
		}
	}
}
