package globis.common.session;

import globis.common.util.ResUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import com.google.gson.Gson;

@Controller
public class SessionCheckController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionCheckController.class);
		
	/** 
	 * AJAX FormSubmit시에 Session값 체크 프로세스
	 * @param String
	 * @return void
	 * @exception Exception
	 */
	@RequestMapping(value="/common/sessionCheck.do")
	public void SessionCheck( Map<String, Object> commandMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		LOGGER.info("SessionCheck Call");
		
		//세션 추출
		HttpSession session = request.getSession();

		//json 결과 맵핍용
		Map<String,Object> map = new HashMap<String,Object>();
		
		try{
			if(session.getAttribute("userPub")==null){
				map.put("result",	 "9");
				map.put("resultMsg", "세션값이 초기화되었습니다.");
			}else{
				map.put("result",	 "1");
				map.put("resultMsg", "세션값이 정상적입니다.");
			}
		}catch( Exception ex ){			
			map.put("result",	 "9");
			map.put("resultMsg", "세션값이 초기화되었습니다.");
			LOGGER.debug("Error : {}", ex.getMessage());
		}
		
		//JSON 생성
		Gson gson = new Gson();
		String rtn = gson.toJson(map);
		
		ResUtil.printString(response, rtn);

	}
}