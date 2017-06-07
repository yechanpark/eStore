<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- Angular JS를 사용하기 위해서는 controller에 해당하는 자바스크립트 파일을 링크해야 한다. -->
<script src="<c:url value="/resources/js/controller.js"/>"></script>

<div class="container-wrapper">
	<!-- AngularJS의 Module명을 ng-app(directive, 지시자)으로 기술 -->
	<!-- 이 페이지(viewProduct.jsp)가 로드될 때  Angular App이 수행되어 "cartApp"이라는 Module을 사용할 수 있다.-->
	<div class="container" ng-app="cartApp">
		<h2>Product Detail</h2>
		<p class="lead">Here is the detail information of the product</p>

		<!-- 이 부분에서 cartCtrl 컨트롤러 객체를 생성, controller.js에 기술한 생성자 함수가 호출되어 $scope에다가 정의된 메서드들을 등록-->
		<div class="row" ng-controller="cartCtrl">
			<div class="col-md-6">
				<img
					src="<c:url value="/resources/images/${product.imageFilename}"/>"
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
					<!-- c:set태그는  role이란 변수에 param.role값을 저장한다.-->
					<c:set var="role" value="${param.role}" />
					<!-- admin과 일반 유저에 따라 요청하는 Request가 다르고
					이에 따라 Controller도 다르므로 각 역할에 따라 URL 세팅을 따로 해야한다. -->
					<c:set var="url" value="/products" />
					<c:if test="${role='admin'}">
						<c:set var="url" value="/admin/productInventory" />
					</c:if>


					<p>
						<a href="<c:url value="${url}"/>" class="btn btn-default">Back</a>

						<!-- ng-click을 통해 AngularJS의 controller.js의 addToCart()가 실행된다. -->
						<button class="btn btn-warning btn-large"
							ng-click="addToCart('${product.id}')">
							<span class="glyphicon glyphicon-shopping-cart"></span> Order Now
						</button>

						<a href="<c:url value="/cart"/>" class="btn btn-default"> <span
							class="glyphicon glyphicon-hand-right"></span> View Cart
						</a>
					</p>

				</c:if>

			</div>
		</div>
	</div>
</div>