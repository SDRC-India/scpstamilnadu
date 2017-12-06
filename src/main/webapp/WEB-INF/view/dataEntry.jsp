<!-- 
@author Swarna (swarnaprava@sdrc.co.in)
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="dataEntryApp">
<head>

<title>SCPS Tamilnadu-DataEntry</title>
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
<jsp:include page="fragments/header.jsp"></jsp:include>
<body ng-controller="dataEntryController" class="xoverflowHidden" ng-cloak>
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
	<div id="mymain" class="container-fluid">
		<div class="col-md-12">
			<div class="col-md-5 facilityDetails">
				<ul class="infodetails">
					<li><h4><b>Institution :</b> {{locationDetails}}</h4></li>
				</ul>
			</div>

			<div class="col-md-7">
				<h3 class="pageNameContainer dataentry-pagename">{{pageName}}</h3>
			</div>
		</div>


		<div class="monthSelection">
			<div class="select-container text-center">
				<div class="input-group timeperiodWithoutBoxshdw" style="margin: auto;">
					<input type="text" placeholder="Month *" id="month"
						class="form-control not-visible-input hiding-cursor" name="month" readonly=""
						ng-model="timePeriod">
				</div>
			</div>
<!-- 			<div class="text-center alreadySubmitted" ng-class="allIndicatorsModel.cssClass"  -->
<!-- 			ng-show="allIndicatorsModel.statusMessage != null">{{alreadySubmitted}}</div> -->
			<div class="submit-remark" ng-if="superRemark">{{superRemark}}</div>
		</div>
		
		
				
		<section class="profile-section col-md-12 col-sm-12 col-xs-12">
		<div class="profile-entry">
		<div id="profileTable" class="accordion-content expanded">
		<div class='content'>
					<div class=" table-responsive">
					<div class="col-md-12 mandatoryfieldsData">
								<h5>All fields are mandatory</h5>
							</div>
							<table items="tableData" show-filter="true" cellpadding="0"
							cellspacing="0" border="0"
							class="dataTable table table-bordered scps" id="dataTable">
							<thead>
								<tr>
									<th class="dataentry-serial">Sl.&nbsp;No.</th>
									<th class="dataentry-indicatorName">Indicator Name</th>
									<th class="dataentry-numeratorName">Numerator</th>
									<th class="dataentry-numeratorValue" >Value</th>
									<th class="dataentry-denominatorName">Denominator</th>
									<th class="dataentry-denominatorValue">Value</th>
									<th class="dataentry-percentageValue">Percentage</th>
								</tr>
							</thead>
							<tbody >
									<tr ng-repeat="td in tableData">
										<td>{{$index+1}}</td>

										<td class="dataentry-indicatorName-td">{{td.indicatorName}}</td>

										<td class="dataentry-numeratorName-td">{{td.numeratorName}}</td>

										<td class="inputBox dataentry-numeratorValue-td"  id="{{'numeratorOut' + $index}}">
											<input
											ng-blur="validateInput(td, 'numeratorValue','denominatorValue', 'numeratorOut' + $index)"
											type='text' only-seven-digits class='tableInput'
											ng-disabled="!disableDataEntry" ng-model='td.numeratorValue'
											ng-keyup='changeIsEnabled(td)'
											ng-change="calculatePercentage(td)"></td>

										<td class="dataentry-denominatorName-td">{{td.denominatorName}}</td>

										<td class="dataentry-denominatorValue-td"
											class="inputBox" id="{{'denominatorOut' + $index}}">
											<input
											ng-blur="validateInput(td,'denominatorValue', 'numeratorValue', 'denominatorOut' + $index)"
											type='text' only-seven-digits class='tableInput' id="denominator_id"
											ng-disabled="!disableDataEntry"
											ng-model='td.denominatorValue' ng-keyup='changeIsEnabled()'
											ng-change="calculatePercentage(td)"></td>

										<td class="dataentry-percentageValue-td" class="inputBox">
											<input tabindex="-1"
											type='text' readonly class='tableInput'
											ng-model='td.percentage'></td>
									</tr>
								</tbody>
						</table>
						<div class="text-center" id="showsubmitdiv">
							<button class="submitSCPS" ng-disabled="!disableDataEntry" 
								ng-click="submitForm()">SUBMIT</button>
						</div>
						<div class="text-center" id="showeditdiv" >
							<button class="submitSCPS" ng-disabled="!disableDataEntry"
								ng-click="updateForm()">UPDATE</button>
						</div>
					</div>
				</div>
				</div>
				</div>

		</section>
		
	</div>
	<!-- popup modal -->
	
	<!-- Modal for confirm adding indicator -->
	
	<!-- Modal for error message -->
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
	<!--end of thematic and chklist  -->
	<!-- Modal for warning message -->
	<div id="warningMessage" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="warnhead"><img alt="" src="resources/images/icons/Messages_warning_caution_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; WARNING</div>
					<div class="warnbody">{{warningMsg}}</div>
					<button type="button" class="btn errorOk" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<div id="infoMessage" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="infohead"><img alt="" src="resources/images/icons/Messages_info_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; INFO</div>
					<div class="warnbody">{{infoMsg}}</div>
					<button type="button" class="btn errorOk" ng-click="redirect()">Ok</button>
				</div>
			</div>
		</div>
	</div>
	
	<div id="updateinfoMessage" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="infohead"><img alt="" src="resources/images/icons/Messages_info_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; INFO</div>
					<div class="warnbody">Data updated successfully!</div>
					<button type="button" class="btn errorOk" ng-click="redirect()">Ok</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modal for warning message -->
	
	<!-- Modal for warning message -->
	<div id="confirmSaveModal" class="confrirmation-modal modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="infohead"><img alt="" src="resources/images/icons/Messages_warning_caution_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; INFO</div>
					<div class="warnbody">{{warningMsg}}</div>
					<button type="button" class="btn errorOk" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn errorOk" data-dismiss="modal" ng-click="saveProfileForm()">Confirm</button>
				</div>
			</div>
		</div>
	</div>
	
	<div id="confirmModifyProfileModal" class="confrirmation-modal modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="infohead"><img alt="" src="resources/images/icons/Messages_warning_caution_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; INFO</div>
					<div class="warnbody">{{warningMsg}}</div>
					<button type="button" class="btn errorOk" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn errorOk" data-dismiss="modal" ng-click="resetFormTable();saveProfileForm()">Confirm</button>
				</div>
			</div>
		</div>
	</div>
	<!-- popup modal -->
	<div id="pop" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="successhead"><img alt="" src="resources/images/icons/Messages_success_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; SUCCESS</div>
					<div class="successbody">{{msg}}</div>
					<a class="btn btn-default" ng-href="{{link}}">Ok</a>
				</div>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript"
		src="resources/js/angularController/dataEntryController.js"></script>
	<script type="text/javascript">
		var app = angular.module("dataEntryApp", []);
		var myAppConstructor = angular.module("dataEntryApp");
		myAppConstructor.controller("dataEntryController",
				dataEntryController);
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