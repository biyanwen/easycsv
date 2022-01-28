package com.github.biyanwen.bean;

import com.github.biyanwen.annotation.CsvProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author byw
 * @Date 2022/1/7 11:52
 */
@Data
public class TestOutputBean {

	@CsvProperty(index = 0)
	private String caseId;

	@CsvProperty(index = 1)
	private Integer dataType;

	@CsvProperty(index = 2)
	private String beanId;

	@CsvProperty(index = 3, size = 3, clazz = BigDecimal.class)
	private List<BigDecimal> list;
}
