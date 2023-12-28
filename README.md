## EasyCsv

English | [中文](README_ZH.md)

EasyCsv is a tool that can read and write a CSV file simplify.

## Quick Start

maven address

~~~xml
<dependency>
    <groupId>com.github.biyanwen</groupId>
    <artifactId>easycsv</artifactId>
    <version>1.0.1</version>
</dependency>
~~~

### Read a CSV

#### Read a CSV with no table head/Read a CSV with a table index.

You can only use a table index to read a CSV when the table does not have a table head. For example :

![img.png](img.png)

**Define the class**：

~~~java

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
~~~

You must use the @CsvProperty annotation of the EasyCsv provided on the field if you want the field to be identified.
The `index()` method of the @CsvProperty provided can set a mapping that records the relationship on a field and a
table. EasyCsv provides the function that can merge cell as same as Excel. So if you want to use the function, you
should set the `size()` and `class()` and non-parameter constructor.

**Create a parser**：

~~~java
public class DemoCsvParser extends AbstractCsvFileParser<TestOutputBean> {
	/**
	 * Save every 3000 data.
	 */
	public static final int BATCH_COUNT = 3000;
	/**
	 * The data of the cache.
	 */
	private List<TestOutputBean> cachedData = new ArrayList<>(BATCH_COUNT);
	/**
	 * DAO
	 */
	private TestOutputDAO testOutputDAO;

	public DemoCsvParser() {
		// This is for test.
		testOutputDAO = new DemoDAO();
	}

	public DemoCsvParser(TestOutputDAO testOutputDAO) {
		this.testOutputDAO = testOutputDAO;
	}

	/**
	 * This method will invoke when any data is parsed.
	 */
	@Override
	protected void invoke(T data) {
		cachedData.add(data);
		if (cachedData.size() >= BATCH_COUNT) {
			saveData();
			cachedData = new ArrayList<>(BATCH_COUNT);
		}
	}

	@Override
	protected void doAfterAllAnalysed() {
		saveData();
	}

	private void saveData() {
		testOutputDAO.save(cachedData);
	}
}

~~~

**Invoke EasyCsv#read method**

~~~java
class EasyCsvTest {

	@Test
	void read() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/TEST_out.csv").getCanonicalPath();

		EasyCsv.read(path, TestOutputBean.class, new DemoCsvParser())
				.doRead();
	}
}
~~~

You can use the built-in parser `PageCsvParser` to simplify the job.For example :

~~~java

class EasyCsvTest {

	@Test
	void read() {
		// This is for test.
		TestOutputDAO dao = new TestOutputDAO();
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/TEST_out.csv").getCanonicalPath();

		EasyCsv.read(path, TestOutputBean.class, new PageCsvParser<TestOutputBean>(list -> dao.save(list)))
				.doRead();
	}
}
~~~

##### You can start reading the CSV file with any row.

You can invoke the `skip()` method of the `EasyCsv.class` provides to skip a specific number of rows. For example:

~~~java

class EasyCsvTest {

	@Test
	void read() {
		TestOutputDAO dao = new TestOutputDAO();
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/TEST_out.csv").getCanonicalPath();

		EasyCsv.read(path, TestOutputBean.class, new PageCsvParser<TestOutputBean>(list -> dao.save(list)))
				//Skip a specific number of rows.
				.skip(num)
				.doRead();
	}
}
~~~

#### Read a CSV with table head

For example:

![img_1.png](img_1.png)

**Define a Class**

~~~java
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
~~~

**Invoke EasyCsv#read method**

~~~java
class EasyCsvTest {

	@Test
	void read() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/WRITE.csv").getCanonicalPath();

		List<WriteTestBean> list = new ArrayList<>();
		EasyCsv.read(path, WriteTestBean.class, new PageCsvParser<WriteTestBean>(list::addAll))
				.doRead();
	}
}
~~~

### Write a csv file.

**Define a Class**

~~~java

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
~~~

**Invoke EasyCsv#write method**

~~~java
class EasyCsvTest {

	@Test
	void read() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/WRITE.csv").getCanonicalPath();
		List<WriteTestBean> writeTestBeans = Collections.singletonList(new WriteTestBean(1, "小明", 18));

		EasyCsv.write(path, WriteTestBean.class)
				.doWrite(writeTestBeans);
	}
}
~~~

**Result**

![img_1.png](img_1.png)

**Merge cell**

A CSV file does not have the merge cell function, but we can simulate it by ourselves. For example :

**Define a Class**

~~~java

@Data
public class WriteListTestBean {
	@CsvProperty(name = "标识")
	private Integer id;
	@CsvProperty(name = "名字")
	private String name;
	@CsvProperty(name = "朋友们")
	private List<String> friends;
}
~~~

The field that name "friends", can simulate a merge cell function. After writing the CSV file, you can read the data
which name "friends" in a list by using this tool. For example :

**Define a Class**

~~~java
@Data
public class WriteListTestBean {
	@CsvProperty(name = "标识")
	private Integer id;
	@CsvProperty(name = "名字")
	private String name;
	@CsvProperty(name = "朋友们")
	private List<String> friends;
}
~~~

~~~java
class EasyCsvTest {

	@Test
	void read() {
		String file = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		String path = new File(file, "/file/WRITE_LIST.csv").getCanonicalPath();

		WriteListTestBean writeListTestBean = new WriteListTestBean();
		writeListTestBean.setId(1);
		writeListTestBean.setName("小黑");
		writeListTestBean.setFriends(Arrays.asList("小明", "胖虎", "小丽"));

		WriteListTestBean writeListTestBeanTwo = new WriteListTestBean();
		writeListTestBeanTwo.setId(2);
		writeListTestBeanTwo.setName("小明");
		writeListTestBeanTwo.setFriends(Arrays.asList("小黑", "胖虎", "小丽"));

		List<WriteListTestBean> writeListTestBeans = Arrays.asList(writeListTestBean, writeListTestBeanTwo);
		EasyCsv.write(path, WriteListTestBean.class).doWrite(writeListTestBeans);

		List<ReadListTestBean> list = new ArrayList<>();
		EasyCsv.read(path, ReadListTestBean.class, new PageCsvParser<ReadListTestBean>(list::addAll))
				.doRead();
		assertEquals(2, list.size());
		assertEquals(3, list.get(0).getFriends().size());
	}
}
~~~

