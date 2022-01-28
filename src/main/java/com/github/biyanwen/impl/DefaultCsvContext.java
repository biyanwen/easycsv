package com.github.biyanwen.impl;

import com.github.biyanwen.api.CsvContext;
import com.github.biyanwen.api.CsvFileParser;

/**
 * 默认csv上下文
 *
 * @Author byw
 * @Date 2022/1/27 9:16
 */
public class DefaultCsvContext implements CsvContext {

	private String encoding = "GBK";
	private Class tClass;
	private String path;
	private CsvFileParser csvFileParser;
	private int skip = 0;

	@Override
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String getEncoding() {
		return this.encoding;
	}

	@Override
	public void setClass(Class clazz) {
		this.tClass = clazz;
	}

	@Override
	public Class getClazz() {
		return this.tClass;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public CsvContext skip(int num) {
		this.skip = num;
		return this;
	}

	@Override
	public int getSkip() {
		return this.skip;
	}

	@Override
	public void doRead() {
		csvFileParser.doParse(this);
	}

	public static Builder createBuilder() {
		return new Builder();
	}


	public static final class Builder {
		private String encoding = "GBK";
		private Class tClass;
		private String path;
		private CsvFileParser csvFileParser;
		private int skip = 0;

		private Builder() {
		}

		public static Builder aDefaultCsvContext() {
			return new Builder();
		}

		public Builder withEncoding(String encoding) {
			this.encoding = encoding;
			return this;
		}

		public Builder withTClass(Class tClass) {
			this.tClass = tClass;
			return this;
		}

		public Builder withPath(String path) {
			this.path = path;
			return this;
		}

		public Builder withCsvFileParser(CsvFileParser csvFileParser) {
			this.csvFileParser = csvFileParser;
			return this;
		}

		public Builder withSkip(int skip) {
			this.skip = skip;
			return this;
		}

		public DefaultCsvContext build() {
			DefaultCsvContext defaultCsvContext = new DefaultCsvContext();
			defaultCsvContext.setEncoding(encoding);
			defaultCsvContext.setPath(path);
			defaultCsvContext.skip(skip);
			defaultCsvContext.csvFileParser = this.csvFileParser;
			defaultCsvContext.tClass = this.tClass;
			return defaultCsvContext;
		}
	}
}
