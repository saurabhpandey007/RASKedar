
var app = angular.module("app", ['ngRoute']);

app.factory('appFact',function(){
	var fac = {};
	
	fac.user = {}; 
	
	return fac;
});


app.factory('sensorFact',function(){
	var fac = {};
	
	fac.sensor = {}; 
	
	return fac;
});

app.config(function($routeProvider) {
	$routeProvider

	.when('/', {
		templateUrl : 'adminHome.html'
	})
    // route for View Rooms
    .when('/viewrooms', {
        templateUrl : 'viewRooms.html',
        controller  : 'viewrooms'
    })
    
    .when('/editsensor', {
        templateUrl : 'editsensor.html',
        controller  : 'editsensor'
    })
    
    .when('/viewsensors', {
        templateUrl : 'viewsensors.html',
        controller  : 'viewsensors'
    })
    
    .when('/addroom', {
        templateUrl : 'addroom.html',
        controller  : 'addroom'
    })
    
    .when('/deleteroom', {
        templateUrl : 'deleteroom.html',
        controller  : 'deleteroom'
    })
    
    .when('/adduser', {
        templateUrl : 'adduser.html',
        controller  : 'adduser'
    })
    
    .when('/deleteuser', {
        templateUrl : 'deleteuser.html',
        controller  : 'deleteuser'
    })
    
    .when('/edituser', {
        templateUrl : 'edituser.html',
        controller  : 'edituser'
    })
    
    .when('/showusers', {
        templateUrl : 'showusers.html',
        controller  : 'showusers'
    })
});


app.controller("viewrooms", function($scope, $window, $http){
	
	$scope.view = function()
	{
		
		
		var e1 = document.getElementById("selectBuilding");
		var building = e1.options[e1.selectedIndex].value;
		var index1=e1.selectedIndex;
		
		var e2 = document.getElementById("selectFloor");
		var floor = e2.options[e2.selectedIndex].value;
		
		var index2=e2.selectedIndex;
		
		if(index1==0 && building !="CT1" && index2==0 && floor !="1" )
			{
			alert('please select both the values');
			}
		else if(index1==0 && building =="CT1" && index2==0 && floor !="1")
		{
		alert('please select both the values');
		}
		else if(index1==0 && building !="CT1" && index2==0 && floor =="1")
		{
		alert('please select both the values');
		}
		
		else{
	
		var req = 
			{
				method : 'POST',
				url : 'http://localhost:8888/Room_automation/rest/room/view',
				headers: {
					   'Content-Type': 'Application/json'
					 },
				data : 
					{
						buildg : $scope.room.building,
						floor : $scope.room.floor
					}
			}
		
		var response = $http(req);
		response.success(function(data)
				{
					$scope.rooms = data;
				})
				
		response.error(function(data)
				{
					bootbox.alert("error" , function(result){
						
							$window.location = "#/viewrooms";
					});
				})	
				
		}
	};
	
	
	$scope.viewRoomSensorDetail=function(rid)
	{
		var req =
		{
			method : 'POST',
			url : 'http://localhost:8888/Room_automation/rest/sensordetails/view',
			headers: {
		   		'Content-Type': 'Application/json'
		 	},
			data : {
				roomid : rid
			}
		}
		
		var response = $http(req);
		
		response.success(function(data)
				{
					bootbox.alert("Sensors::"+data, function(result){
						if(result=== true)
							{
								$window.location = "#/viewrooms";
							}
					})
				})
				
		response.error(function(data)
				{
					bootbox.alert("no sensors in room" , function(result){
						
								$window.location = "#/viewrooms";
						});
					});	
		
	};
	
	
	$scope.deletefun = function(id) 
	{
		bootbox.confirm("Do you really want to Delete?", function(result){
			if(result=== true)
				{
					var req =
					{
							method : 'POST',
							url : 'http://localhost:8888/Room_automation/rest/room/delete',
							headers: {
								'Content-Type': 'Application/json'
							},
							data : {
								roomid : id
							}
					};
		
					var response = $http(req);
					response.success(function(data)
							{
								bootbox.alert("deleted Successfully" , function(result){
									
										$window.location = "#/";
								});
							});
	
					response.error(function(data)
							{
								bootbox.alert("Error" , function(result){
									
										$window.location = "#/viewrooms";
								});
							})
				}
		})
		
	};	
});


