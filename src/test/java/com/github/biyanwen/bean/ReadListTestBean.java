package com.github.biyanwen.bean;

import com.github.biyanwen.annotation.CsvProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author byw
 * @Date 2022/1/30 10:28
 */
@Data
public class ReadListTestBean {
	@CsvProperty(name = "标识")
	private Integer id;
	@CsvProperty(name = "名字")
	private String name;
	@CsvProperty(name = "朋友们", clazz = String.class)
	private List<String> friends;
}
