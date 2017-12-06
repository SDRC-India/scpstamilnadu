<!-- 
@author Swarna (swarnaprava@sdrc.co.in)
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="misReportApp">
<head>

<title>SCPS Tamilnadu-MIS Report</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
<link rel="stylesheet" href="resources/css/style.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<!--[if lte IE 12]>
  <link rel="stylesheet" type="text/css" media="screen, projection" href="resources/css/ie12-down.css" />
<![endif]-->

<spring:url value="/webjars/jquery/2.0.3/jquery.min.js" var="jQuery" />
<script src="${jQuery}"></script>
<spring:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js"
	var="bootstrapjs" />
<script src="${bootstrapjs}"></script>
<spring:url value="/webjars/angularjs/1.5.5/angular.min.js"
	var="angularmin" />
<script src="${angularmin}" type="text/javascript"></script>
<script src="resources/js/html5shiv.js" type="text/javascript"></script>


</head>
<style type="text/css">
section.bottomfooter {position: fixed; !important;}
.navbar
{
	position: fixed;
    top: 0;
    width: 100%;
    z-index: 200;
}
</style>
<jsp:include page="fragments/header.jsp"></jsp:include>
<body ng-controller="misReportController" class="xoverflowHidden" ng-cloak>
	<div id="mymain" class="container-fluid">
		<div class="pageNameContainerReport">
			<h3>{{pageName}}</h3>
		</div>
			<div class="col-md-12 col-sm-12 col-xs-12 addingMisReportbar">
				<div class="col-md-6 col-sm-6 col-xs-12 summaryAllbox monthInMis">
					<label class="col-md-2 text-right labelFont sectorleft">Month </label>
						<div class=" col-md-6 col-xs-6 select-container text-center sectorboxinTabletView sectrboxinIpadview">
							<div class="input-group report-width">
								<input type="text" placeholder="Select Month" id="month"
									class="form-control not-visible-input bgClr" name="month"
									readonly ng-model="month">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu" style="max-height: 136px;">
										<li 
											ng-repeat="monthObj in misReportdata | orderBy : '-monthString'"
											title="{{monthObj.monthString}}"><a
											ng-click="selectMonth(monthObj)">
											{{monthObj.monthString}}</a></li>
									</ul>
								</div>
							</div>
						</div>
				</div>
				
				<div class="col-md-6 col-sm-6 col-xs-12 summaryAllbox">
					<label class="col-md-3 text-right labelFont labelSubsctIntablet label-padding">Year </label>
						<div class=" col-md-6 col-xs-6 select-container text-center subsectorboxinTablt sectrboxinIpadview">
							<div class="input-group report-width reportgrpSubsectrWidthtablet">
								<input type="text" placeholder="Select Year" id="year"
									class="form-control not-visible-input bgClr" name="year"
									readonly ng-model="year">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu" style="max-height: 136px;">
										<li 
											ng-repeat="yearObj in yearsScope"
											title="{{yearObj.yearString}}"><a
											ng-click="selectYear(yearObj)">
											{{yearObj.yearString}}</a></li>
									</ul>
								</div>
							</div>
						</div>
				</div>
			
				<div class="text-center">
							<button class="submitIndicator"
								ng-click="downloadExcel()">Download Report</button>
						</div>
				</div>
		</div>
	<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;
	
<div id="errorMessage" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="errorhead"><img alt="" src="resources/images/icons/Messages_warning_caution_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; ERROR</div>
					<div class="errorbody">{{errorMsg}}</div>
					<button type="button" class="btn errorOk" data-dismiss="modal" >Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="resources/js/angularController/misReportController.js"></script>
	<script type="text/javascript">
		var app = angular.module("misReportApp", []);
		var myAppConstructor = angular.module("misReportApp");
		myAppConstructor.controller("misReportController",
				misReportController);
		/* myAppConstructor.service('allServices', allServices); */
	</script>
	<script type="text/javascript"
		src="resources/js/angularDirective/directive.js"></script>
		<script>
	window.addEventListener("orientationchange", function() {
		// Announce the new orientation number
		location.reload();
	}, false);
</script>
	<script type="text/javascript">
	$(document).ready(function(){
	    $('[data-toggle="tooltip"]').tooltip(); 
	});
	</script>
</body>
<jsp:include page="fragments/footer.jsp"></jsp:include>
</html>