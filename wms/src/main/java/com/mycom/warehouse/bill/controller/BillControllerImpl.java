package com.mycom.warehouse.bill.controller;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.bill.service.BillService;
import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.history.vo.HistoryVO;
import com.mycom.warehouse.member.vo.MemberVO;
import com.mycom.warehouse.stock.vo.MonthlyStockVO;
import com.mycom.warehouse.system.service.SystemService;
import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.SettingVO;
import com.mycom.warehouse.system.vo.StorageRateVO;
import com.mycom.warehouse.warehouse.service.WarehouseService;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Controller("billController")
@RequestMapping(value="/bill")
public class BillControllerImpl extends BaseController implements BillController{
	private static final int STORAGE_BILL_COLUMN = 13;
	private static final int CARGO_BILL_COLUMN = 15;
	private static final int STORAGE_BILL_ROW_START = 9;
	private static final int STORAGE_BILL_COL_START = 2;
	private static final int CARGO_BILL_ROW_START = 9;
	private static final int CARGO_BILL_COL_START = 2;
	@Autowired
	WarehouseVO warehouseVO;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	MemberVO memberVO;
	@Autowired
	MonthlyStockVO monthlyStockVO;
	@Autowired
	HistoryVO historyVO;
	@Autowired
	StorageRateVO storageRateVO;
	@Autowired
	CargoRateVO cargoRateVO;
	@Autowired 
	BillService billService;
	@Autowired
	SystemService systemService;
	@Autowired
	SettingVO settingVO;

