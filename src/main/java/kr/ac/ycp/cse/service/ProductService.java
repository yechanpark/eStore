package kr.ac.ycp.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.ycp.cse.dao.ProductDao;
import kr.ac.ycp.cse.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public List<Product> getProducts() {
		return productDao.getProducts();
	}
	
	// hibernate사용 이후 리턴타입  boolean -> void변경
	public void addProduct(Product product) {
		/*return*/ productDao.addProduct(product);
	}
	
	/* hibernate사용 이후 바뀜
	public boolean deleteProductById(int id) {
		return productDao.deleteProduct(id);
	}*/
	public void deleteProduct(Product product) {
		productDao.deleteProduct(product);
	}
	
	public Product getProductById(int id) {
		return productDao.getProductById(id);
	}
	
	// hibernate사용 이후 리턴타입  boolean -> void변경
	public void editProduct(Product product) {
		/*return*/ productDao.editProduct(product);
	}
}
