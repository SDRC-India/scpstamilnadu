<!-- 
@author Swarna (swarnaprava@sdrc.co.in)
-->
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="org.sdrc.scpstamilnadu.util.Constants"%>
<%@ page import="org.sdrc.scpstamilnadu.model.*"%>

<!DOCTYPE html>

<html>
<head>

<title>SCPS Tamilnadu-Index</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"> --%>
<!-- <link href="https://fonts.googleapis.com/css?family=Josefin+Sans" -->
<!-- 	rel="stylesheet"> -->
<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css"> --%>
<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/resources/css/customLoader.css"> --%>

<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/resources/css/style.css"> --%>
<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"> --%>
	
	
	<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Josefin+Sans"
	rel="stylesheet">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/indexCss.css">
<link rel="stylesheet" href="resources/css/style.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css">


<!--[if lte IE 12]>
  <link rel="stylesheet" type="text/css" media="screen, projection" href="resources/css/ie12-down.css" />
<![endif]-->

</head>
<style type="text/css">
.loginbtn
{
	display: block !important;
}
.navbar {
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 200;
}
</style>
<jsp:include page="fragments/header.jsp"></jsp:include>
<body ng-app="icpsIcpo" ng-controller="factSheetController" ng-cloak>

	<div class="container indextopmargin" style="padding-bottom: 20px;">
		<div class="row">
			<div class="col-md-12 page-header1">
				<h2 class="col-md-8 col-sm-7 col-xs-4 evm-font-blue text-left">
					Index <sup><a
						href="resources/pdf/Index Computation Methodology_TN.pdf"
						target="_blank"><i class="fa fa-question-circle"
							aria-hidden="true"></i></a></sup>
				</h2>
				<button class="col-md-2 btn btn-link text-right exportTableData"
					ng-click="exportTableData('sectorData')">
					<span class="glyphicon glyphicon-share"></span> Export to Excel
				</button>
			</div>

			<div class="indexTop">
				<div class="col-md-2 col-sm-4 col-xs-6">
					<div>
						<label class="labelFont">State :</label>
					</div>
					<!-- <select class="form-control" id="stateSelection"
					ng-options="state as state.area_Name for state in area | orderBy : 'area_Name'"
					ng-model="selectedState" ng-change="changeDistrictList()">
				</select> -->
					<div class="select-container text-center marginZero-in-index">
						<div class="input-group">
							<input type="text" placeholder="Select State" readonly
								ng-model="selectedState.area_Name">
							<div class="input-group-btn" style="position: relative;">
								<button data-toggle="dropdown"
									class="btn btn-color dropdown-toggle btnborder" type="button">
									<i class="fa fa-caret-down" aria-hidden="true"></i>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li ng-repeat="state in area | orderBy : 'area_Name'"
										ng-click="selectState(state);"><a href="">{{state.area_Name}}</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-4 col-xs-6 multiselect-div">
					<div>
						<label class="labelFont">District :</label>
					</div>
					<div class="sdrc-multiselect">
						<input type="text" class="selectedOptions" readonly
							ng-model="selectedDistrict.area_Name">
						<button data-toggle="dropdown" ng-click="showDistrictList()"
							id="distListBtn"
							class="btn btn-color dropdown-toggle btnborder districtbtndsn"
							type="button" style="box-shadow: 0px 0px 4px 2px #CCC;">
							<i class="fa fa-caret-down" id="distListBtnIcon"
								aria-hidden="true"></i>
						</button>
						<ul id="districtListdropdown" class="optionsContainer"
							ng-if="showDistrictListFlag" role="menu">
							<li class="dropdown optionList"
								ng-repeat="dist in districtList | orderBy : 'area_Name'"><label><input
									type="checkbox" ng-checked="dist.selected"
									ng-change="countSelectedDistricts(dist)"
									ng-model="dist.selected"> {{dist.area_Name}}</label></li>
						</ul>
					</div>
				</div>
				<div class="col-md-2 col-sm-4 col-xs-6 timePeriodField">
					<div class="labelTimeperiod">
						<label class="labelFont">Timeperiod :</label>
					</div>
					<div class="select-container text-center marginZero-in-index">
						<div class="input-group timeperiodWidth">
							<input type="text" placeholder="Select Timeperiod" readonly=""
								ng-model="selectedTimeperiod.timePeriod">
							<div class="input-group-btn" style="position: relative;">
								<button data-toggle="dropdown"
									class="btn btn-color dropdown-toggle btnborder" type="button">
									<i class="fa fa-calendar" aria-hidden="true"></i>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li ng-repeat="tp in timePeriodList"
										ng-click="selectTimeperiod(tp);"><a href="">{{tp.timePeriod}}</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-4 submitButton">
					<button type="button"
						class="btn btn-info btnborder btnClr submitBtn submitbuttonIndex"
						ng-click="changeData()">Submit</button>
				</div>

				<div class="col-md-4 col-sm-12 col-xs-12 legendText">
					<div class="col-md-12 col-xs-12 col-sm-5 legendValueView">
						<div class="col-md-1 col-xs-1 legendAttr fourthslices"
							style="padding: 0 !important;"></div>
						<div class="col-md-5 col-xs-5 legendQuality">(0.81-1.00)</div>
						<div class="col-md-1 col-xs-1 legendAttr thirdslices"
							style="padding: 0 !important;"></div>
						<div class="col-md-4 col-xs-5 legendQuality legendQualityValue">(0.61-0.80)</div>
					</div>
					<div class="col-md-12 col-xs-12 col-sm-5 legendValueView">
						<div class="col-md-1 col-xs-1 legendAttr secondslices"
							style="padding: 0 !important;"></div>
						<div class="col-md-5 col-xs-5 legendQuality">(0.41-0.60)</div>
						<div class="col-md-1 col-xs-1 legendAttr sixthslices"
							style="padding: 0 !important;"></div>
						<div class="col-md-4 col-xs-5 legendQuality legendQualityValue">(0.21-0.40)</div>
					</div>
					<div class="col-md-12 col-xs-12 col-sm-5 legendValueView">
						<div class="col-md-1 col-xs-1 legendAttr firstslices"
							style="padding: 0 !important;"></div>
						<div class="col-md-5 col-xs-5 legendQuality">(0.00-0.20)</div>
						<div class="col-md-1 col-xs-1 legendAttr grey"
							style="padding: 0 !important;"></div>
						<div class="col-md-4 col-xs-5 legendQuality legendQualityValue">Not
							Available</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container districtTableWidth">
		<div class="row">
			<!-- Table start -->
			<!-- 			<div class="col-md-12 sectorTable"> -->
			<div
				class="tableMargin table-responsive dist-level-table header-fixed-table table-header-fixed"
				style="width: 100%; max-height: 450px; overflow: auto; border: 1px solid rgb(204, 204, 204);"
				sdrc-table-header-fix tableuniqueclass="'dist-level-table'"
							tabledata="factSheetData">
				<table
					class="table table-bordered table-striped table-hover table-fixed fixed_headers"
					id="sectorData">
					<thead>
						<tr>
							<th class="text-center labelFont thdiswidth">Districts</th>
							<th class="text-center labelFont thdiswidth"
								ng-repeat="sector in indexList | orderBy : ic_Name">{{sector.ic_Name}}</th>
							<!-- <th class="text-center" style="width: 230px;">Average</th> -->
						</tr>
					</thead>

					<tbody>
						<tr
							ng-repeat="data in factSheetData | orderBy : 'districtInfo.value'">
							<td class="text-right">{{data.districtInfo.value}}</td>

							<td class="text-center"><div class="circleAttr"
									ng-class="data.sectorData.ResultsforChildren > 0.80 ? 'fourthslices' : data.sectorData.ResultsforChildren > 0.60 ? 'thirdslices' : 
								data.sectorData.ResultsforChildren > 0.40 ? 'secondslices' : data.sectorData.ResultsforChildren > 0.20 ? 'sixthslices':
								data.sectorData.ResultsforChildren > -1 ? 'firstslices' : 'grey' "
									style="color: transparent;">{{data.sectorData.ResultsforChildren}}</div>
							</td>

							<td class="text-center"><div class="circleAttr"
									ng-class="data.sectorData.HumanResource > 0.80 ? 'fourthslices' : data.sectorData.HumanResource > 0.60 ? 'thirdslices' : 
								data.sectorData.HumanResource > 0.40 ? 'secondslices' : data.sectorData.HumanResource > 0.20 ? 'sixthslices':
								data.sectorData.HumanResource > -1 ? 'firstslices' : 'grey' "
									style="color: transparent;">{{data.sectorData.HumanResource}}</div>
							</td>
							<td class="text-center"><div class="circleAttr"
									ng-class="data.sectorData.ICPSStructureandFunctionality > 0.80 ? 'fourthslices' : data.sectorData.ICPSStructureandFunctionality > 0.60 ? 'thirdslices' : 
								data.sectorData.ICPSStructureandFunctionality > 0.40 ? 'secondslices' : data.sectorData.ICPSStructureandFunctionality > 0.20 ? 'sixthslices':
								data.sectorData.ICPSStructureandFunctionality > -1 ? 'firstslices' : 'grey' "
									style="color: transparent;">{{data.sectorData.ICPSStructureandFunctionality}}</div>
							</td>
							<td class="text-center"><div class="circleAttr"
									ng-class="data.sectorData.Overall > 0.80 ? 'fourthslices' : data.sectorData.Overall > 0.60 ? 'thirdslices' : 
								data.sectorData.Overall > 0.40 ? 'secondslices' : data.sectorData.Overall > 0.20 ? 'sixthslices':
								data.sectorData.Overall > -1 ? 'firstslices' : 'grey' "
									style="color: transparent;">{{data.sectorData.Overall}}</div></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Table end -->
		</div>
	</div>

	<div class="container paddingTop34">
		<div class="row">
			<div class="form-group">
				<div class="col-md-12 col-xs-12 factheading">
					<label class="col-md-2 col-xs-4 labelFont areaofTrackwidth">Areas
						of Tracking : </label> <select class="col-md-3 col-xs-4"
						id="sectorSelection"
						ng-options="sector as sector.ic_Name for sector in sectorList | orderBy : 'ic_Name'"
						ng-model="selectedSector" ng-change="checkData(selectedSector)">
					</select>

					<div
						class="col-md-2 col-sm-4 col-xs-6 multiselect-div subSectorsMultiselect"
						style="display: none;">

						<div class="sdrc-multiselect">
							<input type="text" class="selectedOptions" readonly
								ng-model="selectedDistrict.area_Name">
							<button data-toggle="dropdown" ng-click="showDistrictList()"
								id="distListBtn" class="btn btn-color dropdown-toggle btnborder"
								type="button">
								<i class="fa fa-caret-down" aria-hidden="true"></i>
							</button>
							<ul id="districtListdropdown" class="optionsContainer"
								ng-if="showDistrictListFlag" role="menu">
								<!-- <li class="optionList">
								<label>
									<input type="checkbox" ng-model="checkedAll" ng-change="selectAll()"> All Districts 
								</label>
							</li> -->
								<li class="dropdown optionList"
									ng-repeat="dist in districtList | orderBy : 'area_Name'"><label><input
										type="checkbox" ng-checked="dist.selected"
										ng-change="countSelectedDistricts(dist)"
										ng-model="dist.selected"> {{dist.area_Name}}</label></li>
							</ul>
						</div>
					</div>
					<button
						class="col-md-4 col-xs-4 btn btn-link text-right exportTableData"
						ng-click="exportTableData(id)">
						<span class="glyphicon glyphicon-share"></span> Export to Excel
					</button>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12 fixedHeader">
				<div
					class="tableMargin table-responsive area-level-table header-fixed-table"
					style="width: 100%; max-height: 450px; overflow: auto; border: 1px solid rgb(204, 204, 204);"
					sdrc-table-header-fix tableuniqueclass="'area-level-table'"
					tabledata="subSectorData">
					<table
						class="table table-bordered table table-striped fixed-headers2"
						id="cc">
						<!-- sectorList. ordering all rows with indicator name -->
						<thead>
							<tr>
								<th class="text-center thWidth"
									style="font-size: 14px; font-weight: bold; width: 20%; vertical-align: middle; background-color: #fff !important; color: #333;"
									rowspan="2">Districts</th>

								<th class="text-center thWidth1"
									colspan="{{sector.children.length}}" 
									ng-repeat="sector in selectedSector.children | orderBy : 'ic_Name' "
									style="font-size: 14px; font-weight: bold; padding: 5px 0 0 0; background-color: #fff !important; color: #333;">
									{{sector.ic_Name}}</th>
							</tr>
							<tr>
								<th class="thwidth2"
									ng-repeat="key in Keys | orderBy : 'sorting'"
									style="vertical-align: bottom; background-color: #fff !important; color: #333;"padding-bottom: 0; position:relative;">
									{{key.indicator}}</th>
							</tr>
						</thead>

						<tbody id="childCareData" style="overflow: auto;">
							<tr
								ng-repeat="data in subSectorData | orderBy : 'districtInfo.value'">
								<td class="text-right">{{data.districtInfo.value}}</td>
								<td class="text-center  cellWidth"
									ng-repeat="key in Keys | orderBy : 'sorting'"
									title="{{tooltipTitle(key)}}">
									{{data.sectorData[key.keyValue]}}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="container" style="padding-bottom: 100px;"></div>
	
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

	<spring:url value="/webjars/jquery/2.0.3/jquery.min.js" var="jQuery" />
	<script src="${jQuery}"></script>
	<!-- 	<script type="text/javascript" src="resources/js/jquery-ui.js"></script> -->
	<%-- 	<spring:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js" --%>
	<%-- 		var="bootstrapjs" /> --%>
	<script
		src="${pageContext.request.contextPath}/webjars/jquery/2.0.3/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/webjars/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/angular-animate.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/d3.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/sdrc.dashboard.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/angularController/indexController.js"></script>

	<script>
		window.addEventListener("orientationchange", function() {
			// Announce the new orientation number
			location.reload();
		}, false);
	</script>
	<script>
		var ignoreTableBodyScroll = false;
		var ignoreTableHeadScroll = false;
		$(".table-header-fixed").scroll(
				function() {
					if (!ignoreTableBodyScroll) {
						ignoreTableHeadScroll = true;
						$(".static-header").scrollLeft(
								$(".table-header-fixed").scrollLeft());
					}
					ignoreTableBodyScroll = false;
				});
		$(".static-header").scroll(
				function() {
					if (!ignoreTableHeadScroll) {
						ignoreTableBodyScroll = true;
						$(".table-header-fixed").scrollLeft(
								$(".static-header").scrollLeft());

					}
					ignoreTableHeadScroll = false;
				})
	</script>
	<script type="text/javascript">
		$(':not(#distListBtn)')
				.click(
						function(e) {
							if (e.target.id != 'districtListdropdown'
									&& $(e.target).closest(
											'#districtListdropdown').length == 0
									&& $(e.target)[0].id != 'distListBtn'
									&& $(e.target)[0].id != 'distListBtnIcon') {
								//   if($(e.target).closest('#districtListdropdown').length != 0) return false;
								/* alert("clicked") */
								angular.element("body").scope().showDistrictListFlag = false;
								angular.element("body").scope().$apply();
							}
							/* else{
							  alert("clicked");
							} */
						});
	</script>
</body>
<jsp:include page="fragments/footer.jsp" />
</html>