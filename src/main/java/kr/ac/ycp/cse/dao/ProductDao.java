package kr.ac.ycp.cse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.ycp.cse.model.Product;

@Repository
@Transactional
public class ProductDao {

	/* hibernate적용 이후 더 이상 사용하지 않음
	 * private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}*/

	@Autowired
	private SessionFactory sessionFactory;
	
	public Product getProductById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Product product = session.get(Product.class, id); // id를 기준으로 Product를 가져옴
		
		return product;
		
		/*hibernate적용 이후 더 이상 사용하지 않음
		String sqlStatement = "select * from product where id=?";
		return jdbcTemplateObject.queryForObject(sqlStatement, new Object[] { id }, new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getInt("price"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setUnitInStock(rs.getInt("unitInStock"));
				product.setDescription(rs.getString("description"));
				product.setImageFilename(rs.getString("imageFilename"));

				return product;
				
			}

		});*/
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getProducts() {
		Session session = sessionFactory.getCurrentSession();
		// HQL을 사용. "Product"는 Table이름이 아닌 Class이름이다.
		Query query = session.createQuery("from Product"); 
		List<Product> productList = query.list();
		
		return productList;
		
		/*hibernate적용 이후 더 이상 사용하지 않음
		String sqlStatement = "select * from product";
		return jdbcTemplateObject.query(sqlStatement, new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getInt("price"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setUnitInStock(rs.getInt("unitInStock"));
				product.setDescription(rs.getString("description"));
				product.setImageFilename(rs.getString("imageFilename"));

				return product;
				
			}
		});*/
	}

	//hibernate사용 이후 리턴타입 boolean -> void로 변환
	public void addProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product); // db에 저장 - 없으면 save, 있으면 update
		session.flush(); // 강제로 저장 실행
		
		/*hibernate적용 이후 더 이상 사용하지 않음
		  id는 DB에서 Auto Increment
		String name = product.getName();
		String category = product.getCategory();
		int price = product.getPrice();
		String manufacturer = product.getManufacturer();
		int unitInStock = product.getUnitInStock();
		String description = product.getDescription();
		String imageFilename = product.getImageFilename();

		String sqlStatement = "insert into product (name, category, price, manufacturer, unitInStock, description, imageFilename) "
				+ "values (?,?,?,?,?,?,?)";

		return (jdbcTemplateObject.update(sqlStatement,
				new Object[] { name, category, price, manufacturer, unitInStock, description, imageFilename }) == 1);*/
	}

	//hibernate사용 이후 리턴타입 boolean -> void로 변경, 인자 int id -> Product product로 변경
	public void deleteProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(product);
		session.flush();
		
		/*hibernate적용 이후 더 이상 사용하지 않음
		String sqlStatement = "delete from product where id=?";
		return (jdbcTemplateObject.update(sqlStatement, new Object[] { id }) == 1);*/
	}

	//hibernate사용 이후 리턴타입 boolean -> void로 변경
	public void editProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		session.flush();
		
		/* hibernate적용 이후 더 이상 사용하지 않음
		int id = product.getId();
		String name = product.getName();
		String category = product.getCategory();
		int price = product.getPrice();
		String manufacturer = product.getManufacturer();
		int unitInStock = product.getUnitInStock();
		String description = product.getDescription();
		String imageFilename = product.getImageFilename();

		String sqlStatement = "update product set name=?, category=?, price=?, "
				+ "manufacturer=?, unitInStock=?, description=?, imageFilename=? where id=?";

		return (jdbcTemplateObject.update(sqlStatement, new Object[] { name, category, price, manufacturer, unitInStock,
				description, imageFilename, id }) == 1);*/
	}
}
