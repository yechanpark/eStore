<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-wrapper">
	<div class="container">
		<h2> Login with Username and Password </h2>
		
		<c:if test="${not empty logout}">
			<div style="color:#0000ff"> <h3> ${logout} </h3> </div>
		</c:if>
		
		<c:if test="${not empty error}">
			<div style="color:#ff0000"> <h3> ${error} </h3> </div>
		</c:if>
		
		<!-- login 의 POST Method로 이동 -->
		<!-- login GET : 네비게이션 바에서 Login버튼 클릭 해서 "/login"이동 시 -->
		<!-- login POST : "/admin/**" 접근 시 권한이 없어서 SpringSecurity에 의해 자동적으로 "/login"이동 시 -->
		<form action="<c:url value="/login"/>" method="post">
			<div class="form-group">
				<label for="username">Username:</label>
				<!-- name 값은 반드시 "username"이어야 한다. -->
				<input type="text" class="form-control" name="username" style="width:50%">
			</div>
			
			<div class="form-group">
				<label for="password">Password:</label>
				<!-- name 값은 반드시 "password"이어야 한다. -->
				<input type="password" class="form-control" name="password" style="width:50%">
			</div>
			
			<!-- HTML Form 전송 시 CSRF Token을 같이 전송하지 않으면 오류 발생 -->
			<!-- Spring form 사용 시에는 같이 전송하지 않아도 된다. -->
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="확인" class="btn btn-default">
			
		</form>
		
	</div>
</div>