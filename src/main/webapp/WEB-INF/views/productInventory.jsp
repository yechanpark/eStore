<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="container-wrapper">
	<div class="container">
		<h2>Product Inventory Page</h2>
		<p class="lead">제품 재고 현황입니다.</p>
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
						<a href="<spring:url value="/admin/productInventory/deleteProduct/${product.id}"/>">
								<span class="glyphicon glyphicon-remove"></span>
						</a>
						<a href="<spring:url value="/admin/productInventory/editProduct/${product.id}"/>">
								<span class="glyphicon glyphicon-pencil"></span>
						</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<a href="<c:url value="/admin/productInventory/addProduct"/>"
			class="btn btn-primary"> Add Product </a>
	</div>
</div>