app.controller("editsensor", function($scope, $window, $http, sensorFact) {
	$scope.sensor = {
		sensortype : '',
		frequency : ''
	};
	
	$scope.sensor = sensorFact.sensor;

	$scope.edited = function() {
		var req = {
			method : 'POST',
			url : 'http://localhost:8888/Room_automation/rest/sensor/add',
			headers : {
				'Content-Type' : 'Application/json'
			},
			data : {
				sensortype : $scope.sensor.sensortype,
				frequency : $scope.sensor.frequency
			}
		};

		var response = $http(req);
		response.success(function(data) {
			bootbox.alert(data, function(result) {
				if (result === true)
					$window.location = "adminLogin.html";
			});
		});

		response.error(function(data) {
			bootbox.alert("Invalid Input", function(result) {
				if (result === true)
					$window.location = "addsensor.html";
			});
		});

	}
})

app.controller("viewsensors", function($scope, $window, $http, sensorFact){
	$scope.show = function()
	{
		var response = $http.get('http://localhost:8888/Room_automation/rest/sensor/view');
		response.success(function(data)
		{
			$scope.sensors = data;	
		});
		
		response.error(function(data)
		{
			bootbox.alert("Error" , function(result){
				
					$window.location = "#/viewsensors";
			});
		})
	}
	
	$scope.editsensor = function(sensor)
	{
		sensorFact.sensor=sensor;
		$window.location = "#/editsensor";
	}
});

app.controller("addroom", function($scope, $window, $http) {

	$(document).ready(function() {
		$("select#ddl2").hide();
		$("select#ddl3").hide();
		$("select#ddl4").hide();
		$("select#ddl5").hide();
		$("select#ddl6").hide();
	});

	$scope.room = {
		roomId : '',
		building : '',
		floor : '',
		noofsensors : '',
		sensors : [ {} ]
	}

	$scope.ok = function() {
		var req = {
			method : 'POST',
			url : 'http://localhost:8888/Room_automation/rest/room/add',
			headers : {
				'Content-Type' : 'Application/json'
			},
			data : {
				roomid : $scope.room.roomId,
				buildg : $scope.room.building,
				floor : $scope.room.floor,
				noOfSensors : $scope.room.noofsensors,
				sensors : $scope.room.sensors
			}
		}

		var response = $http(req);
		response.success(function(data) {
			bootbox.alert(data, function(result) {
				if (result === true)
					$window.location = "adminLogin.html";
			});

		});

		response.error(function(data) {
			bootbox.alert(data, function(result) {
				if (result === true)
					$window.location = "#/addroom";
			});
		});
	}
});

app.controller("deleteroom",function($scope, $window, $http) {
	$scope.room = {
		roomId : '',
		building : '',
		floor : ''
	}
	$scope.deleted = function() {
		
		bootbox.confirm("Are you sure you want to delete the room?", function(result) {
            if (result == true)
			{
				var req = {
						method : 'POST',
						url : 'http://localhost:8888/Room_automation/rest/room/delete',
						headers : {
							'Content-Type' : 'Application/json'
						},
						data : {
							roomid : $scope.room.roomId,
							buildg : $scope.room.building,
							floor : $scope.room.floor

						}
					};

					var response = $http(req);
					response.success(function(data) {
						bootbox.alert(data, function(result)
								{
									
										$window.location = "#/";
								});
						
					});

					response.error(function(data) {
						bootbox.alert(data, function(result)
								{
									
										$window.location = "#/deleteroom";
								});

					})
			}
			});
	}
});

