package kr.ac.hansung.cse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;

	// "/admin" defualt call
	@RequestMapping
	public String adminPage() {

		return "admin";
	}

	@RequestMapping("/productInventory")
	public String getProducts(Model model) {
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);

		return "productInventory";
	}

	@RequestMapping("/productInventory/addProduct")
	public String addProduct(Model model) {
		
		// 반드시 객체를 생성하여 Model로 넘겨야 한다.
		// view에서 Spring Form Tag를 사용하는데, 이 때 매치되는 객체가 없으면 에러가 난다.
		Product product = new Product();
		
		product.setName("노트북");
		product.setCategory("컴퓨터");
		model.addAttribute("product", product);

		return "addProduct";
	}
}
