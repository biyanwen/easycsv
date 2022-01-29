package com.github.biyanwen.bean;

import com.github.biyanwen.annotation.CsvProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author byw
 * @Date 2022/1/29 12:03
 */
@Data
public class HeadTestBean {
	@CsvProperty(name = "表头1")
	private String id;

	@CsvProperty(name = "表头2", clazz = String.class)
	private List<String> strList;

	@CsvProperty(name = "表头3", clazz = Integer.class)
	private List<Integer> integerList;

	@CsvProperty(name = "表头4")
	private BigDecimal value;
}
