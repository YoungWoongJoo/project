package com.mycom.warehouse.stock.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.member.vo.MemberVO;
import com.mycom.warehouse.stock.service.StockService;
import com.mycom.warehouse.stock.vo.StockVO;
import com.mycom.warehouse.stock.vo.StockVOs;
import com.mycom.warehouse.warehouse.service.WarehouseService;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Controller("stockConctorller")
@RequestMapping(value="/stock")
public class StockControllerImpl extends BaseController implements StockController {
	@Autowired
	StockService stockService;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	MemberVO memberVO;

	@Override
	@RequestMapping(value="/register.do")
	public ModelAndView registerForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberVO");
		String member_id = memberVO.getMember_id();
		List<WarehouseVO> warehouseList = warehouseService.listWarehouse(member_id);
		mav.addObject("warehouseList", warehouseList);
		int year = yearToStringTwoNum();
		mav.addObject("year", year);
		return mav;
	}

	@Override
	@RequestMapping(value="/addNewStock.do")
	public ResponseEntity<String> addNewStock(@RequestParam("warehouse_name") String warehouse_name, @ModelAttribute("stockVOs") StockVOs stockVOs, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		StockVO[] stockVO = stockVOs.getStockVO();
		
		for(StockVO item : stockVO)
		{
			item.setWarehouse_name(warehouse_name);
			String stock_quantity_40kg = item.getStock_quantity_40kg();
			String[] quantity = stock_quantity_40kg.split("."); //짜투리 확인 quantity[0]은 40kg포대개수, quantity[1]은 짜투리(kg)
			int unit = Integer.parseInt(item.getStock_unit());
			double bag; //단량별 포대 개수
			
			//단량별 포대 개수 계산 시작
			if(quantity==null||quantity.length<2)//수량이 정수일 경우(짜투리가 없을경우)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //수량이 실수일경우 (짜투리가 있을경우)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*Double.parseDouble(item.getStock_unit())+Double.parseDouble(quantity[1]));//총kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			item.setStock_quantity_bag(Integer.toString((int)bag));
			//단량별 포대 개수 계산 끝
			
			
		}
		try {
			stockService.register(stockVO);
		    message  = "<script>";
		    message +=" alert('재고 등록을 마쳤습니다.재고확인창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/stock/list.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += " location.href='"+request.getContextPath()+"/warehouse/register.do';";
		    message += " </script>";
			e.printStackTrace();
		}
		
		resEntity = new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/getList.do", method = RequestMethod.POST)
	public ResponseEntity<List<StockVO>> getList(@RequestParam("warehouse_name") String warehouse_name, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResponseEntity<List<StockVO>> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		List<StockVO> stockVOList = stockService.list(warehouse_name);
		resEntity = new ResponseEntity<List<StockVO>>(stockVOList, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewname");
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("member_id");
		List<WarehouseVO> list = warehouseService.listWarehouse(member_id);
		mav.addObject("warehouseList", list);
		mav.setViewName(viewName);
		return mav;
	}

	@Override
	public int yearToStringTwoNum() {
		Calendar cal = Calendar.getInstance();
		int intYear = cal.get(Calendar.YEAR);
		String stringYear = Integer.toString(intYear);
		stringYear = stringYear.substring(2);
		intYear = Integer.parseInt(stringYear);
		return intYear;
	}
	
	

}
