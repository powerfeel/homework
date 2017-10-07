/**
 * @Class Name : common.js
 * @Description : Axisj 의 공통 사용을 위한 js
 * @Modification Information
 * @
 * @  수정일      		수정자                수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.02.25   	도정훈 	       최초생성
 *
 * @author 그로비스인포텍 프레임웍 개발팀
 * @since 2015. 02.25
 * @version 1.0
 * @see 
 *
 *  Copyright (C) by Globis Infotech All right reserved.
 */

$.ajaxSettings.traditional = true;
$.ajaxSettings.cache = false;

$(function(){
	//onsubmit 무효
	/*
	$( "form" ).submit(function( event ) {
		  event.preventDefault();
	});
	*/
	/*
	$(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
	*/
	//$("input:text,input:checked,select").first().focus();
});


/**
 * axisj modal 생성 
 * @example
  
  var modal = new ModalUtil();
  modal.open({
  	url:"/test.jsp"
  	.
  	.
  });
 */
var ModalUtil = function(){
	var that = this;
	this.modal = new AXModal();
	this.open = function(data){
		var width = 400;
		//var height = 400;
		//var top=-2000;
		var pars = "";		
		if(data.pars) pars = data.pars;
		if(data.width) width = data.width;
		//if(data.height) height = data.height;
		//if(data.top) top = data.top;
		//if(top==0){
		//	top = Math.max(0, ($(window).height() - height) / 2+$(window).scrollTop() );
		//}
		//var top = $(window).scrollTop();
		if(data.onclose!=undefined){
			this.modal.setConfig({
				displayLoading: true,
				onclose: function(){
					data.onclose.call();
				}
			});
		}else{
			this.modal.setConfig({
				onclose: function(){
					
				}
			});
		}
		this.modal.open({
			  url 			: data.url
			, pars 			: pars
			, width			: width			
			//, height		: height
			//, top			: top
			, closeByEscKey	: true	
		});
		setTimeout(function(){
			that.resize();
		},1000);
	};
	
	this.resize = function(data){
		var _winID = that.modal.winID;
		var _popwinID = that.modal.config.windowID;
		var myIframe = $("#"+_winID);
		var height = $(myIframe).contents().find('body').height()+28;
		$(myIframe).height(height);
	}; 
	
	this.openDiv = function(data){
		this.modal.openDiv(data);
	}; 
	
	this.close = function(){
		this.modal.close();
	};
	
	this.deptOpen = function(data){
		this.open({
			url:CONTEXT+"/common/pop/deptPop.do?flag="+data,
			width:500
		});	
	};
	
	this.emplOpen = function(data){
		this.open({
			url:CONTEXT+"/common/pop/emplPop.do?flag="+data,
			width:500
		});	
	};
};
/*
var ModalUtil2 = function(){
	this.modal = new AXModal();
	this.open = function(data){
		var width = 400;
		var pars = "";
		if(data.pars)pars=data.pars;
		if(data.width)width=data.width;
		this.modal.open({
			url : data.url
			,pars : pars
			,width:width
			//,top:100
			,closeByEscKey:true			
		});
	};
	
	this.modal.setConfig({
		displayLoading: true,
		onclose: function(){
			if(typeof listGrid.setList != "undefined"){
				listGrid.setList();
			}
		}
	});
	
	this.openDiv = function(data){
		this.modal.openDiv(data);
	}; 
	
	this.close = function(){
		this.modal.close();
	};
	
	this.deptOpen = function(){
		this.open({
			url:CONTEXT+"/common/pop/deptPop.do",
			width:400
		});	
	};
	this.emplOpen = function(){
		this.open({
			url:CONTEXT+"/common/pop/emplPop.do",
			width:400
		});	
	};
};
*/
/*
 * {maxFileSize:100 
 * ,maxFileCount:10
 * ,fileType:all,image..
 * ,fileId:""
 * ,uploadCompleted:"" //필수
 * ,targetId:"fileArea" //필수
 * ,keyId:"fileId" //필수
 */
var FileUtil = function(data){
	if(!data.targetId){
		toast.push("파일업로드 컴퍼넌트 생성 오류<br/>targetId 는 필수 입니다.");
		return;
	}
	if(!$("#"+data.targetId)[0] ){
		toast.push("파일업로드 컴퍼넌트 생성 오류<br/>"+data.targetId+"오브젝트가 존재하지 않습니다.");
		return; 
	}
	if(!data.keyId){
		toast.push("파일업로드 컴퍼넌트 생성 오류<br/>keyId 는 필수 입니다.");
		return;
	}
	if(!$("#"+data.keyId)[0] ){
		toast.push("파일업로드 컴퍼넌트 생성 오류<br/>"+data.keyId+"오브젝트가 존재하지 않습니다.");
		return;
	}
	var maxFileSize=10240;
	var maxFileCount=1000;
	var fileType="all";
	var uploadCompleted="";
	var usePreview=false;
	
	if(data.maxFileSize)maxFileSize=data.maxFileSize;
	if(data.maxFileCount)maxFileCount=data.maxFileCount;
	if(data.fileType)fileType=data.fileType;
	if(data.uploadCompleted)uploadCompleted=data.uploadCompleted;
	if(data.usePreview)usePreview=data.usePreview;
	
	//var viewTotalSize = data.viewTotalSize==undefined?true:data.viewTotalSize;//총파일크기 보기 여부(true);
	//var viewAllDownload = data.viewAllDownload==undefined?true:data.viewAllDownload;//전체다운로드 보기 여부(true);
	
	
	var fileId = $("#"+data.keyId).val();
	if(fileId==undefined)fileId="";

	this.fileObj;	
	if(data.uploadCompleted){
		this.fileObj = new jwork.fileUpload(data.targetId
			, CONTEXT 
			, fileId
			, {maxFileSize: maxFileSize
				, uploadCompleted:uploadCompleted
				, beanId:"null"
				, fileType:fileType
				, uploadMode:"db"
				, maxFileCount:maxFileCount
				, usePreview:usePreview
				, useSecurity:false
				, keyId:data.keyId
		});
		if(fileId==""){
			this.fileObj.hideBtnAllDown();
			this.fileObj.hidePreview();			
		} else{
			if(usePreview){ 
				this.fileObj.changePreview(fileId,-1);	
			}
		}
		/*
jwork.fileUpload.prototype.changePreview = function(fileId, seq) {
	if(!fileId) {
		fileId = "";
	}	
	if(!seq) {
		seq = "";
	}
	var element = jQuery("#" + this.elementId);
	var img = element.find("#spanThumbnail").find("img").get(0);
	jQuery(img).attr("src", this.ctx + "/jfile/preview.do?fileId=" + fileId + "&fileSeq=" + seq+ "&beanId="+this.beanId+"&uploadPathKey="+this.uploadPathKey+"&useSecurity="+this.useSecurity+"&uploadMode="+this.uploadMode);
};
		 
		 */
	}else{
		if(fileId!=""){
			this.fileObj = new jwork.fileDownload(data.targetId
					, CONTEXT 
					, fileId
					, {maxFileSize: maxFileSize
						, uploadCompleted:uploadCompleted
						, beanId:"null"
						, fileType:fileType
						, uploadMode:"db"
						, maxFileCount:maxFileCount
						, usePreview:usePreview
						, useSecurity:false
						, keyId:data.keyId
				});
			if(usePreview){ 
				this.fileObj.changePreview(fileId,-1);	
			}
		}
	}
	
	//upload를 시작하고 uploadCompleted 펑션 실행
	this.startUpload = function(){
		this.fileObj.startUpload();
	};
	this.getFileCnt = function(){
		var fileAreaTable = jQuery("#" + data.targetId).find("#fileAreaTable tr");
		return fileAreaTable.length;
	};
	//파일 id가 없을 경우 전체다운로드 버튼 안보이도록

	
	
	
};


