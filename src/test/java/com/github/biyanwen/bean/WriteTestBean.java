package com.github.biyanwen.bean;

import com.github.biyanwen.annotation.CsvProperty;
import lombok.Data;

/**
 * @Author byw
 * @Date 2022/1/29 20:38
 */
@Data
public class WriteTestBean {
	@CsvProperty(name = "标识")
	private Integer id;
	@CsvProperty(name = "名字")
	private String name;
	@CsvProperty(name = "年龄")
	private Integer age;

	public WriteTestBean() {
	}

	public WriteTestBean(Integer id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
}
