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

		// hdfs.uploadLocalFile("d:/�����ļ�ת�ƹ���(DOS֮��).ini", "/user/hadoop/input//�����ļ�ת�ƹ���(DOS֮��).ini");
		// hdfs.create(new File("d:/�����ļ�ת�ƹ���(DOS֮��).ini"),"/user/hadoop/input//�����ļ�ת�ƹ���(DOS֮��).ini");
		// hdfs.downFileToLocal("/user/hadoop/input//�����ļ�ת�ƹ���(DOS֮��).ini", "e://a.ini");

		// hdfs.uploadLocalFile("D:/picture", "/test"); //
		// hdfs.create("D:/picture/mypicture/POP����2590.jpg","/test/picture/mypicture/POP����2590.jpg");
		// hdfs.uploadLocalFile(new String[]{"E:/input","E:/output"}, "/");
		// hdfs.rename("/input", "/debug_in");
		// System.out.println(hdfs.deleteFile("output"));
		// hdfs.moveFromLocalFile("E:/test.jpg", "/test/picture/POP����2590.jpg");

		// hdfs.close();
	}
}
