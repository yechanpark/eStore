package kr.ac.hansung.cse.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -5921627991557316398L;

	@Id
	@GeneratedValue
	private int userId;

	@NotEmpty(message = "The username must not be null")
	private String username;

	@NotEmpty(message = "The password must not be null")
	private String password;

	@NotEmpty(message = "The email must not be null")
	private String email;

	// optional을 통해 User는 반드시 ShippingAddress가 존재해야하게 설정.
	// User저장 시 ShippingAddress도 저장되게 설정
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(unique = true) // foreign key 지정, unique로 ShippingAddress는 각
								// User마다 고유해야한다.
	private ShippingAddress shippingAddress;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(unique = true)
	private Cart cart;

	private boolean enabled = false; // 가입자 활성화 여부
	private String authority; // 권한
}