package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.Cart;
import kr.ac.hansung.cse.model.CartItem;
import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.model.User;
import kr.ac.hansung.cse.service.CartItemService;
import kr.ac.hansung.cse.service.CartService;
import kr.ac.hansung.cse.service.ProductService;
import kr.ac.hansung.cse.service.UserService;

// @RestController의 @ResponseBody기능에 의해 Method가 Return하는 내용들이 Response의
// Body부분에 JSON Format으로 들어간다.
@RestController // @Controller + @ResponseBody
@RequestMapping("/rest/cart")
public class CartRestController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	// cartId를 바탕으로 Cart정보를 검색
	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	public ResponseEntity<Cart> getCartById(@PathVariable(value = "cartId") int cartId) {

		Cart cart = cartService.getCartById(cartId);

		// ResponseEntity에 cart, HttpStatus를 담은 후 이것을 ResponseBody에 담아서 보낸다.
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);

	}

	// 카트에 처음 담을 때
	@RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> addItem(@PathVariable(value = "productId") int productId) {

		// 로그인한 사람에 대한 정보를 기반으로 SpringSecurity에 의해 이름을 얻어올 수 있다.
		// servlet-context.xml에 관련 설정을 해야한다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();
		User user = userService.getUserByUsername(username);
		Cart cart = user.getCart();
		Product product = productService.getProductById(productId);

		List<CartItem> cartItems = cart.getCartItems();

		// 이미 Cart에 CartItem이 존재할 시
		for (int i = 0; i < cartItems.size(); i++) {
			if (product.getId() == cartItems.get(i).getProduct().getId()) {
				CartItem cartItem = cartItems.get(i);
				cartItem.setQuantity(cartItem.getQuantity() + 1);
				cartItem.setTotalPrice(product.getPrice() * cartItem.getQuantity());
				cart.setGrandTotal(cart.getGrandTotal() + product.getPrice());
				cartItemService.updateCartItem(cartItem);
				cartService.updateCart(cart);

				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		}

		// Cart에 CartItem 처음 추가 시
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(1);
		cartItem.setTotalPrice(product.getPrice() * cartItem.getQuantity());
		cart.setGrandTotal(cart.getGrandTotal() + cartItem.getTotalPrice());
		cartItem.setCart(cart);

		cartItemService.updateCartItem(cartItem);

		cart.getCartItems().add(cartItem);
		cartService.updateCart(cart);
		product.getCartItemList().add(cartItem);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	// 카트에서 CartItem 자체 제거
	@RequestMapping(value = "/cartitem/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> removeItem(@PathVariable(value = "productId") int productId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();
		User user = userService.getUserByUsername(username);
		Cart cart = user.getCart();

		CartItem cartItem = cartItemService.getCartItemByProductId(cart.getCartId(), productId);
		cart.setGrandTotal(cart.getGrandTotal() - cartItem.getTotalPrice());

		cartService.updateCart(cart);
		cartItemService.removeCartItem(cartItem);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

	// 해당 CartItem Quantity 1개 증가
	@RequestMapping(value = "/cartitem/add/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> plusQuantityToCart(@PathVariable(value = "productId") int productId,
			HttpServletRequest request) {

		Logger logger = LoggerFactory.getLogger(CartRestController.class);
		String requestMessage = request.toString();
		
		logger.info("Request Message:" + requestMessage);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();
		User user = userService.getUserByUsername(username);
		Cart cart = user.getCart();

		List<CartItem> cartItems = cart.getCartItems();

		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			Product product = cartItem.getProduct();

			// 해당하는 버튼을 누른 Product와 현재 카트에 있는 CartItem의 Product와 비교
			if (product.getId() == productId) {

				// 현재 카트에 추가된 갯수보다 재고가 많은 경우
				if (product.getUnitInStock() > cartItem.getQuantity()) {

					// 갯수 : 1증가, 카트아이템 전체 가격 : (현재 총 가격 + Product가격)
					cartItem.setQuantity(cartItem.getQuantity() + 1);
					cartItem.setTotalPrice(cartItem.getTotalPrice() + product.getPrice());

					// 카트 전체가격 반영
					cart.setGrandTotal(cart.getGrandTotal() + product.getPrice());

					// Cart와 CartItem저장
					cartItemService.updateCartItem(cartItem);
					cartService.updateCart(cart);
				}
			}
		}
		
		ResponseEntity<Void> response = new ResponseEntity<Void>(HttpStatus.OK);
		String responseMessage = response.toString();
		logger.info(responseMessage);
		
		return response;

	}

	// 해당 CartItem Quantity 1개 감소
	@RequestMapping(value = "/cartitem/minus/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> minusQuantityToCart(@PathVariable(value = "productId") int productId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();
		User user = userService.getUserByUsername(username);
		Cart cart = user.getCart();

		List<CartItem> cartItems = cart.getCartItems();

		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			Product product = cartItem.getProduct();

			// 해당하는 Product에 대해
			if (product.getId() == productId) {

				// 현재 카트에 추가된 갯수가 0보다 큰 경우
				if (cartItem.getQuantity() > 0) {

					// 갯수 : 1감소, 카트아이템 전체 가격 : (현재 총 가격 - Product가격)
					cartItem.setQuantity(cartItem.getQuantity() - 1);
					cartItem.setTotalPrice(cartItem.getTotalPrice() - product.getPrice());

					// 카트 전체가격 반영
					cart.setGrandTotal(cart.getGrandTotal() - product.getPrice());

					// Cart와 CartItem저장
					cartItemService.updateCartItem(cartItem);
					cartService.updateCart(cart);
				}
			}
		}

		return new ResponseEntity<Void>(HttpStatus.OK);

	}

	// 카트 전체 삭제
	@RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> clearCart(@PathVariable(value = "cartId") int cartId) {

		Cart cart = cartService.getCartById(cartId);
		cart.setGrandTotal(0);

		cartService.updateCart(cart);
		cartItemService.removeAllCartItems(cart);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

}
