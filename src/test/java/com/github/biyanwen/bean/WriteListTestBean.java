package com.github.biyanwen.bean;

import com.github.biyanwen.annotation.CsvProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author byw
 * @Date 2022/1/30 9:52
 */
@Data
public class WriteListTestBean {
	@CsvProperty(name = "标识")
	private Integer id;
	@CsvProperty(name = "名字")
	private String name;
	@CsvProperty(name = "朋友们")
	private List<String> friends;
}
