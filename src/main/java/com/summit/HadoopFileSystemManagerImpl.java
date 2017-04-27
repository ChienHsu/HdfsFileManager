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
 * HDFS(�ֲ�ʽ�ļ��洢ϵͳ������).
 * 
 * @author zhangs
 * @version 1.0 2013-11-22
 */
public class HadoopFileSystemManagerImpl implements IHadoopFileSystemManager {

	private static final String CONFIG_FILE_NAME = "hdfsConfig.properties";

	/**
	 * �ļ�ϵͳ��
	 */
	private FileSystem fileSystem;

	/**
	 * �޲������췽��
	 */
	public HadoopFileSystemManagerImpl() {
		init();
	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		Configuration config = new JobConf(HadoopFileSystemManagerImpl.class);

		try {
			Properties prop = new Properties();
			prop.load(HadoopFileSystemManagerImpl.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
			String hdfsUrl = prop.getProperty("HDFS_URL");
			String hdfsUser = prop.getProperty("HDFS_USER");// �����ļ���ȡ��½�û�

			fileSystem = FileSystem.get(URI.create(hdfsUrl), config, hdfsUser);// ָ����½�û�
			// fileSystem = FileSystem.get(URI.create(hdfsUrl), config);
			System.out.println("��ʼ�����ӷ������ɹ�^_^");
		} catch (IOException e) {
			System.err.println("��ʼ�����ӷ�����ʧ��!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡHDFSָ��Ŀ¼���ļ�״̬�б�
	 * 
	 * @param dirPathָ��Ŀ¼·��
	 * @return fileStatusList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public FileStatus[] getFileStatus(Path path) throws FileNotFoundException, IOException {
		FileStatus[] fileStatusList = fileSystem.listStatus(path);
		return fileStatusList;
	}

	/**
	 * ��ȡHDFSָ��Ŀ¼���ļ�״̬�б�
	 * 
	 * @param pathStrָ��Ŀ¼·��
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
	 * ��ȡָ��Ŀ¼�б�·��
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
			System.out.println("Ŀ¼������");
		}
		return fileList;
	}

	/**
	 * ��ȡ�ļ�
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
	 * ����HDSF�ļ�����
	 * 
	 * @param fileOldName
	 * @param fileNewName
	 * @return boolean:�Ƿ�����ֳɹ�
	 * @throws IOException
	 */
	public boolean rename(String src, String dst) throws IOException {
		Path srcPath = new Path(src);
		if (fileSystem.exists(srcPath)) {
			Path dstPath = new Path(dst);
			return fileSystem.rename(srcPath, dstPath);
		}
		System.out.println("ԭ�ļ�������");
		return false;
	}

	/**
	 * ����HDFSĿ¼
	 * 
	 * @param dir
	 */
	public boolean createDir(String dir) throws IOException {
		Path path = new Path(dir);
		if (fileSystem.exists(path)) {
			System.out.println("��Ŀ¼�Ѿ����ڲ���Ҫ�ٴ���");
			return true;
		}
		return fileSystem.mkdirs(path);
	}

	/**
	 * �ϴ������ļ���HDFS��ע���Ƿ���������Ӳ�̣��ǿͻ���Ӳ��)��
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
	 * �����ϴ������ļ���HDFS
	 * 
	 * @param localFileSrcs�����ļ��б�
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
	 * ��HDFS�����ļ�������(ע���Ƿ���������Ӳ�̣���������ͻ���Ӳ��)
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
	 * HDFS�ļ���Ŀ¼�Ƿ����
	 * 
	 * @param filePath
	 * @return boolean:�Ƿ����
	 * @throws IOException
	 */
	public boolean exists(String filePath) throws IOException {
		Path path = new Path(filePath);
		return fileSystem.exists(path);
	}

	/**
	 * ����·��ɾ���ļ����ļ���
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
		System.out.println("�ļ�������");
		return false;
	}

	/**
	 * ���б����ļ���HDFS(ע��Ϊ�����������ļ�);
	 * 
	 * @param src����·��
	 * @param dst�ֲ�ʽ�洢·��
	 * @throws IOException
	 */
	public void moveFromLocalFile(String localSrc, String HDFSDst) throws IOException {
		Path srcPath = new Path(localSrc);
		Path dstPath = new Path(HDFSDst);
		fileSystem.moveFromLocalFile(srcPath, dstPath);
	}

	/**
	 * HDFS�ļ�֮��ĸ���
	 * 
	 * @param srcԴ�ļ�·��
	 * @param dstҪ���ƺ����ļ���·��
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
	 * HDFS���ƶ��ļ�
	 * 
	 * @param srcԴ�ļ�·��
	 * @param dstҪ�ƶ����·��
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
	 * ����HDFS�ļ�������
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
	 * HDFS�����ļ�
	 * 
	 * @param in������
	 * @param dst�ֲ�ʽ�洢·��
	 * @throws IOException
	 */
	public void create(InputStream in, String dst) throws IOException {
		Path dstPath = new Path(dst);
		FSDataOutputStream out = fileSystem.create(dstPath);
		IOUtils.copy(in, out);
	}

	/**
	 * ��HDFS�����ļ�
	 * 
	 * @param file
	 * @param dst�ֲ�ʽ�洢·��
	 * @throws IOException
	 */
	public void create(File file, String dst) throws IOException {
		InputStream in = new FileInputStream(file);
		this.create(in, dst);
	}

	/**
	 * ��HDFS�����ļ�
	 * 
	 * @param src�����ļ�·��
	 * @param dst�ֲ�ʽ�洢·��
	 * @throws IOException
	 */
	public void create(String src, String dst) throws IOException {
		File file = new File(src);
		this.create(file, dst);
	}

	/**
	 * ��ȡFileSystem����
	 * 
	 * @return
	 */
	public FileSystem getFileSystem() {
		return fileSystem;
	}

	/**
	 * �ر�HDFS
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		fileSystem.close();
	}

	/**
	 * �ж��Ƿ�Ŀ¼
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
		// hdfs.create("D:/picture/mypicture/POP����2590.jpg","/test/picture/mypicture/POP����2590.jpg");
		System.out.println(hdfs.dir("/test/picture/mypicture"));
		// hdfs.uploadLocalFile(new String[]{"E:/input","E:/output"}, "/");
		// hdfs.rename("/input", "/debug_in");
		System.out.println(hdfs.dir("/debug_out"));
		// hdfs.deleteFile("/output");
		// hdfs.moveFromLocalFile("E:/test.jpg", "/test/picture/POP����2590.jpg");
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