package globis.common.util;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import globis.common.code.service.CommonCodeService;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class ResUtil{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResUtil.class);

	@Resource(name = "commonCodeService")
	private CommonCodeService commonCodeService;
	
	final static String treeColSubTree = "subTree";
	//final static String treeRootParentValue = "root";
	//final static String treeRootParentValueIdx = "0";
	 
	public ResUtil() {
	
	}   
	
	/**
	 * String을  print ajax용으로 사용
	 * @param response,String
	 * @return void
	*/
	public static void printString(HttpServletResponse response,String rtn){
		try {
	    	response.setCharacterEncoding("UTF-8");
	    	response.setContentType("text/html;charset=UTF-8");
	        response.getWriter().print(rtn);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * String을  print ajax용으로 사용
	 * @param response,String
	 * @return void
	*/
	public static void printBoolean(HttpServletResponse response,Boolean rtn){
		printString(response,String.valueOf(rtn));
	}
	
	/**
	 * Object를 json 형식으로 변환하여  print ajax용으로 사용
	 * @param response,Object
	 * @return void
	*/
	public static void printJson(HttpServletResponse response,Object obj){
		Gson gson = new Gson();
		String json =  gson.toJson(obj);
		printString(response,json);
	}
	
	
	/**
	 * 페이지가 있는 목록 조회시 total count를 set
	 * @param set할 대상 map,total count
	 * @return void
	*/
	public static void setTotalCnt(Map<String,Object> commandMap,int totalCnt){
		int page = 0;
		int rows = 10;
		try{
			if(commandMap.get("pageNo")!=null){
				page = Integer.parseInt(commandMap.get("pageNo").toString());
			}
			if(commandMap.get("pageSize")!=null){
				rows = Integer.parseInt(commandMap.get("pageSize").toString());
				commandMap.put("pageSize", rows);
			}
			if(page>0 && rows>0){
				int firstRow = 0;
				if(page>1){
					firstRow = ((page-1)*rows);
				}
				commandMap.put("firstRow",firstRow);
				commandMap.put("lastRow",firstRow+rows);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		commandMap.put("totalCnt",totalCnt);
    } 
	
	/**
	 * 페이지가 있는 목록 조회시 total count를 set
	 * @param set할 대상 map,total count
	 * @return void
	*/
	public static void setLimit(Map<String,Object> commandMap,int limitCnt){
  	  	commandMap.put("firstRow", 0);
  	  	commandMap.put("lastRow", limitCnt);
  	  	commandMap.put("pageSize", limitCnt);
    } 
	
	public static void setLimit(Map<String,Object> commandMap,String limitCnt){
		if(limitCnt!=null && !limitCnt.equals("")){
			setLimit(commandMap,Integer.parseInt(limitCnt));
		}
    }

	/**
	 * axisj 형식의 grid 용 json을 print ajax용으로 사용
	 * @param response,commandMap,list
	 * @return void
	*/
	public static void printGridJson(HttpServletResponse response,Map<String,Object> commandMap,List<Map<String, Object>> list){
		String rtn  = "";
		int page = 0;
		int rows = 0;
		int totalCnt = 0;
		try{
			if(commandMap.get("pageNo")!=null){
				page = Integer.parseInt(commandMap.get("pageNo").toString()); 
			}
			if(commandMap.get("pageSize")!=null){
				rows = Integer.parseInt(commandMap.get("pageSize").toString()); 
			}
			if(commandMap.get("totalCnt")!=null){
				totalCnt = Integer.parseInt(commandMap.get("totalCnt").toString()); 
			}else{
				totalCnt=list.size();
			}
			
			//그리드 페이징 처리
			int total = 1;
			if( totalCnt < rows ){
				total = page;
			}else{
				total = (int) Math.ceil((double)totalCnt/(double)rows);
			}
			Gson gson = new Gson();
			Map<String,Object> map = new HashMap<String,Object>();
			Map<String,Object> pageMap = new HashMap<String,Object>();
			pageMap.put("pageNo", page);
			pageMap.put("pageCount", total);
			pageMap.put("listCount", totalCnt);
			
			StringUtil.getConvertHTMLTag(list);//html filter
			 
			map.put("result","ok");
			map.put("msg","");
			map.put("list",list);
			map.put("page",pageMap);
			
			rtn = gson.toJson(map);
		}catch (Exception e){
			e.printStackTrace();
		}
		printString(response,rtn);
	}
	

	/**
	 * axisj 형식의 결과 값을 json 형식으로 print ajax용으로 사용
	 * @param response,commandMap
	 * @return void
	*/
	public static void printResultJson(HttpServletResponse response,Map<String,Object> commandMap){
		StringUtil.getConvertHTMLTag(commandMap);//html filter 
		String rtn  = ""; 
		try{
			Gson gson = new Gson(); 
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("result","ok");
			map.put("item",commandMap);//html filter
			map.put("msg","");
			rtn = gson.toJson(map);
		}catch (Exception e){
			e.printStackTrace();
		}
		printString(response,rtn);
	}
	
	/**
	 * Object를 json 형식으로 변환하여  print ajax용으로 사용
	 * @param response,Object
	 * @return void
	*/
	public static void printResultJson(HttpServletResponse response,String msg){
		Map obj = new HashMap();
		obj.put("result", msg);
		Gson gson = new Gson();
		String json =  gson.toJson(obj);
		printString(response,json);
	}
	 
	
	/**
	 * axisj tree json 형식으로 print ajax용으로 사용
	 * @param response,list,treeColKey,treeColParentKey
	 * @return void
	*/
	public static void printTreeJson(HttpServletResponse response,List<Map<String,Object>> list,String treeColKey,String treeColParentKey){
		StringUtil.getConvertHTMLTag(list);//html filter
		String rtn  = "";
		int size = list.size();
		List<Map<String,Object>> convList = new ArrayList<Map<String,Object>>();
		try{
			for(int i=0;i<size;i++){
				EgovMap map = ((EgovMap)list.get(i));
				Map convMap = new HashMap();
				
				//EgovMap을 HashMap으로 치환(_가 들어가지 않는 이유로)
				Iterator<String> keys = map.keySet().iterator();
		        while( keys.hasNext() ){
		            String key = keys.next();
		            convMap.put(key,map.get(key));
		        }
		        
		        if(convMap.get("hasSubYn")!=null){
					String hasSubYn = (String)convMap.get("hasSubYn");
					if(hasSubYn.equals("Y")){
						convMap.put("__"+treeColSubTree,true);		
					}
				}
		        convMap.put(treeColSubTree,new ArrayList<Map<String,Object>>());
				convList.add(convMap);
			}
			//for(int i = size - 1; i >= 0; i--){
			for(int i=0;i<size;i++){
				boolean addRtn = addSubTree(convList,(Map<String,Object>)convList.get(i),treeColKey,treeColParentKey);
				/*
				if(addRtn){
					convList.get(i).put("_delYn", "Y");
				}
				*/
			}
			for(int i = size - 1; i >= 0; i--){
				//String delYn = (String)(convList.get(i).get("_delYn"));
				String parentKey = String.valueOf(convList.get(i).get(treeColParentKey));
				//if((delYn!=null && delYn.equals("Y"))
				//LOGGER.info(">>>>"+i);
				//LOGGER.info(parentKey);
				if(convList.get(i).get(treeColParentKey) != null&&!parentKey.equals("")&&!parentKey.equals("root")){
					//LOGGER.info("===리므브");
					convList.remove(i);
				}
			}
			
			Gson gson = new Gson();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("result","ok");
			map.put("msg","");
			map.put("tree",convList);
			rtn = gson.toJson(map);
		}catch (Exception e){
			e.printStackTrace();
		}
		printString(response,rtn);
	}
	
	/**
	 * printTreeJson 에서 호출, 재귀 호출하여 하위 트리로 이동
	 * @param list,map,treeColKey,treeColParentKey
	 * @return void
	*/
	private static boolean addSubTree(List<?> list,Map map,String treeColKey,String treeColParentKey){
		boolean addRtn = false;
		int size = list.size();
		for(int i = size - 1; i >= 0; i--){
			Map<String,Object> hm = (Map<String,Object>)list.get(i);
			List<?> subTreeGetList = (List<?>)hm.get(treeColSubTree);
			if(map.get(treeColParentKey)!=null &&hm.get(treeColKey)!=null){
				String key = String.valueOf(map.get(treeColParentKey));
				String parentKey = String.valueOf(hm.get(treeColKey));
				if(key.equals(parentKey)){
					((ArrayList<Map<String,Object>>)hm.get(treeColSubTree)).add(map);
					addRtn = true;
					break;
				}else{
					addRtn = addSubTree(subTreeGetList,map,treeColKey,treeColParentKey);
					if(addRtn){
						break;
					}
				}
			}
		}
		return addRtn;
	}
	

	
	/**
	 * return page정보와 메세지 를 갖고 목록으로 redirect
	 * @param url,commandMap,msg
	 * @return void
	*/
	public static String redirectList(String url,Map<String, Object> commandMap,String msg){
		String rtn = "";
		try{
			if(msg.equals("")){
				rtn = redirectList(url,commandMap);
			}else{
				rtn = "forward:/common/msgAndRedirect.do?_msg="+URLEncoder.encode(msg, "utf-8")+"&_url="+URLEncoder.encode(url, "utf-8")+"&mode=LIST";
			}
		}catch (Exception e){
			e.printStackTrace();
		} 
		return rtn; 
	}
	
	/**
	 * return page정보를 갖고 목록으로 redirect
	 * @param url,commandMap
	 * @return void
	*/
	public static String redirectList(String url,Map<String, Object> commandMap){
		String redirectUrl = url;
		if(commandMap.get("q")!=null){
			if(url.indexOf("?")>-1){
				redirectUrl += "&"+ResUtil.getQ(commandMap);
			}else{
				redirectUrl += "?"+ResUtil.getQ(commandMap);
			}
		}
		return "redirect:"+redirectUrl;
	}
	
	/**
	 * return page정보,메세지를 갖고 페이지 redirect
	 * @param url,commandMap,msg
	 * @return void
	*/
	public static String redirect(String url,Map<String, Object> commandMap,String msg){
		String rtn = "";
		try{
			if(msg.equals("")){
				rtn = redirect(url,commandMap);
			}else{
				rtn = "forward:/common/msgAndRedirect.do?_msg="+URLEncoder.encode(msg, "utf-8")+"&_url="+URLEncoder.encode(url, "utf-8")+"&mode=NORMAL";
			}
		}catch (Exception e){
			e.printStackTrace();
		} 
		return rtn; 
	}
	
	/**
	 * return page정보를 갖고 페이지 redirect
	 * @param url,commandMap
	 * @return void
	*/
	public static String redirect(String url,Map<String, Object> commandMap){
		String redirectUrl = url;
		if(commandMap.get("q")!=null){
			if(url.indexOf("?")>-1){
				redirectUrl += "&q="+(String)commandMap.get("q");
			}else{
				redirectUrl += "?q="+(String)commandMap.get("q");
			}
		}
		return "redirect:"+redirectUrl;
	}
	
	
	/**
	 * commandMap에 목록 조회조건인 q를 추출하여 decode
	 * @param commandMap
	 * @return q
	*/
	private static String getQ(Map<String,Object> commandMap){
		String q = (String)commandMap.get("q");
		if(!q.equals("")){
			try{
				q = URLDecoder.decode(q, "utf-8");
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return q;
	}

	
	/**
	 * javascript형태로 print
	 * @param response,script
	 * @return void
	*/
	public static void printScript(HttpServletResponse response, String script ) throws Exception{
		StringBuffer resultHTML = new StringBuffer();
		resultHTML.append("<!doctype html>");
		resultHTML.append("<html lang=\"ko\">");
		resultHTML.append("<body>");
		resultHTML.append("    <script type='text/javascript'>");
		resultHTML.append(script);
		resultHTML.append("    </script>");
		resultHTML.append("</body>");
		resultHTML.append("</html>");
		printString(response,resultHTML.toString());
	}
	
	/**
	 * axisj 형식의 grid 용 json을 print ajax용으로 사용
	 * @param response,list
	 * @return void
	*/
	public static void printListJson(HttpServletResponse response,List<Map<String, Object>> list){
		String rtn  = "";
		
		try{
			Gson gson = new Gson();
			Map<String,Object> map = new HashMap<String,Object>();
			
			StringUtil.getConvertHTMLTag(list);//html filter
			 
			map.put("result","ok");
			map.put("msg","");
			map.put("list",list);
			
			rtn = gson.toJson(map);
		}catch (Exception e){
			e.printStackTrace();
		}
		printString(response,rtn);
	}
}