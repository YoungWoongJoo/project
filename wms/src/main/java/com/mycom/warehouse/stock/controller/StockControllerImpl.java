package com.mycom.warehouse.stock.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.member.vo.MemberVO;
import com.mycom.warehouse.stock.service.StockService;
import com.mycom.warehouse.stock.vo.MonthlyStockVO;
import com.mycom.warehouse.stock.vo.StockVO;
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
	@Autowired
	StockVO stockVO;

	@Override
	@RequestMapping(value="/register.do")
	public ModelAndView registerForm(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberVO");
		String member_id = memberVO.getMember_id();
		List<WarehouseVO> warehouseList = warehouseService.listWarehouse(member_id);
		String warehouse_name = warehouseVO.getWarehouse_name();
		mav.addObject("warehouse_name", warehouse_name);
		mav.addObject("warehouseList", warehouseList);
		int year = yearToStringTwoNum();
		mav.addObject("year", year);
		return mav;
	}

	@Override
	@RequestMapping(value="/addNewStock.do")
	public ResponseEntity<String> addNewStock(@ModelAttribute("stockVO") StockVO stockVO, @ModelAttribute("monthlyStockVO") MonthlyStockVO monthlyStockVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		if(stockService.selectStock(stockVO)!=null)
		{
			message  = "<script>";
		    message +=" alert('"+stockVO.getStock_year()+"��� "+stockVO.getStock_unit()+"kg�ܷ� "+stockVO.getStock_sort2()+stockVO.getStock_sort1()+"��(��) "+stockVO.getWarehouse_name()+"�� �̹� ��ϵ� ����Դϴ�.');";
		    message += "history.back();";
		    message += " </script>";
		}
		else
		{
			//�ܷ��� ���� ���� ��� ����
			String stock_quantity_40kg = stockVO.getStock_quantity_40kg();
			String[] quantity = stock_quantity_40kg.split("\\."); //¥���� Ȯ�� quantity[0]�� 40kg���밳��, quantity[1]�� ¥����(kg)
			int unit = Integer.parseInt(stockVO.getStock_unit());
			double bag; //�ܷ��� ���� ����
			
			if(quantity==null||quantity.length<2)//������ ������ ���(¥������ �������)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //������ �Ǽ��ϰ�� (¥������ �������)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*40)+Double.parseDouble(quantity[1]);//��kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			stockVO.setStock_quantity_bag(Integer.toString((int)bag));
			//�ܷ��� ���� ���� ��� ��
			
			try {
				stockService.register(stockVO, monthlyStockVO);
			    message  = "<script>";
			    message +=" alert('��� ����� ���ƽ��ϴ�.���Ȯ��â���� �̵��մϴ�.');";
			    message += " location.href='"+request.getContextPath()+"/stock/list.do';";
			    message += " </script>";
			    
			}catch(Exception e) {
				message  = "<script>";
			    message +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			    message += "history.back();";
			    message += " </script>";
				e.printStackTrace();
			}
		}
		resEntity = new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/getList.do", method = RequestMethod.POST) //�����Ȳ���������� â���ý� �����Ʈ �߰�
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
	@RequestMapping(value="/list.do") //�����Ȳ������ �ε� / â����Ʈ ��ü�߰�
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewname");
		HttpSession session = request.getSession();
		memberVO =  (MemberVO)session.getAttribute("memberVO");
		String member_id = memberVO.getMember_id();
		List<WarehouseVO> list = warehouseService.listWarehouse(member_id);
		mav.addObject("warehouseList", list);
		mav.setViewName(viewName);
		return mav;
	}

	@Override
	@RequestMapping(value="/updateForm.do")
	public ModelAndView updateForm(@ModelAttribute("stockVO") StockVO stockVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		memberVO =  (MemberVO)session.getAttribute("memberVO");
		String member_id = memberVO.getMember_id();
		List<WarehouseVO> list = warehouseService.listWarehouse(member_id);
		mav.addObject("warehouseList", list);
		stockVO = stockService.search(stockVO);
		mav.addObject("stockVO", stockVO);
		int year = yearToStringTwoNum();
		mav.addObject("year", year);
		return mav;
	}

	@Override
	@RequestMapping(value="/update.do")
	public ResponseEntity<String> updateStock(@ModelAttribute("stockVO") StockVO stockVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		//�ܷ��� ���� ���� ��� ����
		String stock_quantity_40kg = stockVO.getStock_quantity_40kg();
		String[] quantity = stock_quantity_40kg.split("\\."); //¥���� Ȯ�� quantity[0]�� 40kg���밳��, quantity[1]�� ¥����(kg)
		int unit = Integer.parseInt(stockVO.getStock_unit());
		double bag; //�ܷ��� ���� ����
		
		if(quantity==null||quantity.length<2)//������ ������ ���(¥������ �������)
		{
			bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
			bag = Math.ceil(bag);
		}
		else //������ �Ǽ��ϰ�� (¥������ �������)
		{
			double total;
			total = (Double.parseDouble(quantity[0])*40)+Double.parseDouble(quantity[1]);//��kg
			bag = total/(double)unit;
			bag = Math.ceil(bag);
		}
		stockVO.setStock_quantity_bag(Integer.toString((int)bag));
		//�ܷ��� ���� ���� ��� ��
		
		try {
			stockService.update(stockVO);
		    message  = "<script>";
		    message +=" alert('��� ������ ���ƽ��ϴ�.��� ��Ȳâ���� �̵��մϴ�.');";
		    message += " location.href='"+request.getContextPath()+"/stock/list.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		
		resEntity = new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/delete.do")
	public ResponseEntity<String> deleteStock(@ModelAttribute("stockVO") StockVO stockVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			stockService.delete(stockVO);
		    message  = "<script>";
		    message +=" alert('��� ������ ���ƽ��ϴ�.��� ��Ȳâ���� �̵��մϴ�.');";
		    message += " location.href='"+request.getContextPath()+"/stock/list.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		
		resEntity = new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@ResponseBody
	@RequestMapping(value="/keywordSearch.do")
	public List<String> keywordSearch(@RequestParam("keyword") String keyword, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		if(keyword == null || keyword.equals(""))
		   return null ;
		List<String> kewordList = stockService.keywordSearch(keyword);
		return kewordList;
	}

	@Override
	@RequestMapping(value="/selectStock.do")
	public @ResponseBody StockVO selectStock(@ModelAttribute StockVO stockVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return stockService.selectStock(stockVO);
	}

	@Override
	@RequestMapping(value="/selectMonth.do")
	public @ResponseBody List<String> selectMonth(@RequestParam("warehouse_name") String warehouse_name, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return stockService.selectMonth(warehouse_name);
	}
}
