<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-wrapper">
	<div class="container">
		<h2>Product Inventory Page</h2>
		<p class="lead">제품 재고 현황입니다.</p>
		<table class="table table-striped">
			<thead>
				<tr class="bg-success">
					<th>Product Name</th>
					<th>Category</th>
					<th>Price</th>
					<th>Manufacturer</th>
					<th>unitInStock</th>
					<th>Description</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${products}">
					<tr>
						<td>${product.name}</td>
						<td>${product.category}</td>
						<td>${product.price}</td>
						<td>${product.manufacturer}</td>
						<td>${product.unitInStock}</td>
						<td>${product.description}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<a href="<c:url value="/admin/productInventory/addProduct"/>"
			class="btn btn-primary"> Add Product </a>
	</div>
</div>
