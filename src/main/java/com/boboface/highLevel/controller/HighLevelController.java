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
		return new PubRetrunMsg(CODE._100000, data);
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
		int status = 0;
		String msg = "密钥错误，启用高权状态失败";
		if(cmd == null){
			logger.error("参数cmd为空");
			throw new CustomException("参数错误");
		}
		if("whosyourdaddy".equals(cmd)){
			status = 1;
			msg = "启用高权状态成功,3秒后重置页面";
			httpSession.setAttribute("whosyourdaddy", status);
			WebUtil.addCookie(resp, "whosyourdaddyId", sessionId, 30 * 60);
		}else if("quit".equals(cmd)){
			msg = "退出高权状态成功,3秒后重置页面";
			httpSession.removeAttribute("whosyourdaddy");
		}else{//高权密钥错误
			return new PubRetrunMsg(CODE._200005, null);
		}
		data.put("status", status);
		data.put("msg", msg);
		return new PubRetrunMsg(CODE._100000, data);
	}
}
