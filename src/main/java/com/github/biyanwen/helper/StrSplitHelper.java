package com.github.biyanwen.helper;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author byw
 * @Date 2022/1/30 10:47
 */
public class StrSplitHelper {

	/**
	 * 字符串切割
	 *
	 * @param str  str
	 * @param mark 标记位
	 * @return {@link String[]}
	 */
	public static String[] split(String str, Character mark) {
		Node node = createNodes(str);
		List<String> result = new ArrayList<>();
		StringBuilder buffer = new StringBuilder();

		for (; ; ) {
			Character value = node.value;
			if (value.equals(mark)) {
				if (StrUtil.isNotBlank(buffer.toString())) {
					result.add(buffer.toString());
					buffer = new StringBuilder();
				}
				if (node.pre != null) {
					if (value.equals(node.pre.value)) {
						result.add("byw");
						if (node.next == null) {
							break;
						}
					}
					node = node.next;
					continue;
				}
				continue;
			}
			buffer.append(value);
			if (node.next == null) {
				break;
			}
			node = node.next;
		}
		if (StrUtil.isNotBlank(buffer.toString())) {
			result.add(buffer.toString());
		}
		result.replaceAll(t -> {
			if (t.equals("byw")) {
				return "";
			} else {
				return t;
			}
		});
		return result.toArray(new String[0]);
	}

	private static Node createNodes(String str) {
		char[] chars = str.toCharArray();
		char firstChar = chars[0];
		Node head = new Node();
		head.value = firstChar;

		Node tmp = head;
		chars = deleteFirst(chars);
		for (char aChar : chars) {
			Node node = new Node();
			node.pre = tmp;
			node.value = aChar;
			tmp.next = node;
			tmp = node;
		}
		return head;
	}

	private static char[] deleteFirst(char[] arr) {
		char[] temp = new char[arr.length - 1];
		System.arraycopy(arr, 1, temp, 0, temp.length);
		return temp;
	}


	private static class Node {
		public Node pre;
		public Node next;
		public Character value;
	}
}
