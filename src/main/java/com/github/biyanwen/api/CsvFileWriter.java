package com.github.biyanwen.api;

import java.util.List;

/**
 * 输入文件创造者
 *
 * @Author byw
 * @Date 2022/1/6 20:08
 */
public interface CsvFileWriter {

 /**
  * 写
  *
  * @param objects         对象列表
  * @param csvWriteContext csv写上下文
  */
 void doWrite(List<Object> objects, CsvWriteContext csvWriteContext);
}
