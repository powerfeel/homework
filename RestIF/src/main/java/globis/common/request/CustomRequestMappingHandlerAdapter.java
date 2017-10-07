package globis.common.request;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
/**
 * @Class Name : CustomRequestMappingHandlerAdapter.java
 * @Description : CustomRequestMappingHandlerAdapter Class
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
public class CustomRequestMappingHandlerAdapter extends
		RequestMappingHandlerAdapter {
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		if (getArgumentResolvers() != null) {
			List resolvers = new ArrayList(getArgumentResolvers()
					.getResolvers());

			int mapMethodProcessorInx = -1;
			int commandMapInx = -1;
			HandlerMethodArgumentResolver commandMapArgResolver = null;

			for (int inx = 0; inx < resolvers.size(); ++inx) {
				HandlerMethodArgumentResolver resolver = (HandlerMethodArgumentResolver) resolvers
						.get(inx);
				if (resolver instanceof MapMethodProcessor)
					mapMethodProcessorInx = inx;
				else if (resolver instanceof CustomCommandMapArgumentResolver) {
					commandMapInx = inx;
				}
			}

			if (commandMapInx != -1) {
				commandMapArgResolver = (HandlerMethodArgumentResolver) resolvers
						.remove(commandMapInx);
				resolvers.add(mapMethodProcessorInx, commandMapArgResolver);
				setArgumentResolvers(resolvers);
			}
		}
	}
}