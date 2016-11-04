package com.boboface.advice;

import java.lang.annotation.Annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boboface.base.model.vo.PubRetrunMsg;

/**
 * 
 * Title:LogAdvice
 * Description:Spring aop全局日志管理
 * @author    zwb
 * @date      2016年9月12日 下午2:02:31
 *
 */
@Aspect
@Component
public class LogAdvice {
	
	private Logger logger = LoggerFactory.getLogger(LogAdvice.class);
	
	@Around("@annotation(rm)")
	public Object logAdvice(ProceedingJoinPoint pjp,RequestMapping rm) throws Throwable{
		Object responseBody = pjp.proceed();
		if(responseBody instanceof PubRetrunMsg){
			//url
			String[] lTypeStrArr = null;
			Annotation[] annotations = pjp.getSourceLocation().getWithinType().getAnnotations();
			for (Annotation annotation : annotations) {
				if(annotation instanceof RequestMapping){
					lTypeStrArr = new String[((RequestMapping)annotation).value().length];
					lTypeStrArr = ((RequestMapping)annotation).value();
				}
			}
			
			String[] strArr = rm.value();
			String serviceName = "";
			if(strArr != null && strArr.length > 0){
				serviceName = strArr[0];
			}
			
			if(lTypeStrArr != null && lTypeStrArr.length > 0){
				serviceName = lTypeStrArr[0] + serviceName;
			}
			
			
			PubRetrunMsg pubRetrunMsg = (PubRetrunMsg) responseBody;
			if(pubRetrunMsg.getCode() != 100000){
				logger.error("url:" + serviceName + ",code:" + pubRetrunMsg.getCode() + ",logMsg:" + pubRetrunMsg.getLogMsg());//
			}
		}
		return responseBody;
	}
}
