package com.cloud.bbs.aspect;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//스프링에게 인식시키기 위해 Compenent를 붙여준다. 
@Component
@Aspect
public class SignInAspect {
	private static final Logger logger = LoggerFactory.getLogger(SignInAspect.class);
	
	//where?
	@Pointcut("execution(* com.cloud.bbs.controller.BBSController.writeForm(..))")
	public void signIn() {
		logger.info("SingInAspect의 writeForm 포인트컷 동작");
	}
	
	@Pointcut("execution(* com.cloud.bbs.controller.BBSController.write(..))")
	public void checkMethodTime() {
		logger.info("SingInAspect의 write 포인트컷 동작");
	}
	
	//when? what? advice의 종류는 총 5개이다. (before, after, after-returning, after-thorwing, around)
	@Around("signIn()") //value안에 들어갈 밸류는 포인터컷의 이름, ||로 여러개의 포인트컷을 지정할 수 있다.
	public Object signIncheck(ProceedingJoinPoint pjt) throws Throwable {
		HttpSession session = null;
		
		for(Object obj : pjt.getArgs()) {
			//타입 비교
			if(obj instanceof HttpSession) {
				session=(HttpSession)obj;
			}
		}
		
		if(session.getAttribute("id")==null) {
			return "login";	
		}
		Object result = pjt.proceed(); //기능의 전 후를 결정하는 코드
		return result;
	}
	
	@Around("checkMethodTime()") 
	public Object checkMethodTime(ProceedingJoinPoint pjt) throws Throwable {
		long startTime = System.currentTimeMillis();
		
		Object result = pjt.proceed(); //기능의 전 후를 결정하는 코드
		long methodExecutionTime = System.currentTimeMillis() - startTime;
		logger.info("메소드 동작시간은 :" + methodExecutionTime);
		return result;
	}
}
