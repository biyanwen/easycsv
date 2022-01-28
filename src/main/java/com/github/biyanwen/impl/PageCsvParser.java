package com.github.biyanwen.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author byw
 * @Date 2022/1/27 9:40
 */
public class PageCsvParser<T> extends AbstractCsvFileParser<T> {

	public static final int BATCH_COUNT = 3000;

	private List<T> cachedData = new ArrayList<>(BATCH_COUNT);
	/**
	 * consumer
	 */
	private final Consumer<List<T>> consumer;

	public PageCsvParser(Consumer<List<T>> consumer) {
		this.consumer = consumer;
	}

	@Override
	protected void doAfterAllAnalysed() {
		consumer.accept(cachedData);
	}

	@Override
	protected void invoke(T data) {
		cachedData.add(data);
		if (cachedData.size() >= BATCH_COUNT) {
			consumer.accept(cachedData);
			// 存储完成清理 list
			cachedData = new ArrayList<>(BATCH_COUNT);
		}

	}
}
