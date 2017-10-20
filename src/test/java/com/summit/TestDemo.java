package com.summit;

import java.io.IOException;

import org.junit.Test;

public class TestDemo {

	public HadoopFileSystemManagerImpl hdfs = new HadoopFileSystemManagerImpl();

	@Test
	public void test1() throws IOException {
		// System.out.println(hdfs.dir("/"));
		System.out.println(hdfs.dir("/user/hadoop/"));
		// System.out.println(hdfs.exists("/user/hadoop/input/"));

		// hdfs.uploadLocalFile("d:/个人文件转移工具(DOS之家).ini",
		// "/user/hadoop/input//个人文件转移工具(DOS之家).ini");
		// hdfs.create(new
		// File("d:/个人文件转移工具(DOS之家).ini"),"/user/hadoop/input//个人文件转移工具(DOS之家).ini");
		// hdfs.downFileToLocal("/user/hadoop/input//个人文件转移工具(DOS之家).ini",
		// "e://a.ini");

		// hdfs.uploadLocalFile("D:/picture", "/test"); //
		// hdfs.create("D:/picture/mypicture/POP海报2590.jpg","/test/picture/mypicture/POP海报2590.jpg");
		// hdfs.uploadLocalFile(new String[]{"E:/input","E:/output"}, "/");
		// hdfs.rename("/input", "/debug_in");
		// System.out.println(hdfs.deleteFile("output"));
		// hdfs.moveFromLocalFile("E:/test.jpg", "/test/picture/POP海报2590.jpg");

		// hdfs.close();
	}
}
