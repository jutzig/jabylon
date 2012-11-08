package de.jutzig.jabylon.rest.api;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class TestServlet {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		postProject();
//		postProjectVersion();
//		postProjectLocale();
//		postNewProjectLocale("de_CH");
		postProperty();
//		postTranslatedProperty();
	}

	private static void postProject() throws Exception {
		URL url = new URL("http://localhost:8080/jabylon/api/apiproject");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		PrintStream printStream = new PrintStream(httpCon.getOutputStream());
		printStream.close();
		System.out.println(httpCon.getResponseCode());
		System.out.println(httpCon.getResponseMessage());
		
	}
	
	private static void postProjectVersion() throws Exception {
		URL url = new URL("http://localhost:8080/jabylon/api/apiproject/master");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		PrintStream printStream = new PrintStream(httpCon.getOutputStream());
		printStream.close();
		System.out.println(httpCon.getResponseCode());
		System.out.println(httpCon.getResponseMessage());
	}
	
	private static void postProjectLocale() throws Exception {
		URL url = new URL("http://localhost:8080/jabylon/api/apiproject/master/de_FR");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		PrintStream printStream = new PrintStream(httpCon.getOutputStream());
		printStream.close();
		System.out.println(httpCon.getResponseCode());
		System.out.println(httpCon.getResponseMessage());
		
	}

	
	
	private static void postNewProjectLocale(String locale) throws Exception {
		URL url = new URL("http://localhost:8080/jabylon/api/apiproject/master/"+locale);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		PrintStream printStream = new PrintStream(httpCon.getOutputStream());
		printStream.close();
		System.out.println(httpCon.getResponseCode());
		System.out.println(httpCon.getResponseMessage());
		
	}
	
	private static void postProperty() throws IOException {
		URL url = new URL("http://localhost:8080/jabylon/api/apiproject/master/foobbbbbbarrrr/test.properties");
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
	
	private static void postTranslatedProperty() throws IOException {
		URL url = new URL("http://localhost:8080/jabylon/api/apiproject/master/neu/test2_ch_DE.properties");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		PrintStream printStream = new PrintStream(httpCon.getOutputStream());
		printStream.println("anderer_key = übersetzt");
		printStream.println("foo = übersetzt");
		printStream.println("lala = übersetzt");
		printStream.println("narf = übersetzt");
		printStream.close();
		System.out.println(httpCon.getResponseCode());
		System.out.println(httpCon.getResponseMessage());
		
	}
	

}
