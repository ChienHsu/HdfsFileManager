package com.summit;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapred.JobConf;

/**
 * HDFS服务器
 * 
 * @author hjn
 * @version 1.0 2013-11-20
 */
public class HDFSServerInit {
	private static Configuration configuration;
	private static FileSystem fileSystem;
	private static final String CONFIG_FILE_NAME = "hdfsConfig.properties";

	/**
	 *
	 * HDFS服务器读取初始化
	 */
	private static void init() {
		try {
			configuration = new JobConf(HDFSServerInit.class);

			Properties prop = new Properties();
			prop.load(HDFSServerInit.class.getResourceAsStream(CONFIG_FILE_NAME));
			String hdfsUrl = prop.getProperty("HDFS_URL");

			fileSystem = FileSystem.get(URI.create(hdfsUrl), configuration);
		} catch (IOException e) {
			System.out.println("读取服务器失败");
			e.printStackTrace();
		}
	}

	public static FileSystem getFileSystem() {
		if (fileSystem == null) {
			init();
		}
		return fileSystem;
	}
}