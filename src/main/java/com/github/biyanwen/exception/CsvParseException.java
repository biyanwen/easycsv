package com.github.biyanwen.exception;

/**
 * @Author byw
 * @Date 2022/1/29 12:31
 */
public class CsvParseException extends RuntimeException {
	public CsvParseException() {
	}

	public CsvParseException(String message) {
		super(message);
	}
}
