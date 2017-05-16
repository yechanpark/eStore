package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.Cart;
import kr.ac.hansung.cse.model.ShippingAddress;
import kr.ac.hansung.cse.model.User;
import kr.ac.hansung.cse.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;

	@RequestMapping("/register")
	public String registerUser(Model model) {

		/* User객체와 관계를 가지는 객체(ShippingAddress)를 세팅하지 않으면 User저장 시 오류 발생. */
		User user = new User();
		ShippingAddress shippingAddress = new ShippingAddress();
		user.setShippingAddress(shippingAddress);

		// shippingAddress.setUser(user);

		user.setUsername("test");
		model.addAttribute("user", user);

		return "registerUser";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUserPost(@Valid User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "registerUser";
		}

		List<User> userList = userService.getAllUsers();
		for (int i = 0; i < userList.size(); i++) {
			if (user.getUsername().equals(userList.get(i).getUsername())) {
				model.addAttribute("usernameMsg", "Username already exists.");

				return "registerUser";
			}
		}

		user.setEnabled(true);

		if (user.getUsername().equals("admin"))
			user.setAuthority("ROLE_ADMIN");
		else
			user.setAuthority("ROLE_USER");

		// user.getShippingAddress().setUser(user);

		/* User객체와 관계를 가지는 객체(Cart)를 세팅하지 않으면 User저장 시 오류 발생. */
		Cart newCart = new Cart();
		user.setCart(newCart);
		userService.addUser(user);

		return "registerUserSuccess";

	}
}
