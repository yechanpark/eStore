package kr.ac.ycp.cse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.ycp.cse.model.Cart;

@Repository
@Transactional
public class CartDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Cart getCartById(int cartId) {
		Session session = sessionFactory.getCurrentSession();
		return (Cart) session.get(Cart.class, cartId);
	}

	public void updateCart(Cart cart) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cart);
	}

}
