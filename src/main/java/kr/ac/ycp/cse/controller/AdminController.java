package kr.ac.ycp.cse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.ycp.cse.model.Product;
import kr.ac.ycp.cse.service.ProductService;

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
	public String addProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			System.out.println("===Form data has some errors===");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors)
				System.out.println(error.getDefaultMessage());

			return "addProduct";
		}
		/* 리소스 이미지 저장 -> 4-6.imageUpload.mp4 30:00 참조*/
		MultipartFile productImage = product.getProductImage();
		// 루트 디렉토리를 가져옴 -> 배포 시에 사용 경로가 모두 다르기 때문
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");

		// 루트 디렉토리 포함한 path -> 이미지 최종 저장 경로
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename());

		if ((productImage != null) && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString()));
				System.out.println("이미지 저장 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("이미지 저장 실패");
			} finally {
				System.out.println("이미지 저장 시도 경로 : " + savePath.toString());
			}
		}
		/* 리소스 이미지 저장 끝 */

		product.setImageFilename(productImage.getOriginalFilename());

		/*hibernate 이후로 변경
		if (!productService.addProduct(product))
			System.out.println("Adding Product cannot be done");*/
		productService.addProduct(product);
			

		// getProducts(Model model)로 redirect
		return "redirect:/admin/productInventory";
	}

	@RequestMapping("/productInventory/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpServletRequest request) {

		Product product = productService.getProductById(id);

		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path path = Paths.get(rootDirectory + "\\resources\\images\\" + product.getImageFilename());

		// 파일 존재 시 실행
		if (Files.exists(path)) {
			try {
				Files.delete(path);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*hibernate 이후로 변경
		if (!productService.deleteProductById(id))
			System.out.println("Deleting product cannot be done");*/
		productService.deleteProduct(product);
		
		return "redirect:/admin/productInventory";
	}

	@RequestMapping("/productInventory/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model model) {

		Product product = productService.getProductById(id);
		model.addAttribute("product", product);

		return "editProduct";
	}

	@RequestMapping(value = "/productInventory/editProduct", method = RequestMethod.POST)
	public String editProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			System.out.println("===Form data has some errors===");

			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors)
				System.out.println(error.getDefaultMessage());

			return "editProduct";
		}

		MultipartFile productImage = product.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path savePath = Paths.get(rootDirectory + "\\resources\\images\\" + productImage.getOriginalFilename());

		if ((productImage != null) && !productImage.isEmpty()) {
			try {
				productImage.transferTo(new File(savePath.toString()));
				System.out.println("이미지 저장 성공");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("이미지 저장 실패");
			} finally {
				System.out.println("이미지 저장 시도 경로 : " + savePath.toString());
			}
		}

		product.setImageFilename(productImage.getOriginalFilename());

		/*hibernate 이후로 변경
		if (!productService.editProduct(product))
			System.out.println("Editing Product cannot be done");*/
		productService.editProduct(product);

		return "redirect:/admin/productInventory";
	}
}
