package com.github.biyanwen;

import com.github.biyanwen.api.CsvReadContext;
import com.github.biyanwen.bean.*;
import com.github.biyanwen.impl.PageCsvParser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		CsvReadContext context = EasyCsv.read(path, HeadTestBean.class, new PageCsvParser<HeadTestBean>(list::addAll));
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

	@SneakyThrows
	@Test
	public void test_for_write() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/WRITE.csv").getCanonicalPath();

		List<WriteTestBean> writeTestBeans = Collections.singletonList(new WriteTestBean(1, "小明", 18));
		EasyCsv.write(path, WriteTestBean.class)
				.doWrite(writeTestBeans);

		List<WriteTestBean> list = new ArrayList<>();
		EasyCsv.read(path, WriteTestBean.class, new PageCsvParser<WriteTestBean>(list::addAll))
				.doRead();

		assertEquals(1, list.size());
		WriteTestBean one = list.get(0);
		assertEquals(1, one.getId());
		assertEquals("小明", one.getName());
		assertEquals(18, one.getAge());
	}

	@SneakyThrows
	@Test
	public void test_for_write_list_field() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/WRITE_LIST.csv").getCanonicalPath();

		WriteListTestBean writeListTestBean = new WriteListTestBean();
		writeListTestBean.setId(1);
		writeListTestBean.setName("小黑");
		writeListTestBean.setFriends(Arrays.asList("小明", "胖虎", "小丽"));

		WriteListTestBean writeListTestBeanTwo = new WriteListTestBean();
		writeListTestBeanTwo.setId(2);
		writeListTestBeanTwo.setName("小明");
		writeListTestBeanTwo.setFriends(Arrays.asList("小黑", "胖虎", "小丽"));

		List<WriteListTestBean> writeListTestBeans = Arrays.asList(writeListTestBean, writeListTestBeanTwo);
		EasyCsv.write(path, WriteListTestBean.class).doWrite(writeListTestBeans);

		List<ReadListTestBean> list = new ArrayList<>();
		EasyCsv.read(path, ReadListTestBean.class, new PageCsvParser<ReadListTestBean>(list::addAll))
				.doRead();
		assertEquals(2, list.size());
		assertEquals(3, list.get(0).getFriends().size());
	}
}