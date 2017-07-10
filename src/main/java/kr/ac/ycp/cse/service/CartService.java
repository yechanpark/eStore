package kr.ac.ycp.cse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.ycp.cse.dao.CartDao;
import kr.ac.ycp.cse.model.Cart;

@Service
public class CartService {

	@Autowired
	private CartDao cartDao;

	public Cart getCartById(int cartId) {
		return cartDao.getCartById(cartId);
	}

	public void updateCart(Cart cart) {
		cartDao.updateCart(cart);
	}

}
