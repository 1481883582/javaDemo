package com.aspect;

import com.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 利用 aop查看sql参数
 */
@Slf4j
@Aspect
@Component

public class LogAspect {

	@Pointcut("execution(* com.*.controller..*(..))")
	public void log() {
	}

	@Before("log()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest httpServletRequest = attributes.getRequest();

		//获取请求地址
		log.info("url={}", httpServletRequest.getRequestURL());

		//请求方法
		log.info("method={}", httpServletRequest.getMethod());

		//类方法
		log.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

		// 获取参数名
		String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		log.info("argNames={}", argNames.toString());

		// 获取参数值ֵ
//		Object[] args = joinPoint.getArgs();
//		for (int i = 0; i < args.length; i++) {
//			log.info("具体值={}", args[i].toString());
//		}
		log.info("args={}", joinPoint.getArgs());
	}

	@AfterReturning(returning = "object", pointcut = "log()")
	public void doAfterReturning(Object object) {
		if (Strings.isNotEmpty(object)) log.info("response={}", object.toString());
	}
}
