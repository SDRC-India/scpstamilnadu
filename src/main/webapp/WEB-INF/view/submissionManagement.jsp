<!-- 
@author Swarna (swarnaprava@sdrc.co.in)
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="submissionManagementApp">
<head>

<title>SCPS Tamilnadu-Submission Management</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
<link rel="stylesheet" href="resources/css/style.css">
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

</head>

<style type="text/css">
section.bottomfooter {
	position: fixed;
}
 .footer_width 
 { 
  position: fixed; 
  bottom: 0; */
  width: 100%; 
 } 
</style>
<jsp:include page="fragments/header.jsp"></jsp:include>
<body ng-controller="submissionManagementController"
	class="xoverflowHidden" ng-cloak>
	
	
	<div id="mymain" class="container-fluid">
	<div class="col-md-12">
			<div class="col-md-5 facilityDetails submissionfacilityDetails">
<!-- 				<ul class="infodetails"> -->
<!-- 					<li><h4>Location: {{locationDetails}}</h4></li> -->
<!-- 					<li><h4>User Type: {{userTypeDetails}}</h4></li> -->
<!-- 					<li><h4>User Name: {{usernameDetails}}</h4></li> -->
<!-- 				</ul> -->
			</div>

			<div class="col-md-7">
				<h3 class="submissionPageInfo">{{pageName}}</h3>
			</div>
		</div>
		<div class="col-md-12" id="record_found" ng-show="showIfData">
			<div class="container-fluid">
				<div class="col-md-12">
					<section class="profile-section col-md-12 col-sm-12 col-xs-12 submissionMgmtTable">
						<div class="profile-entry">
							<div id="profileTable" class="accordion-content expanded">
								<div class='content'>
									<div class=" table-responsive">
										<table items="tableData" show-filter="true" cellpadding="0"
											cellspacing="0" border="0"
											class="dataTable table table-bordered scps submanagemnt"
											id="dataTable">
											<thead>
												<tr>
													<th class="submissionmgmt-serial">Sl.&nbsp;No.</th>
													<th class="submissionmgmt-date">Submission Month</th>
													<th class="submissionmgmt-action-history">Action
														History</th>
														<th class="submissionmgmt-lastmodified">Last Modified</th>
													<th class="submissionmgmt-action-preview-edit">Action</th>
												</tr>
											</thead>
											<tbody>
												<tr ng-repeat="td in submissiontableData">
												
													<td>{{$index+1}}</td>

													<td class="submissionmgmt-date-td">{{td.dataSubmittedFor}}</td>

													<td class="submissionmgmt-action-history-td">{{td.actionTakenType}}	</td>
													
													<td class="submissionmgmt-last-modified-td">{{td.lastModified}}	</td>

													<td class="inputBox submissionmgmt-action-preview-edit-td">
														<button class="submitSCPS btnMarginzero" ng-show="td.availableViewType == 'preview'"
															ng-click="preView($index)">Preview</button>
														<button class="submitSCPS btnMarginzero" ng-show="td.availableViewType == 'edit'"
															ng-click="edit()">Edit</button>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</section>
				</div>
			</div>
		</div>
		
		
		<div class="col-md-12" id="no_data" ng-show="showIfNoData">
			<div class="container-fluid">
				<div class="col-md-12">
					<section class="profile-section col-md-12 col-sm-12 col-xs-12">
						<div class="col-md-12 profile-entry">
							<div id="profileTable" class="col-md-12 accordion-content expanded">
								<div class='content'>
									<h2 class="col-md-12 no_records">No record found !</h2>
								</div>
							</div>
						</div>
					</section>
				</div>
			</div>
		</div>
		
	</div>

	<!-- start preview details modal -->
	<div id="submissiondetailsPreviewModal"
		class="modal fade submsnDetailsModal" role="dialog"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content submsnDetailsTableeModal">
				<div class="modal-body text-center">
					<div class="infohead">
						<img alt="" src="resources/images/icons/Messages_info_icon.svg"
							style="width: 25px; margin-top: -5px;">&nbsp; INFO
					</div>
					<div class="col-md-12">
						<div class="col-md-12">
							<section class="profile-section col-md-12 col-sm-12 col-xs-12 submissionMgmtTable previewofDataentry">
								<div class="profile-entry">
									<div id="profileTable" class="accordion-content expanded">
										<div class='content'>
											<div class=" table-responsive">
												<table items="tableData" show-filter="true" cellpadding="0"
													cellspacing="0" border="0"
													class="dataTable table table-bordered scps" id="dataTable">
													<thead>
														<tr>
															<th class="dataentry-serial">Sl.&nbsp;No.</th>
															<th class="dataentry-indicatorName">Indicator Name</th>
															<th class="dataentry-numeratorName">Numerator</th>
															<th class="dataentry-numeratorValue">Value</th>
															<th class="dataentry-denominatorName">Denominator</th>
															<th class="dataentry-denominatorValue">Value</th>
															<th class="dataentry-percentageValue">Percentage</th>
														</tr>
													</thead>
													<tbody>
														<tr ng-repeat="td in previewData">
															<td>{{$index+1}}</td>

															<td class="dataentry-indicatorName-td">{{td.indicatorName}}</td>

															<td class="dataentry-numeratorName-td">{{td.numeratorName}}</td>

															<td class="inputBox dataentry-numeratorValue-td"
																id="{{'numeratorOut' + $index}}"><input disabled
																ng-blur="validateInput(td, 'numeratorValue','denominatorValue', 'numeratorOut' + $index)"
																type='text' only-seven-digits class='tableInput'
																ng-model='td.numeratorValue'
																ng-keyup='changeIsEnabled(td)'
																ng-change="calculatePercentage(td)"></td>

															<td class="dataentry-denominatorName-td">{{td.denominatorName}}</td>

															<td class="dataentry-denominatorValue-td"
																class="inputBox" id="{{'denominatorOut' + $index}}">
																<input disabled
																ng-blur="validateInput(td,'denominatorValue', 'numeratorValue', 'denominatorOut' + $index)"
																type='text' only-seven-digits class='tableInput'
																ng-model='td.denominatorValue'
																ng-keyup='changeIsEnabled()'
																ng-change="calculatePercentage(td)" hide-zero>
															</td>

															<td class="dataentry-percentageValue-td" class="inputBox">
																<input type='text' readonly class='tableInput'
																ng-model='td.percentage'>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>
					<button class="submitSCPS errorOk" ng-click="redirectPreview()">Ok</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- end preview details modal -->
	<script type="text/javascript"
		src="resources/js/angularController/submissionManagemrntController.js"></script>
	<script type="text/javascript">
		var app = angular.module("submissionManagementApp", []);
		var myAppConstructor = angular.module("submissionManagementApp");
		myAppConstructor.controller("submissionManagementController",
				submissionManagementController);
		/* myAppConstructor.service('allServices', allServices); */
	</script>
	<script type="text/javascript"
		src="resources/js/angularDirective/directive.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	    $('[data-toggle="tooltip"]').tooltip(); 
	});
	</script>
</body>
<jsp:include page="fragments/footer.jsp"></jsp:include>
</html>