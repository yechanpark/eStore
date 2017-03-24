package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.POST)
	// Product객체에 사용자가 입력한 값이 스프링에 의해 바인딩/검증 된다.
	public String addProductPost(@Valid Product product, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("===Form data has some errors===");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors)
				System.out.println(error.getDefaultMessage());

			return "addProduct";
		}

		if (!productService.addProduct(product))
			System.out.println("Adding Product cannot be done");

		// getProducts(Model model)로 redirect
		return "redirect:/admin/productInventory";
	}

	@RequestMapping("/productInventory/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id) {

		if (!productService.deleteProductById(id))
			System.out.println("Deleting product cannot be done");

		return "redirect:/admin/productInventory";
	}

	@RequestMapping("/productInventory/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model model) {

		Product product = productService.getProductById(id);

		model.addAttribute("product", product);

		return "editProduct";
	}

	@RequestMapping(value = "/productInventory/editProduct", method = RequestMethod.POST)
	public String editProductPost(@Valid Product product, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println("===Form data has some errors===");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors)
				System.out.println(error.getDefaultMessage());

			return "editProduct";
		}

		if (!productService.editProduct(product))
			System.out.println("Editing Product cannot be done");

		return "redirect:/admin/productInventory";
	}
}
