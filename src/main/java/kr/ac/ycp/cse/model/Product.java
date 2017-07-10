package kr.ac.ycp.cse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity // DB에 테이블로 매핑
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1399541639012677824L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private int id;

	@NotEmpty(message = "The product name must not be null")
	private String name;

	private String category;

	@Min(value = 0, message = "The product price must not be less than zero")
	private int price;

	@NotEmpty(message = "The product manufacturer must not be null")
	private String manufacturer;

	@Min(value = 0, message = "The product unitInStock must not be less than zero")
	private int unitInStock;

	private String description;

	@Transient // db에 저장하지 않는다.
	private MultipartFile productImage;

	private String imageFilename; // DB에 저장되는 값

	// Product 저장 시 CarItem들도 저장.
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore // JSON으로 변환하지 않음 -> 싸이클 끊음
	private List<CartItem> cartItemList = new ArrayList<CartItem>();
}
