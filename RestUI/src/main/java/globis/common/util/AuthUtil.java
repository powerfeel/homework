package globis.common.util;
import globis.common.code.service.CommonCodeService;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
/**
 * @Class Name : GridUtil.java
 * @Description : Axisj Grid의 포맷에 맞는 json형식 변환 Class
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
public class AuthUtil{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthUtil.class);

	public AuthUtil() {
	
	}
	
	public static boolean hasAuth(Map<String,Object> commandMap,String empNo,String deptCd){
		String _authScopeCd = (String)commandMap.get("_authScopeCd");
		String _empNo = (String)commandMap.get("_empNo");
		String _deptCd = (String)commandMap.get("_deptCd");
		boolean rtn = false;
		if(_authScopeCd.equals("A")){//전체
			rtn =true;
		}else if(_authScopeCd.equals("D")){//부서
			if(_deptCd.equals(deptCd))rtn =true;
		}else if(_authScopeCd.equals("P")){//개인
			if(_empNo.equals(_empNo))rtn =true;
		}
		return rtn;
    } 
}