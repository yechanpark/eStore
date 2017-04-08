<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="container-wrapper">
	<div class="container">
		<h2>All Products</h2>
		<p class="lead">착한 가격에 모든 상품을 살펴보세요 !!!</p>
		<table class="table table-striped">
			<thead>
				<tr class="bg-success">
					<th>Photo Thumb</th>
					<th>Product Name</th>
					<th>Category</th>
					<th>Price</th>
					<th>Manufacturer</th>
					<th>unitInStock</th>
					<th>Description</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${products}">
					<tr>
						<td><img src="<c:url value="/resources/images/${product.imageFilename}"/>"
							alt="image" style="width:80%" />
						</td>
						<td>${product.name}</td>
						<td>${product.category}</td>
						<td>${product.price}</td>
						<td>${product.manufacturer}</td>
						<td>${product.unitInStock}</td>
						<td>${product.description}</td>
						<td>
							<a href="<spring:url value="/viewProduct/${product.id}"/>">
								<span class="glyphicon glyphicon-info-sign"></span>
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