	@Override
	@RequestMapping(value="/view.do")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberVO");
		String member_id = memberVO.getMember_id();
		List<WarehouseVO> warehouseList = warehouseService.listWarehouse(member_id);
		mav.addObject("warehouseList", warehouseList);
		return mav;
	}

	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping(value="/calc.do")
	public @ResponseBody Map<String,Object> calc(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(!billService.checkUpdate(map))//월말 정산이 안된경우 월말정산진행
		{
			billService.upToDateStock(map);//월재고 테이블 최신화
		}
		String storageBillError = null, cargoBillError = null;
		Map<String,Object> listMap = billService.selectLists(map);
		List<String[]> storageBill = calcStorageBill(map, listMap); //보관료청구서 계산
		if(storageBill==null)
		{
			storageBillError = "보관료 요율이 없습니다. 보관료 요율을 확인해주세요.";
		}
		Map<String, Object> cargoMap = calcCargoBill(map, listMap); //부대비청구서 계산
		List<String[]> cargoBill = (List<String[]>) cargoMap.get("cargoBill");
		cargoBillError = (String) cargoMap.get("cargoBillError");
		
		Map<String,Object> billMap = new HashMap<String, Object>();
		billMap.put("storageBill", storageBill);
		billMap.put("storageBillError", storageBillError);
		billMap.put("cargoBill", cargoBill);
		billMap.put("cargoBillError", cargoBillError);
		
		return billMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@RequestMapping(value="downloadExcel.do")
	public void downloadExcel(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> listMap = billService.selectLists(map);
		List<String[]> storageBill = calcStorageBill(map, listMap); //보관료청구서 계산
		Map<String, Object> cargoMap = calcCargoBill(map, listMap); //부대비청구서 계산
		List<String[]> cargoBill = (List<String[]>) cargoMap.get("cargoBill");
		List<SettingVO> settingList = systemService.settingList(); 
		String warehouse_name = (String) map.get("warehouse_name");
		String stock_month = (String) map.get("stock_month");
		warehouseVO = warehouseService.searchWarehouse(warehouse_name);
		String region = warehouseVO.getWarehouse_region_name();
		Iterator<SettingVO> settingIterator = settingList.iterator();
		while(settingIterator.hasNext())
		{
			settingVO = settingIterator.next();
			if(region.equals(settingVO.getSetting_region()))
				break;
		}
		
		File dir = new File(request.getSession().getServletContext().getRealPath("/")+"resources"+File.separator+"billForm"); //엑셀파일 디렉토리위치
		FileInputStream in = new FileInputStream(dir+File.separator+"보관임양식.xlsx"); //엑셀 파일 읽기
		
		Workbook wb = WorkbookFactory.create(in); //읽은 엑셀 파일을 workbook에 저장
		int i,j;
		Row row;
		Cell cell;
		String value;
		int cellNum, totalPrice=0;
		int sheetCnt=0, cnt, rowCnt, remainingBillRow, storageBillSheetCnt = 0, cargoBillSheetCnt = 0;
		Sheet storageBillSheet = null;
		Sheet cargoBillSheet = null;
		
		wb.setSheetName(0, warehouse_name);
		wb.setSheetName(1, warehouse_name+"(부대)");
		
		if(storageBill.size()>15)
		{
			storageBillSheetCnt = storageBill.size()/15;
			if(storageBill.size()%15>0)
				storageBillSheetCnt++;
		}
		else
			storageBillSheetCnt = 1;
		if(cargoBill.size()>15)
		{
			cargoBillSheetCnt = cargoBill.size()/15;
			if(cargoBill.size()%15>0)
				cargoBillSheetCnt++;
		}
		else
			cargoBillSheetCnt = 1;
		for(i=1; i<storageBillSheetCnt; i++)
			wb.cloneSheet(0);
		for(i=1; i<cargoBillSheetCnt; i++)
			wb.cloneSheet(1);
		
		if(storageBill!=null) //보관료내역이 있을경우,
		{
			sheetCnt = storageBill.size()/15; //엑셀행 15개 이상의 보관료내역이 있을경우 시트 수 계산
			if(storageBill.size()%15>0)
				sheetCnt++;
			rowCnt = 0;
			if(storageBill.size()>15)
				remainingBillRow = 15;
			else
				remainingBillRow = storageBill.size();
			for(cnt=0; cnt<storageBillSheetCnt; cnt++)
			{
				if(cnt>0)
					wb.setSheetOrder(warehouse_name+" ("+(cnt+1)+")", cnt);
				storageBillSheet = wb.getSheetAt(cnt);
				
				for(i=rowCnt; i<remainingBillRow; i++)
				{
					row = storageBillSheet.getRow(STORAGE_BILL_ROW_START+(i%15));
					
					for(j=0; j<STORAGE_BILL_COLUMN; j++)
					{
						cellNum = STORAGE_BILL_COL_START+j;
						if(cellNum>=6)
							cellNum+=2;
						if(cellNum==16)
							cellNum+=1;
						cell = row.getCell(cellNum);
						
						if(storageBill.get(i)[j]!=null)
						{
							if(j==12)
								totalPrice +=  Integer.parseInt(storageBill.get(i)[j]);
							value = storageBill.get(i)[j];
							value = value.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
							cell.setCellValue(value);
						}
					}
				}
				rowCnt = 15;
				if(storageBill.size()-remainingBillRow>15)
					remainingBillRow += 15;
				else
					remainingBillRow += storageBill.size()-remainingBillRow;
				
				row = storageBillSheet.getRow(24);
				cell = row.getCell(17);
				value = Integer.toString(totalPrice).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
				totalPrice = 0;
				cell.setCellValue(value); //총합계액
				
				setHeader(storageBillSheet, warehouseVO, stock_month, settingVO);
				setFooter(storageBillSheet, settingVO);
			}
		}
		totalPrice=0;
		if(cargoBill.size()!=0)
		{
			sheetCnt = cargoBill.size()/15; //엑셀행 15개 이상의 보관료내역이 있을경우 시트 수 계산
			if(cargoBill.size()%15>0)
				sheetCnt++;
			rowCnt = 0;
			if(cargoBill.size()>15)
				remainingBillRow = 15;
			else
				remainingBillRow = cargoBill.size();
			for(cnt=0; cnt<cargoBillSheetCnt; cnt++)
			{
				cargoBillSheet = wb.getSheetAt(cnt+storageBillSheetCnt);
				
				for(i=rowCnt; i<remainingBillRow; i++)
				{
					row = cargoBillSheet.getRow(CARGO_BILL_ROW_START+(i%15));
				
					for(j=0; j<CARGO_BILL_COLUMN; j++)
					{
						cellNum = CARGO_BILL_COL_START+j;
						cell = row.getCell(cellNum);
						
						if(cargoBill.get(i)[j]!=null)
						{
							if(j==14)
								totalPrice +=  Integer.parseInt(cargoBill.get(i)[j]);
							value = cargoBill.get(i)[j];
							value = value.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
							cell.setCellValue(value);
						}
					}
				}
				rowCnt = 15;
				if(cargoBill.size()-remainingBillRow>15)
					remainingBillRow += 15;
				else
					remainingBillRow += cargoBill.size()-remainingBillRow;
				
				row = cargoBillSheet.getRow(24);
				cell = row.getCell(16);
				value = Integer.toString(totalPrice).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
				totalPrice = 0;
				cell.setCellValue(value); //총합계액
				
				setHeader(cargoBillSheet, warehouseVO, stock_month, settingVO);
				setFooter(cargoBillSheet, settingVO);
			}
		}
		else
		{
			wb.removeSheetAt(1);
		}
		
		String[] date = stock_month.split("-");
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = (userAgent.indexOf("MSIE") > -1) || (userAgent.indexOf("Trident") > -1) || (userAgent.indexOf("Edge") > -1);
		String fileName = "보관임청구서("+date[0].substring(2)+"년"+date[1]+"월"+")-"+warehouse_name.split("창고")[0]+".xlsx";
		if (ie) {
		        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else {
		        fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		}
		response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +"\"");
		 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		 		
		wb.write(response.getOutputStream()); //workbook을 통해 엑셀파일 생성
		response.getOutputStream().close();
	}
	
	private void setHeader(Sheet sheet, WarehouseVO warehouseVO, String stock_month, SettingVO settingVO)
	{
		Row row = null;
		Cell cell = null;
		
		//제목
		row = sheet.getRow(1);
		cell = row.getCell(8);
		String month = stock_month.split("-")[1];
		String value = cell.getStringCellValue();
		cell.setCellValue(month+value);
		
		row = sheet.getRow(4);
		cell = row.getCell(1);
		cell.setCellValue(settingVO.getSetting_chief_rank()+"        귀하");
		
		//창고명, 창고주명
		row = sheet.getRow(4);
		cell = row.getCell(15);
		cell.setCellValue(warehouseVO.getWarehouse_name());
		row = sheet.getRow(5);
		cell = row.getCell(15);
		cell.setCellValue(warehouseVO.getWarehouse_owner());
		
		//창고명,코드명,등급순위
		row = sheet.getRow(5);
		cell = row.getCell(20);
		cell.setCellValue(warehouseVO.getWarehouse_name());
		cell = row.getCell(21);
		cell.setCellValue(warehouseVO.getWarehouse_code());
		cell = row.getCell(22);
		cell.setCellValue(warehouseVO.getWarehouse_region()+warehouseVO.getWarehouse_rating());
	}
	
	private void setFooter(Sheet sheet, SettingVO settingVO)
	{
		Row row = null;
		Cell cell = null;
		String str = null;
		StringBuffer sb = null;
		
		row = sheet.getRow(28);
		cell = row.getCell(12);
		cell.setCellValue(settingVO.getSetting_chief_rank());
		cell = row.getCell(14);
		str = settingVO.getSetting_chief_name();
		sb = new StringBuffer(str);
		sb.insert(0, "   ");
		sb.insert(4, "   ");
		sb.insert(8, "   ");
		sb.insert(12, "   (인)");
		str = sb.toString();
		cell.setCellValue(str);
		row = sheet.getRow(29);
		cell = row.getCell(12);
		cell.setCellValue(settingVO.getSetting_manager_rank());
		cell = row.getCell(14);
		str = settingVO.getSetting_manager_name();
		sb = new StringBuffer(str);
		sb.insert(0, "   ");
		sb.insert(4, "   ");
		sb.insert(8, "   ");
		sb.insert(12, "   (인)");
		str = sb.toString();
		cell.setCellValue(str);
	}
	
	@SuppressWarnings("unchecked")
	private List<String[]> calcStorageBill(Map<String,Object> map, Map<String,Object> listMap) throws Exception
	{
		List<String[]> storageBill = new ArrayList<String[]>();
		
		List<MonthlyStockVO> monthlyStockList = (List<MonthlyStockVO>) listMap.get("monthlyStockList");
		List<HistoryVO> historyList = (List<HistoryVO>) listMap.get("historyList");
		storageRateVO = (StorageRateVO) listMap.get("storageRateVO");
		if(storageRateVO==null)
		{
			return null;
		}
		
		Iterator<MonthlyStockVO> stockIterator = monthlyStockList.iterator();
		
		int stockNum, historyCnt, startDay, endDay = 0, lastDay = 0, storageCnt, juksu, totalJuksu;
		String workDay, prevWorkDay, nextWorkDay, storageTerm, unit;
		String[] storageBillRow = null;
		boolean emptyStock;
		BigDecimal num;
		
		//보관료청구서 작성시작
		while(stockIterator.hasNext())
		{
			monthlyStockVO = stockIterator.next();
			stockNum = monthlyStockVO.getStock_seq_num();
			
			historyCnt = 0;
			emptyStock = false;
			totalJuksu = 0;
			prevWorkDay = null;
			for(int i=0;i<historyList.size();i++)//관리내역이 있는 재고가 있는지 체크
			{
				historyVO = historyList.get(i);
				if(!historyVO.getHistory_sort1().equals("이적") && stockNum==historyVO.getStock_seq_num())
				{
					historyCnt++;
					
					//보관기간, 보관일수 계산시작
					
					if(historyCnt==1 && historyVO.getHistory_sort1().equals("입고") && historyVO.getStock_prev().equals("0"))//새로 입고된 경우, 
					{
						emptyStock = true;
						workDay = historyVO.getHistory_date().substring(8);
						startDay = Integer.parseInt(workDay)+1; //작업일다음날이 보관시작일
						if(i+1<historyList.size())
						{
							if(historyList.get(i+1).getStock_seq_num()==stockNum) //다음작업이 또 있을 경우,
							{
								nextWorkDay = historyList.get(i+1).getHistory_date().substring(8);
								endDay = Integer.parseInt(nextWorkDay); //다음작업일이 마지막보관날짜
							}
						}
						else //다음작업이 없는 경우,
							lastDay = getLastDay((String)map.get("stock_month"));;
							endDay = lastDay;
					}
					else 
					{
						if(historyVO.getHistory_sort1().equals("출고") && historyVO.getStock_present().equals("0"))
							emptyStock = true;
						if(historyCnt>1) //해당 재고의 두번째이상 작업일 경우,
						{
							prevWorkDay = historyList.get(i-1).getHistory_date().substring(8);
							if(prevWorkDay.equals(historyVO.getHistory_date().substring(8)))
								startDay = Integer.parseInt(prevWorkDay);
							else
								startDay = Integer.parseInt(prevWorkDay)+1; //이전작업일다음날이 보관시작일
						}
						else//기존에 있던 재고가 추가로 입고되거나 출고된 경우,
						{
							startDay = 1;//보관시작일 = 당월 1일
						}
						workDay = historyVO.getHistory_date().substring(8);
						endDay =  Integer.parseInt(workDay);
					}
					if(prevWorkDay!=null&&prevWorkDay.equals(workDay))
					{
						storageTerm = Integer.toString(startDay);
						storageCnt = 0;
					}
					else
					{
						storageTerm = Integer.toString(startDay)+" - "+Integer.toString(endDay); //보관기간
						storageCnt = endDay-startDay+1;//보관일수
					}
															
					//보관기간, 보관일수 계산끝
					
					storageBillRow = new String[STORAGE_BILL_COLUMN];
					storageBillRow[0] = historyVO.getStock_year(); //년산
					storageBillRow[1] = historyVO.getStock_sort2()+historyVO.getStock_sort1(); //곡종
					storageBillRow[2] = historyVO.getStock_unit(); //단량			 
					unit = historyVO.getStock_unit(); //단량			 
					storageBillRow[3] = storageTerm; //보관기간
					storageBillRow[4] = Integer.toString(storageCnt); //보관일수			
					storageBillRow[5] = historyVO.getStock_prev(); //이월량
					if(historyVO.getHistory_sort1().equals("입고"))
					{
						storageBillRow[6] = historyVO.getHistory_quantity(); //입고량
					}
					else if(historyVO.getHistory_sort1().equals("출고"))
					{
						storageBillRow[7] = historyVO.getHistory_quantity(); //출고량
					}
					storageBillRow[8] = historyVO.getStock_present(); //잔량
					if(storageBillRow[5].equals("0"))
						num = new BigDecimal(storageBillRow[8]);
					else
						num = new BigDecimal(storageBillRow[5]);
					juksu = num.setScale(0, RoundingMode.FLOOR).intValue()*storageCnt;
					storageBillRow[9] = Integer.toString(juksu);
					totalJuksu += juksu;
					calcStorageBillRowPrice(storageBillRow, unit, storageRateVO);
					storageBill.add(storageBillRow);
					/*
					for(int i1=0; i1<13; i1++)
						System.out.println(storageBillRow[i1]);*/
				}
			}//관리내역 체크끝
			if(historyCnt==0)
			{
				storageBillRow = new String[STORAGE_BILL_COLUMN];
				storageBillRow[0] = monthlyStockVO.getStock_year();
				storageBillRow[1] = monthlyStockVO.getStock_sort2()+monthlyStockVO.getStock_sort1();
				storageBillRow[2] = monthlyStockVO.getStock_unit();
				unit = monthlyStockVO.getStock_unit();
				startDay = 1;
				lastDay = getLastDay((String)map.get("stock_month"));
				endDay = lastDay;
				storageTerm = Integer.toString(startDay)+" - "+Integer.toString(endDay); //보관기간
				storageCnt = endDay-startDay+1;//보관일수
				storageBillRow[3] = storageTerm;
				storageBillRow[4] = Integer.toString(storageCnt);
				storageBillRow[5] = monthlyStockVO.getStock_quantity_40kg();
				storageBillRow[8] = storageBillRow[5];
				num = new BigDecimal(storageBillRow[5]);
				juksu = num.setScale(0, RoundingMode.FLOOR).intValue()*storageCnt;
				storageBillRow[9] = Integer.toString(juksu);
				calcStorageBillRowPrice(storageBillRow, unit, storageRateVO);
				storageBill.add(storageBillRow);
			}
			else if(!(historyCnt==1 && emptyStock==true)) //관리내역이 1개이고 새로 입고되거나 전부 출고된 경우가 아닐 때,
			{
				String[] remainStorageBillRow = null;
				lastDay = getLastDay((String)map.get("stock_month"));
				if(!storageBillRow[8].equals("0") && endDay!= lastDay)
				{
					remainStorageBillRow = new String[STORAGE_BILL_COLUMN];
					for(int index=0; index<3; index++)
						remainStorageBillRow[index] = storageBillRow[index];
					unit = remainStorageBillRow[2];
					storageTerm = Integer.toString(endDay+1)+" - "+Integer.toString(lastDay);
					storageCnt = lastDay - (endDay+1) +1;
					remainStorageBillRow[3] = storageTerm;
					remainStorageBillRow[4] = Integer.toString(storageCnt);
					remainStorageBillRow[5] = storageBillRow[8];
					remainStorageBillRow[8] = storageBillRow[8];
					num = new BigDecimal(remainStorageBillRow[5]);
					juksu = num.setScale(0, RoundingMode.FLOOR).intValue()*storageCnt;
					remainStorageBillRow[9] = Integer.toString(juksu);
					totalJuksu += juksu;
					calcStorageBillRowPrice(remainStorageBillRow, unit, storageRateVO);
					storageBill.add(remainStorageBillRow);
				}
				String[] sumStorageBillRow = new String[STORAGE_BILL_COLUMN];
				unit = storageBillRow[2];
				sumStorageBillRow[3] = "소 계";
				sumStorageBillRow[9] = Integer.toString(totalJuksu);
				sumStorageBillRow[11] = remainStorageBillRow[11];
				calcStorageBillRowPrice(sumStorageBillRow, unit, storageRateVO);
				storageBill.add(sumStorageBillRow);
			}
		}
		//보관료청구서 작성끝
		
		for(int i=0; i<storageBill.size(); i++)
		{
			if(storageBill.get(i)[3].equals("소 계"))
			{
				String year = storageBill.get(i-1)[0];
				String sort = storageBill.get(i-1)[1];
				unit = storageBill.get(i-1)[2];
				Iterator<String[]> billIterator = storageBill.iterator();
				while(billIterator.hasNext())
				{
					String[] billRow = billIterator.next();
					if(billRow[0]!=null&&billRow[1]!=null&&billRow[2]!=null)
					{
						if(billRow[0].equals(year)&&billRow[1].equals(sort)&&billRow[2].equals(unit))
						{
							billRow[10] = null;
							billRow[11] = null;
							billRow[12] = null;
						}
					}
				}
			}
		}
		
		return storageBill;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> calcCargoBill(Map<String,Object> map, Map<String,Object> listMap)
	{
		Map<String,Object> resultMap = new HashMap<String, Object>();
		List<String[]> cargoBill = new ArrayList<String[]>();
		String[] cargoBillRow = null;
		String history_sort1 = null;
		String history_sort2 = null;
		String stock_unit = null;
		String history_date = null;
		String[] cargo_sort = null;
		String cargoBillError = null;
		int row_cnt = 0;
		
		List<HistoryVO> historyList = (List<HistoryVO>) listMap.get("historyList");
		List<CargoRateVO> cargoRateList = (List<CargoRateVO>) listMap.get("cargoRateList");
		
		Iterator<HistoryVO> historyIterator = historyList.iterator();
		while(historyIterator.hasNext())
		{
			
			historyVO = historyIterator.next();
			history_sort1 = historyVO.getHistory_sort1();
			history_sort2 = historyVO.getHistory_sort2();
			stock_unit = historyVO.getStock_unit();
			history_date = historyVO.getHistory_date();
			history_date = history_date.split("-")[1]+"."+history_date.split("-")[2];
			switch(history_sort2)
			{
			case "없음":
				row_cnt = 1;
				cargo_sort = new String[1];
				cargo_sort[0] = history_sort1;
				break;
			case "현장수매":
				row_cnt = 2;
				if(stock_unit.equals("800"))//톤백
				{
					row_cnt++;
					cargo_sort = new String[3];
					cargo_sort[0] = "매입장소입고료";
					cargo_sort[1] = "경비료";
					cargo_sort[2] = "톤백매입료";
				}
				else//톤백x
				{
					cargo_sort = new String[2];
					cargo_sort[0] = "매입장소입고료";
					cargo_sort[1] = "경비료";
				}
				break;
			case "수매이동":
				row_cnt = 4;
				if(stock_unit.equals("800"))//톤백
				{
					row_cnt++;
					cargo_sort = new String[5];
					cargo_sort[0] = "입고";
					cargo_sort[1] = "하차";
					cargo_sort[2] = "직송상차료";
					cargo_sort[3] = "경비료";
					cargo_sort[4] = "톤백매입료";
				}
				else//톤백x
				{
					cargo_sort = new String[4];
					cargo_sort[0] = "입고";
					cargo_sort[1] = "하차";
					cargo_sort[2] = "직송상차료";
					cargo_sort[3] = "경비료";
				}
				break;
			default:
				row_cnt = 2;
				cargo_sort = new String[2];
				cargo_sort[0] = history_sort1;
				cargo_sort[1] = history_sort2;
			}
			for(int i=0; i<row_cnt; i++)
			{
				cargoBillRow = new String[CARGO_BILL_COLUMN];
				cargoBillRow[0] = historyVO.getStock_year();
				cargoBillRow[1] = historyVO.getStock_sort2()+historyVO.getStock_sort1();
				cargoBillRow[2] = historyVO.getStock_unit();
				cargoBillRow[3] = history_date;
				if(historyVO.getStock_sort1().equals("쌀"))
				{
					String quantity = historyVO.getHistory_quantity();
					if(quantity.contains("."))
					{
						BigDecimal num1 = new BigDecimal(quantity.split(".")[0]);
						int num2 = new BigDecimal(quantity.split(".")[1]).intValue();
						int weight = num1.multiply(new BigDecimal(historyVO.getStock_unit())).intValue();
						weight += num2;
						cargoBillRow[12] = Integer.toString(weight);
					}
					else
						cargoBillRow[12] = calcBigDecimal(historyVO.getHistory_quantity(), historyVO.getStock_unit(), "multiplication");
				}
				else
				{
					String quantity = historyVO.getHistory_quantity();
					if(quantity.contains("."))
					{
						BigDecimal num1 = new BigDecimal(quantity.split(".")[0]);
						int num2 = new BigDecimal(quantity.split(".")[1]).intValue();
						int weight = num1.multiply(new BigDecimal("40")).intValue();
						weight += num2;
						cargoBillRow[12] = Integer.toString(weight);
					}
					else
						cargoBillRow[12] = calcBigDecimal(historyVO.getHistory_quantity(), "40", "multiplication");
				}
				calcCargoBillRowPrice(cargoBillRow, historyVO.getHistory_quantity(), cargo_sort[i], cargoRateList);
				if(cargoBillRow[13]==null&&cargoBillRow[14]==null)
				{
					if(cargoBillError==null)
					{
						cargoBillError = "하역료 요율이 없습니다. 하역료 요율을 확인해주세요.";
						map.put("cargoBillError", cargoBillError);
					}
					return map;
				}
				cargoBill.add(cargoBillRow);
			}
		}
		map.put("cargoBill", cargoBill);
		
		return map;
	}
	
	private int getLastDay(String stock_month)
	{
		int lastDay=0;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar cal = Calendar.getInstance();
			stock_month += "-01";
			cal.setTime(formatter.parse(stock_month));
			lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return lastDay;
	}
	
	private String calcBigDecimal(String sNum1, String sNum2, String command)
	{
		BigDecimal num1 = new BigDecimal(sNum1);
		BigDecimal num2 = new BigDecimal(sNum2);
		String result;
		switch(command)
		{
		case "multiplication":
			result = num1.multiply(num2).toString();
			break;
		default:
			result = num1.divide(num2).toString();
		}
		return result;
	}
	
	private void calcStorageBillRowPrice(String[] row, String unit, StorageRateVO storageRateVO)
	{
		String juksuK = "0";
		switch(unit)
		{
		case "10":
			juksuK = calcBigDecimal(row[9], "10", "multiplication"); 
			break;
		case "20":
			juksuK = calcBigDecimal(row[9], "20", "multiplication"); 
			break;
		default:
			juksuK = calcBigDecimal(row[9], "40", "multiplication"); 
		}
		
		row[10] = juksuK; //적수중량
		String sort = row[1]; //곡종
		String rate;
		BigDecimal num;
		if(row[11]==null)
		{
			if(sort.contains("쌀"))
				rate = storageRateVO.getWhite_rice_rate();
			else if(sort.contains("현미"))
				rate = storageRateVO.getBrown_rice_rate();
			else
				rate = storageRateVO.getRice_rate();
			if(unit.equals("800") || unit.equals("1000"))
			{
				rate = calcBigDecimal(rate, "1.05", "multiplication");
				num = new BigDecimal(rate);
				rate = num.setScale(1, RoundingMode.FLOOR).toString();
			}
			row[11] = rate;
		}
		String price;
		price = calcBigDecimal(juksuK, row[11], "multiplication");
		price = calcBigDecimal(price, "1000", "division");
		num = new BigDecimal(price);
		price = num.setScale(0, RoundingMode.FLOOR).toString();
		row[12] = price;
	}
	
	private void calcCargoBillRowPrice(String[] row, String quantity, String cargo_sort, List<CargoRateVO> cargoRateList)
	{
		String unit = row[2];
		String rate = null;
		if(unit.equals("800") || unit.equals("1000"))
			unit = "톤백";
		String price = null;
		
		Iterator<CargoRateVO> cargoIterator = cargoRateList.iterator();
		while(cargoIterator.hasNext())
		{
			cargoRateVO = cargoIterator.next();
			if(cargoRateVO.getWrap_sort().equals(unit))
			{
				switch(cargo_sort)
				{
				case "매입장소입고료":
					row[4] = quantity;
					rate = cargoRateVO.getPurchase_warehousing_rate();
					break;
				case "입고":
					row[5] = quantity;
					rate = cargoRateVO.getWarehousing_rate();
					break;
				case "출고":
					row[6] = quantity;
					rate = cargoRateVO.getRelease_rate();
					break;
				case "상차":
					row[8] = quantity;
					rate = cargoRateVO.getLoad_rate();
					break;
				case "하차":
					row[7] = quantity;
					rate = cargoRateVO.getUnload_rate();
					break;
				case "직송상차료":
					row[9] = quantity;
					rate = cargoRateVO.getPurchase_load_rate();
					break;
				case "경비료":
					row[10] = quantity;
					rate = cargoRateVO.getSecurity_rate();
					break;
				case "톤백매입료":
					row[11] = quantity;
					rate = cargoRateVO.getBag_purchase_rate();
					break;
				default:
					row[10] = quantity;
					if(cargo_sort.equals("이적"))
						rate = cargoRateVO.getTransfer_rate();
					else if(cargo_sort.contains("이송"))
					{
						rate = cargoRateVO.getMigration_20m_rate();
						rate = calcBigDecimal(rate, "0.5", "multiplication");
						
						if(!cargo_sort.equals("이송20m")) //50m초과 20m마다
						{
							int distance = Integer.parseInt(cargo_sort.substring(2,4));
							distance = distance - 50;
							int cnt = distance/20;
							String rate1 = calcBigDecimal(cargoRateVO.getMigration_50m_rate(), Integer.toString(cnt), "multiplication");
							String sumRate = Integer.toString(Integer.parseInt(rate)+Integer.parseInt(rate1));
							rate = calcBigDecimal(sumRate, "0.5", "multiplication");
						}
					}
				}
				if(rate==null)
					return;
				
				row[13] = rate;
				
				price = calcBigDecimal(row[12], row[13], "multiplication");
				price = calcBigDecimal(price, "1000", "division");
				BigDecimal num = new BigDecimal(price);
				price = num.setScale(0, RoundingMode.FLOOR).toString();
				row[14] = price;
			}
		}
	}
}
