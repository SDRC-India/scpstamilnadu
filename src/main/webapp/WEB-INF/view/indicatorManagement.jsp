<!-- 
@author Swarna (swarnaprava@sdrc.co.in)
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html ng-app="indicatorManagementApp">
<head>

<title>SCPS Tamilnadu-Indicator Management</title>
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
<body ng-controller="indicatorManagementController" class="xoverflowHidden" ng-cloak>
	<div id="mymain" class="container-fluid">
		<div class="pageNameContainerIndicator">
			<h3 class="pagenameIndicator">{{pageName}}</h3>
		</div>
		<div class="col-md-12 mandatoryfields">
			<h5>All <span class="mandatory_star fontsizeofStar">&#42;</span>  fields are mandatory</h5>
		</div>	

<!-- 		<form class="addingIndicator" name="selctionForm"> -->
			<div class="col-md-12 col-sm-12 col-xs-12 addingIndicatorbar">
				<div class="col-md-6 col-sm-5 col-xs-12 summaryAllbox ipadLandscpaeView">
					<label class="col-md-2 text-right labelFont sectorleft">Sector <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-6 col-xs-6 select-container text-center sectorboxinTabletView sectrboxinIpadview">
							<div class="input-group report-width">
								<input type="text" placeholder="Select Sector" id="sector"
									class="form-control not-visible-input bgClr" name="sector"
									readonly ng-model="selectedSector.sectorName">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu" style="max-height: 136px;">
										<li 
											ng-repeat="sector in sector"
											title="{{sector.sectorName}}"><a
											ng-click="selectSector(sector)">
											{{sector.sectorName}}</a></li>
									</ul>
								</div>
							</div>
						</div>
				</div>
				
				<div class="col-md-6 col-sm-5 col-xs-12 summaryAllbox subsectorleft">
					<label class="col-md-3 text-right labelFont labelSubsctIntablet">Subsector <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-6 col-xs-6 select-container text-center subsectorboxinTablt sectrboxinIpadview">
							<div class="input-group report-width reportgrpSubsectrWidthtablet">
								<input type="text" placeholder="Select Subsector" id="subsector"
									class="form-control not-visible-input bgClr" name="subsector"
									readonly ng-model="selectedSubSector.sectorName">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu" style="max-height: 136px;">
										<li 
											ng-repeat="subsector in subSector"
											title="{{subsector.sectorName}}"><a
											ng-click="selectSubSector(subsector)">
											{{subsector.sectorName}}</a></li>
									</ul>
								</div>
							</div>
						</div>
				</div>
				<!-- indicator start -->
				<!-- enter indicator manually part   -->
	<div class="col-md-12 indicatorBoxMarginTop">
	<div class="line-separator margin-topIpad"></div>
		<div class="col-md-8 indicatorTop">
			<div class="col-md-12 col-sm-12 col-xs-12 summaryAllbox lineseparatorBottom tabletMarginleft" >
					<div class="col-md-12 col-sm-6 col-xs-6 tabletViewlandscpaeindctr">
					<label class="col-md-3 text-right labelFont inicatorPadding indicatorboxwidthipad">Indicator Name <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-6 col-xs-10 select-container text-center manuallyindicatorPaddingLeft">
							<div class="input-group indicator-width">
								<input type="text" placeholder="Enter Indicator Name" id="indicator" ng-disabled="!selectedSubSector.sectorName"
									class="form-control not-visible-input bgClr" name="indicator"
									ng-model="indicatorMgmt.indicatorName" ng-keyup="clearText(isSelected, indicatorMgmt.indicatorName)">
								
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 summaryAllbox numeratorboxIn-mobile tabletMarginleft">
					<div class="col-md-12 col-sm-6 col-xs-6 numeratorBoxLabel-mobile">
					<label class="col-md-3 text-right labelFont inicatorPadding numeratorboxwidthipad">Numerator Name <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-6 col-xs-10 select-container text-center manuallyindicatorPaddingLeft">
							<div class="input-group indicator-width numeratorwidth-mobile">
								<input type="text" placeholder="Enter Numerator Name" id="numerator" ng-disabled="!selectedSubSector.sectorName"
									class="form-control not-visible-input bgClr" name="numerator" maxlength="150"
									ng-model="indicatorMgmt.numeratorName">
								
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12 summaryAllbox denominatorbox-mobile tabletMarginleft">
					<div class="col-md-12 col-sm-6 col-xs-6 denominatorBoxlebl-mobile">
					<label class="col-md-3 col-xs-12 text-right labelFont denominatorPadding">Denominator Name <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-6 col-xs-10 select-container text-center denominatorwidthinMob manuallyindicatorPaddingLeft ">
							<div class="input-group indicator-width denominatorwidth-mobile">
								<input type="text" placeholder="Enter Denominator Name" id="denominator" ng-disabled="!selectedSubSector.sectorName"
									class="form-control not-visible-input bgClr" name="denominator" maxlength="150"
									ng-model="indicatorMgmt.denominatorName">
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="col-md-4 col-sm-12 col-xs-12 summaryAllbox metadataMargin">
					<div class="col-md-12 col-sm-6 col-xs-6 metadata-mobile">
					<label class="col-md-12 labelFont margintopofMetaData">Meta Data :</label>
						<div class="col-md-8 select-container text-center metadataText-width">
							<div class="input-group indicator-width metadataWidth">
								<textarea rows="6" type="text" placeholder="Enter Meta Data" id="meta-data" maxlength="400"
									class="form-control not-visible-input bgClr" name="meta-data" ng-disabled="!selectedSubSector.sectorName"
									ng-model="indicatorMgmt.metadata"></textarea>
							</div>
						</div>
					</div>
					<div style="float: right; margin-right: 5px;" ng-show="selectedSubSector.sectorName">
						{{metadataLimit-indicatorMgmt.metadata.length}} characters left
					</div>
				</div>
				</div>
				
				
				<div class="col-md-12 col-sm-12 col-xs-12 summaryAllbox margin-topAllinone indesktopView">
					<div class="col-md-4 col-sm-4 col-xs-6 newindicatorSection paddingZero subgroupMargin">
					<label class="col-md-6 text-right labelFont subgrouppadding">Subgroup <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-5 select-container text-center subgrouplistPadding">
							<div class="input-group widthSubgroup paddingZero">
								<input type="text" placeholder="Select Subgroup" id="subgroup"
									class="form-control not-visible-input bgClr" name="subgroup"
									readonly ng-model="selectedSubgroup.subgroupName">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu" style="max-height: 136px;">
										<li 
											ng-repeat="subgroup in subgroups"
											title="{{subgroup.subgroupName}}"><a
											ng-click="selectSubgroup(subgroup)">
											{{subgroup.subgroupName}}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-6 newindicatorSection paddingZero unitLeftMarginIpad">
					<label class="col-md-4 text-right labelFont unitLabelinIpadportrait">Unit <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-5 select-container text-center">
							<div class="input-group widthSubgroup paddingZero">
								<input type="text" placeholder="Select Unit" id="unit"
									class="form-control not-visible-input bgClr" name="unit"
									readonly ng-model="selectedUnit.unitName">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown" id="unit-id"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li 
											ng-repeat="unit in units"
											title="{{unit.unitName}}"><a
											ng-click="selectUnit(unit)">
											{{unit.unitName}}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-6 newindicatorSection paddingZero highisgoodMargin">
					<label class="col-md-5 text-right labelFont ipadlandscpaeViewhighzgd">High is good <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-5 select-container text-center">
							<div class="input-group widthSubgroup paddingZero">
								<input type="text" placeholder="Select High Is Good" id="highisGood"
									class="form-control not-visible-input bgClr" name="highisGood"
									readonly ng-model="selectedhighisGood.label">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown" id="high-is-good-id"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu">
										<li 
											ng-repeat="highisGood in highIsGood"
											title="{highisGood.label}}"><a
											ng-click="selectHighisgood(highisGood)">
											{{highisGood.label}}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				
			<!-- role and publish date part -->
			
			
			<div class="col-md-12 col-sm-12 col-xs-12 summaryAllbox margin-topAllinone">
			<div class="line-separator margintopin-mobile"></div>
					<div class="col-md-4 col-sm-4 col-xs-6 newindicatorSection paddingZero subgroupMargin">
					<label class="col-md-6 text-right labelFont subgrouppadding">Role <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-5 select-container text-center subgrouplistPadding">
							<div class="input-group widthSubgroup paddingZero">
								<input type="text" placeholder="Select Role" id="role"
									class="form-control not-visible-input bgClr" name="role"
									readonly ng-model="selectedRole.roleName">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown" id="role-id"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu" style="max-height: 136px;">
										<li 
											ng-repeat="role in roles | filter:query | orderBy:'name'"
											title="{{role.roleName}}"><a
											ng-click="selectRole(role)">
											{{role.roleName}}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-6 newindicatorSection paddingZero unitLeftMarginIpad">
					<label class="col-md-4 text-right labelFont unitLabelinIpadportrait publishMonthlabel">Publish Month <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-5 select-container text-center">
							<div class="input-group widthSubgroup paddingZero">
								<input type="text" placeholder="Select Month" id="p-month"
									class="form-control not-visible-input bgClr" name="p-month"
									readonly ng-model="selectedMonth">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu" role="menu" style="max-height: 136px">
										<li 
											ng-repeat="monthYear in monthYears"
											title="{{monthYear.monthName}}"><a
											ng-click="selectMonth(monthYear)">
											{{monthYear.monthName}}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-6 newindicatorSection paddingZero highisgoodMargin">
					<label class="col-md-5 text-right labelFont ipadlandscpaeViewhighzgd">Year <span class="mandatory_star">&#42;</span></label>
						<div class=" col-md-5 select-container text-center">
							<div class="input-group widthSubgroup paddingZero">
								<input type="text" id="year"
									class="form-control not-visible-input bgClr" name="year"
									readonly ng-model="selectedYear">
								<div class="input-group-btn btnBgColor"
									style="position: relative;">
									<button data-toggle="dropdown"
										class="btn btn-color dropdown-toggle btnBgColor" type="button">
										<i class="fa fa-list"></i>
									</button>
									
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- end role and publish date part -->
				<div class="text-center">
							<button class="submitIndicator" ng-disabled="isDisabled"
								ng-click="submitForm()">SUBMIT</button>
						</div>
				</div>
<!-- 			</form> -->
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
	
	<div id="alreadyAdded" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="warnhead"><img alt="" src="resources/images/icons/Messages_warning_caution_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; WARNING</div>
					<div class="warnbody">Already Added</div>
					<button type="button" class="btn errorOk" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="errorMessage" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="errorhead"><img alt="" src="resources/images/icons/Messages_warning_caution_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; ERROR</div>
					<div class="errorbody"><b>{{validationMsg}}</b></div>
					<button type="button" class="btn errorOk" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<div id="warningMessage" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="errorhead"><img alt="" src="resources/images/icons/Messages_warning_caution_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; ERROR</div>
					<div class="errorbody"><b>This indicator has already been added</b></div>
					<button type="button" class="btn errorOk" data-dismiss="modal" ng-click="clrIndicator()">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<script type="text/javascript"
		src="resources/js/angularController/indicatorManagementController.js"></script>
	<script type="text/javascript">
		var app = angular.module("indicatorManagementApp", []);
		var myAppConstructor = angular.module("indicatorManagementApp");
		myAppConstructor.controller("indicatorManagementController",
				indicatorManagementController);
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