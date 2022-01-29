package com.github.biyanwen.bean;

import com.github.biyanwen.annotation.CsvProperty;
import lombok.Data;

/**
 * @Author byw
 * @Date 2022/1/29 11:57
 */
@Data
public class TogetherNameAndIndex {

	@CsvProperty(index = 0)
	private String name;

	@CsvProperty(name = "表头")
	private String age;
}
