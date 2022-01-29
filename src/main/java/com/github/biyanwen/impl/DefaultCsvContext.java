package com.github.biyanwen.impl;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.biyanwen.annotation.CsvProperty;
import com.github.biyanwen.api.CsvContext;
import com.github.biyanwen.api.CsvFileParser;
import com.github.biyanwen.exception.CsvParseException;

import java.lang.reflect.Field;

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
	private boolean hasHead = false;

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
	public boolean hasHead() {
		return hasHead;
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
		private boolean hasHead = false;

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
			check(tClass);
			return this;
		}

		private void check(Class tClass) {
			boolean useIndex = checkIfUseIndex(tClass);
			boolean userName = checkIfUseName(tClass);
			if (useIndex && userName) {
				throw new CsvParseException("不可同时使用 CsvProperty#name() 和 CsvProperty#index()");
			}
			if (userName) {
				this.hasHead = true;
			}
		}

		private boolean checkIfUseName(Class tClass) {
			for (Field field : ReflectUtil.getFields(tClass)) {
				CsvProperty annotation = field.getAnnotation(CsvProperty.class);
				if (annotation != null && !StrUtil.isBlank(annotation.name())) {
					return true;
				}
			}
			return false;
		}

		private boolean checkIfUseIndex(Class tClass) {
			for (Field field : ReflectUtil.getFields(tClass)) {
				CsvProperty annotation = field.getAnnotation(CsvProperty.class);
				if (annotation != null && annotation.index() != -1) {
					return true;
				}
			}
			return false;
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
			defaultCsvContext.hasHead = this.hasHead;
			return defaultCsvContext;
		}
	}
}
