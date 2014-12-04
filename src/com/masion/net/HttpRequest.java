package com.masion.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpRequest{
	public HttpRequest( String url ) throws Exception {
	    m_Url = new URL( url );
	}

	public final static String boundary = "---------------------------7d71b526e00e4";
	public final static String prefix = "--";
	public final static String newLine = "\r\n";

	public final static String fileContentType = "Content-Type: application/octet-stream\r\n\r\n";
	public final static String fileContentDisposition = "Content-Disposition: form-data; name=\"%name\"; filename=\"%fileName\"\r\n";
	public final static String paramContentDisposition = "Content-Disposition: form-data; name=\"%name\"\r\n\r\n";

	private URL m_Url = null;
	private URLConnection uc = null;
	private Map m_fileMap = new HashMap();
	private Map m_paraMap = new HashMap();
	long m_FileTotalSize = 0;

	public void AddFile(String name, String filename){
		File file = new File( filename );
	    if( file.exists() ){
	      m_FileTotalSize += file.length();
	      m_fileMap.put(name, file);
	    }
	}

	public void AddFile(String name, File file){
	    if( file.exists() ){
	      m_FileTotalSize += file.length();
	      m_fileMap.put(name, file);
	    }
	}

	public void AddParam( String name, String value){
		m_FileTotalSize += value.length();
		m_paraMap.put(name, value);
	}

	public void download() throws Exception {
		uc = m_Url.openConnection();
		uc.setDoInput( true );
//		uc.setRequestProperty( "Accept", "*/*" );
//		uc.setRequestProperty( "Accept-Language", "ja" );
//		uc.setRequestProperty( "Content-Type","text/xml; charset=utf-8");
//		uc.setRequestProperty( "Content-Length","100");
//		uc.setRequestProperty( "Accept-Encoding", "gzip, deflate" );
//		uc.setRequestProperty( "Connection", "Keep-Alive" );
		uc.setRequestProperty( "Cache-Control", "no-cache" );

//		InputStreamReader isr = new InputStreamReader(uc.getInputStream(),"utf-8");
//		BufferedReader in = new BufferedReader(isr);
//
//		String inputLine;
//		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:/result.html")));
//		while ((inputLine = in.readLine()) != null){
//		     System.out.println(inputLine);
//		     bw.write(inputLine);
//		     bw.newLine();
//		    }
//		bw.close();
//	    in.close();

	}

	public void Upload() throws Exception {
		try{
			uc = m_Url.openConnection();
			uc.setDoOutput( true );
			uc.setRequestProperty( "Accept", "*/*" );
			uc.setRequestProperty( "Accept-Language", "ja" );
			uc.setRequestProperty( "Content-Type", "multipart/form-data; boundary=" + boundary);
			uc.setRequestProperty( "Accept-Encoding", "gzip, deflate" );
			uc.setRequestProperty( "Connection", "Keep-Alive" );
			uc.setRequestProperty( "Cache-Control", "no-cache" );

			DataOutputStream dos = new DataOutputStream( uc.getOutputStream() );

			for(Iterator iterator = m_fileMap.keySet().iterator(); iterator.hasNext();){
				String key = (String)iterator.next();
				File file = (File)m_fileMap.get(key);
				String fcd = fileContentDisposition.replaceAll("%name", key);
				fcd = fcd.replaceAll("%fileName", file.getName());

				dos.writeBytes(prefix + boundary + newLine);
				dos.writeBytes(fcd);
				dos.writeBytes(fileContentType);

				FileInputStream fstram = new FileInputStream(file);
				byte[] buf = new byte[4000];
				int len = 0;
				while (len != -1) {
					len = fstram.read(buf);
					if(len>0){
						dos.write(buf, 0, len);
					}
				}
				dos.writeBytes(newLine);
				fstram.close();
			}

			for(Iterator iterator = m_paraMap.keySet().iterator();iterator.hasNext();){
				String key = (String)iterator.next();
				String value = (String)m_paraMap.get(key);

				String fcd = paramContentDisposition.replaceAll("%name", key);
				dos.writeBytes(prefix + boundary + newLine);
				dos.writeBytes(fcd);
				dos.writeBytes(value);
				dos.writeBytes(newLine);
			}

			dos.writeBytes(prefix + boundary + prefix + newLine);

			dos.close();
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
	    }
	}

	public InputStream getInputStream() throws Exception{
	    if(uc==null){
	      return null;
	    }
	    return uc.getInputStream();
	}

	public static void main(String[] args) throws Exception {

		HttpRequest httpRequestMaker= new HttpRequest("http://www.baidu.com");
		httpRequestMaker.download();
		InputStream is = httpRequestMaker.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = 0;
        while((i = bis.read()) != -1){
              baos.write(i);
        }
        System.out.println(baos.toString());

        bis.close();
        is.close();
	}

	/**
	 *
	 * @param timeout
	 */
	public void setReadTimeout(int timeout){
		if(uc == null){
			return;
		}
		uc.setReadTimeout(timeout);
	}
}
