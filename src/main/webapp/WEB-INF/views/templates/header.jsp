<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="navbar-wrapper">
	<div class="container">

		<nav class="navbar navbar-inverse navbar-static-top">
			<div class="container">
			
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Project name</a>
				</div>
				
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active"><a href="<c:url value="/"/>">Home</a></li>
						<li><a href="<c:url value="/products"/>">Products</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>

					<ul class="nav navbar-nav pull-right">
						<!-- 로그인한 경우 -->
						<c:if test="${pageContext.request.userPrincipal.name != null}">
							<li> <a>Welcome:${pageContext.request.userPrincipal.name}</a></li>

							<!-- admin이 로그인한 경우 -->
							<c:if test="${pageContext.request.userPrincipal.name == 'admin'}">
								<li><a href="<c:url value="/admin"/>">AdminPage</a></li>
							</c:if>
							<li><a href="<c:url value="/logout"/>">Logout</a></li>
						</c:if>

						<!-- 로그인하지 않은 경우 -->
						<!-- login 의 GET Method로 이동 -->
						<!-- login GET : display form -->
						<!-- login POST : spring -->
						<c:if test="${pageContext.request.userPrincipal.name == null}">
							<li><a href="<c:url value="/login"/>">Login</a></li>
							<li><a href="<c:url value="/register"/>">Register</a></li>
						</c:if>
					</ul>

				</div>
			</div>
		</nav>

	</div>
</div>