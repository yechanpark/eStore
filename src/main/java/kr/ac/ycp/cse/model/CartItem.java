package kr.ac.ycp.cse.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class CartItem implements Serializable {

	private static final long serialVersionUID = -213127954507414254L;

	@Id
	@GeneratedValue
	private int cartItemId;

	@ManyToOne
	@JoinColumn(name = "cartId") // foreign key "cartId"지정
	@JsonIgnore // JSON으로 변환하지 않음 -> 싸이클 끊음
	private Cart cart;

	@ManyToOne
	@JoinColumn(name = "productId") // foreign key "productId"지정
	private Product product;

	private int quantity;
	private double totalPrice;
}