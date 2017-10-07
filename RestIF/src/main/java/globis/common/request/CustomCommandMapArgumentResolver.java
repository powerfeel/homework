package globis.common.request;

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

import egovframework.rte.ptl.mvc.bind.annotation.CommandMap;

/**
 * @Class Name : CustomCommandMapArgumentResolver.java
 * @Description : request를 commandMap으로 변환(user session 정보를 '_'를 앞에 붙여 추가)
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
public class CustomCommandMapArgumentResolver implements
		HandlerMethodArgumentResolver {
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
		HttpServletRequest request = (HttpServletRequest) webRequest
				.getNativeRequest();
		Enumeration enumeration = request.getParameterNames();
		
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String[] values = request.getParameterValues(key);
			if (values != null) {
				commandMap.put(key, (values.length > 1) ? values : values[0]);
			}
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