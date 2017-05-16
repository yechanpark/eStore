<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="container-wrapper">
	<!-- AngularJS의 Module명을 ng-app으로 기술 -->
	<!-- 이 페이지(viewProduct.jsp)가 로드될 때  cartApp Module을 사용할 수 있다.-->
	<div class="container" ng-app="cartApp">
		<h2>Product Detail</h2>
		<p class="lead">Here is the detail information of the product</p>
		
		<!-- 이 부분에서 cartCtrl 객체를 생성, controller.js에 기술한 생성자 부분이 호출되어 $Scope에 값을 바인딩 -->
		<div class="row" ng-controller="cartCtrl">
			<div class="col-md-6">
				<img
					src="<c:url value="/resources/images/${product.imageFilename}" />"
					alt="image" style="width: 80%" />
			</div>

			<div class="col-md-6">
				<h3>${product.name}</h3>
				<p>${product.description}</p>
				<p>
					<strong>Manufacturer</strong> : ${product.manufacturer}
				</p>
				<p>
					<strong>Category</strong> : ${product.category}
				</p>

				<h4>${product.price}원</h4>

				<br />

				<c:if test="${pageContext.request.userPrincipal.name != null}">
					<c:set var="role" value="${param.role}" />
					<!-- admin과 일반 유저에 따라 요청하는 Request가 다르고
					이에 따라 Controller도 다르므로 각 역할에 따라 URL 세팅을 따로 해야한다. -->
					<c:set var="url" value="/products" />
					<c:if test="${role='admin'}">
						<c:set var="url" value="/admin/productInventory" />
					</c:if>

					<p>
						<a href="<c:url value="${url}" />" class="btn btn-default">Back</a>
						
						<!-- ng-click을 통해 AngularJS의 controller.js의 addToCart()가 실행된다. -->
						<button class="btn btn-warning btn-large" ng-click="addToCart('${product.id}')">
							<span class="glyphicon glyphicon-shopping-cart"></span> Order Now
						</button>
						
						<a href="<c:url value="/cart" />" class="btn btn-default">
							<span class="glyphicon glyphicon-hand-right"></span> View Cart
						</a>
					</p>
				</c:if>

			</div>
		</div>
	</div>
</div>