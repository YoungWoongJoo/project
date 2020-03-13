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
import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.StorageRateVO;
import com.mycom.warehouse.warehouse.service.WarehouseService;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Controller("billController")
@RequestMapping(value="/bill")
public class BillControllerImpl extends BaseController implements BillController{
	private static final int STORAGE_BILL_COLUMN = 13;
	private static final int CARGO_BILL_COLUMN = 15;
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
		if(!billService.checkUpdate(map))//���� ������ �ȵȰ�� ������������
		{
			billService.upToDateStock(map);//����� ���̺� �ֽ�ȭ
		}
		String storageBillError = null, cargoBillError = null;
		Map<String,Object> listMap = billService.selectLists(map);
		List<String[]> storageBill = calcStorageBill(map, listMap); //������û���� ���
		if(storageBill==null)
		{
			storageBillError = "������ ������ �����ϴ�. ������ ������ Ȯ�����ּ���.";
		}
		Map<String, Object> cargoMap = calcCargoBill(map, listMap); //�δ��û���� ���
		List<String[]> cargoBill = (List<String[]>) cargoMap.get("cargoBill");
		cargoBillError = (String) cargoMap.get("cargoBillError");
		
		Map<String,Object> billMap = new HashMap<String, Object>();
		billMap.put("storageBill", storageBill);
		billMap.put("storageBillError", storageBillError);
		billMap.put("cargoBill", cargoBill);
		billMap.put("cargoBillError", cargoBillError);
		
