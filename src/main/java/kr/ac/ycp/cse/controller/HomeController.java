package kr.ac.ycp.cse.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {

		Logger logger = LoggerFactory.getLogger(HomeController.class);

		// 패키지명을 포함하여 인자를 넘겨도 상관 없음. 이 방법은 다소 불편
		// Logger logger = LoggerFactory.getLogger(kr.ac.ycp.cse.controller.HomeController);

		/*
		 * 이 로거는 "kr.ac.ycp.cse"라는 이름 때문에 logback.xml에 기술돼있는
		 * kr.ac.ycp.cse logger의 자식이 되며, 따라서 상속받는다. 물론 HomeController.class로
		 * 기술하여도 상속을 받는다.
		 */

		// logger.trace("trace level: hello");
		// logger.debug("debug level: hello");
		// logger.info("info level: hello");
		// logger.warn("warn level: hello");
		// logger.error("error level: hello");

		// Request URL, getRequestURI()로 하지 않도록 주의
		String url = request.getRequestURL().toString();

		// client IP Address
		String clientIPaddress = request.getRemoteAddr();

		// 다음 코드보다 구현한 코드가 더  낫다.
		// logger.info("request url: " + url);
		// logger.info("client ip: " + clientIPaddress);
		logger.info("request url: {}", url);
		logger.info("client ip: {}", clientIPaddress);

		return "home";
	}

}
