package kr.ac.ycp.cse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.ycp.cse.dao.CartItemDao;
import kr.ac.ycp.cse.model.Cart;
import kr.ac.ycp.cse.model.CartItem;

@Service
public class CartItemService {

	@Autowired
	private CartItemDao cartItemDao;

	public void updateCartItem(CartItem cartItem) {
		cartItemDao.updateCartItem(cartItem);
	}

	public void removeCartItem(CartItem cartItem) {
		cartItemDao.removeCartItem(cartItem);
	}

	public void removeAllCartItems(Cart cart) {
		cartItemDao.removeAllCartItem(cart);
	}

	public CartItem getCartItemByProductId(int cartId, int productId) {
		return cartItemDao.getCartItemByProductId(cartId, productId);
	}
}