		return billMap;
	}

	@Override
	@RequestMapping(value="downloadExcel.do")
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		File dir = new File(request.getSession().getServletContext().getRealPath("/")+"resources"+File.separator+"billForm"); //�������� ���丮��ġ
		FileInputStream in = new FileInputStream(dir+File.separator+"�����Ӿ��.xlsx"); //���� ���� �б�
		
		Workbook wb = WorkbookFactory.create(in); //���� ���� ������ workbook�� ����
		
		String userAgent = request.getHeader("User-Agent");
		boolean ie = (userAgent.indexOf("MSIE") > -1) || (userAgent.indexOf("Trident") > -1);
		String fileName = "";
		if (ie) {
		        fileName = URLEncoder.encode("������û����.xlsx", "UTF-8").replaceAll("\\+", "%20");
		} else {
		        fileName = new String("������û����.xlsx".getBytes("UTF-8"), "iso-8859-1");
		}
		response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +"\"");
		 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		
		wb.write(response.getOutputStream()); //workbook�� ���� �������� ����
		response.getOutputStream().close();
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
		
		//������û���� �ۼ�����
		while(stockIterator.hasNext())
		{
			monthlyStockVO = stockIterator.next();
			stockNum = monthlyStockVO.getStock_seq_num();
			
			historyCnt = 0;
			emptyStock = false;
			totalJuksu = 0;
			for(int i=0;i<historyList.size();i++)//���������� �ִ� ��� �ִ��� üũ
			{
				historyVO = historyList.get(i);
				if(!historyVO.getHistory_sort1().equals("����") && stockNum==historyVO.getStock_seq_num())
				{
					historyCnt++;
					
					//�����Ⱓ, �����ϼ� ������
					
					if(historyCnt==1 && historyVO.getHistory_sort1().equals("�԰�") && historyVO.getStock_prev().equals("0"))//���� �԰�� ���, 
					{
						emptyStock = true;
						workDay = historyVO.getHistory_date().substring(8);
						startDay = Integer.parseInt(workDay)+1; //�۾��ϴ������� ����������
						if(i+1<historyList.size())
						{
							if(historyList.get(i+1).getStock_seq_num()==stockNum) //�����۾��� �� ���� ���,
							{
								nextWorkDay = historyList.get(i+1).getHistory_date().substring(8);
								endDay = Integer.parseInt(nextWorkDay); //�����۾����� ������������¥
							}
						}
						else //�����۾��� ���� ���,
							lastDay = getLastDay((String)map.get("stock_month"));;
							endDay = lastDay;
					}
					else 
					{
						if(historyVO.getHistory_sort1().equals("���") && historyVO.getStock_present().equals("0"))
							emptyStock = true;
						if(historyCnt>1) //�ش� ����� �ι�°�̻� �۾��� ���,
						{
							prevWorkDay = historyList.get(i-1).getHistory_date().substring(8);
							startDay = Integer.parseInt(prevWorkDay)+1; //�����۾��ϴ������� ����������
						}
						else//������ �ִ� ��� �߰��� �԰�ǰų� ���� ���,
						{
							startDay = 1;//���������� = ��� 1��
						}
						workDay = historyVO.getHistory_date().substring(8);
						endDay =  Integer.parseInt(workDay);
					}
					storageTerm = Integer.toString(startDay)+" - "+Integer.toString(endDay); //�����Ⱓ
					storageCnt = endDay-startDay+1;//�����ϼ�
															
					//�����Ⱓ, �����ϼ� ��곡
					
					storageBillRow = new String[STORAGE_BILL_COLUMN];
					storageBillRow[0] = historyVO.getStock_year(); //���
					storageBillRow[1] = historyVO.getStock_sort2()+historyVO.getStock_sort1(); //����
					storageBillRow[2] = historyVO.getStock_unit(); //�ܷ�			 
					unit = historyVO.getStock_unit(); //�ܷ�			 
					storageBillRow[3] = storageTerm; //�����Ⱓ
					storageBillRow[4] = Integer.toString(storageCnt); //�����ϼ�			
					storageBillRow[5] = historyVO.getStock_prev(); //�̿���
					if(historyVO.getHistory_sort1().equals("�԰�"))
					{
						storageBillRow[6] = historyVO.getHistory_quantity(); //�԰�
					}
					else if(historyVO.getHistory_sort1().equals("���"))
					{
						storageBillRow[7] = historyVO.getHistory_quantity(); //���
					}
					storageBillRow[8] = historyVO.getStock_present(); //�ܷ�
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
			}//�������� üũ��
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
				storageTerm = Integer.toString(startDay)+" - "+Integer.toString(endDay); //�����Ⱓ
				storageCnt = endDay-startDay+1;//�����ϼ�
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
			else if(!(historyCnt==1 && emptyStock==true)) //���������� 1���̰� ���� �԰�ǰų� ���� ���� ��찡 �ƴ� ��,
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
				sumStorageBillRow[3] = "�� ��";
				sumStorageBillRow[9] = Integer.toString(totalJuksu);
				sumStorageBillRow[11] = remainStorageBillRow[11];
				calcStorageBillRowPrice(sumStorageBillRow, unit, storageRateVO);
				storageBill.add(sumStorageBillRow);
			}
		}
		//������û���� �ۼ���
		
		for(int i=0; i<storageBill.size(); i++)
		{
			if(storageBill.get(i)[3].equals("�� ��"))
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
			case "����":
				row_cnt = 1;
				cargo_sort = new String[1];
				cargo_sort[0] = history_sort1;
				break;
			case "�������":
				row_cnt = 2;
				if(stock_unit.equals("800"))//���
				{
					row_cnt++;
					cargo_sort = new String[3];
					cargo_sort[0] = "��������԰��";
					cargo_sort[1] = "����";
					cargo_sort[2] = "�����Է�";
				}
				else//���x
				{
					cargo_sort = new String[2];
					cargo_sort[0] = "��������԰��";
					cargo_sort[1] = "����";
				}
				break;
			case "�����̵�":
				row_cnt = 4;
				if(stock_unit.equals("800"))//���
				{
					row_cnt++;
					cargo_sort = new String[5];
					cargo_sort[0] = "�԰�";
					cargo_sort[1] = "����";
					cargo_sort[2] = "���ۻ�����";
					cargo_sort[3] = "����";
					cargo_sort[4] = "�����Է�";
				}
				else//���x
				{
					cargo_sort = new String[4];
					cargo_sort[0] = "�԰�";
					cargo_sort[1] = "����";
					cargo_sort[2] = "���ۻ�����";
					cargo_sort[3] = "����";
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
				cargoBillRow[12] = calcBigDecimal(historyVO.getHistory_quantity(), stock_unit, "multiplication");
				calcCargoBillRowPrice(cargoBillRow, historyVO.getHistory_quantity(), cargo_sort[i], cargoRateList);
				if(cargoBillRow[13]==null&&cargoBillRow[14]==null)
				{
					cargoBillError = "������ ������ �����ϴ�. ������ ������ Ȯ�����ּ���.";
					map.put("cargoBillError", cargoBillError);
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
		
		row[10] = juksuK; //�����߷�
		String sort = row[1]; //����
		String rate;
		BigDecimal num;
		if(row[11]==null)
		{
			if(sort.contains("��"))
				rate = storageRateVO.getWhite_rice_rate();
			else if(sort.contains("����"))
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
			unit = "���";
		String price = null;
		
		Iterator<CargoRateVO> cargoIterator = cargoRateList.iterator();
		while(cargoIterator.hasNext())
		{
			cargoRateVO = cargoIterator.next();
			if(cargoRateVO.getWrap_sort().equals(unit))
			{
				switch(cargo_sort)
				{
				case "��������԰��":
					row[4] = quantity;
					rate = cargoRateVO.getPurchase_warehousing_rate();
					break;
				case "�԰�":
					row[5] = quantity;
					rate = cargoRateVO.getWarehousing_rate();
					break;
				case "���":
					row[6] = quantity;
					rate = cargoRateVO.getRelease_rate();
					break;
				case "����":
					row[7] = quantity;
					rate = cargoRateVO.getLoad_rate();
					break;
				case "����":
					row[8] = quantity;
					rate = cargoRateVO.getUnload_rate();
					break;
				case "���ۻ�����":
					row[9] = quantity;
					rate = cargoRateVO.getPurchase_load_rate();
					break;
				case "����":
					row[10] = quantity;
					rate = cargoRateVO.getSecurity_rate();
					break;
				case "�����Է�":
					row[11] = quantity;
					rate = cargoRateVO.getBag_purchase_rate();
					break;
				default:
					row[10] = quantity;
					if(cargo_sort.equals("����"))
						rate = cargoRateVO.getTransfer_rate();
					else if(cargo_sort.contains("�̼�"))
					{
						if(cargo_sort.equals("�̼�20m"))
							rate = cargoRateVO.getMigration_20m_rate();
						else
						{
							String distance = cargo_sort.substring(2,4);
							int cnt = Integer.parseInt(distance)/20;
							rate = calcBigDecimal(cargoRateVO.getMigration_50m_rate(), Integer.toString(cnt), "multiplication");
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

	
	/*
	private String[][] calcStorageBill(Map<String,Object> map, Map<String,Object> listMap)
	{
		List<MonthlyStockVO> monthlyStockList = (List<MonthlyStockVO>) listMap.get("monthlyStockList");
		List<HistoryVO> historyList = (List<HistoryVO>) listMap.get("historyList");
		List<StorageRateVO> storageRateList = (List<StorageRateVO>) listMap.get("storageRateList");
		
		int rowNum =  countRowNumForStorageBill(monthlyStockList, historyList);
		String[][] storageBill = new String[rowNum][STORAGE_BILL_COLUMN];
		
		return storageBill;
	}
	
	private String[][] calcCargoBill(Map<String,Object> map, Map<String,Object> listMap)
	{
		List<HistoryVO> historyList = (List<HistoryVO>) listMap.get("historyList");
		List<CargoRateVO> cargoRateList = (List<CargoRateVO>) listMap.get("cargoRateList");
		
		int rowNum = countRowNumForCargoBill(historyList);
		String[][] cargoBill = new String[rowNum][CARGO_BILL_COLUMN];
		return cargoBill;
	}
	
	private int countRowNumForStorageBill(List<MonthlyStockVO> monthlyStockList, List<HistoryVO> historyList)
	{
		int rowNum = 0;
		
		rowNum += monthlyStockList.size();
		
		Iterator<HistoryVO> historyIterator = historyList.iterator();
		
		while(historyIterator.hasNext())
		{
			
		}
		
		return rowNum;
	}
	
	private int countRowNumForCargoBill(List<HistoryVO> historyList)
	{
		int rowNum = 0;
		
		Iterator<HistoryVO> iterator = historyList.iterator();
		while(iterator.hasNext())
		{
			historyVO = iterator.next();
			String stock_unit = historyVO.getStock_unit();
			String history_sort2 = historyVO.getHistory_sort2();
			
			switch(history_sort2)
			{
			case "�������":
				rowNum += 2;
				if(stock_unit.equals("800"))
					rowNum += 1;
				break;
			case "�����̵�":
				rowNum += 4;
				if(stock_unit.equals("800"))
					rowNum += 1;
				break;
			case "����":
				rowNum += 1;
			default:
				rowNum += 2;
			}
		}
		
		return rowNum;
	}
	*/
}
