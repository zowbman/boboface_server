package com.boboface.advice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.boboface.base.model.vo.PubRetrunMsg;

/**
 * 
 * Title:HighLevelAdvice
 * Description:高权切面
 * @author    zwb
 * @date      2016年7月27日 下午7:35:49
 *
 */
@Aspect
@Component
public class HighLevelAdvice {
	@Around("@annotation(rm)")
	public Object highLevelAdvice(ProceedingJoinPoint pjp,RequestMapping rm) throws Throwable{
		Object responseBody = pjp.proceed();
		/*if(responseBody instanceof PubRetrunMsg){
			PubRetrunMsg pubRetrunMsg = (PubRetrunMsg) responseBody;
			Map<String, Object> data =  (Map<String, Object>) pubRetrunMsg.getData();
			if(data != null){
				//request对象
		        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		        //session对象
		        HttpSession session = request.getSession();  
		        Integer status = (Integer) session.getAttribute("whosyourdaddy");
		        data.put("highLevel", status == null ? 0 : status);
				pubRetrunMsg.setData(data);
				responseBody = pubRetrunMsg;
			}
		}*/
	    return responseBody;
	}
}
