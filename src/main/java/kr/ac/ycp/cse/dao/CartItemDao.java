package kr.ac.ycp.cse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.ycp.cse.model.Cart;
import kr.ac.ycp.cse.model.CartItem;

@Repository
@Transactional
public class CartItemDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void updateCartItem(CartItem cartItem) {

		Logger logger = LoggerFactory.getLogger("customHibernateSQL");
		Object[] paramArray;

		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cartItem);
		session.flush();

		paramArray = new Object[] { cartItem.getCart().getCartId(), cartItem.getProduct().getId(),
				cartItem.getQuantity(), cartItem.getTotalPrice(), cartItem.getCartItemId() };

		logger.info(" update CartItem set cartId={}, productId={}, quantity={}, totalPrice={} where cartItemId={} ",
				paramArray);

	}

	public void removeCartItem(CartItem cartItem) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(cartItem);
		session.flush();
	}

	public void removeAllCartItem(Cart cart) {
		// Cart Class에서 EAGER로 되어있기 때문에 제대로 된 값이 나온다.
		// LAZY로 설정되어 있다면 NULL로 나오므로 추가적으로 읽어오는 작업이 필요하다.
		List<CartItem> cartItems = cart.getCartItems();

		for (CartItem item : cartItems) {
			removeCartItem(item);
		}

	}

	public CartItem getCartItemByProductId(int cartId, int productId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from CartItem where cart.cartId = :cid and product.id = :pid");

		query.setParameter("cid", cartId);
		query.setParameter("pid", productId);

		return (CartItem) query.uniqueResult();
	}

}