app.controller("adduser", function($scope, $window, $http) {

	$scope.user = {
		fname : '',
		lname : '',
		uname : ''
	}

	$scope.add = function() {
		var req = {
			method : 'POST',
			url : 'http://localhost:8888/room_autoamtion/project/addUser/'
					+ $scope.user.fname + '/' + $scope.user.lname + '/'
					+ $scope.user.uname,
			headers : {
				'Content-Type' : 'Text/plain'
			}

		};

		var response = $http(req);
		response.success(function(data) {
			bootbox.alert(data, function(result) {
				if (result === true)
					$window.location = "adminLogin.html";
			});
		});

		response.error(function(data) {
			bootbox.alert("Enter Valid Input", function(result) {
				if (result === true)
					$window.location = "#/adduser";
			});
		});

	}
});

app.controller("deleteuser", function($scope, $window, $http){
			$scope.user =
				{
					userId : '',
					userName : ''
				}
			
			$scope.deletefun = function()
			{
				bootbox.confirm("Do you really want to delete?", function(result){
					if(result === true)
						{
							var req =
							{
									method : 'POST',
									url : 'http://localhost:8888/room_autoamtion/project/deleteUser/'+$scope.user.userName+'/'+$scope.user.userId,
									headers: {
										'Content-Type': 'Text/plain'
									}
							};
					
							var response = $http(req);
							response.success(function(data)
									{
										bootbox.alert(data , function(result){
												$window.location = "#/";
										});
									});
							
							response.error(function(data)
									{
										bootbox.alert("user does not Exists" , function(result){
											
												$window.location = "#/deleteuser";
										});
									})
							}
				})
			}
				
		});

app.controller("edituser", function($scope, $window, $http, appFact) {
	$scope.user = {
			userId : '',
			firstName : '',
			lastName : '',
			userName : '',
			role : ''
		};
	$scope.user = appFact.user;

	$scope.edited = function() {
		var req = {
			method : 'POST',
			url : 'http://localhost:8888/room_autoamtion/project/updateUser/'
					+ $scope.user.firstName + '/' + $scope.user.lastName + '/'
					+ $scope.user.userName + '/' + $scope.user.userId + '/'
					+ $scope.user.role,
			headers : {
				'Content-Type' : 'Text/plain'
			}
		};

		var response = $http(req);
		response.success(function(data) {
			bootbox.alert(data, function(result) {
					$window.location = "#/";
			});
		});

		response.error(function(data) {
			bootbox.alert("user does not Exists", function(result) {
					$window.location = "#/edituser";
			});
		})
	}
});

app.controller("showusers", function($scope,$window, $http, appFact)
		{
			$scope.show = function()
			{
				var response = $http.get('http://localhost:8888/room_autoamtion/project/showAllUsers');
				response.success(function(data)
				{
					$scope.users = data;
				});	
				
				response.error(function(data)
				{
					bootbox.alert("Something went wrong" , function(result){
							$window.location = "#/showusers";
					});
				});	
			}
			
			$scope.editfunc = function(user) 
			{
				appFact.user=user;
				$window.location = "#/edituser";
			};
			
			$scope.deleteuser = function(uname,uid) 
			{
				bootbox.confirm("Do you really want to delete?", function(result){
						if(result === true)
							{
								$scope.uname = uname;
								$scope.uid = uid;
								var req =
								{
										method : 'POST',
										url : 'http://localhost:8888/room_autoamtion/project/deleteUser/'+$scope.uname+'/'+$scope.uid,
										headers: {
											'Content-Type': 'Text/plain'
										}
								};
				
								var response = $http(req);
								response.success(function(data)
										{
											bootbox.alert(data , function(result){
												
													$window.location = "adminLogin.html";
											});
										});
				
								response.error(function(data)
										{
											bootbox.alert("Error" , function(result){
												
													$window.location = "#/showusers";
											});
										});
							}
				})	
			}
		});