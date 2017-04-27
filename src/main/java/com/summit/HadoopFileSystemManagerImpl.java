package com.summit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

/**
 * 
 * HDFS(分布式文件存储系统操作类).
 * 
 * @author zhangs
 * @version 1.0 2013-11-22
 */
public class HadoopFileSystemManagerImpl implements IHadoopFileSystemManager {

	private static final String CONFIG_FILE_NAME = "hdfsConfig.properties";

	/**
	 * 文件系统类
	 */
	private FileSystem fileSystem;

	/**
	 * 无参数构造方法
	 */
	public HadoopFileSystemManagerImpl() {
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		Configuration config = new JobConf(HadoopFileSystemManagerImpl.class);

		try {
			Properties prop = new Properties();
			prop.load(HadoopFileSystemManagerImpl.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
			String hdfsUrl = prop.getProperty("HDFS_URL");
			String hdfsUser = prop.getProperty("HDFS_USER");// 配置文件读取登陆用户

			fileSystem = FileSystem.get(URI.create(hdfsUrl), config, hdfsUser);// 指定登陆用户
			// fileSystem = FileSystem.get(URI.create(hdfsUrl), config);
			System.out.println("初始化连接服务器成功^_^");
		} catch (IOException e) {
			System.err.println("初始化连接服务器失败!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取HDFS指定目录下文件状态列表
	 * 
	 * @param dirPath指定目录路径
	 * @return fileStatusList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public FileStatus[] getFileStatus(Path path) throws FileNotFoundException, IOException {
		FileStatus[] fileStatusList = fileSystem.listStatus(path);
		return fileStatusList;
	}

	/**
	 * 获取HDFS指定目录下文件状态列表
	 * 
	 * @param pathStr指定目录路径
	 * @return fileStatusList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public FileStatus[] getFileStatus(String pathStr) throws FileNotFoundException, IOException {
		Path path = new Path(pathStr);
		FileStatus[] fileStatusList = fileSystem.listStatus(path);
		return fileStatusList;
	}

	/**
	 * 获取指定目录列表路径
	 * 
	 * @param dirPath
	 */
	public List<String> dir(String dirPath) throws IOException {
		List<String> fileList = null;
		Path path = new Path(dirPath);
		if (fileSystem.exists(path)) {
			fileList = new ArrayList<String>();
			FileStatus[] list = this.getFileStatus(path);
			for (FileStatus fileStatus : list) {
				fileList.add(fileStatus.getPath().toString());
			}
		} else {
			System.out.println("目录不存在");
		}
		return fileList;
	}

	/**
	 * 获取文件
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public InputStream getFile(String filePath) throws IOException {
		Path path = new Path(filePath);
		return fileSystem.open(path);
	}

	/**
	 * 更改HDSF文件名称
	 * 
	 * @param fileOldName
	 * @param fileNewName
	 * @return boolean:是否更名字成功
	 * @throws IOException
	 */
	public boolean rename(String src, String dst) throws IOException {
		Path srcPath = new Path(src);
		if (fileSystem.exists(srcPath)) {
			Path dstPath = new Path(dst);
			return fileSystem.rename(srcPath, dstPath);
		}
		System.out.println("原文件不存在");
		return false;
	}

	/**
	 * 创建HDFS目录
	 * 
	 * @param dir
	 */
	public boolean createDir(String dir) throws IOException {
		Path path = new Path(dir);
		if (fileSystem.exists(path)) {
			System.out.println("此目录已经存在不需要再创建");
			return true;
		}
		return fileSystem.mkdirs(path);
	}

	/**
	 * 上传本地文件到HDFS（注意是服务器本地硬盘，非客户端硬盘)）
	 * 
	 * @return
	 * @throws IOException
	 */
	public void uploadLocalFile(String localFileSrc, String HDFSFileDst) throws IOException {
		Path src = new Path(localFileSrc);
		Path dst = new Path(HDFSFileDst);
		fileSystem.copyFromLocalFile(src, dst);
	}

	/**
	 * 批量上传本地文件到HDFS
	 * 
	 * @param localFileSrcs本地文件列表
	 * @param HDFSFileDst
	 * @throws IOException
	 */
	public void uploadLocalFile(String[] localFileSrcs, String HDFSFileDst) throws IOException {
		Path dstPath = new Path(HDFSFileDst);
		Path[] paths = new Path[localFileSrcs.length];
		for (int i = 0; i < localFileSrcs.length; i++) {
			paths[i] = new Path(localFileSrcs[i]);
		}
		fileSystem.copyFromLocalFile(false, false, paths, dstPath);
	}

	/**
	 * 从HDFS下载文件到本地(注意是服务器本地硬盘，非浏览器客户端硬盘)
	 * 
	 * @param HDFSFilePath
	 * @param localFilePath
	 * @throws IOException
	 */
	public void downFileToLocal(String HDFSFilePath, String localFilePath) throws IOException {
		Path dstPath = new Path(HDFSFilePath);
		FSDataInputStream in = fileSystem.open(dstPath);
		OutputStream out = new FileOutputStream(new File(localFilePath));
		IOUtils.copy(in, out);
	}

	/**
	 * HDFS文件或目录是否存在
	 * 
	 * @param filePath
	 * @return boolean:是否存在
	 * @throws IOException
	 */
	public boolean exists(String filePath) throws IOException {
		Path path = new Path(filePath);
		return fileSystem.exists(path);
	}

	/**
	 * 根据路径删除文件或文件夹
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public boolean deleteFile(String filePath) throws IOException {
		if (this.exists(filePath)) {
			Path path = new Path(filePath);
			fileSystem.delete(path);
			return true;
		}
		System.out.println("文件不存在");
		return false;
	}

	/**
	 * 剪切本地文件到HDFS(注意为服务器本地文件);
	 * 
	 * @param src本地路径
	 * @param dst分布式存储路径
	 * @throws IOException
	 */
	public void moveFromLocalFile(String localSrc, String HDFSDst) throws IOException {
		Path srcPath = new Path(localSrc);
		Path dstPath = new Path(HDFSDst);
		fileSystem.moveFromLocalFile(srcPath, dstPath);
	}

	/**
	 * HDFS文件之间的复制
	 * 
	 * @param src源文件路径
	 * @param dst要复制后复制文件的路径
	 * @throws IOException
	 */
	public void copyHDFSFile(String src, String dst) throws IOException {
		Path srcPath = new Path(src);
		Path dstPath = new Path(dst);
		InputStream in = fileSystem.open(srcPath);
		OutputStream out = fileSystem.create(dstPath);
		IOUtils.copy(in, out);
	}

	/**
	 * HDFS中移动文件
	 * 
	 * @param src源文件路径
	 * @param dst要移动后的路径
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void moveHDFSFile(String src, String dst) throws IOException {
		Path srcPath = new Path(src);
		Path dstPath = new Path(dst);
		InputStream in = fileSystem.open(srcPath);
		OutputStream out = fileSystem.create(dstPath);
		IOUtils.copy(in, out);
		fileSystem.delete(srcPath);
	}

	/**
	 * 剪切HDFS文件到本地
	 * 
	 * @param HDFSSrc
	 * @param localDst
	 * @throws IOException
	 */
	public void moveToLocalFile(String HDFSSrc, String localDst) throws IOException {
		Path srcPath = new Path(HDFSSrc);
		Path dstPath = new Path(localDst);
		fileSystem.moveToLocalFile(srcPath, dstPath);
	}

	/**
	 * HDFS创建文件
	 * 
	 * @param in输入流
	 * @param dst分布式存储路径
	 * @throws IOException
	 */
	public void create(InputStream in, String dst) throws IOException {
		Path dstPath = new Path(dst);
		FSDataOutputStream out = fileSystem.create(dstPath);
		IOUtils.copy(in, out);
	}

	/**
	 * 在HDFS创建文件
	 * 
	 * @param file
	 * @param dst分布式存储路径
	 * @throws IOException
	 */
	public void create(File file, String dst) throws IOException {
		InputStream in = new FileInputStream(file);
		this.create(in, dst);
	}

	/**
	 * 在HDFS创建文件
	 * 
	 * @param src本地文件路径
	 * @param dst分布式存储路径
	 * @throws IOException
	 */
	public void create(String src, String dst) throws IOException {
		File file = new File(src);
		this.create(file, dst);
	}

	/**
	 * 获取FileSystem对象
	 * 
	 * @return
	 */
	public FileSystem getFileSystem() {
		return fileSystem;
	}

	/**
	 * 关闭HDFS
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		fileSystem.close();
	}

	/**
	 * 判断是否目录
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public boolean isDir(String src) throws IOException {
		Path path = new Path(src);
		return fileSystem.isDirectory(path);
	}

	public static void main(String[] args) throws IOException {
		HadoopFileSystemManagerImpl hdfs = new HadoopFileSystemManagerImpl();
		// hdfs.uploadLocalFile("D:/picture", "/test"); //
		System.out.println(hdfs.dir("/"));
		// hdfs.create("D:/picture/mypicture/POP海报2590.jpg","/test/picture/mypicture/POP海报2590.jpg");
		System.out.println(hdfs.dir("/test/picture/mypicture"));
		// hdfs.uploadLocalFile(new String[]{"E:/input","E:/output"}, "/");
		// hdfs.rename("/input", "/debug_in");
		System.out.println(hdfs.dir("/debug_out"));
		// hdfs.deleteFile("/output");
		// hdfs.moveFromLocalFile("E:/test.jpg", "/test/picture/POP海报2590.jpg");
		/*
		 * 
		 * System.out.println(hdfs.dir("/test"));
		 * 
		 * System.out.println(hdfs.exists("/test/picture/mypicture")); //
		 * 
		 * hdfs.delete("/test/picture/mypicture");
		 * 
		 * System.out.println(hdfs.dir("/test/picture"));
		 * 
		 */
		hdfs.close();
	}
}