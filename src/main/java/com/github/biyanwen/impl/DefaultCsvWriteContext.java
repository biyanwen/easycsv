package com.github.biyanwen.impl;

import com.github.biyanwen.api.CsvWriteContext;
import com.github.biyanwen.factory.CsvFileWriterFactory;
import com.github.biyanwen.helper.CheckDataHelper;

import java.util.List;

/**
 * @Author byw
 * @Date 2022/1/29 15:10
 */
public class DefaultCsvWriteContext implements CsvWriteContext {

	private String encoding = "GBK";
	private Class writeClass;
	private String filePath;
	private boolean writeByHead = false;

	@Override
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String getEncoding() {
		return this.encoding;
	}

	@Override
	public void setWriteClass(Class writeClass) {
		this.writeClass = writeClass;
		boolean userName = CheckDataHelper.checkIfUseName(writeClass);
		if (userName) {
			writeByHead = true;
		}
	}

	@Override
	public Class getWriteClass() {
		return this.writeClass;
	}

	@Override
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String getFilePath() {
		return this.filePath;
	}

	@Override
	public boolean writeByHead() {
		return this.writeByHead;
	}

	@Override
	public void doWrite(List objects) {
		CsvFileWriterFactory.defaultWriter().doWrite(objects, this);
	}

	public static Builder createBuilder() {
		return new Builder();
	}

	public static final class Builder {
		private String encoding = "GBK";
		private Class writeClass;
		private String filePath;
		private boolean writeByHead = false;

		private Builder() {
		}

		public static Builder aDefaultCsvWriteContext() {
			return new Builder();
		}

		public Builder withEncoding(String encoding) {
			this.encoding = encoding;
			return this;
		}

		public Builder withWriteClass(Class writeClass) {
			this.writeClass = writeClass;
			return this;
		}

		public Builder withFilePath(String filePath) {
			this.filePath = filePath;
			return this;
		}

		public DefaultCsvWriteContext build() {
			DefaultCsvWriteContext defaultCsvWriteContext = new DefaultCsvWriteContext();
			defaultCsvWriteContext.setEncoding(encoding);
			defaultCsvWriteContext.setWriteClass(writeClass);
			defaultCsvWriteContext.setFilePath(filePath);
			return defaultCsvWriteContext;
		}
	}
}
