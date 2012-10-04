package de.jutzig.jabylon.rest.api;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestServlet {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL("http://localhost:8080/jabylon/api/petproject/master/template/bar/test.properties");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		PrintStream printStream = new PrintStream(httpCon.getOutputStream());
		printStream.println("anderer_key = total_anderer_value");
		printStream.println("foo = bar");
		printStream.println("lala = blubb");
		printStream.println("narf = blah");
		printStream.close();
		System.out.println(httpCon.getResponseCode());
		System.out.println(httpCon.getResponseMessage());
	}

}
