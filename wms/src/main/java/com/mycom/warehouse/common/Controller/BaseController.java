package com.mycom.warehouse.common.Controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController  {	
	@RequestMapping(value="/*.do" ,method={RequestMethod.POST,RequestMethod.GET})
	protected  ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}
	
	public int yearToStringTwoNum() {
		Calendar cal = Calendar.getInstance();
		int intYear = cal.get(Calendar.YEAR);
		String stringYear = Integer.toString(intYear);
		stringYear = stringYear.substring(2);
		intYear = Integer.parseInt(stringYear);
		return intYear;
	}
}
