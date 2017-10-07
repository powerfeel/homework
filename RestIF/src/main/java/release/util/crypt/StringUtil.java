package release.util.crypt;

/**
 * Class Name : StringUtil.java
 * Description : SEED256 암복화관련 StringUtil Class
 * Modification Information
 * @
 * @  수정일              수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2016.12.30    최형준 	       최초생성
 *
 * @author 그로비스 최형준 차장
 * @since 2016.12.30
 * @version 1.0
 */

public class StringUtil {

	/**
	 * (length - str.length) 만큼 앞에 0을 추가한다.
	 * 
	 * @param String
	 * @param Int length
	 * @return 0
	 */
	public static String addZero (String str, int length) {
		String temp = "";
		for (int i = str.length(); i < length; i++)
			temp += "0";
		temp += str;
		return temp;
	}

	/**
	 * 입력된 값에 따른 널값 체크
	 * 
	 * @param String
	 * @return true/false
	 */
	public static boolean isEmpty(String str) {
		if(str == null || str.length() == 0) {
			return true;
		}else {
			return false;
		}
	}
}
