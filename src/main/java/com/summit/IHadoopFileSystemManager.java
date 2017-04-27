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
	 * ��ȡHDFSָ��Ŀ¼���ļ�״̬�б�
	 * 
	 * @param dirPathָ��Ŀ¼·��
	 * @return fileStatusList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	FileStatus[] getFileStatus(Path path) throws FileNotFoundException, IOException;// ��ȡHDFSָ��Ŀ¼���ļ�״̬�б�

	/**
	 * ��ȡָ��Ŀ¼�б�·��
	 * 
	 * @param dirPath
	 */
	List<String> dir(String dirPath) throws IOException;// ��ȡָ��Ŀ¼�б�·��

	/**
	 * ��ȡ�ļ�
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	InputStream getFile(String filePath) throws IOException;// ��ȡ�ļ�

	/**
	 * ����HDSF�ļ�����
	 * 
	 * @param fileOldName
	 * @param fileNewName
	 * @return boolean:�Ƿ�����ֳɹ�
	 * @throws IOException
	 */
	boolean rename(String src, String dst) throws IOException;// ����HDSF�ļ�����

	/**
	 * ����HDFSĿ¼
	 * 
	 * @param dir
	 */
	boolean createDir(String dir) throws IOException;// ����HDFSĿ¼

	/**
	 * �ϴ������ļ���HDFS��ע���Ƿ���������Ӳ�̣��ǿͻ���Ӳ��)��
	 * 
	 * @return
	 * @throws IOException
	 */
	public void uploadLocalFile(String localFileSrc, String HDFSFileDst) throws IOException;

	/**
	 * �����ϴ������ļ���HDFS
	 * 
	 * @param localFileSrcs�����ļ��б�
	 * @param HDFSFileDst
	 * @throws IOException
	 */
	public void uploadLocalFile(String[] localFileSrcs, String HDFSFileDst) throws IOException;

	/**
	 * ��HDFS�����ļ�������(ע���Ƿ���������Ӳ�̣���������ͻ���Ӳ��)
	 * 
	 * @param HDFSFilePath
	 * @param localFilePath
	 * @throws IOException
	 */
	public void downFileToLocal(String HDFSFilePath, String localFilePath) throws IOException;

	/**
	 * HDFS�ļ���Ŀ¼�Ƿ����
	 * 
	 * @param filePath
	 * @return boolean:�Ƿ����
	 * @throws IOException
	 */
	public boolean exists(String filePath) throws IOException;

	/**
	 * ����·��ɾ���ļ����ļ���
	 * 
	 * @param filePath
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String filePath) throws IOException;

	/**
	 * ���б����ļ���HDFS(ע��Ϊ�����������ļ�);
	 * 
	 * @param src����·��
	 * @param dst�ֲ�ʽ�洢·��
	 * @throws IOException
	 */
	public void moveFromLocalFile(String localSrc, String HDFSDst) throws IOException;

	/**
	 * HDFS�ļ�֮��ĸ���
	 * 
	 * @param srcԴ�ļ�·��
	 * @param dstҪ���ƺ����ļ���·��
	 * @throws IOException
	 */
	public void copyHDFSFile(String src, String dst) throws IOException;

	/**
	 * HDFS���ƶ��ļ�
	 * 
	 * @param srcԴ�ļ�·��
	 * @param dstҪ�ƶ����·��
	 * @throws IOException
	 */
	public void moveHDFSFile(String src, String dst) throws IOException;

	/**
	 * ����HDFS�ļ�������
	 * 
	 * @param HDFSSrc
	 * @param localDst
	 * @throws IOException
	 */
	public void moveToLocalFile(String HDFSSrc, String localDst) throws IOException;

	/**
	 * HDFS�����ļ�
	 * 
	 * @param in������
	 * @param dst�ֲ�ʽ�洢·��
	 * @throws IOException
	 */
	public void create(InputStream in, String dst) throws IOException;

	/**
	 * ��HDFS�����ļ�
	 * 
	 * @param file
	 * @param dst�ֲ�ʽ�洢·��
	 * @throws IOException
	 */
	public void create(File file, String dst) throws IOException;

	/**
	 * ��HDFS�����ļ�
	 * 
	 * @param src�����ļ�·��
	 * @param dst�ֲ�ʽ�洢·��
	 * @throws IOException
	 */
	public void create(String src, String dst) throws IOException;

	/**
	 * ��ȡFileSystem����
	 * 
	 * @return
	 */
	public FileSystem getFileSystem();

	/**
	 * �ر�HDFS
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;

	/**
	 * �ж��Ƿ�Ŀ¼
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 */
	public boolean isDir(String src) throws IOException;

}
