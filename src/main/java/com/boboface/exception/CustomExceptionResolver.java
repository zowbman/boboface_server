package com.boboface.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zowbman.base.helper.CodeHelper.CODE;
import net.zowbman.base.model.vo.PubRetrunMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Title:CustomExceptionResolver
 * Description:全局异常处理器
 * 				如果方法返回是返回json信息，则返回json
 * 				如果方法返回页面，则返回页面
 * @author    zwb
 * @date      2016年5月10日 下午11:09:18
 *
 */
public class CustomExceptionResolver extends ExceptionHandlerExceptionResolver implements HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);  
	
	/**
	 * json序列化
	 */
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		HandlerMethod handlerMethod = null;//处理器method
		if( handler instanceof HandlerMethod){
			handlerMethod = (HandlerMethod) handler;
		}else{
			return modelAndView;
		}
		ResponseBody body = handlerMethod.getMethodAnnotation(ResponseBody.class);//获取返回类型
		
		CustomException customException=null;
		
		if(ex instanceof CustomException){
			customException = (CustomException) ex;
		}else{
			customException = new CustomException("未知错误");
			logger.error("全局异常处理捕获异常 catch:", ex);
		}
		String message = customException.getMessage();
		if(body == null){			
			modelAndView.setViewName("jsps/error");//指向错误页面
			modelAndView.addObject("msg", message);
			return modelAndView;
		}
	
		//返回json串
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
	    response.setHeader("Cache-Control","no-cache, must-revalidate");
	    try {
	    	response.getWriter().write(objectMapper.writeValueAsString(new PubRetrunMsg(CODE._300000, null)));
		} catch (IOException e) {
			logger.error("IOException catch:" ,e);
		}
	    return modelAndView;
	}
}
