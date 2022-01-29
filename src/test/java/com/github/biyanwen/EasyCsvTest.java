package com.github.biyanwen;

import com.github.biyanwen.api.CsvContext;
import com.github.biyanwen.bean.HeadTestBean;
import com.github.biyanwen.bean.TestOutputBean;
import com.github.biyanwen.bean.TogetherNameAndIndex;
import com.github.biyanwen.impl.PageCsvParser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author byw
 * @Date 2022/1/27 10:37
 */
class EasyCsvTest {

	@Test
	void read() {
		skipTest(0);
		skipTest(1);
	}

	@SneakyThrows
	private void skipTest(int num) {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/TEST_out.csv").getCanonicalPath();

		List<TestOutputBean> list = new ArrayList<>();
		EasyCsv.read(path, TestOutputBean.class, new PageCsvParser<TestOutputBean>(list::addAll))
				.skip(num)
				.doRead();

		if (num == 0) {
			assertEquals(2, list.size());
			TestOutputBean testOutputBean = list.get(0);
			assertEquals("123", testOutputBean.getCaseId());
			assertEquals(1, testOutputBean.getDataType());
			assertEquals("111", testOutputBean.getBeanId());
			assertEquals(3, testOutputBean.getList().size());
		}

		if (num == 1) {
			assertEquals(1, list.size());
			TestOutputBean testOutputBean = list.get(0);
			assertEquals("123", testOutputBean.getCaseId());
			assertEquals(2, testOutputBean.getDataType());
			assertEquals("222", testOutputBean.getBeanId());
			assertEquals(3, testOutputBean.getList().size());
		}
	}

	@SneakyThrows
	@Test
	public void test_for_together_use_name_and_index() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/TEST_out.csv").getCanonicalPath();
		List<TogetherNameAndIndex> list = new ArrayList<>();
		assertThrows(RuntimeException.class, () -> EasyCsv.read(path, TogetherNameAndIndex.class, new PageCsvParser<TogetherNameAndIndex>(list::addAll))
				.doRead());
	}

	@SneakyThrows
	@Test
	public void test_for_name() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/TEST_out.csv").getCanonicalPath();

		List<HeadTestBean> list = new ArrayList<>();
		CsvContext context = EasyCsv.read(path, HeadTestBean.class, new PageCsvParser<HeadTestBean>(list::addAll));
		assertTrue(context.hasHead());
	}

	@SneakyThrows
	@Test
	public void test_for_not_found_head() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/TEST_out.csv").getCanonicalPath();
		List<HeadTestBean> list = new ArrayList<>();
		assertThrows(RuntimeException.class, () -> EasyCsv.read(path, HeadTestBean.class, new PageCsvParser<HeadTestBean>(list::addAll))
				.doRead());
	}

	@SneakyThrows
	@Test
	public void test_for_use_name() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/HEAD.csv").getCanonicalPath();

		List<HeadTestBean> list = new ArrayList<>();
		EasyCsv.read(path, HeadTestBean.class, new PageCsvParser<HeadTestBean>(list::addAll))
				.doRead();
		assertEquals(1, list.size());
		HeadTestBean headTestBean = list.get(0);
		assertEquals("1", headTestBean.getId());
		List<String> strings = Arrays.asList("2", "2");
		assertEquals(strings, headTestBean.getStrList());
		List<Integer> integers = Arrays.asList(3, 3, 3);
		assertEquals(integers, headTestBean.getIntegerList());
		assertEquals(new BigDecimal("4"), headTestBean.getValue());
	}
}