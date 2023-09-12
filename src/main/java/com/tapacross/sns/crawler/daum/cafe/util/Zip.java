package com.tapacross.sns.crawler.daum.cafe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class Zip {

	public static void deleteAfterCompress(String inFilename, String outFilename) throws IOException  {

	    GZIPOutputStream out = null;
		File f = new File( outFilename.trim().replaceAll("/[^/]*$","") ) ;
	    
		try {
			
			if( !f.exists()) { 
				f.mkdirs();
			}			
			out = new GZIPOutputStream(new FileOutputStream(outFilename));
			 // Open the input file
		    FileInputStream in = new FileInputStream(inFilename);

		    // Transfer bytes from the input file to the GZIP output stream
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		    out.flush();
		    in.close();

		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			if( out != null ) { 
				out.finish();
			    out.close();
			}

		    File file = new File(inFilename);
		    if( file.exists() ) { 
		    	file.delete();
		    }
		}

	   
	    
		
	}

}
