/* (c) Copyright 2020 Paul Nguyen. All Rights Reserved */

package umlparser ;

import java.io.*;
import java.nio.file.*;

/*
    Apache HTTP Client 3.0 (Legacy)

    http://hc.apache.org/httpclient-legacy/index.html
    https://hc.apache.org/httpclient-3.x/apidocs/
*/

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

/**
 *  yUML Diagram Generator
 */
class YUML {

	private boolean DEBUG = true ;
	private String basedir = "" ;

	public YUML( String basedir ) {
		this.basedir = basedir ;
	}

	public String generate( String dsl, String testcase ) {

		String testdir = "" ;
		String outdir = "" ;

		/* Setup Test and Output Directories */
		testdir = basedir + File.separator + "testcases" + File.separator + testcase ;
		outdir = basedir + File.separator + "output" + File.separator + testcase ;

		if (DEBUG) {
			System.out.printf("basedir: %s%n", basedir) ;
			System.out.printf("testdir: %s%n", testdir) ;
			System.out.printf("outdir: %s%n", outdir) ;
		}

		try {

			String uml_image = "" ;
			String image_id = "" ;
			HttpClient httpclient = new HttpClient();

			/* 	POST DSL Text to Generate Diagram
				Result Expected back is Image ID
				of Diagram to Download from yUML Servers.
			*/
			PostMethod post = new PostMethod("https://yuml.me/diagram/scruffy/class");
			NameValuePair[] data = {
				new NameValuePair("dsl_text", dsl)
			};
			post.setRequestBody(data);
			try {
				httpclient.executeMethod(post);
				if (DEBUG)
					System.out.println("yUML POST STATUS: " + post.getStatusLine());
				InputStream uid = post.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(uid));
				String line;
				StringBuffer buffer = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
					buffer.append("\n");
				}
				reader.close();
				uml_image = buffer.toString();
				image_id = uml_image.split("\\.")[0];
				if (DEBUG)
					System.out.println("yUML Image ID: " + image_id ) ;
			} finally {
				post.releaseConnection();
			}

			/* 	Download Generated Diagram
				in JPG Image Format.
			*/
			GetMethod httpget = new GetMethod("https://yuml.me/" + image_id + ".jpg");
			try {
				httpclient.executeMethod(httpget);
				if (DEBUG)
					System.out.println("yUML GET STATUS: " + httpget.getStatusLine());
				InputStream in = httpget.getResponseBodyAsStream();
				copyInputStreamToFile( in, outdir + File.separator + testcase + ".jpg") ;
			} finally {
				httpget.releaseConnection();
			}

			/* Save DSL Code */
			File file = new File( outdir, testcase + ".yuml" ) ;
			if ( file.exists() )
				file.delete() ;
			try {
				String path = outdir + File.separator + testcase + ".yuml" ;
				Files.write( Paths.get(path), dsl.getBytes());
			} catch ( Exception e ) {
				System.out.println( e ) ;
			}

		} catch (Exception e) {
			System.err.println( e ) ;
		}

		return outdir + File.separator + testcase + ".jpg" ;
	}

	private void copyInputStreamToFile(InputStream inputStream, String filename) throws IOException {
		File file = new File(filename) ;
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			int read;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}
	}

}