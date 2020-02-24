package com.mycom.warehouse.history.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.history.vo.HistoryVO;

public interface HistoryController {
	public ModelAndView register(HttpServletRequest request, HttpServletResponse resoponse) throws Exception;
	public ModelAndView list(HttpServletRequest request, HttpServletResponse resoponse) throws Exception;
	public ModelAndView registerCheck(@ModelAttribute("historyVO")HistoryVO historyVO, HttpServletRequest request, HttpServletResponse resoponse) throws Exception;
	public ResponseEntity<String> addNewHistory(@ModelAttribute("historyVO")HistoryVO historyVO, HttpServletRequest request, HttpServletResponse resoponse) throws Exception;
	public List<HistoryVO> getList(@ModelAttribute("historyVO")HistoryVO historyVO, HttpServletRequest request, HttpServletResponse resoponse) throws Exception;
	public ResponseEntity<String> deleteHistory(@ModelAttribute("historyVO")HistoryVO historyVO, HttpServletRequest request, HttpServletResponse resoponse) throws Exception;
}
