package globis.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder; 


public final class I18nUtil {

//    public static final String DEFAULT_CHARSET = "MS949";
//    public static final String KOR_CHARSET = "MS949";
	public static final String DEFAULT_CHARSET = "EUC-KR";
    //public static final String KOR_CHARSET = "EUC-KR";
	public static final String KOR_CHARSET = "EUC-KR";
    public static final String ENG_CHARSET = "ISO-8859-1";
    
    private I18nUtil() {}

    /**
    * 영문을 한글로 Conversion해주는 프로그램.
    * @param english 한글로 바꾸어질 영문 String
    * @return 한글로 바꾸어진 String
    */
    public static String E2K(String english) {
        String korean = null;
    
        if (english == null ) {
            return null;
        }
        
        try { 
            korean = new String(english.getBytes(ENG_CHARSET), KOR_CHARSET);
        } catch (UnsupportedEncodingException e) {
            korean = new String(english);
        }

        return korean;
    }

    /**
    * 한글을 영문으로 Conversion해주는 프로그램.
    * @param korean 영문으로 바꾸어질 한글 String
    * @return 영문로 바꾸어진 String
    */
    public static String K2E(String korean) {
        String english = null;
        
        if (korean == null ) {
            return null;
        }
        
        try { 
            english = new String(korean.getBytes(KOR_CHARSET), ENG_CHARSET);
        } catch (Exception e) {
            english = new String(korean);
        }

        return english;
    }

    /**
     * Method encode.
     * @param s
     * @return String
     */
    public static String encode(String s) {
        try {
            return URLEncoder.encode(s, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

}











