/*
 * @author Swarna(swarnaprava@sdrc.co.in) 
 */

function misReportController($scope, $http, $filter ,$timeout, $window,$compile) {
	$("#loader-mask").show();
	$scope.activeMenu = "MIS Report";
	$scope.pageName = "MIS Report";
	
	$http.get('initMonthAndYear').
	then(function(result){
		
		$scope.misReportdata = result.data;
	});
	
	$scope.selectMonth = function(month)
	{
		$scope.year = "";
		$scope.month = month.monthString;
		$scope.monthid = month.month;
		$scope.yearsScope = month.years;
		
	}
	$scope.selectYear = function(year)
	{
		$scope.year = year.yearString;
		$scope.yearid = year.year;
	}
	$("#loader-mask").fadeOut();
	
	
	
	$scope.downloadExcel = function()
	{
		
		if ($scope.month == undefined) {
			$scope.errorMsg = "Please Select Month";
			$('#errorMessage').modal("show");
		}else if ($scope.yearid == undefined) {
			$scope.errorMsg = "Please Select Year";
			$('#errorMessage').modal("show");
		}
		else
			
		{
		
		var months = $scope.monthid;
		var years = $scope.yearid;
		   $("#loader-mask").show();
		$http({
				url : "editedReport?month=" +months + '&year=' +years,
				method : 'POST',
				contentType : 'application/json',
				responseType:'arraybuffer'
				}).then(function(result) {
				
				jsonData = result.data;
				   
				  
				
				var file =new Blob([jsonData], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
				var fileURL = (window.URL || window.webkitURL).createObjectURL(file);
				var downloadLink = document.createElement("a");

				document.body.appendChild(downloadLink);
				downloadLink.style = "display: none";
				$("#loader-mask").fadeOut();
				downloadLink.href = fileURL;
				downloadLink.download ="MISReport.xlsx";
				downloadLink.click();
				deferred.resolve(jsonData);
				
				}, function(error) {
				$("#loader-mask").fadeOut();
				jsonData = error;
				deferred.reject(error);
				});

	}
	
}
	
}