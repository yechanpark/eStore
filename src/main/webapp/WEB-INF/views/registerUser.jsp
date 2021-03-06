<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<div class="container-wrapper">
	<div class="container">

		<h1>Register User</h1>
		<p class="lead">회원 가입을 위한 정보를 입력해주세요.</p>

		<!-- modelAttribute의 "user"라는 값은 RegisterController에서 넘긴
		     model.addAttribute("user", user)의 키값"user"와 같아야 한다.-->
		<sf:form action="${pageContext.request.contextPath}/register"
			method="post" modelAttribute="user">
			

			<h3>기본 정보</h3>
			<div class="form-group">
				<label for="username">아이디</label>
				<span style="color:#ff0000">${usernameMsg}</span>
				<!-- path속성의 값은 Model 클래스에 기술되어있는 필드명과 같아야 한다.
				     spring form tag를 쓰면 여기서 사용자가 기술한 값이 자동적으로 Model안의 User객체에 바인딩된다.-->
				<sf:input path="username" id="username" class="form-control" />
				<sf:errors path="username" cssStyle="color:#ff0000" />
			</div>

			<div class="form-group">
				<label for="password">패스워드</label>
				<sf:password path="password" id="password" class="form-control" />
				<sf:errors path="password" cssStyle="color:#ff0000" />
			</div>

			<div class="form-group">
				<label for="email">이메일</label>
				<sf:input path="email" id="email" class="form-control" />
				<sf:errors path="email" cssStyle="color:#ff0000" />
			</div>

			<h3>배송주소 정보</h3>
			<div class="form-group">
				<label for="shippingStreet">주소</label>
				<sf:input path="shippingAddress.address" id="shippingStreet"
					class="form-control" />
			</div>

			<div class="form-group">
				<label for="shippingCountry">국가</label>
				<sf:input path="shippingAddress.country" id="shippingCountry"
					class="form-control" />
			</div>

			<div class="form-group">
				<label for="shippingzip">우편번호</label>
				<sf:input path="shippingAddress.zipCode" id="shippingzip"
					class="form-control" />
			</div>

			<br>

			<input type="submit" value="submit" class="btn btn-default">
			<a href="<c:url value="/" />" class="btn btn-default">Cancel</a>

		</sf:form>
		<br />
	</div>
</div>