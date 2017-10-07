package globis.common.request;

import egovframework.rte.ptl.mvc.bind.annotation.CommandMap;
import globis.common.login.service.UserSessionVO;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CustomCommandMapArgumentResolver implements HandlerMethodArgumentResolver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCommandMapArgumentResolver.class);
	
	public boolean supportsParameter(MethodParameter parameter) {
		return ((Map.class.isAssignableFrom(parameter.getParameterType())) && (parameter
				.hasParameterAnnotation(CommandMap.class)));
	}

	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory
	) throws Exception {
		
		Map<String,Object> commandMap = new HashMap<String,Object>();
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Enumeration enumeration = request.getParameterNames();
		
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String[] values = request.getParameterValues(key);
			if (values != null) {
				commandMap.put(key, (values.length > 1) ? values : values[0]);
			}
		}
		
		HttpSession session = request.getSession();
		
		//세션이 존재할 경우 commandMap에 기본 키 put
		if(session.getAttribute("userPub")!=null){
			UserSessionVO userVO = (UserSessionVO)session.getAttribute("userPub");
			
			commandMap.put("_memSeqNo", 	userVO.getMemSeqNo());
			commandMap.put("_id", 			userVO.getId());
			commandMap.put("_name", 		userVO.getName());

		}else{
			commandMap.put("_memSeqNo", 	"");
			commandMap.put("_id", 			"");
			commandMap.put("_name", 		"");

		}
		
		if(request.getRequestURI().lastIndexOf(".do")>-1){
			LOGGER.info("===============================================================");
			LOGGER.info(" >>>> RequestURI : " + request.getRequestURI());
			LOGGER.info(" >>>> parameters : " + commandMap.toString());
			LOGGER.info("===============================================================");
		}

		return commandMap;
	}
}