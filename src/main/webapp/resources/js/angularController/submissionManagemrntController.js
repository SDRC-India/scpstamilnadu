/*
 * @author Swarna(swarnaprava@sdrc.co.in) 
 */

function submissionManagementController($scope, $http, $log){
	$("#loader-mask").show();
	$scope.activeMenu = "submission-management";
	$scope.pageName = "Submission Management";
	$scope.tableData = [""];
	$scope.submissiontableData = [];
	
	/* show submission mgmt details details in modal */
	$scope.showIfData = true;
    $scope.showIfNoData = false;

	$http.get('getSubmissionHistory').
	then(function(result){
		$log.info(result.data);
		if(result.data.data.length > 0){
		for(var i=0; i < result.data.data.length; i++ )
			{
		$scope.submissiontableData.push(result.data.data[i]);
		
			}
		$("#loader-mask").fadeOut();
		}else{
			$scope.showIfData = false;
			$scope.showIfNoData = true;
			$("#loader-mask").fadeOut();
		}
	});
	
	/* show submission mgmt details in modal */
	
	
	/* show preview details in modal */
	
	$scope.preView = function(index)
	{
		$("#loader-mask").show();
		var month = $scope.submissiontableData[index].dataForMonth;
		var year = $scope.submissiontableData[index].dataForYear;
		var facilityid = $scope.submissiontableData[index].facility_id;
		$http.get('getPreviewData?facilityId=' +facilityid + '&month=' +month+ '&year=' +year).
		then(function(result){
			$scope.disableDataEntry = result.data.isDataSubmissionAllowed;
			if(result.data.isDataSubmissionAllowed){
				$scope.previewData = result.data.indTypeIndicatorModelMap.dataEntry;
				console.log(result);
			}else if(!result.data.isDataSubmissionAllowed && result.data.message == undefined){
				$scope.previewData = result.data.indTypeIndicatorModelMap.dataEntry;
			}
		});
		
		$("#submissiondetailsPreviewModal").modal("show");
		$("#loader-mask").fadeOut();
	}
	
	
	
	$scope.redirectPreview = function()
	{
		$("#submissiondetailsPreviewModal").modal("hide");
	};
	
	/* show preview details in modal */
	
	/* redirect to data entry page on click of edit btn */
	
	$scope.edit = function()
	{
		window.location = "dataEntry";
	}
	
	/* end redirect to data entry page on click of edit btn */
	
	
	
	
	
	
}