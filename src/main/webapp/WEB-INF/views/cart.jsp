<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="<c:url value="/resources/js/controller.js"/>"></script>

<div class="container-wrapper">
	<div class="container">
		<div class="jumbotron">
			<div class="container">
				<h2>Cart</h2>

				<p>All the selected products in your shopping cart</p>
			</div>
		</div>

		<section class="container" ng-app="cartApp">
			<!-- controller 생성 -->
			<div ng-controller="cartCtrl" ng-init="initCartId('${cartId}')">
				<div class="padding">
					<a class="btn btn-danger pull-left" ng-click="clearCart()"> <span
						class="glyphicon glyphicon-remove-sign"></span>Clear Cart
					</a>
				</div>

				<table class="table table-hover">

					<tr>
						<th>Product</th>
						<th>Unit Price</th>
						<th>Quantity</th>
						<th>Total Price</th>
						<th>Action</th>
					</tr>
					<!-- cart객체는 controller.js에서 위에서 수행된 initCartId()가 수행되면서 refreshCart()가 수행되면서 $scope을 통해 넘어온 것이다. -->
					<!-- 태그 내부를 루프를 돌면서 cart객체의 cartItems 사이즈만큼 돌면서 변수 item에 값을 넣는다. -->
					<tr ng-repeat="item in cart.cartItems">
						<!-- View에서 $scope에 있는 property에 접근할 때는 다음과 같이 기술한다. -->
						<td>{{item.product.name}}</td>
						<td>{{item.product.price}}</td>
						<td>{{item.quantity}}</td>
						<td>{{item.totalPrice}}</td>
						<td><a href="#" class="label label-danger"
							ng-click="removeFromCart(item.product.id)"> <span
								class="glyphicon glyphicon-remove"></span>remove
						</a> <a href="#" class="label label-danger"
							ng-click="plusQuantityToCart(item.product.id)"> <span
								class="glyphicon glyphicon-plus"></span>plus
						</a> <a href="#" class="label label-danger"
							ng-click="minusQuantityToCart(item.product.id)"> <span
								class="glyphicon glyphicon-minus"></span>minus
						</a></td>
					</tr>

					<tr>
						<td></td>
						<td></td>
						<td>Grand Total</td>
						<td>{{calGrandTotal()}}</td>
						<td></td>
					</tr>
				</table>

				<a href="<c:url value="/products"/>" class="btn btn-default">Continue
					Shopping</a>
			</div>
		</section>

	</div>
</div>