/**
 * axisj tree 생성 
 * @example
  
  var tree = new TreeUtil();
  tree.setTree({
  	url:"/test.jsp"
  	treeId:"divId"
  	.
  	.
  });
 */
var TreeUtil = function(){
		this.tree = new AXTree();
		this.initExpand = true;
		this.listUrl = "";
		this.formName = "";
		this.init = false;
		var that = this;
		this.colGroup  = [];
		
		
		this.getCheckedList = function(num){
			if(!num)num = 0;
			return that.tree.getCheckedList(num); 
		};
		
		this.getSelectedValue = function(colName){
			var obj = that.tree.getSelectedItem();
			var rtn = "";
			if(obj && obj.item && obj.item[colName]){
				rtn = obj.item[colName];
			}
			return rtn; 
		};
		this.expandAll = function(level){
			if(level=="ALL"){
				that.tree.expandAll();
			}else{
				that.tree.expandAll(level);
			}
		};
		//this.removeTree = function(obj){
		//	that.tree.removeTree(obj.index, obj.item);
		//};
		this.getSelectedList = function(){
			return that.tree.getSelectedList();
		};
		

		this.addCol = function(data){
			if(data.type=="row_check"){
				this.colGroup.push({
	        		label:"NO"
	        		,key:data.key
	        		,width:40
	        		,align:"center"	
	        		,formatter:"checkbox"
        			,checked:function(){
							//사용 가능한 변수
							//this.index
							//this.item
							//this.list
        					//alert(this.item[data.key])
							return (this.item[data.key] == "Y");
					}
	        		,onclick:function(){
	        			
	        		}
//	        		,editorFormatter:function(){
//	        			
//	        		}
	        	});
				this.rowCheckbox = true;
			}else if(data.type=="tree"){
				this.colGroup.push({
						key:data.key,
						label:data.label, 
						width:data.width==undefined?"100%":data.width,
						align:"left",
						indent:true,
						getIconClass: function(){
							//folder, AXfolder, movie, img, zip, file, fileTxt, fileTag
							//var iconNames = "folder, AXfolder, movie, img, zip, file, fileTxt, fileTag".split(/, /g);
							var iconName = "";
							//CommonUtil.out(this.item)
							//alert(this.item.__subTreeLength)
							if(this.item.__subTreeLength>0){
								iconName = "folder";
							}else{
								iconName = "file";
							}
						//	iconName = "folder";
							//alert(iconName)
							//var iconName = icon;
							//if(this.item.type) iconName = iconNames[this.item.type];
							return iconName;
						}
						//,disabled:data.disabled
				
				});
			}else{
				this.colGroup.push(data);
			}
		};
		
		this.setTree = function(data){
			this.init = true;
			var url = data.listUrl;//URL(필수)
			var treeId = data.treeId;//grid table ID (필수)
//			var fitToWidth = data.fitToWidth==undefined?false:data.fitToWidth;
			var height = data.height==undefined?"auto":data.height;
			var onClickBody = data.onClickBody==undefined?null:data.onClickBody;
			var expandAjax = data.expandAjax==undefined?false:data.expandAjax;
			//var icon = data.icon==undefined?"folder":data.icon;
			//var treeColNm = data.treeColNm==undefined?"tree_nm":data.treeColNm;
			var pk = data.pk==undefined?"tree_id":data.pk;
			var fitToWidth = data.fitToWidth==undefined?false:data.fitToWidth;
			var fk = data.fk==undefined?"parent_tree_id":data.fk;
			var type = data.type==undefined?"none":data.type;
			var headDisplay = false; 
			this.formName = data.formName;
			if(data.initExpand!=undefined)this.initExpand = data.initExpand;
			//if(expandAjax==true){
			//	this.expand = false;
			//}
			var theme = "AXTree_none";
			if(type=="list"){
				theme = "AXTree";
				headDisplay = true; 
			}
			
			this.listUrl = url; 
			
			this.tree.setConfig({
				targetID : treeId, 
				theme : theme,
				xscroll:true,//?
				indentRatio:1,
				colHeadAlign: "center", // 헤드의 기본 정렬 값
				//showConnectionLine:true,
				reserveKeys:{
					parentHashKey:"pHash", // 부모 트리 포지션
					hashKey:"hash", // 트리 포지션
					openKey:"open", // 확장여부
					subTree:"subTree", // 자식개체키
					displayKey:"display" // 표시여부
				},
				relation:{
					parentKey:fk,
					childKey:pk,
					openKey: "open"
				},
				fitToWidth:fitToWidth,
				height:height,
				colHead: {
					display:headDisplay
				},
				colGroup : this.colGroup,
				body : {
					onclick:onClickBody,
					onexpand:function(){
						//if(expandAjax){
							if(this.subTree.length == 0){ //자식개체가 없으므로.. subTree 호출 처리 합니다.
								var pars = fk+"="+this.item[pk]+"&loadMode=AJAX";
								var index = this.index, item = this.item;
								/////that.tree.setLoading(index, item); // 화살표를 loading mark 로 전환 합니다.
								
								new AXReq(url, {debug:false, pars:pars, onsucc:function(res){
									if(res.result == AXUtil.ajaxOkCode){
										//trace(res.tree);
										//alert(1)
										that.tree.appendTree(index, item, res.tree);
									}else{
										trace(res);
									}
									//myTree.endLoading(index, item); // 화살표를 loading mark 로 전환 합니다.
								}, onerr:null});
								
								/*
								$.post(url,param,function(res){
									if(res.result == AXUtil.ajaxOkCode){
										that.tree.appendTree(index, item, res.tree);
									}
									//that.tree.endLoading(index, item); // 화살표를 loading mark 로 전환 합니다.
								},"json");
								*/
							} 
						//}
					}
					/*
					,
					onexpand: function(){
						if(this.subTree.length == 0){ //자식개체가 없으므로.. subTree 호출 처리 합니다.
							var index = this.index, item = this.item;
							that.tree.setLoading(index, item); // 화살표를 loading mark 로 전환 합니다.
							var url = that.listUrl;
							var pars = "parent_menu_id="+this.item['menu_id'];
							new AXReq(url, {debug:false, pars:pars, onsucc:function(res){
								if(res.result == AXUtil.ajaxOkCode){
									trace(res.tree);
									that.tree.appendTree(index, item, res.tree);
								}else{
									trace(res);
								}
								that.tree.endLoading(index, item); // 화살표를 loading mark 로 전환 합니다.
							}, onerr:null});
						}
					}
					*/
				}
			});
		    this.getTree();
		};
		
		/*
		var list = $("form[name='"+this.formName+"'] input:text,form[name='"+this.formName+"'] input:hidden,form[name='"+this.formName+"'] input:checked,form[name='"+this.formName+"'] select");
		
		//if($("input[name="+objectName+"]:checked").val()==undefined){
		
		var cnt = 0;
		list.each(function(i, el){
			if(el.name!="" && el.name!="q"&& el.value!="" ){
				if(cnt>0)q+="&";
				q+=el.name+"="+el.value;
				cnt++;
			}
		});
		*/
		
		
		this.getTree = function(){
			//toast.push(sortBy);
			//var sortBy = myGrid.getSortParam("one");
			that.tree.setTree({
				ajaxUrl: that.listUrl,
				
				
				dataType:"json",
				//method:"POST",
				//contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
				//contentType: "application/json",
				//cache: false,
				ajaxPars: $("form[name='"+that.formName+"']").serialize(),
				onLoad: function(){
					//toast(this);
					if(that.initExpand){
						that.tree.expandAll();
					}
				}
			}); 
		};
		
		
		this.upDownTree = function(data){
			var url  = data.url;
			var way  = data.way;//UP,DOWN
			var key  = data.key;
			
			
			//obj.item
			var obj = that.tree.getSelectedList();
			if(obj.error){
				toast.push("메뉴를 선택하세요.");
				return;
			}
			var moveAble = false;
			if(way=="UP"){
				moveAble = that.tree.moveUpTree();
			}else if(way=="DOWN"){
				moveAble = that.tree.moveDownTree();
			}
			if(moveAble){
				var keyValue = obj.item[key];
				var param = key+"="+keyValue+"&way="+way;
				
				$.post(url,param,function(result){
					if(result=="ok"){
						
					}else{
						CommonUtil.alert("이동 실패");
						that.tree.getTree();
					}
				},"text");
			}
		};
		
		
		this.moveTree = function(data){
			var url  = data.url;
			var key  = data.key;
			var parentKey  = data.parentKey;
			var success  = data.success;
			
			that.tree.moveTree({
				startMove: function(){
					that.tree.addClassItem({
						className:"disable", 
						addClass:function(){
							return (this.nodeID == "N");
						}
					});
				},
				validate:function(){
					//this.moveObj
					//this.targetObj
					if(this.targetObj.nodeID == "N"){
						CommonUtil.alert("이동할 수 없는 대상을 선택하셨습니다.");
						return false;
					//}else{
					//	return true;	
					}
					return true;
				},
				endMove: function(){
					//obj.item
					var obj = that.tree.getSelectedList();
					var parentObj = that.tree.getSelectedListParent();
				
					var keyValue = obj.item[key];
					var parentKeyValue = parentObj.item[key];
					var param = key+"="+keyValue+"&"+parentKey+"="+parentKeyValue;
					
					//var myTree = that;
					$.post(url,param,function(result){
						if(result=="ok"){
							
						}else{ 
							CommonUtil.alert("이동 실패");
						}
						that.getTree();
						if(success)success();
					},"text");
					
					 					
					/*
					that.tree.removeClassItem({
						className:"disable", 
						removeClass:function(){
							return (this.nodeID == "N");
						}
					});
					*/
					
				} 
			});
		};	
		
};

