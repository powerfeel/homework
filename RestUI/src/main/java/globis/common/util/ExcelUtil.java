package globis.common.util;
import egovframework.rte.fdl.excel.EgovExcelService;
import egovframework.rte.fdl.excel.impl.EgovExcelServiceImpl;
import globis.common.code.service.CommonCodeService;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
/**
 * @Class Name : ResUtil.java
 * @Description : Resonse 관련 공통 util
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
public class ExcelUtil{
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	public ExcelUtil() {
	
	}
	
	
	/**
	 * axisj grid와 연동된 프로그램에서 엑셀을 생성하여 사용자에게 다운로드 시킴
	 * @param commandMap(grid 해드 형태가 string json으로 존재),list,response
	 * @return void
	*/
	public static void makeExcel( Map<String,Object> commandMap,List<Map<String,Object>> list,HttpServletResponse response ) throws Exception{
		String colGroupStr = (String)commandMap.get("_colGroup");
		String title = "목록";
		if(commandMap.get("_excelTitle")!=null)title=(String)commandMap.get("_excelTitle");
		
		Gson gson = new Gson();
		List<Map<String,Object>> colGroup = gson.fromJson(colGroupStr, List.class);
		
			
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Sheet1");
	        

	        OutputStream fileOut = null;
	        try{
	            //1.타이틀 추가
	            Font titleFont = workbook.createFont();
	            //titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD); 
	            titleFont.setFontHeightInPoints((short)20);
	            titleFont.setFontName("맑은 고딕");

	            XSSFCellStyle  titleStyle = workbook.createCellStyle();
	            titleStyle.setFont(titleFont);
	             
	            Row titleRow = sheet.createRow((short)0);
	            Cell titleCell = titleRow.createCell((short)0 ); 
	            titleCell.setCellStyle(titleStyle);
	            titleCell.setCellValue(title);
	            
	            //2.해더 추가
	            CellStyle headerStyle = createBorderedStyle(workbook);
	            headerStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex()); 
	            headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND); 
	            headerStyle.setAlignment(CellStyle.ALIGN_CENTER); 
	            Row row = sheet.createRow((short)2); 
	            
	            Cell hearderCell = row.createCell(0 ); 
	            hearderCell.setCellValue("No."); 
	            hearderCell.setCellStyle(headerStyle);
	            
	            int cellNum = 0;
	            for(int i=0;i<colGroup.size();i++){
	            	cellNum++;
	                String label = (String)colGroup.get(i).get("label");
	                hearderCell = row.createCell((short)cellNum );
	                hearderCell.setCellValue(label.replaceAll("<br/>", "\n")); 
	                hearderCell.setCellStyle(headerStyle);
	            }

	            //3.내용 추가 
	            CellStyle styleL = createBorderedStyle(workbook);
	            styleL.setAlignment(CellStyle.ALIGN_LEFT);
	            CellStyle styleC = createBorderedStyle(workbook);
	            styleC.setAlignment(CellStyle.ALIGN_CENTER);
	            CellStyle styleR = createBorderedStyle(workbook);
	            styleR.setAlignment(CellStyle.ALIGN_RIGHT);
	        
	            
	            
	            for(int j=0;j<list.size();j++){
	                row = sheet.createRow((short)(j+3)); 
	                hearderCell = row.createCell(0);
	                hearderCell.setCellValue(j+1);
	                hearderCell.setCellStyle(styleC);
	                
	                Map<String,Object> dataMap = list.get(j);
	                
	                cellNum = 0;
	                for(int i=0;i<colGroup.size();i++){
	                	
	                	//Map<String,Object> vo = (Map<String,Object>)colGroup.get(i);
	                    
	                    
	                    //hearderCell.setCellValue(rsv.getString(vo.getCellFieldName()));
	                    //hearderCell.setCellValue(1111111111);
	                    //if(vo.isNumeric()){
	                    //    hearderCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	                    //}else{
	                    //    hearderCell.setCellType(Cell.CELL_TYPE_STRING);
	                    //}
	                    String key = (String)colGroup.get(i).get("key");
		                String align = (String)colGroup.get(i).get("align");
		                
		                cellNum++;
		                Object value= dataMap.get(key);
		                
		                hearderCell = row.createCell((short)cellNum ); 
		                if(value instanceof String){
		                	hearderCell.setCellValue((String)value);
		                }else if(value instanceof Date){
		                	hearderCell.setCellValue((Date)value);
		                }else if(value instanceof Calendar){
		                	hearderCell.setCellValue((Calendar)value);
		                }else if(value instanceof RichTextString){
		                	hearderCell.setCellValue((RichTextString)value);
		                }else if(value instanceof Integer){
		                	hearderCell.setCellValue((Integer)value);
		                }else if(value instanceof Double){
		                	hearderCell.setCellValue((Double)value);
		                }else if(value instanceof Float){
		                	hearderCell.setCellValue((Float)value);
		                }else if(value instanceof BigDecimal){
		                	BigDecimal mod = ((BigDecimal)value).remainder(new BigDecimal(1));
		                	if(mod.compareTo(new BigDecimal(0))==1){
		                		hearderCell.setCellValue(((BigDecimal)value).floatValue());
		                	}else{
		                		hearderCell.setCellValue(Math.round(  ((BigDecimal)value).floatValue()));
		                	}
		                }
		                if(align==null||align.equals("left")){
                            hearderCell.setCellStyle(styleL);
                            hearderCell.setCellType(Cell.CELL_TYPE_STRING);
                        }else if(align.equals("center")){
                            hearderCell.setCellStyle(styleC);
                            hearderCell.setCellType(Cell.CELL_TYPE_STRING);
                        }else if(align.equals("right")){
                            hearderCell.setCellStyle(styleR);
                            hearderCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        }
	                }
	            }
	            
	            //cell 가로 사이즈
	            sheet.setColumnWidth(0, 256*4);
	            //sheet.autoSizeColumn(0);
	            
	            cellNum = 0;
	            for(int i=0;i<colGroup.size();i++){
	            	//double width = (Double)colGroup.get(i).get("width");
	            	double width = (Double)colGroup.get(i).get("_owidth");
	            	
	            	cellNum++;
	                if(width!=0){
	                    sheet.setColumnWidth(cellNum, Integer.parseInt(String.valueOf(32*Math.round(width))));
	                }else{
	                    sheet.autoSizeColumn(cellNum);
	                    sheet.setColumnWidth(cellNum, (sheet.getColumnWidth(cellNum))+512 ); 
	                }
	            }
	            
	            
	            String fileName = I18nUtil.K2E(title)+".xlsx";
	            response.reset();
	            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	            response.setHeader("Content-Disposition", "attachment;filename="+fileName+";");
	            fileOut = response.getOutputStream(); 
	            workbook.write(fileOut);
	            fileOut.close();
	        }catch (Exception e){
	            e.printStackTrace();
	        }finally{
	            try{
	                if(fileOut!=null)fileOut.close();
	            }catch (Exception e){
	                e.printStackTrace();
	            }
	        }
	}
    private static CellStyle createBorderedStyle(XSSFWorkbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }
    

	/**
	 * 엑셀 저장시 사용하는 메소드로 commandMap에 존재하는 q를 파라미터로 변환하여 put해줌
	 * @param commandMap
	 * @return void
	*/
    public static void setParameterToMap(Map<String,Object> commandMap){
    	
    	String q = (String)commandMap.get("q");
    	//Map<String,Object> map = new HashMap<String,Object>();
		String[] params = q.split("&");
		for(int i=0;i<params.length;i++){
			//String[] data = params[i].split("=");
			String param = params[i];
			String[] data = param.split("=");
			if(data.length==2){
				/*
				if(data[1].indexOf("%20")>-1){
					data[1] = data[1].replace("%20", " ");
				}
				*/
				commandMap.put(data[0],StringUtil.decode(data[1]));
			}
		}
		commandMap.remove("q");
    }
    
    /** [사용자관리] 엑셀업로드 데이터 등록 프로세스 **/
    public static String getCellValue( HSSFCell cell ) throws Exception {
		String value = "";
		if (cell != null) {
            switch (cell.getCellType()) {
	            case HSSFCell.CELL_TYPE_FORMULA:
	                value = String.valueOf(cell.getNumericCellValue());
	                break;
	            case HSSFCell.CELL_TYPE_NUMERIC:
	                //double data = cell.getNumericCellValue();
	                //if(HSSFDateUtil.isValidExcelDate(data) && String.valueOf(cell.getNumericCellValue()).length() == 7 && String.valueOf(cell.getNumericCellValue()).substring(0, 1).equals("4")){
	                //    SimpleDateFormat fommatter = new SimpleDateFormat("yyyy-MM-dd");
	                //    value = fommatter.format(cell.getDateCellValue()); 
	                //}else{
	                    value = String.valueOf((long)cell.getNumericCellValue());
	               // }
	                break;
	            case HSSFCell.CELL_TYPE_STRING:
	                value = String.valueOf(cell.getRichStringCellValue().toString()).trim();
	                break;
	            case HSSFCell.CELL_TYPE_BLANK:
	                value = "";
	                break;
	            case HSSFCell.CELL_TYPE_BOOLEAN:
	            	value = String.valueOf(cell.getBooleanCellValue());
	            	break;
	            case HSSFCell.CELL_TYPE_ERROR:
	                value = String.valueOf(cell.getErrorCellValue());
	                break;
	            default:
	                value = "[type?]";
	                break;
            }
        }
		return value;
	}
    
    /** [사용자관리] 엑셀업로드 데이터 등록 프로세스 **/
    public static List<String[]> convertXlsToList( MultipartFile file) throws Exception {
    	InputStream inputStream = file.getInputStream();
    	EgovExcelService excelZipService = new EgovExcelServiceImpl();
    	HSSFWorkbook hssfWB = (HSSFWorkbook) excelZipService.loadWorkbook(inputStream);
    	List<String[]> list = new ArrayList<String[]>();
    	int sheetCellCnt = 0;
    	if( hssfWB.getNumberOfSheets() == 1 ){
            HSSFSheet sheet = hssfWB.getSheetAt(0); 			//시트 가져오기.
            HSSFRow   row   = sheet.getRow(0); 					//row 가져오기
            sheetCellCnt    = row.getPhysicalNumberOfCells();	//cell Cnt
            
            for(int j=0; j < sheet.getPhysicalNumberOfRows(); j++){
            	String result[] = new String[sheetCellCnt];
            	HSSFRow hssfRow = sheet.getRow(j);    						
            	for(int i=0; i < sheetCellCnt; i++){
					result[i] = ExcelUtil.getCellValue( hssfRow.getCell(i) );
            	}
            	list.add(result);
            }
    	}
		return list;
	}
}