package kr.ac.ycp.cse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
// Serialization을 위해 Serializable Interface를 implements해야한다.
public class Cart implements Serializable {

	// Cart Class의 버전
	private static final long serialVersionUID = 1413783241344225820L;

	@Id
	@GeneratedValue
	private int cartId;

	// mappedBy의 "cart"값은 CartItem Class의 Cart타입의 변수명과 같다.
	// cascade의 값을 ALL로 줘서 Cart를 저장하면 모든 CartItem들도 같이 저장되게 한다.
	// fetch타입을 EAGER로 줘서 Cart를 읽어오면 여기에 관계된 모든 CartItem을 읽어온다.
	// LAZY의 경우 CartItem을 읽어오지 않는다.
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<CartItem> cartItems = new ArrayList<CartItem>();

	private double grandTotal;
	
}
