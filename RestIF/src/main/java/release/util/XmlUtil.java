package release.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;

/*************************
 * XmlUtil
 * 공통
 * @author 최형준
 * @since 2016.06.19
 * @version 1.0
 ************************/
public class XmlUtil {
	private static final Logger LOGGER = Logger.getLogger(XmlUtil.class.getClass());
	
	/**
	 * Document RootElement 생성
	 * @param 루트명
	 * @return Document
	 * @author 최형준
	 */
	public Document make(String rootStr){
		Element element = new Element(rootStr);
		Document document = new Document(element);
		return document;
	}
	
	/**
	 * Element 추가
	 * @param Element : element, HashMap : 첫번째 인자안에 들어갈 데이터셋
	 * @return Element
	 * @author 최형준
	 */
	public Element addElement(Element targetElement, LinkedHashMap<String, Object> data){
		
		Iterator<String> iter = data.keySet().iterator();
		
		while(iter.hasNext()){
			String key = iter.next();
			Element childElement = new Element(key);
			if(data.get(key) instanceof LinkedHashMap){
				addElement(childElement, (LinkedHashMap) data.get(key));
			}else{
				childElement.addContent(new CDATA((String) data.get(key)));
			}
			targetElement.addContent(childElement);
		}
		
		return targetElement;
	}
}