/**
 * axisj grid 생성 
 * @example
  
  var grid = new GridUtil();
  grid.addCol({label:"메뉴수",key:"menu_cnt",width:70,align:"right"});
  grid.setGrid({
  	url:"/test.jsp"
  	gridId:"gridId"
  	.
  	.
  });
 */
//var checkMode = "";
var GridUtil = function(){
		this.grid = new AXGrid();
		this.lastQ = "";
		this.formName = "";
		this.listUrl = "";
		this.defaultSortBy = "";
		var that = this;
		this.colGroup  = [];
		this.rowCheckbox = false;
		this.paging = true;
		this.onload;
		this.getColGroup = function(){
			return this.colGroup;
		};
		
		this.setHeight = function(height){
			that.grid.setHeight(height);
		}; 
		
//		this.setWidth = function(width){
//			that.grid.setHeight(height);
//		}; 
		
		this.emptyCol = function(){
			this.colGroup = [];
		};
		
		this.downloadExcel = function(url,title){
			if(that.grid.getList().length==0){
				toast.push("조회된 데이터가 없습니다.");
				return;
			}  
			if(!title)title=$("#_title").html();
			if(title.indexOf("<span>")>-1){
				title = title.substring(0,title.indexOf("<span>"));
			}
			
           
			var num = 0;
			var colGroupTemp = new Array();
			for(var i=0;i<this.colGroup.length;i++){
				var groupMap = this.colGroup[i];
				var key = groupMap.key;
                var formatter = groupMap.formatter;
                var display = groupMap.display;
                if(key!="asc"&&key!="desc"&&key!="row_check"&&display==true&&formatter==undefined){
                	groupMap.label = groupMap.label.replace(/<br>/gi,"").replace(/<br\/>/gi,"").replace(/<\/br>/gi,"");
                	colGroupTemp[num]=groupMap;
                	num++;
                }
			}
			//alert(Object.toJSON(colGroupTemp) )
			var form = $("<form />").attr("method", "post").attr("action", url);
			form.append( $("<input />").attr("type", "hidden").attr("name", "q").attr("value", this.lastQ) );
			form.append( $("<input />").attr("type", "hidden").attr("name", "_colGroup").attr("value", Object.toJSON(colGroupTemp) ));
			form.append( $("<input />").attr("type", "hidden").attr("name", "_excelTitle").attr("value", title ));
			$("body").append(form);
			form.get(0).submit();
			form.remove();
		};
		
		this.checkRow = function(){
			var obj = that.grid.getSelectedItem();
			that.grid.checkedColSeq(0, null, obj.index); // 바디를 클릭했을때 0번째 체크박스 토글체크처리
		};
			
		this.getSelectedValue = function(colName){
			var obj = that.grid.getSelectedItem();
			var rtn = "";
			if(obj && obj.item && obj.item[colName]){
				rtn = obj.item[colName];
			}
			return rtn; 
		};
		
		this.scrollTop = function(index){
			return that.grid.scrollTop(index); 
		};
		
		this.getCheckedList = function(num){
			if(!num)num = 0;
			return that.grid.getCheckedList(num); 
		};
		
		this.getList = function(){
			return that.grid.getList();
		}
		
		this.pushList = function(selectItems){
			/** 요고 추가 **/		
			if(this.grid.list.length > 0) {
				return that.grid.pushList(selectItems, this.grid.list.length);				
			} else {
				return that.grid.pushList(selectItems);
			}
		}
		
		
		this.setHeight = function(height){
			return that.grid.setHeight(height);
		}
		
		this.removeList = function(selectItems){
			return that.grid.removeList(selectItems);
		}
//		this.getCheckedListWithIndex = function(num){
//			if(!num)num = 0;
//			return that.grid.getCheckedListWithIndex(num); 
//		};
//		
		this.addCol = function(data){
			if(data.type=="row_check"){
				//쓰기 권한이 있을 경우만 row체크박스 활성화
				if(menuWriteAbleYn=="Y"){
					this.colGroup.push({
		        		label:"NO"
		        		,key:"row_check"
		        		,width:40
		        		,align:"center"	
		        		,formatter:"checkbox"
	        			,checked:function(){
							//사용 가능한 변수
							//this.index
							//this.item
							//this.list
	    					return (this.item[data.key] == "Y");
						} 
		        		,onclick:function(){
		        			
		        		}
	//	        		,editorFormatter:function(){
	//	        			 
	//	        		}
		        	});
					this.rowCheckbox = true;
				}
			}else if(data.type=="desc"){
					//쓰기 권한이 있을 경우만 row체크박스 활성화
					this.colGroup.push({
						key: "desc", label: "번호", width: "50", align:"center", formatter:function(){
							return this.page.listCount-(this.page.pageSize*(this.page.pageNo-1))-this.index;
		        	},sort:false
				});
			}else if(data.type=="asc"){
				//쓰기 권한이 있을 경우만 row체크박스 활성화
				this.colGroup.push({
					key: "asc", label: "번호", width: "50", align:"center", formatter:function(){
						return (this.index + 1) + ( ( this.page.pageNo - 1) * this.page.pageSize );
					},sort:false
				});
			}else{
				//if(data.formatter!=undefined){
					//alert(data.formatter)
				//}
				data.tooltip = function() {
					if(this.value!=undefined){
                    	return this.value;
					}else{
						return "";
					}
                };
				this.colGroup.push(data);
			}
		};
		this.setGrid = function(data){
			var url = data.listUrl;//URL(필수)
			var editorUrl = data.editUrl;//edit URL
			
			//쓰기 권한이 없을 경우 inline editor 기능이 실행되지 않음
			if(menuWriteAbleYn=="N"){
				editorUrl = null;
			}
			
			var formName = data.formName;//form name(필수)
			//var colModel = data.colModel;//컬럼모델(필수)
			var gridId = data.gridId;//grid table ID (필수)
			var fixedColSeq = data.fixedColSeq==undefined?-1:data.fixedColSeq;
			var pageNo = data.pageNo==undefined||data.pageNo==""?1:data.pageNo;//페이지(기본값1)
			var pageSize = data.pageSize==undefined||data.pageSize==""?10:data.pageSize;//행(기본값10)
			var fitToWidth = data.fitToWidth==undefined?false:data.fitToWidth;
			var height = data.height==undefined?"400":data.height;
			//var width = data.width==undefined?"100%":data.width;
			var sortBy = data.sortBy==undefined?"":data.sortBy;
			var onClickBody = data.onClickBody==undefined?null:data.onClickBody;
			var onCheckBody = data.onCheckBody==undefined?null:data.onCheckBody;
			var onCheckHead = data.onCheckHead==undefined?null:data.onCheckHead;
			var onDblClickBody = data.onDblClickBody==undefined?null:data.onDblClickBody;
			//var editor = data.editor==undefined?null:data.editor;
			var paging = data.paging==undefined?true:data.paging;
			var colHead = data.colHead;
			
			this.formName = formName;
			this.listUrl = url;
			this.defaultSortBy = sortBy;
			this.paging = paging;
			this.onload = data.onload;

			
			//edit url 이 존재할 경우 setEditor 활성화
			if(editorUrl){
				onClickBody = function(){
					that.setEditor(this);
				};
			//체크박스가 존재하고 body onlick이벤트가 존재히자 않을 시 body를 클릭하면 체크박스가 체크되도록 함
			}else if(!onClickBody && this.rowCheckbox){
				
				
				
				
				
				onClickBody = function(){
					var obj = that.grid.getSelectedItem();
					that.grid.checkedColSeq(0, null, obj.index); // 바디를 클릭했을때 0번째 체크박스 토글체크처리
				};
				
				
				
				
				
				
			}
	
			//editor 추가 시작
			var editorRows = [];
			var editorRowsTemp = [];
			var btnColArr = [];
			for(var i=0;i<this.colGroup.length;i++){
				//var row = colModel[i];
				var row = {colSeq:i, align:this.colGroup[i].align, valign:"middle"};
				if(this.colGroup[i].formAlign != undefined){
					row['align'] = this.colGroup[i].formAlign;
				}else if(this.colGroup[i].align != undefined){
					row['align'] = this.colGroup[i].align;
				}
				if(this.colGroup[i].editorFormatter != undefined){
					row['formatter'] = this.colGroup[i].editorFormatter;
				}
				if(this.colGroup[i].form != undefined){
					row['form'] = this.colGroup[i].form;
				}
				if(this.colGroup[i].AXBind != undefined){
					row['AXBind'] = this.colGroup[i].AXBind;
				}
				if(this.colGroup[i].key=="row_check" || (this.colGroup[i].formatter != undefined && this.colGroup[i].key!="asc"&& this.colGroup[i].key!="desc"
														&& typeof this.colGroup[i].formatter == "function")
					){
					var text = this.colGroup[i].formatter.toString().toLowerCase();
					if(text=="checkbox"
							||text.indexOf("<button")>-1
							||text.indexOf("<input")>-1
							||text.indexOf("<select")>-1
							||text.indexOf("commonutil.make")>-1
						){
						btnColArr.push(i);
					}
				}
				editorRowsTemp.push(row);
			}
			
			
			editorRows.push(editorRowsTemp);
			//editor 추가 끝

			this.grid.setConfig({
				targetID : gridId, 
				theme : "AXGrid",
				colHeadAlign: "center", // 헤드의 기본 정렬 값
				fixedColSeq:fixedColSeq,
				fitToWidth:fitToWidth,
				height:height,
				colGroup : this.colGroup,
				body : {
					onclick:function(){
						if(btnColArr.indexOf(parseInt(this.c,10))==-1){
							onClickBody.call(this);
						}
					}
					,
					oncheck:function(idx){
						if(idx){
							if(onCheckBody)onCheckBody.call(this);
						}else{
							if(onCheckHead)onCheckHead.call(this);
						}
					}
					,ondblclick : onDblClickBody
			
				},
				page:{
					paging:paging,
					pageNo:pageNo,
					pageSize:pageSize,
					status:{formatter: null}
				},
				editor:{
					rows: editorRows,
						request:{ajaxUrl:editorUrl},
						response: function(){ // ajax 응답에 대해 예외 처리 필요시 response 구현
							// response에서 처리 할 수 있는 객체 들
							//trace({res:this.res, index:this.index, insertIndex:this.insertIndex, list:this.list, page:this.page});
							if(this.error){
								//trace(this);
								toast.push(this.error);
								return;
							}
							//toast.push("index:"+this.index);
							//trace(this.res.item);
							if(this.index == null){ // 추가
								//var pushItem = this.res.item;
								//listGrid.pushList(pushItem, this.insertIndex);
								that.setList(true);
								//that.grid.reloadList();
							}else{ // 수정
								//AXUtil.overwriteObject(this.list[this.index], this.res.item, true); // this.list[this.index] object 에 this.res.item 값 덮어쓰기
								//that.grid.updateList(this.index, this.list[this.index]);
								//that.grid.reloadList();
								that.setList();
							}
							
						},
						onkeyup: function(event, element){
							//if(event.keyCode == 13 && element.name != "title") listGrid.saveEditor();
							//else if(event.keyCode == 13 && element.name == "title") listGrid.focusEditorForm("regDate");
						}
					},
				
	                colHead : {
	                    onclick: function(){
	                    	if(paging==true){
	                    		//alert(paging)
	                    		//toast.push(Object.toJSON({index:this.index, r:this.r, c:this.c, colHead:this.colHead, page:this.page}));
	                    		that.setList(false);
	                    	}
	                    }
	                }
					
			});
		    this.setList(true,true);
		};
		this.addRow = function(){
			this.grid.appendList({});
		};
		this.disabledEditBtn = function(){
			$("#gridList_AX_editorButtons_AX_save").attr("disabled", true);
			$("#gridList_AX_editorButtons_AX_delete").attr("disabled", true);
		};
		this.activeEditBtn = function(){
			$("#gridList_AX_editorButtons_AX_save").attr("disabled", false);
			$("#gridList_AX_editorButtons_AX_delete").attr("disabled", false);
		};
		
		this.setList = function(defaultSortMode,firstLoad){
			mask.open();
			this.setLastQuery(defaultSortMode);
			this.grid.page.pageNo=1;
			this.grid.setList({
				ajaxUrl: this.listUrl,
				dataType:"json",
				method:"POST",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
				//contentType: "application/json",
				cache: false,
				ajaxPars: that.lastQ,
				onLoad: function(){
					if(that.onload&&firstLoad){
						that.onload.call();
					}
				} 
			});
			mask.close(); 
			//that.setQ();//조회조건 저장을 위한 펑션(q hidden으로 담는다)
		};
		this.setEditor = function(obj){
			if(this.rowCheckbox && obj.c==0){
        		this.grid.checkedColSeq(0, null, obj.index);// 바디를 클릭했을때 0번째 체크박스 체크처리
        		this.grid.cancelEditor();
        	}else{
        		this.grid.setEditor(obj.item, obj.index);
        	}
		};
		this.setLastQuery = function(defaultSortMode){
			var sortBy = ""; 
			if(defaultSortMode){
				sortBy=this.defaultSortBy;
			}else{
				//sortBy = this.grid.getSortParam("one");
				//sortBy = this.grid.getSortParam().sortKey;
				//alert(this.grid.getSortParam("one"))
				//alert(this.defaultSortBy)
				
				if(this.grid.getSortParam("one")!=""){
					sortBy=this.grid.getSortParam("one").split("=")[1];
				}else{
					sortBy=this.defaultSortBy;	
				}
			}

			//카멜 표기 sort를 "_" 구분 표기법으로 변경 예)empNo > emp_no
			var sortByC = "";
			for(var i=0;i<sortBy.length;i++){
				var patt = new RegExp("[A-Z]");
				if(patt.test(sortBy.charAt(i))){
					sortByC+="_"+sortBy.charAt(i).toLowerCase();
				}else{
					sortByC+=sortBy.charAt(i);
				}
			}
			//sortByC=sortByC.toUpperCase();
			//sort 표기법 변환 끝
			
			var q = "";
			var list = $("form[name='"+this.formName+"'] input:text,form[name='"+this.formName+"'] input:hidden,form[name='"+this.formName+"'] input:checked,form[name='"+this.formName+"'] select");
			var cnt = 0;
			list.each(function(i, el){
				if(el.name!="" && el.name!="q"&& el.value!="" ){
					if(cnt>0)q+="&";
					q+=el.name+"="+encodeURI(el.value);
					cnt++;
				}
			});
			if(q!="")q += "&";
			q += "sortBy="+encodeURI(sortByC);
			
			
			
			
			this.lastQ = q;
		}; 
		this.removeRows = function(deleteUrl,colNames,paramName) {
			if(!paramName)paramName = colNames;
        	var checkedList = this.grid.getCheckedList(0);// colSeq
			if(checkedList.length == 0){
				CommonUtil.alert("선택된 목록이 없습니다. 삭제할 건을 선택하세요");
				return;
			}
			CommonUtil.confirm("선택한 "+checkedList.length+"건을 삭제 하시겠습니까?",function(){
				var paramJson = {};
				var array = [];
				$.each(checkedList, function(){
					array.push(this[colNames]);
				});
				paramJson[paramName] = array;
				$.post(deleteUrl,paramJson,function(){
						that.grid.reloadList();
				});
			});
        };
        this.setQ = function(){
        	var pageNo = this.grid.page.pageNo.number();
        	var pageSize = this.grid.page.pageSize.number();
        	var q=this.lastQ;
        	if(pageNo!=""){
        		if(q!="")q+="&";
        		q+="pageNo="+pageNo;
        	}
        	if(pageSize!=""){
        		if(q!="")q+="&";
        		q+="pageSize="+pageSize;
        	}
        	CommonUtil.setQ(this.formName,encodeURIComponent(q));
        };
        
};









