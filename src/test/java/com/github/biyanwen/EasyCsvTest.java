package com.github.biyanwen;

import com.github.biyanwen.bean.TestOutputBean;
import com.github.biyanwen.impl.PageCsvParser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
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

		if (num == 1){
			assertEquals(1, list.size());
			TestOutputBean testOutputBean = list.get(0);
			assertEquals("123", testOutputBean.getCaseId());
			assertEquals(2, testOutputBean.getDataType());
			assertEquals("222", testOutputBean.getBeanId());
			assertEquals(3, testOutputBean.getList().size());
		}
	}
}