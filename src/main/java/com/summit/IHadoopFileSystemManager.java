package com.summit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public interface IHadoopFileSystemManager {

	/**
	 * 获取HDFS指定目录下文件状态列表
	 * 
	 * @param dirPath指定目录路径
	 * @return fileStatusList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	FileStatus[] getFileStatus(Path path) throws FileNotFoundException, IOException;// 获取HDFS指定目录下文件状态列表

	/**
	 * 获取指定目录列表路径
	 * 
	 * @param dirPath
	 */
	List<String> dir(String dirPath) throws IOException;// 获取指定目录列表路径

	/**
	 * 获取文件
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	InputStream getFile(String filePath) throws IOException;// 获取文件

	/**
	 * 更改HDSF文件名称
	 * 
	 * @param fileOldName
	 * @param fileNewName
	 * @return boolean:是否更名字成功
	 * @throws IOException
	 */
	boolean rename(String src, String dst) throws IOException;// 更改HDSF文件名称

	/**
	 * 创建HDFS目录
	 * 
	 * @param dir
	 */
	boolean createDir(String dir) throws IOException;// 创建HDFS目录

	/**
	 * 上传本地文件到HDFS（注意是服务器本地硬盘，非客户端硬盘)）
	 * 
	 * @return
	 * @throws IOException
	 */
	public void uploadLocalFile(String localFileSrc, String HDFSFileDst) throws IOException;

	/**
	 * 批量上传本地文件到HDFS
	 * 
	 * @param localFileSrcs本地文件列表
	 * @param HDFSFileDst
	 * @throws IOException
	 */
	public void uploadLocalFile(String[] localFileSrcs, String HDFSFileDst) throws IOException;

	/**
	 * 从HDFS下载文件到本地(注意是服务器本地硬盘，非浏览器客户端硬盘)
	 * 
	 * @param HDFSFilePath
	 * @param localFilePath
	 * @throws IOException
	 */
	public void downFileToLocal(String HDFSFilePath, String localFilePath) throws IOException;

	/**
	 * HDFS文件或目录是否存在
	 * 
	 * @param filePath
	 * @return boolean:是否存在
	 * @throws IOException
	 */
	public boolean exists(String filePath) throws IOException;

	/**
	 * 根据路径删除文件或文件夹
	 * 
	 * @param filePath
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String filePath) throws IOException;

	/**
	 * 剪切本地文件到HDFS(注意为服务器本地文件);
	 * 
	 * @param src本地路径
	 * @param dst分布式存储路径
	 * @throws IOException
	 */
	public void moveFromLocalFile(String localSrc, String HDFSDst) throws IOException;

	/**
	 * HDFS文件之间的复制
	 * 
	 * @param src源文件路径
	 * @param dst要复制后复制文件的路径
	 * @throws IOException
	 */
	public void copyHDFSFile(String src, String dst) throws IOException;

	/**
	 * HDFS中移动文件
	 * 
	 * @param src源文件路径
	 * @param dst要移动后的路径
	 * @throws IOException
	 */
	public void moveHDFSFile(String src, String dst) throws IOException;

	/**
	 * 剪切HDFS文件到本地
	 * 
	 * @param HDFSSrc
	 * @param localDst
	 * @throws IOException
	 */
	public void moveToLocalFile(String HDFSSrc, String localDst) throws IOException;

	/**
	 * HDFS创建文件
	 * 
	 * @param in输入流
	 * @param dst分布式存储路径
	 * @throws IOException
	 */
	public void create(InputStream in, String dst) throws IOException;

	/**
	 * 在HDFS创建文件
	 * 
	 * @param file
	 * @param dst分布式存储路径
	 * @throws IOException
	 */
	public void create(File file, String dst) throws IOException;

	/**
	 * 在HDFS创建文件
	 * 
	 * @param src本地文件路径
	 * @param dst分布式存储路径
	 * @throws IOException
	 */
	public void create(String src, String dst) throws IOException;

	/**
	 * 获取FileSystem对象
	 * 
	 * @return
	 */
	public FileSystem getFileSystem();

	/**
	 * 关闭HDFS
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;

	/**
	 * 判断是否目录
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public boolean isDir(String src) throws IOException;

}
