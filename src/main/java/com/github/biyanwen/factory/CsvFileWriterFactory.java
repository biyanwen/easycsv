package com.github.biyanwen.factory;

import com.github.biyanwen.api.CsvFileWriter;
import com.github.biyanwen.impl.DefaultCsvFileWriter;

/**
 *
 * @Author byw
 * @Date 2022/1/29 20:20
 */
public class CsvFileWriterFactory {

	public static CsvFileWriter defaultWriter() {
		return new DefaultCsvFileWriter();
	}
}