/**
 * 공통 유틸 static
 * @example
  CommonUtil.out(obj)
 */
var CommonUtil = {
	//json object를 화면상에 출력
	out : function(obj){
		//toast.push(Object.toJSON(obj),"");
		toast.push({body:Object.toJSON(obj), type:'Caution'});
		
		//CommonUtil.alert(Object.toJSON(obj));
    }
	//8자리 날짜 형식을 XXXX-XX-XX로 변환
	,dateFormat : function(day){
		var rtn = "";
		if(day.length==8){
			rtn = day.substring(0,4)+"-"+day.substring(4,6)+"-"+day.substring(6,8);
		}
		return rtn;
    }
	//저장하기 전에 필수 값 체크
	/*
	{name:"name",
	require:true,
	msg:"이름",
	maxbyte:5,
	minbyte:4,
	exp:"email"}
	*/
	,validate : function(data){
		for(var i=0;i<data.length;i++){
			var rtn = this.validateObj(data[i]);
			if(!rtn){
				return false;
				break;
			}
		}
		return true;
	}
	,validateObj : function(data){
		var obj = $("[name='"+data.name+"']");
		if(obj.length==0){
			toast.push("name:"+data.name+" 오브젝트가 존재하지 않습니다.");
			return false;
		}
		if(data.require){
			if(CKEDITOR.instances[data.name]){
				if(Editor.isEmpty(data.name)){
					toast.push(data.msg+"을(를) 입력하세요.");
					return false;
				}
			}else{
				rtn  = CommonUtil.requireObj(obj,data.msg);
				if(!rtn)return false;
			}
		}
		if(data.maxbyte){
			rtn  = CommonUtil.maxbyteObj(obj,data.msg,data.maxbyte);
			if(!rtn)return false;
		}
		if(data.exp){
			if(data.exp=="email"){
				rtn  = CommonUtil.expEmail(obj,data.msg);
			}
			if(!rtn)return false;
		}
		return true;
	}
	,maxbyteObj : function(obj,colName,maxbyte){
		var rtn = false;
		if(CommonUtil.length(obj.val())>maxbyte){
			var han = Math.floor(maxbyte/3);
			toast.push(colName+"의 길이는 한글 "+han+"자 또는 <br/>영문/숫자 "+maxbyte+"자 이하여야 합니다.");
		}else{
			rtn = true;	
		}
		if(!rtn){
			obj.first().focus();
		}
		return rtn;
	}
	,expEmail : function(obj,colName){
		var rtn = false;
		var regExp = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    	if(!regExp.test(obj.val())) {
    		 toast.push(colName+"은(는) 이메일 주소 형식으로 입력하여야 합니다.");
    		 obj.first().focus();
    	}else{
    		 rtn = true;
    	}
    	return rtn;
	}
	,requireObj : function(obj,colName){
		var rtn = false;
		var type = obj.attr("type");
		var tagName = obj.prop("tagName");
		if(type!=undefined){
			type = type.toLowerCase();
		}
		if(tagName!=undefined){
			tagName = tagName.toLowerCase();
		}
		var val;
		if(type=="text"||type=="password"||tagName=="select"||tagName=="textarea"||type=="file"||type=="hidden"){ 
			val = obj.val(); 
		}else if(type=="checkbox"||type=="radio"){
			val = obj.filter(":checked").val();
		}
		if(val == ""||val == undefined){
			if(type=="checkbox" || type=="radio" ||tagName=="select"||type=="hidden"){
				toast.push(colName+"을(를) 선택하세요.");
			}else if(type=="file"){
					toast.push(colName+"을(를) 첨부하세요.");
			}else{
				toast.push(colName+"을(를) 입력하세요.");
			}
		}else{
			rtn = true;	
		}
		if(!rtn){
			obj.first().focus();
		}
		return rtn;
	}
	/*
	,validateValue : function(value,colName){
		if(value=="undefined" || value == ""){
			if(value=="undefined"){
				toast.push(colName+"을(를) 선택하세요.");
			}else{
				toast.push(colName+"을(를) 입력하세요.");
			}
			return false;
		}else{
			return true;	
		}
	}
	*/
	,confirm : function(msg,fnc){
		dialog.push({
			title:"확 인",
		    body:msg, type:'Caution', buttons:[
	            {buttonValue:'확 인', buttonClass:'Blue W50', onClick:fnc},
	            {buttonValue:'취 소', buttonClass:'White W50'}
		    ]});
	}
	,alert : function(msg,fnc){
		/*
		 * 승민 
		 * fnc가 없을경우 자동으로 mask.close();
		 * fnc가 있을경우 fnc함수 안에 mask.close(); 추가
		 */
		mask.open();
		if(!fnc){
			dialog.push({
				title:"확 인",
			    body:msg, type:'Alert',
			    buttons:[{buttonValue:'확 인', buttonClass:'Blue W50', onClick:function(){mask.close();} }]
			});
		}else{
			dialog.push({
				title:"확 인",
			    body:msg, type:'Alert',
			    buttons:[{buttonValue:'확 인', buttonClass:'Blue W50',onClick:fnc}]
			});
		}
	}
	,decode : function(value,options){
		for(var i=0;i<options.length;i++){ 
			if(value==options[i].value){
				return options[i].text;
				break;
			}
		}
	}
	,makeCheckbox : function(value,name){
		var selectTag = "<input type='checkbox' name='"+name+"'";
		if(value=="Y"){
			selectTag+=" checked='checked'";
		}
		selectTag+="/>";
		return selectTag;
	}
	,makeSelect : function(value,options,name){
		var selectTag = "<select name='"+name+"' class='AXSelect' style='width:95%'>";
		for(var i=0;i<options.length;i++){
			selectTag+="<option value='"+options[i].value+"'";
			if(value==options[i].value){
				selectTag+=" selected='selected'";
			}
			selectTag+=">";
			selectTag+=options[i].text;
			selectTag+="</option>";
		}
		selectTag+="</select>";
		return selectTag;
	}
	,makeRadio : function(value,options,name){
		var selectTag = "";
		for(var i=0;i<options.length;i++){
			selectTag+="<input type='radio' name='"+name+"' id='"+name+i+"' class='AXInput' value='"+options[i].value+"'";
			if(value==options[i].value){
				selectTag+=" checked='checked'";
			}
			selectTag+="/>";
			selectTag+="<label for='"+name+i+"'>";
			selectTag+=options[i].text;
			selectTag+="</label>";
		}
		selectTag+="</select>";
		return selectTag;
	}
	,nvl : function(value,defaultValue){
		if(value == ""){
			return defaultValue;
		}else{
			return value;	
		}
	}
	,lpad : function(n, digits,str){
		  var zero = '';
		  n = n.toString();
		  if (n.length < digits) {
		  for (var i = 0; i < digits - n.length; i++)
		      zero += str;
		  }
		  return zero + n;
	}
	,setQ : function(formName,q){
		if($("#q").length == 0){
			$("form[name='"+formName+"']").append("<input type='hidden' name='q' id='q'/>");
		}
		$("#q").val(q);
	}
	,goList : function(url){
		var q = decodeURIComponent($("#q").val());
		if(q==undefined||q=="undefined")q="";
		if(q!=""){
			if(url.indexOf("?")>-1){
				q="&"+q;
			}else{ 
				q="?"+q;
			}
		}
		document.location.href=url+q;
	}
	/*
	 * name으로 value를 적용.XSS방지를 위해 제거했던 태그를 다시 원복한다.
	 */
	,setVal : function(objName,value,typeArg){
		if(value=="")return false;
		var obj = $("[name='"+objName+"']");
		var type = obj.attr("type");
		if(type=="checkbox"||type=="radio"){//체크박스와 라디오 박스는 값 비교
			obj.filter('[value="'+value+'"]').prop('checked',true);
		}else{//나머지는 value
			value = CommonUtil.htmlRestore(value);
			if(typeArg){
				//alert(typeArg)
				if(typeArg=="day"){
					value = CommonUtil.dateFormat(value);
				}
			}
			$("[name='"+objName+"']").val(value);
		}
	}
	/*
	 * id로 text를 적용.XSS방지를 위해 제거했던 태그를 다시 원복한다.
	 */
	,setText : function(objId,value){
		if(value == undefined || value == "undefined") {
			value = "";
		}
		$("#"+objId).text(CommonUtil.htmlRestore(value));
	}
	,htmlRestore : function(value){
		if(value==undefined||value=="undefined")value="";
		return value.replace(/&lt;/gi, "<")
				.replace(/&gt;/gi, ">")
				.replace(/&amp;/gi, "&")
				.replace(/&quot;/gi, "\"")
				.replace(/&apos;/gi, "\'")
				.replace(/&amp;/gi, "&");
	}
	,replaceAll : function (str, searchStr, replaceStr){
	    return str.split(searchStr).join(replaceStr);
	}
	,convBr: function (str){
		if(str){
			return CommonUtil.replaceAll(str,"\n","<br/>");
		} 
	}
	,length : function(string){
		var stringByteLength = 0;
		stringByteLength = (function(s,b,i,c){
		    for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
		    return b;
		})(string);
		return stringByteLength;
	}
	//onkeydown으로 숫자 입력 체크
	,chkNumber : function(e){ 
		if(!event && e!=undefined)event = e;
		// BackSpace=8, Tab=9, Enter=13, left-right=37 39, Delete=46 사용, .=110/190 사용 ,End=35,Home=36
		if((event.keyCode == 8) || (event.keyCode == 9) || (event.keyCode == 13)
				|| (event.keyCode == 37) || (event.keyCode == 39) || (event.keyCode == 46) 
				|| (event.keyCode == 110) || (event.keyCode == 190)
				|| (event.keyCode == 35) || (event.keyCode == 36)			
			){
		// Number Key Pad 사용
		} else if((event.keyCode >= 96 ) && (event.keyCode <= 105)){
		} else if((event.keyCode == 109) || (event.keyCode == 189)){	
		// 기타 미사용	
		} else {
			if((event.keyCode < 48) || (event.keyCode > 57)){
				event.returnValue = false;
				if(event.preventDefault)event.preventDefault();
				return;
			}	
		}
	}
	
	
};

