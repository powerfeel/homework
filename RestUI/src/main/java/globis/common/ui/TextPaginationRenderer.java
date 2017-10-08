/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package globis.common.ui;

import java.text.MessageFormat;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer;


public class TextPaginationRenderer implements PaginationRenderer{

	public String firstPageLabel;
	public String previousPageLabel;
	public String currentPageLabel;
	public String otherPageLabel;
	public String nextPageLabel;
	public String lastPageLabel;
	
	/**
    * PaginationRenderer
	* 
    * @see 개발프레임웍크 실행환경 개발팀
    */
	public TextPaginationRenderer() {
	
	}
	public String renderPagination(PaginationInfo paginationInfo,String jsFunction){
		String context = jsFunction.split(",")[0];
		jsFunction = jsFunction.split(",")[1];
		
		String strWebDir = context+"/images/";
		String firstPageLabel = "<a href=\"javascript:{0}({1});\"><img src=\""+strWebDir+"ppre.gif\" width=\"23\" alt=\"처음\">";
		String previousPageLabel = " <a href=\"javascript:{0}({1});\"><img src=\""+strWebDir+"pre.gif\" width=\"23\" alt=\"이전\">";
		String currentPageLabel = "<a href=\"javascript:{0}({1});\" alt='현재 페이지'><span>{2}</span></a>";
		String otherPageLabel = "<a href=\"javascript:{0}({1});\" alt='페이지 이동'>{2}</a>";
		String nextPageLabel = "<a href=\"javascript:{0}({1});\"><img src=\""+strWebDir+"next.gif\" width=\"23\" alt=\"다음\">";
		String lastPageLabel = " <a href=\"javascript:{0}({1});\"><img src=\""+strWebDir+"nnext.gif\" width=\"23\" alt=\"마지막\">";
		
		
		StringBuffer strBuff = new StringBuffer();
        
        int firstPageNo = paginationInfo.getFirstPageNo();
        int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
        int totalPageCount = paginationInfo.getTotalPageCount();
		int pageSize = paginationInfo.getPageSize();
		int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
		int currentPageNo = paginationInfo.getCurrentPageNo();
		int lastPageNo = paginationInfo.getLastPageNo();
		int totalRecordCount = paginationInfo.getTotalRecordCount();
		
		if(totalRecordCount>0){
			if(totalPageCount > pageSize){
				if(firstPageNoOnPageList > pageSize){
					strBuff.append("<div class=\"pre\">");
					strBuff.append(MessageFormat.format(firstPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNo)}));
					strBuff.append(MessageFormat.format(previousPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNoOnPageList-1)}));
					strBuff.append("</div>");
		        }else{
		        	strBuff.append("<div class=\"pre\">&nbsp;");
					strBuff.append("</div>");
		        	//strBuff.append(MessageFormat.format(firstPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNo)}));
					//strBuff.append(MessageFormat.format(previousPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNo)}));
		        }
			}else{
				strBuff.append("<div class=\"pre\">&nbsp;");
				strBuff.append("</div>");
			}
			
			strBuff.append("<div class=\"num\">");
			for(int i=firstPageNoOnPageList;i<=lastPageNoOnPageList;i++){
				if(i==currentPageNo){
	        		//strBuff.append(MessageFormat.format(currentPageLabel,new Object[]{Integer.toString(i)}));
	        		strBuff.append(MessageFormat.format(currentPageLabel,new Object[]{jsFunction,Integer.toString(i),Integer.toString(i)}));
	        	}else{
	        		strBuff.append(MessageFormat.format(otherPageLabel,new Object[]{jsFunction,Integer.toString(i),Integer.toString(i)}));
	        	}
	        }
			strBuff.append("</div>");
	        
			if(totalPageCount > pageSize){
				if(lastPageNoOnPageList < totalPageCount){
					strBuff.append("<div class=\"next\">");
		        	strBuff.append(MessageFormat.format(nextPageLabel,new Object[]{jsFunction,Integer.toString(firstPageNoOnPageList+pageSize)}));
		        	strBuff.append(MessageFormat.format(lastPageLabel,new Object[]{jsFunction,Integer.toString(lastPageNo)}));
		        	strBuff.append("</div>");
		        }else{
		        	strBuff.append("<div class=\"next\">&nbsp;");
		        	strBuff.append("</div>");
		        	//strBuff.append(MessageFormat.format(nextPageLabel,new Object[]{jsFunction,Integer.toString(lastPageNo)}));
		        	//strBuff.append(MessageFormat.format(lastPageLabel,new Object[]{jsFunction,Integer.toString(lastPageNo)}));
		        }
			}else{
				strBuff.append("<div class=\"next\">&nbsp;");
	        	strBuff.append("</div>");
			}
		}
		return strBuff.toString();
	}
	
    
}
