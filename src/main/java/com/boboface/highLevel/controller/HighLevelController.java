package com.boboface.highLevel.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zowbman.base.helper.CodeHelper.CODE;
import net.zowbman.base.model.vo.PubRetrunMsg;
import net.zowbman.base.util.WebUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boboface.base.controller.BaseController;
import com.boboface.common.HighLevelStatus;
import com.boboface.exception.CustomException;

@Controller
@RequestMapping("/boboface")
public class HighLevelController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(HighLevelController.class);
	
	/**
	 * 获取高权状态
	 * @param request
	 * @param response
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/highLevel/status")
	public @ResponseBody PubRetrunMsg highLevelStatusV1(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> data = new HashMap<String, Object>();
		Integer status = HighLevelStatus.getStatus(request, response);
		data.put("status", status);
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * 进入高权状态
	 * @param httpSession
	 * @param sessionId
	 * @param resp
	 * @param cmd 命令
	 * @return PubRetrunMsg
	 * @throws CustomException
	 */
	@RequestMapping("/json/v1/highLevel")
	public @ResponseBody PubRetrunMsg highLevelInV1(HttpSession httpSession, @CookieValue(value = "JSESSIONID", defaultValue = "") String sessionId,
			HttpServletResponse resp, String cmd) throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		if(cmd == null){
			return new PubRetrunMsg(CODE.D200001, "参数错误,cmd不允许为空");
		}
		if("whosyourdaddy".equals(cmd)){
			data.put("status", 1);
			httpSession.setAttribute("whosyourdaddy", 1);
			WebUtil.addCookie(resp, "whosyourdaddyId", sessionId, 30 * 60);
			return new PubRetrunMsg(CODE.D200400, data);
		}else if("quit".equals(cmd)){
			httpSession.removeAttribute("whosyourdaddy");
			return new PubRetrunMsg(CODE.D200401, data);
		}else{//高权密钥错误
			return new PubRetrunMsg(CODE.D200402, data);
		}
	}
}