var Editor = {
		//json object를 화면상에 출력
		lengthCheck : function(objId,maxbyte,msg){
			var str = CKEDITOR.instances[objId].getData();
			var length = CommonUtil.length(str);
        	if(length>maxbyte){
        		var han = Math.floor(maxbyte/3);
        		CommonUtil.alert(colName+"의 길이는 한글 "+han+"자 또는 <br/>영문/숫자 "+maxbyte+"자 이하여야 합니다.");
        		return false;
        	};
        	return true;
		},
		getData : function(objId){
			var str = CKEDITOR.instances[objId].getData();
			return str;
		},
		setData : function(objId, str){
			CKEDITOR.instances[objId].setData(str);
		},
		//json object를 화면상에 출력
		isEmpty : function(objId){
			var str = CKEDITOR.instances[objId].getData();
			if(str==""){
				return true;
			}else{
				return false;
			}
		},
		edit : function(objId){
			CKEDITOR.replace(objId);
		},
		view : function(objId){
			toolbar = [{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Preview', 'Print'] },
			       //    { name: 'insert', items: [ 'Image',  'Table', 'HorizontalRule','SpecialChar'] },
			           { name: 'insert', items: [ 'Image',  'Table', 'HorizontalRule'] },
			           { name: 'links', items: [ 'Link'] },
	           	{ name: 'tools', items: [ 'Maximize' ] }
	           	];
			
        	/*
           	{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source', '-', 'Preview', 'Print'] },
           	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike' ] },
           	{ name: 'insert', items: [ 'Image',  'Table', 'HorizontalRule','SpecialChar'] },
           	{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-',  'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'] },
           	{ name: 'links', items: [ 'Link', 'Unlink'] },
           	{ name: 'styles', items: [ 'Font', 'FontSize' ] },
           	{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
           	{ name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
           	{ name: 'others', items: [ '-' ] },
           ];
           */
			
			CKEDITOR.replace(objId 
					   ,{toolbar:toolbar,readOnly:true}		
			);  
		}
}; 



$.fn.extend({
	bindEnter:function(func){
		$(this).keydown(function (key) {
            if (key.keyCode == 13) {
            	func.call();
            }
        });
	},
	copy:function(){
		var text = $(this).text();
		var $temp = $("<input>")
	    $("body").append($temp);
	    $temp.val(text).select();
		document.execCommand('copy');
		$temp.remove();
		toast.push("'"+text+"' 문구가 클립보드에 복사되었습니다.");
	},
	/*
	 * 기본값은 숫자에 마이너스 없음
	 * $("#title").numberOnly({mode:"moneyint",minus:true});
	 * $("#title").numberOnly({mode:"numberint"});
	 * $("#title").numberOnly();
	 */
	numberOnly:function(obj){//mode()
		var mode = "numberint";//money,moneyint,number,numberint
		var minus=false;
		var max_length=20; 
		//var max_round=5;
		if(obj!=undefined){
			if(obj.mode)mode=obj.mode;
			if(obj.minus)minus=obj.minus;//true,false
			if(obj.max_length)max_length=obj.max_length;
			//if(obj.max_round)max_round=obj.max_round;
		}
		$(this).bindPattern({
            pattern: mode,
            allow_minus: minus,
             max_length: max_length
          //   max_round: max_round
		});
	}, 
	/*
	 * $("#selectId").bindCode(type,{
	 * parentCode:"TEST" 
	 * ,addText:"전체" 
	 * ,addValue:"%" //지정하지 않을 경우 ""
	 * ,mode:"all" // mode 가 all 일 경우 use_yn 이 N 인 코드도 조회 
	 * ,value:"01" // 선택 값
	 * ,defaultValue:"01" // 선택 값이 없을 경우 기본값 
	 * ,onchange:function(){alert('test')}
	 * ,onload:function(){alert('test')}
	 * });
	 * 
	 */
	bindCode:function(type,obj){ 
		var mode = obj.mode==undefined?"":obj.mode;//use_yn 상관없이 전체 조회 하려면 "ALL"
		var value = "";
		if(obj.value&&obj.value!=""){
			 value = obj.value;//조회된 값
		}else if(obj.defaultValue&&obj.defaultValue!=""){
			 value = obj.defaultValue;//기본값. 조회된 값이 없을 경우 기본값
		}
		
		var target = $(this);
		var id = target.attr("id");
		
		if(type!="select" &&
				type!="radio" &&
				type!="checkbox"){
			toast.push("첫번째 인자는 select,radio,checkbox 중 하나를 입력해야 합니다.");
			return;
		}
		
		//if(!obj.parentCode){
		//	toast.push("bindCode 호츨시 parentCode 가 존재해야 합니다.");
		//	return;
		//}
		
		 
		// var returnObj;
		 
		 if(obj.options){
			 if(type=="select"){
				  var html ="<select class='AXSelect' name='"+id+"' id='"+id+"'>";
				  var addValue = "";
				  if(obj.addValue){
					  addValue = obj.addValue; 
				  }
				  if(obj.addText){
					  html += "<option value='"+addValue+"'>"+obj.addText+"</option>";
				  }
				  $.each(obj.options,function(i,val){
					  html += "<option value='"+val.value+"'>"+val.text+"</option>";
				  });
				  html +="</select>";
				  target.after(html);
				  target.remove();

				  if(value!="")$("#"+id).val(value);
				  
				  if(obj.width){
					  $("#"+id).css("width",obj.width);
				  }
				  if(obj.onchange){
					  $("#"+id).change(obj.onchange);
				  }
				  returnObj =  $("#"+id);
			}else if(type=="radio"||type=="checkbox"){
				 var html = "";
				  $.each(obj.options,function(i,val){
					  if(i>0)html += "&nbsp;&nbsp;";
					  html += "<input type='"+type+"' class='AXInput' value='"+val.value+"' name='"+id+"' id='"+id+"_"+i+"'/>";
					  html += "<label for='"+id+"_"+i+"'>"+val.text+"</label>";
				  });
				  target.after(html);
				  target.remove();
				  if(value!=""){
					  $("input[name='"+id+"']:input[value='"+value+"']").prop('checked',true);
				  }

				  if(obj.onchange){
					  $("input[name='"+id+"']").on('change',obj.onchange);
				  }
				  returnObj =  $("input[name='"+id+"']");
			}
			 if(obj.onload){
				  obj.onload.call();
			}
		 }else{
			$.getJSON(CONTEXT+"/common/getCodeListAjax.do",{parentCode:obj.parentCode,mode:mode},function(res){
				if(type=="select"){
					  var html ="<select class='AXSelect' name='"+id+"' id='"+id+"'>";
					  var addValue = "";
					  if(obj.addValue){
						  addValue = obj.addValue; 
					  }
					  if(obj.addText){
						  html += "<option value='"+addValue+"'>"+obj.addText+"</option>";
					  }
					  $.each(res,function(i,val){
						  html += "<option value='"+val.value+"'>"+val.text+"</option>";
					  });
					  html +="</select>";
					  target.after(html);
					  target.remove();
	
					  if(value!="")$("#"+id).val(value);
					  
					  if(obj.width){
						  $("#"+id).css("width",obj.width);
					  }
					  if(obj.onchange){
						  $("#"+id).change(obj.onchange);
					  }
					  returnObj =  $("#"+id);
				}else if(type=="radio"||type=="checkbox"){
					 var html = "";
					  $.each(res,function(i,val){
						  if(i>0)html += "&nbsp;&nbsp;";
						  html += "<input type='"+type+"' class='AXInput' value='"+val.value+"' name='"+id+"' id='"+id+"_"+i+"'/>";
						  html += "<label for='"+id+"_"+i+"'>"+val.text+"</label>";
					  });
					  target.after(html);
					  target.remove();
					 
					  if(value!="")$("input[name='"+id+"']:input[value='"+value+"']").prop('checked',true);
	
					  if(obj.onchange){
						  $("input[name='"+id+"']").on('change',obj.onchange);
					  }
					  returnObj =  $("input[name='"+id+"']");
				}
				if(obj.onload){
					  obj.onload.call();
				}
			});
		 }
		//return  returnObj;
	},
	/*
	 * $("#selectId").bindTemplate(tempTypeCd,textareaId)
	 */
	bindTemplate:function(tempTypeCd,textareaId){ 
		var target = $(this);
		var id = target.attr("id");
		 ///ST/TI/STTIContsSubList.do
		$.getJSON(CONTEXT+"/common/getTemplateListAjax.do",{tempTypeCd:tempTypeCd},function(res){
			  var html ="<select class='AXSelect' name='"+id+"' id='"+id+"'>";
				 html += "<option value=''>선택</option>";
			  $.each(res,function(i,val){
				  html += "<option value='"+val.value+"'>"+val.text+"</option>";
			  });
			  html +="</select>";
			  target.after(html);
			  target.remove();
			  
			  $("#"+id).css("width","400px");
			  $("#"+id).change(function(){
				  if($(this).val()!=""){
					  $.post(CONTEXT+"/common/getTemplateAjax.do",{tempIdx:$(this).val(),trnType:tempTypeCd},function(data){
						  if( tempTypeCd=="PHN"||tempTypeCd=="MSG"){
							  $("#"+textareaId).val(data);
							    
						  }else{
							  Editor.setData(textareaId, data);
						  }
					  });
				  }else{
					  Editor.setData(textareaId, "");
				  }
			  });
			  returnObj =  $("#"+id);
		});
	},
	/*
	 * $("#selectId").bindHpno(tempTypeCd,textareaId)
	 */
	bindHpno:function(){ 
		var target = $(this);
		var id = target.attr("id");
		 ///ST/TI/STTIContsSubList.do
		$.getJSON(CONTEXT+"/common/getHpnoListAjax.do",function(res){
			  var html ="<select class='AXSelect' name='"+id+"' id='"+id+"'>";
				 html += "<option value=''>선택</option>";
			  $.each(res,function(i,val){
				  html += "<option value='"+val.value+"'>"+val.text+"</option>";
			  });
			  html +="</select>";
			  target.after(html);
			  target.remove();
			  $("#"+id).css("width","150px");
			  returnObj =  $("#"+id);
		});
	}
});

/**
 * axisj AXUserSelect 생성 
 */
var UserSelectUtil = function(){
	
	var myUserBox = new AXUserSelect();
	this.userInit  = function(ID) {
		myUserBox.setConfig({
			containerID: ID,
			onchange: function(){
				trace(this);
			}
		});
	};
	this.getUserList = function() {
		return myUserBox.getDS();
	};
	this.add = function(data) {
    	var ds = [];
    	ds.push({
		    id:data.id,
		    nm:data.nm,
		    desc:data.desc
	    });
	    myUserBox.push(ds);
	};
	this.remove = function() {
        myUserBox.del( myUserBox.getSelect() );
	};
	this.selectedList = function() {
		return Object.toJSON(myUserBox.getSelectDS());
	};

};

/*
 * <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script> 필수
 */
/*
function openAddr(objId){
    new daum.Postcode({
        oncomplete: function(data) {
        	//CommonUtil.out(data);
        	var addr = data.address;
        	if(data.bname!=""||data.buildingName!=""){
        		addr+=" (";
        		if(data.bname!=""){
        			addr+=data.bname;
        		}
        		if(data.bname!=""&&data.buildingName!=""){
        			addr+=",";
        		}
        		if(data.buildingName!=""){
        			addr+=data.buildingName;
        		}
        		addr+=")";
        	}
        	$("#"+objId).val(addr);
        	 //{"postcode":"300-833", "postcode1":"300", "postcode2":"833", "postcodeSeq":"001", "zonecode":"34672", "address":"대전 동구 판교1길 3", "addressEnglish":"3 Pangyo 1-gil, Dong-gu, Daejeon, Korea", "addressType":"R", "bcode":"3011010700", "bname":"판암동", "sido":"대전", "sigungu":"동구", "buildingName":"", "buildingCode":"", "jibunAddress":"대전 동구 판암동 497-7", "roadAddress":"대전 동구 판교1길 3", "autoRoadAddress":"", "autoJibunAddress":"", "userSelectedType":"J"}  
        }
    }).open();
}
*/



/*
 *  DateUtil.now("time"))
 *  DateUtil.now()
 *  DateUtil.now("yyyy.mm.dd")
    DateUtil.addDay(3,"yyyy.mm.dd"))
    DateUtil.addMonth(-3,"yyyy.mm.dd a/p hh:mm E"))
	DateUtil.addYear(-3)
 */
var DateUtil = {
	format : function(d,format) {
		if(!format)format="yyyy-MM-dd";
		if(format=="time")format="yyyy-MM-dd HH:mm";
		
		return format.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
	        switch ($1) {
	            case "yyyy": return d.getFullYear();
	            case "yy": return CommonUtil.lpad((d.getFullYear() % 1000),2,"0");
	            case "MM": return CommonUtil.lpad((d.getMonth() + 1),2,"0");
	            case "dd": return CommonUtil.lpad(d.getDate(),2,"0");
	            case "E": return weekName[d.getDay()];
	            case "HH": return CommonUtil.lpad(d.getHours(),2,"0");
	            case "hh": return CommonUtil.lpad(((h = d.getHours() % 12) ? h : 12),2,"0");
	            case "mm": return CommonUtil.lpad(d.getMinutes(),2,"0");
	            case "ss": return CommonUtil.lpad(d.getSeconds(),2,"0");
	            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
	            default: return $1;
	        }
	    }); 
	}
	,now : function(format) {//yyyy-MM-dd HH:mm
		var d=new Date();
		return this.format(d,format);
		
	}
	,addYear : function(addDate,format) {//yyyy-MM-dd HH:mm
		var d=new Date();
		d.setMonth(d.getMonth() + (addDate*12));;
		//d.setDate(d.getDate() + 1);
		return this.format(d,format);
	}
	,addMonth : function(addDate,format) {//yyyy-MM-dd HH:mm
		var d=new Date();
		d.setMonth(d.getMonth() + addDate);
		return this.format(d,format);
	}
	,addDay : function(addDate,format) {//yyyy-MM-dd HH:mm
		var d=new Date();
		d.setDate(d.getDate() + addDate);
		//d.setDate(d.getDate() + 1);
		return this.format(d,format);
	}
}



