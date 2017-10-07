package globis.common.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import globis.common.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import egovframework.com.cmm.service.Globals;

/***********************
 * 특정 웹페이지를 호출
 * 즉 특정 URL의 웹프로그램을 실행 시키는게 목적이거나
 * 특정 URL의 내용을 읽어 오는게 목적일시 
 * @author 강광묵
 * @since 2014.05.21.
 ***********************/
public class URLConnection {
private static final Logger LOG = Logger.getLogger(URLConnection.class.getClass());
	
	private static final int BUFFER_SIZE = 4096;
	
	public static String callInServer( String path,Map map ) throws Exception{
		return callUrl( "http://"+Globals.COM_CONN_URL+path, map );
	}
	public static String callOutServer( String path,Map map ) throws Exception{
		return callUrl( "http://"+Globals.FORWARD_CONN_URL+path, map );
	}
	private static String callUrl( String strUrl,Map map )  throws Exception{
		String param = "";
		if(map!=null)param=StringUtil.mapToParam(map);
		StringBuffer bff = new StringBuffer();
		
		try{		
			URL outServer = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection)outServer.openConnection();
		    urlConn.setDoOutput(true);
		    urlConn.setRequestMethod("POST");
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));
	    
		    bw.write(param);
		    bw.flush();
		    bw.close();
		    /* read */
		    InputStream inputStream = urlConn.getInputStream();
		    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		    BufferedReader br = new BufferedReader(inputStreamReader);
		    String str = "";
		    int i=0;
		    while((str = br.readLine()) != null){
		    	if(i>0)bff.append("\n");
		    	bff.append(str);
		    }
			br.close();
			inputStreamReader.close();
			inputStream.close();
			urlConn.disconnect();
		}catch (Exception e)  {
			e.printStackTrace();
			throw e;
		}
		return bff.toString();  
	}
	
	public static void viewInServerImage( String path,HttpServletResponse response)throws Exception{
		try{
			URL outServer = new URL("http://"+Globals.COM_CONN_URL+path);
			
			HttpURLConnection urlConn = (HttpURLConnection)outServer.openConnection();
		    urlConn.setDoOutput(true);
		    urlConn.setRequestMethod("GET");
		    
		    /* read */
		    InputStream inputStream = urlConn.getInputStream();
		    ServletOutputStream out = response.getOutputStream();
	    
		    response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition","attachment;filename=image.jpg");
		    /* read */
		    int bytesRead = -1;
		    byte[] buffer = new byte[BUFFER_SIZE];
		    while ((bytesRead = inputStream.read(buffer)) != -1) {
		    	out.write(buffer, 0, bytesRead);
	        }
		    out.close();
			inputStream.close();
			urlConn.disconnect();
	    }catch (Exception e)  {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static String getShortUrl( String targetUrl) throws Exception{
		String returnUrl = targetUrl;
		if(Globals.API_SHORT_URL_MODE.equals("NAVER")){
			returnUrl = getNaverShortUrl(targetUrl);
		}else if(Globals.API_SHORT_URL_MODE.equals("GOOGLE")){
			returnUrl = getGoogleShortUrl(targetUrl);
		}
		return returnUrl;
	}
	

	//naver:1일 250000건
	public static String getNaverShortUrl( String targetUrl) throws Exception{
		String returnUrl = "";
		String url = "https://openapi.naver.com/v1/util/shorturl?url="+StringUtil.encode(targetUrl);
		Gson gson = new Gson();
		try{
    		URL outServer = new URL(url);
		    HttpURLConnection urlConn = (HttpURLConnection)outServer.openConnection();
		    urlConn.setDoOutput(true);
		    urlConn.setRequestMethod("POST");
		    urlConn.setRequestProperty("User-Agent", "curl/7.43.0");
		    urlConn.setRequestProperty("Content-Type", "application/json");
		    urlConn.setRequestProperty("X-Naver-Client-Id", Globals.API_NAVER_SHORT_URL_ID);
		    urlConn.setRequestProperty("X-Naver-Client-Secret", Globals.API_NAVER_SHORT_URL_SECRET);
		    
		    /* read */
		    BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		    String jsonStr="";
		    try{
		    	String inputLine;
			    while((inputLine = br.readLine()) != null){
			    	jsonStr+=inputLine;
			    }
			    HashMap map = gson.fromJson(jsonStr, HashMap.class);//다른 클래스(InnerClass)를 생성하여
			    LinkedTreeMap result = (LinkedTreeMap)map.get("result");
			    returnUrl = (String)result.get("url");
		    }catch (Exception e){
		    	e.printStackTrace();
		    	returnUrl = targetUrl;
		    }finally{
		    	br.close();
		    }
		}catch (Exception e){
	    	e.printStackTrace();
	    	returnUrl = targetUrl;
	    	LOG.error(returnUrl);
	    }
		return returnUrl;    
	}
	
	//google 1일 10만건
	//https://console.developers.google.com
	public static String getGoogleShortUrl( String targetUrl) throws Exception{
		 String returnUrl = "";
		 String url = "https://www.googleapis.com/urlshortener/v1/url?key="+Globals.API_GOOGLE_SHORT_URL_KEY;
			
		Gson gson = new Gson();
		try{
    		URL outServer = new URL(url);
		    HttpURLConnection urlConn = (HttpURLConnection)outServer.openConnection();
		    urlConn.setDoOutput(true);
		    urlConn.setRequestMethod("POST");
		    //urlConn.setRequestProperty("User-Agent", "toolbar");
		    urlConn.setRequestProperty("Content-Type", "application/json");
		    String pars = "{\"longUrl\":\""+targetUrl+"\"}";
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));
    	    
		    bw.write(pars);
		    bw.flush();
		    bw.close();
		     
		    
		    /* read */
		    BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		    String jsonStr="";
		    try{
		    	String inputLine;
			    while((inputLine = br.readLine()) != null){
			    	jsonStr+=inputLine;
			    }
			    HashMap map = gson.fromJson(jsonStr, HashMap.class);//다른 클래스(InnerClass)를 생성하여
			    returnUrl = (String)map.get("id");
		    }catch (Exception e){
		    	e.printStackTrace();
		    	returnUrl = targetUrl;
		    }finally{
		    	br.close();
		    }
		}catch (Exception e){
	    	e.printStackTrace();
	    	returnUrl = targetUrl;
	    }
		return returnUrl;    
	}
}
