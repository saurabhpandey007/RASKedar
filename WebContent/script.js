

    var scotchApp = angular.module('chartApp', ['ngRoute']);

  
    scotchApp.config(function($routeProvider) {
    	 count=0;
    	 
        $routeProvider

            // route for the home page
            .when('/', {
                templateUrl : 'home.html',
                controller  : 'mainController'
            })

            // route for the about page
            .when('/tempDetail', {
                templateUrl : 'temperatureChart.html',
                controller  : 'tempDetailController'
            })
            
            .when('/pressDetail', {
                templateUrl : 'pressureChart.html',
                controller  : 'pressDetailController'
            })
            
            .when('/humiDetail', {
                templateUrl : 'humidityChart.html',
                controller  : 'humiDetailController'
            })
            
            .when('/luxDetail', {
                templateUrl : 'luxChart.html',
                controller  : 'luxDetailController'
            })
            
             .when('/responseCode', {
                templateUrl : 'responseCode.html',
                controller  : 'responseCodeController'
            })

         
             .when('/apiHelth', {
                templateUrl : 'apichart.html',
                controller  : 'apiController'
            });
    });

    // create the controller and inject Angular's $scope
    scotchApp.controller('mainController', function($scope,$http) {
    	
       
    });

    
    scotchApp.controller('responseCodeController', function($scope) {
        $scope.message = 'Look! I am an about page.';
    });

    
    
    scotchApp.controller('apiController', function($scope,$http) {
                
        $scope.arr1=[[]];
    	count++;
    
       	var response = $http.get('http://localhost:8888/room_autoamtion/charts/apidetail');
		response.success(function(data)
		{
			console.log(data);
			$scope.arr1 = data;
			
		});	
		
        
        
        if(count<2)
        	google.charts.load('current', {'packages':['corechart']});
        
        google.charts.setOnLoadCallback(drawChart1);
  		
        function drawChart1() {
          var data1 = google.visualization.arrayToDataTable($scope.arr1);

          var options1 = {
            title: 'Api Response Code Details',
            curveType: 'function',
            legend: { position: 'bottom' }
          };
          var chart = new google.visualization.PieChart(document.getElementById('curve_chart'));
         
          chart.draw(data1, options1);
        }
        
    
        
    });
    
    scotchApp.controller('tempDetailController', function($scope,$http) {
    	$scope.arr=[[]];
    	count++;
 
    	
    	 $scope.selectRoom=[];
        	var response = $http.get('http://localhost:8888/room_autoamtion/charts/roomlist');
    		response.success(function(data)
    		{
    			console.log(data);
    			$scope.selectRoom = data;
    						
    		});	
    	
    
	
		$scope.show=function(){
    		
			
       		var e=document.getElementById("ddList");
    		var roomid=e.options[e.selectedIndex].text;
    		if(roomid == "select room")
    			{
    				bootbox.alert("Please select valid room number" , function(result){
    					
    				});    			
    			}
    		else{
    			roomid=Number(roomid);
    			var response = $http.post('http://localhost:8888/room_autoamtion/charts/tempdetail/'+roomid);
    			response.success(function(data)
    			{
    				console.log(data);
    				$scope.arr = data;
    				
    			});	
    			response.error(function(data)
    		    		{
    						bootbox.alert("No data found for this room!" , function(result){
    						});
    		    			
    		    		});
    			    			
        if(count<2)
        	google.charts.load('current', {'packages':['corechart']});
        
       
        google.charts.setOnLoadCallback(drawChart);
  		
        function drawChart() {
          var data = google.visualization.arrayToDataTable($scope.arr);

          var options = {
            title: 'Sensor performance',
            hAxis: {title: 'TIME', minValue: 0, maxValue: 15},
            vAxis: {title: 'Sensor Frequency', minValue: 0, maxValue: 15},
            curveType: 'function',
            legend: { position: 'bottom' }
          };

          var chart = new google.visualization.LineChart(document.getElementById('curve_chart_temp'));

          chart.draw(data, options);
        }
        
    		}
		}
       
        
    });
    
    
    
    
    
    scotchApp.controller('pressDetailController', function($scope,$http) {
    	$scope.arr=[[]];
    	count++;
 
    	
    	 $scope.selectRoom=[];
       	var response = $http.get('http://localhost:8888/room_autoamtion/charts/roomlist');
   		response.success(function(data)
   		{
   			console.log(data);
   			$scope.selectRoom = data;
   						
   		});	
    	
   		
    	$scope.show=function(){
    		
       		var e=document.getElementById("ddList");
    		var roomid=e.options[e.selectedIndex].text;
    		if(roomid == "select room")
    			{
    			bootbox.alert("Please select valid room no" , function(result){
				});		
    			}
    		else{
    			
    			roomid=Number(roomid);
    	
    	var response = $http.post('http://localhost:8888/room_autoamtion/charts/pressdetail/'+roomid);
		response.success(function(data)
		{
			console.log(data);
			$scope.arr = data;
			
		});	
		
		response.error(function(data)
	    		{
			bootbox.alert("No data found for this room!" , function(result){
			});
	    			
	    		});
        
        if(count<2)
        	google.charts.load('current', {'packages':['corechart']});
        
        google.charts.setOnLoadCallback(drawChart);
  		
        function drawChart() {
          var data = google.visualization.arrayToDataTable($scope.arr);

          var options = {
            title: 'Sensor performance',
            hAxis: {title: 'TIME', minValue: 0, maxValue: 15},
            vAxis: {title: 'Sensor Frequency', minValue: 0, maxValue: 15},
            curveType: 'function',
            legend: { position: 'bottom' }
          };

          var chart = new google.visualization.LineChart(document.getElementById('curve_chart_press'));

          chart.draw(data, options);
        }
        
    		}
    	}
       
        
    });
    
    
    scotchApp.controller('humiDetailController', function($scope,$http) {
    	$scope.arr=[[]];
    	count++;
 
    	
    	 $scope.selectRoom=[];
     	var response = $http.get('http://localhost:8888/room_autoamtion/charts/roomlist');
 		response.success(function(data)
 		{
 			console.log(data);
 			$scope.selectRoom = data;
 						
 		});	
    
    	$scope.show=function(){
    		
       		var e=document.getElementById("ddList");
    		var roomid=e.options[e.selectedIndex].text;
    		if(roomid == "select room")
    			{
    			bootbox.alert("Please select valid room no" , function(result){
				});	  			
    			}
    		else{
    			
    			roomid=Number(roomid);
    			
    		var response = $http.post('http://localhost:8888/room_autoamtion/charts/humidetail/'+roomid);
    		response.success(function(data)
    		{
    			console.log(data);
    			$scope.arr = data;
    			
    		});	
    		response.error(function(data)
     	    		{
    			bootbox.alert("No data found for this room!" , function(result){
				});
     	    			
     	    		});
    	
            
            
            if(count<2)
            	google.charts.load('current', {'packages':['corechart']});
            
            google.charts.setOnLoadCallback(drawChart);
      		
            function drawChart() {
              var data = google.visualization.arrayToDataTable($scope.arr);

              var options = {
                title: 'Sensor performance',
                hAxis: {title: 'TIME', minValue: 0, maxValue: 15},
                vAxis: {title: 'Sensor Frequency', minValue: 0, maxValue: 15},
                curveType: 'function',
                legend: { position: 'bottom' }
              };

              var chart = new google.visualization.LineChart(document.getElementById('curve_chart_humi'));

              chart.draw(data, options);
            }
        
    	}
    	
    	
    	}
    
       
        
    });
    
    scotchApp.controller('luxDetailController', function($scope,$http) {
    	$scope.arr=[[]];
    	count++;
 
    	 $scope.selectRoom=[];
      	var response = $http.get('http://localhost:8888/room_autoamtion/charts/roomlist');
  		response.success(function(data)
  		{
  			console.log(data);
  			$scope.selectRoom = data;
  						
  		});	
    		$scope.show=function(){
    		
       		var e=document.getElementById("ddList");
    		var roomid=e.options[e.selectedIndex].text;
    		if(roomid == "select room")
    			{
    			bootbox.alert("Please select valid room no" , function(result){
				});	    			
    			}
    		else{
    			
    			roomid=Number(roomid);
    	var response = $http.post('http://localhost:8888/room_autoamtion/charts/luxdetail/'+roomid);
		response.success(function(data)
		{
			console.log(data);
			$scope.arr = data;
			
		});	
		
		response.error(function(data)
	    		{
			bootbox.alert("No data found for this room!" , function(result){
			});
	    		});
        
        if(count<2)
        	google.charts.load('current', {'packages':['corechart']});
        
        google.charts.setOnLoadCallback(drawChart);
  		
        function drawChart() {
          var data = google.visualization.arrayToDataTable($scope.arr);

          var options = {
            title: 'Sensor performance',
            hAxis: {title: 'TIME', minValue: 0, maxValue: 15},
            vAxis: {title: 'Sensor Frequency', minValue: 0, maxValue: 15},
            curveType: 'function',
            legend: { position: 'bottom' }
          };

          var chart = new google.visualization.LineChart(document.getElementById('curve_chart_lux'));

          chart.draw(data, options);
        }
        
   	}
    		}
        
    });