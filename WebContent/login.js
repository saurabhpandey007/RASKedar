var app = angular.module("app" , []);
 	
 	app.controller("login", function($scope, $window, $http)
 	{
 		$scope.user = 
 			{
 				userName : '',
 				password : ''
 			};
 		
 		$scope.login = function()
 		{
 			
 			var req =
 				{
 					method : 'POST',
 					url : 'http://localhost:8888/room_autoamtion/project/authenticateUser',
 					headers: {
						   'Content-Type': 'Application/json'
						 },
					data : 
						{
							userName : $scope.user.userName,
							password : $scope.user.password
						}
 				};
 			
 			var response = $http(req);
 			$scope.role = "admin";
 			response.success(function(data)
			{
					if(data == $scope.role)
					{
						$window.location = "adminLogin.html";
					}
					if(data == "user")
					{
						$window.location = "userHome.html";
					}
			});
 			
 			response.error(function()
 			{
 				bootbox.alert("Invalid Username or password", function(result)
						{
							if(result === true)
								$window.location = "login.html";
						});
 						
 			})
 		}
 		
 	});
 	