package kr.ac.ycp.cse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.ycp.cse.model.User;
import kr.ac.ycp.cse.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private UserService userService;

	@RequestMapping // default method
	private String getCart(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();

		User user = userService.getUserByUsername(username);
		int cartId = user.getCart().getCartId();

		model.addAttribute("cartId", cartId);

		return "cart";
	}
}