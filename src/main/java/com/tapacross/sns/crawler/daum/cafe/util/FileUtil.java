package com.tapacross.sns.crawler.daum.cafe.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	
	public static void writeToFile( String filename, String content ) throws IOException {
		
		BufferedWriter bw = null; 
		File f = new File( filename.replaceAll("/[^/]*$","") ) ;
			
		try {
			
			if( !f.exists()) { 
				f.mkdirs();
			}

			bw = new BufferedWriter(new FileWriter(filename));
			bw.write( content ); 
			bw.flush(); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally { 
			if( bw != null ) { 
				bw.close(); 
			}
		}
	}

	/**
	 * 파일이 존재하지 않을 경우 빈문자열을 리턴한다.
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String readFromFile(String filename) throws IOException {
		return readFromFile(filename, "");
	}
	
	/**
	 * 구분자를 입력할 경우 한줄마다 구분자를 붙인다.
	 * @param filename
	 * @param lineSeparator
	 * @return
	 * @throws IOException
	 */
	public static String readFromFile(String filename, String lineSeparator) throws IOException {
		return readFromFile(filename, lineSeparator, "utf-8");
	}
	
	/**
	 * os에 따라 실행시 인코딩이 깨지는 현상이 발생할 경우 사용한다. 주로 utf-8 인코딩을 한다. 대부분 리눅스에서는 인코딩 문제가 없으나
	 * 윈도우에서는 한글이 정상 출력되지 않는다.
	 * @param filPath
	 * @param lineSeparator
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readFromFile(String filePath, String lineSeparator, String encoding) throws IOException {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			String line = null;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
			while((line = reader.readLine()) != null) {
				sb.append( line + lineSeparator);            
			}
		} catch( FileNotFoundException e ) { 
		} catch(IOException ex) {
			throw ex; 
		} finally { 
			if( reader != null ) { 
				reader.close();
			}
		}
		
		return sb.toString();
	}
	
	/**
	*
	* 파일을 오픈한뒤 File 객체를 돌려준다. 파일이 존재하지 않을 경우 FileNotFoundException 발생
	*/	
	public static File Open(String path) throws FileNotFoundException {
		File file = new File(path);
		if(!file.exists()){
			throw new FileNotFoundException("file ["+file.getAbsolutePath()+"] not found");
		}
		return file;
	}
	
	/**
	*
	* 파일을 오픈한뒤 File 객체를 돌려준다. 파일이 존재하지 않거나 디렉토리일 경우 FileNotFoundException 발생
	*/	
	public static File OpenFile(String path) throws FileNotFoundException {
		File file = Open(path);
		if(file.isDirectory()){
			throw new FileNotFoundException("file ["+file.getAbsolutePath()+"'] is directory");
		}		
		return file;
	}
	
	/**
	*
	* 파일을 오픈한뒤 File 객체를 돌려준다. 파일이 존재하지 않거나 디렉토리가 아닐 경우 FileNotFoundException 발생
	*/	
	public static File OpenDirectory(String path) throws FileNotFoundException {
		File file = Open(path);
		if(!file.isDirectory()){
			throw new FileNotFoundException("file ['"+file.getAbsolutePath()+"] is not directory");
		}		
		return file;
	}
	
	/**
	*
	* 파일을 오픈한뒤 File 객체를 돌려준다. create가 true일때 디렉토리가 존재하지 않을 경우 디렉토리 생성 아닐경우 FileNotFoundException 발생
	*/	
	public static File OpenDirectory(String path, boolean create) throws FileNotFoundException {
		File file = null;
		try{
			file = OpenDirectory(path);
		}
		catch(FileNotFoundException e){
			if(create){
				file = new File(path);
				file.mkdir();
			}
			else{
				throw  e;
			}			
		}
		return file;
	}
	
	
	
	
	public static void writeObjectToFile(Object object, String fileName) throws IOException, FileNotFoundException{
		File tempFile;
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		
		tempFile = new File(fileName+"__");
		
		if(tempFile.exists()){
			tempFile.delete();
		}
		
		try {
		 	fileOutputStream  = new FileOutputStream (tempFile);
			objectOutputStream  = new ObjectOutputStream (fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			throw e;
		}
		finally{
			try{
				if(objectOutputStream != null) {
					objectOutputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
			finally{
				if(objectOutputStream != null) {
					objectOutputStream.close();
				}
			}
		}
		
		File file = new File(fileName);
		if(file.exists()){
			file.delete();
		}
		tempFile.renameTo(file);		
	}
	
	public static Object readObjectFromFile(String fileName) throws IOException, ClassNotFoundException, FileNotFoundException{
		Object object = null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		
		try {
			fileInputStream = new FileInputStream(fileName);
			objectInputStream = new ObjectInputStream(fileInputStream);
			object = objectInputStream.readObject();
			
		} catch (IOException e) {
			throw e;
		}
		finally{
			try{
				if(objectInputStream != null){
					objectInputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
			finally{
				if(objectInputStream != null){
					objectInputStream.close();
				}		
			}
		}
		return object;		
	}
	
	public static byte[] writeObjectToByteArray(Object object) throws IOException{
		
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		
		try {
	
			byteArrayOutputStream  = new ByteArrayOutputStream (4096);
			
			objectOutputStream  = new ObjectOutputStream (byteArrayOutputStream);
			
			objectOutputStream.writeObject(object);
			
		} catch (IOException e) {
			throw e;
		}
		finally{
			try{
				if(objectOutputStream != null) {
					objectOutputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
			finally{
				if(objectOutputStream != null) {
					objectOutputStream.close();
				}
			}
		}
		return byteArrayOutputStream.toByteArray();
		
	}
	
	public static Object readObjectFromByteArray(byte[] byteArray) throws IOException, ClassNotFoundException{
		
		Object object = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		
		try {
			byteArrayInputStream = new ByteArrayInputStream(byteArray);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			object = objectInputStream.readObject();
			
		} catch (IOException e) {
			throw e;
		}
		finally{
			try{
				if(objectInputStream != null){
					objectInputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
			finally{
				if(byteArrayInputStream != null){
					byteArrayInputStream.close();
				}	
			}
		}
		return object;		
		
	}
	
	private static List<File> listFiles(File parentFile, String pattern)
	{
		List<File> fileList = new ArrayList<File>();
		for(File file : parentFile.listFiles()){
			if(file.exists()){   //work/20111115231124215.json => save/20111115231124215.json
				if(file.isFile()){//"[0-9]+.json$"
					if(pattern == null || file.getName().matches(pattern)){
							fileList.add(file);
					}					
				}				
			}
		}
		return fileList;
	}

	
	public static void writeObjectToByteArray(Object object, String workDir, String fileName, String date) throws IOException, FileNotFoundException{
		File tempFile;
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		
		tempFile = new File(workDir+fileName+"__");
		
		if(tempFile.exists()){
			tempFile.delete();
		}
		
		try {
		 	fileOutputStream  = new FileOutputStream (tempFile);
			objectOutputStream  = new ObjectOutputStream (fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (IOException e) {
			throw e;
		}
		finally{
			try{
				if(objectOutputStream != null) {
					objectOutputStream.close();
				}
			} catch (IOException e) {
				throw e;
			}
			finally{
				if(objectOutputStream != null) {
					objectOutputStream.close();
				}
			}
		}
		
		File file = new File(workDir+fileName);
		if(file.exists()){
			file.delete();
		}		
				
		tempFile.renameTo(file);
		
	}
	
	public static String readObjectFromByteArray(String workDir, String saveDir) throws IOException, ClassNotFoundException, FileNotFoundException{
		File workFile = null;
		Object object = null;
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		
		File fileDir = new File(workDir);
		File[] fileList = fileDir.listFiles();
		String fileName = null;
		if( fileDir.exists() ){
			
			for(File file : fileList){
					if(file.getName().toLowerCase().endsWith(".xml")){
						fileName = file.getName();
						workFile = file;
						break;
					}
			}
			if(workFile == null)return null;
			if(fileName == null) return null;
		}
		
		try {
		
			
			fileInputStream = new FileInputStream(workFile);
			objectInputStream = new ObjectInputStream(fileInputStream);
			object = objectInputStream.readObject();
			
		} catch (IOException e) {
			throw e;
		}
		finally{
			try{
				if(objectInputStream != null){
					objectInputStream.close();
				}
			} catch (IOException e) {
				throw e;				
			}
			finally{
				if(objectInputStream != null){
					objectInputStream.close();
				}		
			}
		}
		File completeFile = new File(saveDir, fileName);
		workFile.renameTo(completeFile);
		
		return object.toString();
	}
	
	
	
}
