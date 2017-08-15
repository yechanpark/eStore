package kr.ac.ycp.cse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogController {

	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {

		if (error != null)
			model.addAttribute("error", "Invalid username and password");

		if (logout != null)
			model.addAttribute("logout", "You have been logged out successfully");

		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

		// Cookie removal logic to perform a clean logout (쿠키 제거 작업)
		CookieClearingLogoutHandler cookieClearingLogoutHandler
			= new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
		
		cookieClearingLogoutHandler.logout(request, response, null);

		// Invalidates HTTP Session. Removes the Authentication
		// from the Security Context
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null)
			new SecurityContextLogoutHandler().logout(request, response, auth);

		return "redirect:/login?logout";// You can redirect wherever you want,
										// but generally it's a good practice to
										// show login screen again.
	}
}
