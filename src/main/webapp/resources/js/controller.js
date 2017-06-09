// cartApp Module 생성, []안에는 이 모듈이 의존하는 또다른 모듈을 기술한다. 여기서는 의존하는 관계에 있는 모듈이 없다는 것을 의미
var cartApp = angular.module('cartApp', []);

// cartApp Controller의 이름(cartCtrl)과 정의. $scope, $http 2개 주입
// $http의 경우 remote서버로부터 data를 읽기 위한 Angular JS의 서비스이다.
// controller는 일종의 Function이다. 여기서는 Anonymous Function으로 생성자 함수를 구현.
// 생성자 함수가 실행되면서 $scope에 함수들을 등록하는 것이다.
// controller에서 %scope에는 property와 method를 등록할 수 있다.
cartApp.controller("cartCtrl", function($scope, $http) {

	/* methods를 $scope에 추가 */

	// View(cart.jsp)로부터 넘어온 carId를 사용하여 $scope에 바인딩
	$scope.initCartId = function(cartId) {
		$scope.cartId = cartId;
		$scope.refreshCart();
	};

	$scope.refreshCart = function() {
		// $http의 get method사용, Rest API(CartRestController)의 getCartById()를
		// 호출(Request보냄)
		$http.get('/eStore/rest/cart/' + $scope.cartId).then(
		// 성공했을 경우의 Callback Function(then()). Rest API에 의해 반환된 JSON포맷으로 된 Response에서 data를 꺼내서
		// $scope에 집어넣는다.
		function successCallback(response) {
			// getCartById()의 반환 값 (Body에 있는 내용 = cart객체를 Serialization한 내용)이 response.data(response body에 있는 값을 의미)에 해당
			// response의 properties는 .config, .data, .headers, .status, .statusText 등이 있다.
			$scope.cart = response.data;
		});
	};

	$scope.addToCart = function(productId) {

		$scope.setCsrfToken();

		// put method를 사용, Rest API(CartRestController)의 addItem()를
		// 호출(Request보냄). shortcut 방식.
		// 카트에 1개의 항목을 담는다.
		$http.put('/eStore/rest/cart/add/' + productId).then(
		// 성공 시
		function successCallback() {
			alert("Product successfully added to the cart!");

			// 실패시
		}, function errorCallback() {
			alert("Adding to the cart failed!")
		});

	};

	// delete method를 사용, Rest API(CartRestController)의 removeItem()를
	// 호출(Request보냄). general 방식.
	// 카트에 1개의 항목을 삭제.
	$scope.removeFromCart = function(productId) {

		$scope.setCsrfToken();

		$http({
			method : 'DELETE',
			url : '/eStore/rest/cart/cartitem/' + productId
		}).then(function successCallback() {
			$scope.refreshCart();

		}, function errorCallback(response) {
			console.log(response.data);
		});

	};

	$scope.plusQuantityToCart = function(productId) {

		$scope.setCsrfToken();

		$http({
			method : 'PUT',
			url : '/eStore/rest/cart/cartitem/add/' + productId
		}).then(function successCallback() {
			$scope.refreshCart();
		}, function errorCallback(response) {
			console.log(response.data);
		});
	};

	$scope.minusQuantityToCart = function(productId) {

		$scope.setCsrfToken();

		$http({
			method : 'PUT',
			url : '/eStore/rest/cart/cartitem/minus/' + productId
		}).then(function successCallback() {
			$scope.refreshCart();
		}, function errorCallback(response) {
			console.log(response.data);
		});
	};

	$scope.clearCart = function() {

		$scope.setCsrfToken();

		$http({
			method : 'DELETE',
			url : '/eStore/rest/cart/' + $scope.cartId
		}).then(function successCallback() {
			$scope.refreshCart();
		}, function errorCallback(response) {
			console.log(response.data);
		});

	};

	// 총합 계산
	$scope.calGrandTotal = function() {

		var grandTotal = 0;

		for (var i = 0; i < $scope.cart.cartItems.length; i++) {
			grandTotal += $scope.cart.cartItems[i].totalPrice;
		}

		return grandTotal;

	};

	// Spring Security 4를 사용하는 경우, View에서 Controller에 GET메서드를 제외한 모든 메서드에 대해 Request 시 CSRF Token을 추가해야한다.
	$scope.setCsrfToken = function() {
		// 헤더에 CSRF 토큰 정보가 들어간다. contoller.js의 각 함수 실행 마다 이 함수를 호출하게 한다.
		// JQuery를 사용하여 <meta>태그에 기술된 CSRF토큰 정보를 검색한다. Apache Tiles에 의해 결합된 여러 jsp 중 layout.jsp 상단의 <meta>태그를 참조한다.
		// value 값
		var csrfToken = $("meta[name='_csrf']").attr("content");
		// header 네임
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");

		// $http의 헤더에 CSRF토큰 세팅
		$http.defaults.headers.common[csrfHeader] = csrfToken;
	};

});