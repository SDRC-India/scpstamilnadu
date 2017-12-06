/*
 * @author Swarna(swarnaprava@sdrc.co.in) 
 */

function dataEntryController($scope, $http, $timeout){
	$scope.activeMenu = "data-entry";
	$scope.pageName = "Data Entry";
	$("#loader-mask").show();
	$scope.pageLoaded = false;
	$scope.tableData = [""];
	$scope.profileDataSaved = false;
	
	$scope.profileFormDisabled = false;
	$scope.numberSelectedIndicators = {Prematurity: 0, Sepsis: 0, Asphyxia: 0};
	$scope.demo = {
			value: 50
	};
	
// $http.get('getAgData').
// success(function(data){
// console.log(data);
// });
	
	$scope.putInputBox = function(model){
		$timeout(function(){
			$(".inputBox").html("<input type='number' class='tableInput' ng-model='demo.value'>");
		}, 1000);
	};
	/* (@author: Pratyush) The following get method receives data from server. */
	$http.get('dataEntryJsonData').
	then(function(result){
		$scope.disableDataEntry = result.data.isDataSubmissionAllowed;
		$scope.showedit = result.data.edit;
		$scope.showsubmit = result.data.submit;
		$scope.tableData = result.data.indTypeIndicatorModelMap.dataEntry;
		$scope.infodata = result.data.userDetails;
		$scope.locationDetails = result.data.userDetails.location;
		$scope.userTypeDetails = result.data.userDetails.userType;
		$scope.usernameDetails = result.data.userDetails.username;
		if($scope.showedit == "true")
			{
			 	$('#showsubmitdiv').hide();
			    $('#showeditdiv').show();
			}
		else
			{
				$('#showsubmitdiv').show();
				$('#showeditdiv').hide();
			}
		
		if(!$scope.disableDataEntry && result.data.message != undefined){
			$scope.infoMsg = result.data.message;
			$("#infoMessage").modal("show");
		}
		if(result.data.isDataSubmissionAllowed){
			angular.forEach($scope.tableData, function(key, value){
				key.numeratorValue = key.numeratorValue == null ? null : key.numeratorValue.toString();
				key.denominatorValue = key.denominatorValue == null ? null : key.denominatorValue.toString();
			});
			$scope.timePeriod = result.data.timeperiodMonth+" "+result.data.timeperiodYear;
			console.log(result);
		}else if(!result.data.isDataSubmissionAllowed && result.data.message == undefined){
			$scope.tableData = result.data.indTypeIndicatorModelMap.dataEntry;
			$scope.timePeriod = result.data.timeperiodMonth+" "+result.data.timeperiodYear;
		}else{
			$scope.infoMsg = result.data.message;
			$("#infoMessage").modal("show");
		}
		$("#loader-mask").fadeOut();
	},function(error){
		console.log(error);
	});
	
	
	
	
// $scope.getTimeperiod = $http.get("getTimePeriod").then(function(response){
// $scope.timeperiod = response.data;
// if($scope.timeperiod){
// $scope.getRestIndicators();
// }
// $scope.getFormTable($scope.timeperiod.timePeriodId, false);
// },function(error){
//		
// });
	
	
// $scope.getRestIndicators();
	
	
	

	
	/*
	 * (@author: Pratyush) The following function is for enabling and disabling
	 * the submit button depending upon all input fields filled or not
	 */
	$scope.isDisabled = true;
	$scope.changeIsEnabled = function(){
		var counter = 0;
		for(var i=0; i<$scope.tableData.length; i++){
			if($scope.tableData[i].numeratorValue == null)
				$scope.tableData[i].numeratorValue = "";
			$scope.isDisabled = true;
			if(Number($scope.tableData[i].denominatorValue) < Number($scope.tableData[i].numeratorValue) || $scope.tableData[i].numeratorValue.toString().trim() == "" || Number($scope.tableData[i].denominatorValue) <= 0){
				counter++;
			}
		}
		if(!(counter > 0))
			$scope.isDisabled = false;
	};
	/*
	 * (@author: Pratyush) The following http call is for sending dataEntry data
	 * to server for saving purpose.
	 */
	$scope.submitForm = function(){
		var flag = false;
		if($scope.tableData){
			console.log($scope.tableData);
			for(var i=0; i<$scope.tableData.length; i++){
				if($scope.tableData[i].numeratorValue == null || $scope.tableData[i].numeratorValue.toString() == undefined || $scope.tableData[i].numeratorValue.toString()=="")
					{
						flag = true;
					}
				else if($scope.tableData[i].denominatorValue == null || $scope.tableData[i].denominatorValue.toString() == undefined || $scope.tableData[i].denominatorValue.toString()=="")
					{
						flag = true;
					}
				else if($scope.tableData[i].numeratorValue > $scope.tableData[i].denominatorValue )
				{
						flag = true;
						$scope.errorMsg = "Numerator value cannot be greater than denominator value";
						$("#errorMessage").modal("show");
				}
				else
				{
					flag = false;
				}
			}
		}
		
		if(flag){
			$scope.errorMsg = "Please enter values for all numerator and denominator fields";
			$("#errorMessage").modal("show");
		}else{
			$("#loader-mask").show();
			angular.forEach($scope.tableData, function(value, key){
				if(typeof(value.denominatorValue) == "string")
					value.denominatorValue = Number(value.denominatorValue);
				if(typeof(value.numeratorValue) == "string")
					value.numeratorValue = Number(value.numeratorValue);
				value.percentage = value.percentage.toString();
			});
			
			var dataEntryObjArr = [];
			for(var i=0; i<$scope.tableData.length;i++){
				var dataEntryObj = {};
				if( $scope.tableData[i].percentage == "-"){
					dataEntryObj.percentage = 0.0;
				}
				else
				dataEntryObj.percentage = $scope.tableData[i].percentage;
				
				dataEntryObj.denominatorValue = $scope.tableData[i].denominatorValue;
				dataEntryObj.indicatorId = $scope.tableData[i].indicatorId;
				dataEntryObj.numeratorValue = $scope.tableData[i].numeratorValue;
				
				dataEntryObjArr.push(dataEntryObj);
			}
			console.log($scope.tableData);
			$http.post('saveDataEntryJsonData',dataEntryObjArr).
			success(function(result){
				console.log(result);
				if(result.status == "200"){
					$scope.infoMsg = result.message;
					$("#infoMessage").modal("show");
				}
				$("#loader-mask").fadeOut();
			},function(error){
				$("#loader-mask").fadeOut();
			});
	}
	};
	
	$scope.updateForm = function(){
		var flag = false;
		if($scope.tableData){
			console.log($scope.tableData);
			for(var i=0; i<$scope.tableData.length; i++){
				if($scope.tableData[i].numeratorValue == null || $scope.tableData[i].numeratorValue.toString() == undefined || $scope.tableData[i].numeratorValue.toString()=="")
					{
						flag = true;
					}
				else if($scope.tableData[i].denominatorValue == null || $scope.tableData[i].denominatorValue.toString() == undefined || $scope.tableData[i].denominatorValue.toString()=="")
					{
						flag = true;
					}
				else if($scope.tableData[i].numeratorValue > $scope.tableData[i].denominatorValue )
				{
						flag = true;
						$scope.errorMsg = "Numerator value cannot be greater than denominator value";
						$("#errorMessage").modal("show");
				}
				else
					{
						flag = false;
					}
			}
		}	
		
			$("#loader-mask").show();
			angular.forEach($scope.tableData, function(value, key){
				if(typeof(value.denominatorValue) == "string")
					value.denominatorValue = Number(value.denominatorValue);
				if(typeof(value.numeratorValue) == "string")
					value.numeratorValue = Number(value.numeratorValue);
				value.percentage = value.percentage.toString();
			});
			
			var dataEntryObjArr = [];
			for(var i=0; i<$scope.tableData.length;i++){
				var dataEntryObj = {};
				if( $scope.tableData[i].percentage == "-"){
					dataEntryObj.percentage = 0.0;
				}
				else
				dataEntryObj.percentage = $scope.tableData[i].percentage;
				dataEntryObj.denominatorValue = $scope.tableData[i].denominatorValue;
				dataEntryObj.indicatorId = $scope.tableData[i].indicatorId;
				dataEntryObj.numeratorValue = $scope.tableData[i].numeratorValue;
				dataEntryObjArr.push(dataEntryObj);
			}
			console.log($scope.tableData);
			$http.post('saveDataEntryJsonData',dataEntryObjArr).
			success(function(result){
				console.log(result);
				if(result.status == "200"){
					$scope.infoMsg = result.message;
					$("#updateinfoMessage").modal("show");
				}
				$("#loader-mask").fadeOut();
			},function(error){
				$("#loader-mask").fadeOut();
			});
			
	};
	
	$scope.redirect = function(){
			$("#infoMessage").modal("hide");
			window.location = "submissionManagement";
	};
	
	$scope.countSelectedIndicators = function(checked, coreArea){
		if(checked)
			$scope.numberSelectedIndicators[coreArea] += 1;
		else
			$scope.numberSelectedIndicators[coreArea] -= 1;
	};
	
	
	
	
	/* validations */

	$scope.validateInput = function(model,key,oppsiteKey,id){
		if(key == 'numeratorValue'){
			
			if(model[oppsiteKey] != undefined && model[oppsiteKey].toString() != ""){
				if((model[key] || model[key] == 0) 
						&& (model[oppsiteKey] || model[oppsiteKey] == 0) 
						&& (Number(model[key]) > Number(model[oppsiteKey]))) {
//					model[key] = "10";
					$("#" + id).addClass("errorFound");
					$scope.errorMsg = "'"+model.numeratorName + "' can not be greater than '" + model.denominatorName+"'";
					$("#errorMessage").modal("show");
					model.percentage = "";
					$scope.calculatePercentage(model);
					
				}else{
					$("#" + id).removeClass("errorFound");
				}
			
			}	
			else{
			$("#" + id).removeClass("errorFound");
			}
			
		}
		
		if(key == 'denominatorValue'){
			if(model[oppsiteKey] != undefined && model[oppsiteKey].toString() != ""){
			if((model[key] || model[key] != "" || model[key] == "0") && (model[oppsiteKey] || model[oppsiteKey] == 0) && (Number(model[key]) < Number(model[oppsiteKey]))) {
//				model[key] = "";
				$("#" + id).addClass("errorFound");
				$scope.errorMsg = "'"+model.denominatorName + "' can not be less than '" + model.numeratorName+"'";
				$("#errorMessage").modal("show");
				model.percentage = "";
				$scope.calculatePercentage(model);
				
			}else{
				$("#" + id).removeClass("errorFound");
			}
			}
			else{
				$("#" + id).removeClass("errorFound");
			}
		}
		
		
//		if(model[key] == "0")
//			{
//				$("#" + id).removeClass("errorFound");
//			}
		
		
	};
	
	$scope.calculatePercentage = function(model){
		if(model.numeratorValue != undefined && model.denominatorValue != undefined){
			model.numeratorValue = Number(model.numeratorValue);
			model.denominatorValue = Number(model.denominatorValue);
			if(model.denominatorValue != 0)
				model.percentage = (Math.round( model.numeratorValue / model.denominatorValue * 100 * 10 ) / 10).toFixed(1);
			else if(model.denominatorValue == 0){
				model.percentage = "-";
			}
		}else
			model.percentage = "";
	};	
	
	

	$scope.validateInput1 = function(model,key,oppsiteKey,id){
		if(key == 'numeratorValue'){
			
			if(model[oppsiteKey] != undefined && model[oppsiteKey].toString() != ""){
				if((model[key] || model[key] == 0) 
						&& (model[oppsiteKey] || model[oppsiteKey] == 0) 
						&& (Number(model[key]) > Number(model[oppsiteKey]))) {
					model[key] = "";
					$("#" + id).addClass("errorFound");
					$scope.errorMsg = "'"+model.numeratorName + "' can not be greater than '" + model.denominatorName+"'";
					$("#errorMessage").modal("show");
					model.percentage = "";
					$scope.calculatePercentage(model);
					
				}else{
					$("#" + id).removeClass("errorFound");
				}
			
			}	
			else{
			$("#" + id).removeClass("errorFound");
			}
			
		}
		
		if(key == 'denominatorValue'){
			if(model[oppsiteKey] != undefined && model[oppsiteKey].toString() != ""){
			if((model[key] || model[key] != "" || model[key] == "0") && (model[oppsiteKey] || model[oppsiteKey] == 0) && (Number(model[key]) < Number(model[oppsiteKey]))) {
				model[key] = "";	
				$("#" + id).addClass("errorFound");
				$scope.errorMsg = "'"+model.denominatorName + "' can not be less than '" + model.numeratorName+"'";
				$("#errorMessage").modal("show");
				model.percentage = "";
				$scope.calculatePercentage(model);
			
			}else{
				$("#" + id).removeClass("errorFound");
			}
			
			}else if((model[key] || model[key] != "" || model[key] == "0")){
			$("#" + id).removeClass("errorFound");
			$("#" + id).focus();
				}
				else {
				$("#" + id).removeClass("errorFound");
				$("#" + id).focus();
					}
		}
		
	};
	
}




$(document).ready(function(){
	$("#addIndicatorModal .modal-dialog").css("max-height", $(window).height() - 75 -44);
	if($(window).height()>992)
	$("#addIndicatorModal .modal-dialog .modal-body .table-responsive").css("max-height", $(window).height() - 75 -44 -200);
	else{
		$("#addIndicatorModal .modal-dialog .modal-body .table-responsive").css("max-height", 'none');
	}
	$("#confirmAddIndicator .modal-dialog").css("max-height", $(window).height() - 75 -44);
	$("#confirmAddIndicator .modal-dialog .modal-body ul").css("max-height", $(window).height() - 75 -44-180);
});