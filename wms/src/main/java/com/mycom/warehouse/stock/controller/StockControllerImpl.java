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
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
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

	@Override
	@RequestMapping(value="/register.do")
	public ResponseEntity<String> register(@RequestParam("warehouse_name") String warehouse_name, @ModelAttribute("stockVOs") StockVOs stockVOs, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		StockVO[] stockVO = stockVOs.getStockVO();
		
		for(StockVO item : stockVO)
		{
			item.setWarehouse_name(warehouse_name);
			String stock_quantity_40kg = item.getStock_quantity_40kg();
			String[] quantity = stock_quantity_40kg.split("."); //¥���� Ȯ�� quantity[0]�� 40kg���밳��, quantity[1]�� ¥����(kg)
			int unit = Integer.parseInt(item.getStock_unit());
			double bag; //�ܷ��� ���� ����
			
			//�ܷ��� ���� ���� ��� ����
			if(quantity==null||quantity.length<2)//������ ������ ���(¥������ �������)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //������ �Ǽ��ϰ�� (¥������ �������)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*Double.parseDouble(item.getStock_unit())+Double.parseDouble(quantity[1]));//��kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			item.setStock_quantity_bag(Integer.toString((int)bag));
			//�ܷ��� ���� ���� ��� ��
			
			
		}
		try {
			stockService.register(stockVO);
		    message  = "<script>";
		    message +=" alert('��� ����� ���ƽ��ϴ�.���Ȯ��â���� �̵��մϴ�.');";
		    message += " location.href='"+request.getContextPath()+"/stock/list.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
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

}