var CookieUtil = {
	set : function(cookieName, value, exdays){
		var exdate = new Date();
	    exdate.setDate(exdate.getDate() + exdays);
		var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
		document.cookie = cookieName + "=" + cookieValue;
	}
	,remove:function(cookieName){
		var expireDate = new Date();
	    expireDate.setDate(expireDate.getDate() - 1);
	    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
	}
	,get:function(cookieName){
		cookieName = cookieName + '=';
	    var cookieData = document.cookie;
	    var start = cookieData.indexOf(cookieName);
	    var cookieValue = '';
	    if(start != -1){
	        start += cookieName.length;
	        var end = cookieData.indexOf(';', start);
	        if(end == -1)end = cookieData.length;
	        cookieValue = cookieData.substring(start, end);
	    }
	    return unescape(cookieValue);
	}
}

function fn_checkRadio(objectName,value){
	if(value==undefined || value==null || value==""){
		$("input[name="+objectName+"]").prop("checked", false);	
	}else{
		$("input[name="+objectName+"]").filter('[value="'+value+'"]').prop("checked",true);
	}
}
/** 라디오 박스 필수 체크 **/
function fn_getRadio(objectName){
	var rtn = $("input[name="+objectName+"]:checked").val();
	if(rtn==undefined){
		return "";
	}else{
		return rtn;
	}
}


function fn_htmlRestore(value){
	if(value==undefined||value=="undefined")value="";
	return value.replace(/&lt;/gi, "<")
			.replace(/&gt;/gi, ">")
			.replace(/&amp;/gi, "&")
			.replace(/&quot;/gi, "\"")
			.replace(/&apos;/gi, "\'")
			.replace(/&amp;/gi, "&");
}

function fn_required(objectName,msg){
	if( $("[name="+objectName+"]").val()==""){
		G_Dialog( "ALERT", msg, $("input[name="+objectName+"]").focus() );
		return false;
	}else{
		return true;	
	}
}
/** 라디오 박스 필수 체크 **/
function fn_requiredRadio(objectName,msg){
	if($("input[name="+objectName+"]:checked").val()==undefined){
		G_Dialog( "ALERT", msg, $("input[name="+objectName+"]").filter(":first").focus() );
		return false;
	}else{
		return true;
	}
}




