/*
 * @Laxman Paikaray(laxman@sdrc.co.in)
 * 
 * */
function checkSessionOut(data){
	if(typeof data == 'string' && data.indexOf("You are not authorized to view this page") != -1){
		$("body").append('<div id="sessionOutMessage" class="modal fade" role="dialog"><div class="modal-dialog"><div class="modal-content"><div class="modal-body text-center"><h3>Session is expired</h3><a href="home" class="btn btn-default errorOk" type="submit">OK</a></div></div></div></div>');
		$("#sessionOutMessage").modal("show");
	}
}

 
  function allServices($http, $q) {
	
	this.getMSTEngagementScoreData = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getMSTEngagementScoreData")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getAllPDSAs = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getAllPDSAs")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.saveTXNEngagementScore = function(engagementScoreId, facilityId, timeperiodId){
		var deferred = $q.defer();
        // get posts from database
		$http.get("saveTXNEngagementScore?engagementScoreId=" + engagementScoreId + 
				"&facilityId=" + facilityId +
				"&timeperiodId=" + timeperiodId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getLineChartOfEngagementScore = function(facilityId){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getLineChartOfEngagementScore?facilityId=" + facilityId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getPDSAForFacility = function(facilityId){
		var deferred = $q.defer();
        // get posts from database
		$http.get("getPDSAForFacility?facilityId=" + facilityId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getSuperitendentMnE = function(){
		var deferred = $q.defer();
        // get posts from database
		$http.get('submissions/superintendentMnE')
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	
	this.getSubmissionInicators = function(txnSubmissionId, refSubmissionId, facilityId, timePeriodId){
		var deferred = $q.defer();
        // get posts from database
		if(refSubmissionId == null){
			var url = 'submissions/viewSubmissionInicators?txnSubmissionId=' + txnSubmissionId +
			'&facilityId=' + facilityId +
			'&timePeriodId=' + timePeriodId;
		}
		else{
			var url = 'submissions/viewSubmissionInicators?txnSubmissionId=' + txnSubmissionId +
			'&refSubmissionId=' + refSubmissionId +
			'&facilityId=' + facilityId +
			'&timePeriodId=' + timePeriodId;
		}
		$http.post(url)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.approveOrRejectSubmission = function(txnSubmissionId, remark, isApprove){
		var deferred = $q.defer();
        // get posts from database
		var url = "submissions/approveOrReject?txnSubmissionId=" + txnSubmissionId + "&isApprove=" + isApprove ;
		$http.post(url, JSON.stringify(remark))
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getPDSAFilterOption = function(){
		var deferred = $q.defer();
		$http.get("getPDSAFilterOption")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getPlanningData = function(){
		var deferred = $q.defer();
		$http.get("getPlanningData")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getClosingPDSAStatus = function(){
		var deferred = $q.defer();
		$http.get("getClosingPDSAStatus")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getTXNPDSADetails = function(pdsaId){
		var deferred = $q.defer();
		$http.get("getTXNPDSADetails?pdsaId="+pdsaId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getPDSAForFacility = function(){
		var deferred = $q.defer();
		$http.get("getPDSAForFacility")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.generatePDSANumberAndDate = function(){
		var deferred = $q.defer();
		$http.get("generatePDSANumberAndDate")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getBlankPDSAObject = function(){
		var deferred = $q.defer();
		$http.get("getBlankPDSAObject")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getTimePeriodForReport = function(periodicityId){
		var deferred = $q.defer();
		$http.get("getTimePeriodForReport?periodicityId="+periodicityId)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getAreaForReportFilterData = function(){
		var deferred = $q.defer();
		$http.get("getAreaForReportFilterData")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	
	this.getAllAreaForHistoricalView = function(){
		var deferred = $q.defer();
		$http.get("getAllAreaForHistoricalView")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	
	this.saveChangeIdea = function( indicatorId, changeIdeaDescription){
		var deferred = $q.defer();
        // get posts from database

			var url = 'saveChangeIdea?indicatorId=' + indicatorId +
			'&changeIdea=' + changeIdeaDescription ;
		$http.post(url)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.closePDSA = function( pdsaId, documents,statusId, description){
		var deferred = $q.defer();
        // get posts from database

			var url = 'closePDSA?pdsaId=' + pdsaId +
			'&documents=' + documents + 'statusId=' + statusId + 'description=' + description ;
		$http.post(url)
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	this.getTimePeriodForLegacy = function(){
		var deferred = $q.defer();
		$http.get("getTimePeriodForLegacy")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
	
	this.getFacilityTypeAndSize = function(){
		var deferred = $q.defer();
		$http.get("getFacilityTypeAndSize")
          .then(function(result) {
        	  checkSessionOut(result.data);
        	  jsonData = result.data;
            deferred.resolve(jsonData);
          }, function(error) {
        	  jsonData = error;
            deferred.reject(error);
          });
        jsonData = deferred.promise;
        return $q.when(jsonData);
	};
}	
/*$(document).ready(function(){
	$("#mymain").css("min-height", $(window).height()-92-58);
	    $('[data-toggle="tooltip"]').tooltip();   
});  
$(window).scroll(function(){
	if($(window).scrollTop() > 92){
		$(".navbar.nav-menu-container").addClass("fixed");
	}
	else{
		$(".navbar.nav-menu-container").removeClass("fixed");
	}
});*/
function minmax(value, min, max) 
{
    if(parseInt(value) < min || isNaN(value)) 
        return 0; 
    else if(parseInt(value) > max) 
        return 100; 
    else return value;
}
