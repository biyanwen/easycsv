package com.github.biyanwen.api;

/**
 * 输入文件创造者
 *
 * @Author byw
 * @Date 2022/1/6 20:08
 */
public interface InputFileCreator {
 /**
  * 创建
  *
  * @param path   输入文件存储路径
  * @param caseId 方案id
  */
 void doCreate(String path, String caseId);
}
