package globis.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @Class Name : UrlUtil.java
 * @Description : Url 패턴 분석 Class
 * @Modification Information
 * @
 * @  수정일      		수정자                수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.02.10   	도정훈 	       최초생성
 *
 * @author 그로비스인포텍 프레임웍 개발팀
 * @since 2015. 02.10
 * @version 1.0
 */

public class StringUtil{
	
	public StringUtil() {
	
	}
	
	public static List<Map<String,Object>> getConvertHTMLTag(List<Map<String,Object>> list) {
		for(int i=0;i<list.size();i++){
			getConvertHTMLTag(list.get(i));
		}
		return list;
	}
	
	public static Map<String,Object> getConvertHTMLTag(Map<String,Object> map) {
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			Object obj = map.get(key);
			//if(obj.getClass().getCanonicalName().equals("java.lang.String")){
			if(obj instanceof String ){
				map.put(key, getConvertHTMLTag((String)obj));
			}
		}
		return map;
	}
	
	public static String getConvertHTMLTag(String value) {
		StringBuffer strBuff = new StringBuffer();
		for (int j = 0; j < value.length(); ++j) {
			char c = value.charAt(j);
			switch (c) {
			case '<':
				strBuff.append("&lt;");
				break;
			case '>':
				strBuff.append("&gt;");
				break;
			case '&':
				strBuff.append("&amp;");
				break;
			case '"':
				strBuff.append("&quot;");
				break;
			case '\'':
				strBuff.append("&apos;");
				break;
			default:
				strBuff.append(c);
			}
		}
		return strBuff.toString();
	}
	
	/**
	 * commandMap에서 배열 형태의 값을 get하여 배열로 반환(그냥 get면 1개일 경우 String형태로 return됨)
	 * @param commandMap,key
	 * @return String[]
	*/
	public static String[] getArrayFromMap(Map<String,Object> commandMap,String key){
		String[] rtn = new String[0];
		Object obj = commandMap.get(key);
		if(obj!=null){
			if(obj instanceof String){
				rtn = new String[1];
				rtn[0] = (String)obj; 
			}else{
				String[] target = (String[])obj;
				rtn = new String[target.length];
				for(int i=0;i<target.length;i++){
					rtn[i] = (String)target[i];
				}
			}
		}
		return rtn;
	}
	
	public static boolean isNumber(String s) {
	    try {
	        Double.parseDouble(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	public static String mapToParam( Map map ){
		StringBuffer param = new StringBuffer();
		Set set = map.keySet();
		Iterator iter = set.iterator();
		int i=0;
		while(iter.hasNext()){
			String key = (String)iter.next();
			String value = String.valueOf(map.get(key));
			if(i>0)param.append("&");
			param.append(key+"="+encode(value));
			i++;
		}
		return param.toString();
	}
	
	public static String encode( String url ){
		String rtn = "";
		try{
			rtn = URLEncoder.encode(url,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return rtn;
	}
	public static String decode( String url ){
		String rtn = "";
		try{
			rtn = URLDecoder.decode(url,"UTF-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return rtn;
	}
	
	public static String makeTrnKey() throws Exception {
    	//영문대문자 아스키 65~90 26자
    	//영문소문자 아스키 97~122 26자
    	//10+26+26=62
    	Random rnd =new Random();
    	StringBuffer buf =new StringBuffer();

    	//현재 날짜를 받아옴. 
    	Calendar t = Calendar.getInstance();
    	//오늘날짜의 해당연도를 구함
    	String year = Integer.toString(t.get(Calendar.YEAR));
    	//오늘날짜의 해당 월을 구함. 1월은 0이기 때문에 + 1
    	String month = Integer.toString((t.get(Calendar.MONTH) + 1));
    	//한자리 숫자, 예를 들면 1월인 경우 "01"등으로 문자열 변환
    	month = month.length() > 1 ? month : "0" + month;
    	//오늘의 날짜 구함
    	String day = Integer.toString(t.get(Calendar.DAY_OF_MONTH));
    	day = day.length() > 1 ? day : "0" + day;
    	buf.append(year+month+day);
    	
    	for(int i=0;i<8;i++){
    		int ranInt = rnd.nextInt(62);//0~61까지
    	    if(ranInt<10){
    	    	buf.append(ranInt); 
    	    }else if(ranInt<(10+26)){
    	    	buf.append((char)((int)(ranInt)+65-10));
    	    }else{
    	    	buf.append((char)((int)(ranInt)+97-10-26));
    	    }
    	}
    	return buf.toString();
    }
	
	public static String getNowTime() throws Exception {
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmm");
		return dayTime.format(new Date(time));
	}
	public static Object nvl(Object obj,Object obj2) throws Exception {
		if(obj==null){
			return obj2;
		}else{
			return obj;
		}
	}
	
}