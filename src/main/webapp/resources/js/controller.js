// cartApp Model 생성
var cartApp = angular.module('cartApp', []);

// cartApp Controller의 이름과 생성자 정의, $scope, $http 2개 주입
// $http의 경우 remote서버로부터 data를 읽기 위한 Angular JS의 서비스이다.
cartApp.controller("cartCtrl", function($scope, $http) {

	/* method를 $scope에 추가 */

	// carId를 사용하여 $scope에 바인딩
	$scope.initCartId = function(cartId) {
		$scope.cartId = cartId;
		$scope.refreshCart();
	};

	$scope.refreshCart = function() {
		// $http의 get method사용, Rest API(CartRestController)의 getCartById()를
		// 호출(Request보냄)
		$http.get('/eStore/rest/cart/' + $scope.cartId).then(
				// 성공했을 경우(then) Rest API에 의해 반환된 Response를 가지고 Cart에 집어넣는다.
				function successCallback(response) {
					// getCartById()의 반환 값 (Body에 있는 내용 = cart객체)이 response.data에 해당
					$scope.cart = response.data;
				}
		);
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
				}
		);

	};

	// delete method를 사용, Rest API(CartRestController)의 removeItem()를
	// 호출(Request보냄). general 방식.
	// 카트에 1개의 항목을 삭제.
	$scope.removeFromCart = function(productId) {

		$scope.setCsrfToken();

		$http(
				{
					method : 'DELETE',
					url : '/eStore/rest/cart/cartitem/' + productId
				}
		).then(
			function successCallback() {
				$scope.refreshCart();

			}, function errorCallback(response) {
				console.log(response.data);
			}
		);

	};

	$scope.clearCart = function() {

		$scope.setCsrfToken();

		$http(
			{
				method : 'DELETE',
				url : '/eStore/rest/cart/' + $scope.cartId
			}
		).then(
			function successCallback() {
				$scope.refreshCart();
			}, function errorCallback(response) {
				console.log(response.data);
			}
		);

	};

	// 총합 계산
	$scope.calGrandTotal = function() {

		var grandTotal = 0;

		for (var i = 0; i < $scope.cart.cartItems.length; i++) {
			grandTotal += $scope.cart.cartItems[i].totalPrice;
		}

		return grandTotal;

	};

	$scope.setCsrfToken = function() {
		// 헤더에 CSRF 토큰 정보가 들어간다. contoller.js의 각 함수 실행 마다 이 함수를 호출하게 한다.
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");

		$http.defaults.headers.common[csrfHeader] = csrfToken;
	};

});