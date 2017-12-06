<!-- 
@author Swarna (swarnaprava@sdrc.co.in)
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="org.sdrc.scpstamilnadu.util.Constants"%>
<%@ page import="org.sdrc.scpstamilnadu.model.*"%>

<!DOCTYPE html>

<html>
<head>

<title>SCPS Tamilnadu-Dashboard</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Josefin+Sans"
	rel="stylesheet">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
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
</style>
<jsp:include page="fragments/header.jsp"></jsp:include>
<body ng-app="esamapp" ng-controller="DashboardController" ng-cloak>

	<div id="wrapper">
		<div id="containerId" class="content" style="margin-bottom: 50px">
			<div class="container">
				<div style="margin-top: 10px;">
					<serror:Error id="msgBox" errorList="${formError}"
						cssInfClass="${className}">
					</serror:Error>
				</div>
			</div>
			<div class="container dashboardbodyTop">
				<div class="sectors" data-html2canvas-ignore="true">
					<nav class="sector" role="navigation">
						<div class="thumb">
							<button class="navbar-toggle" data-toggsamikshaMaple="collapse"
								data-target="#navsector">
								<span class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<div class="sector_wrap">
								<ul class="sectorlists">
									<li ng-repeat="sector in parentSectors" class="sectorlist"><a
										ng-class="{active:selectedParentSector.value == sector.value}"
										ng-click="selectParentSector(sector)" href="#">
											{{sector.value}}</a></li>
								</ul>
							</div>
						</div>
					</nav>
					<div class="thumb_slide_ctrl">
						<a href="javascript:void(0)" class="left-arrow"><i
							class="fa fa-6 fa-caret-left" style="font-size: 2em"></i></a> <a
							href="javascript:void(0)" class="right-arrow"><i
							class="fa fa-6 fa-caret-right" style="font-size: 2em"></i></a>
					</div>
				</div>
				<h3 class="page-header evm-font-blue" data-html2canvas-ignore="true">Indicator</h3>
				<!-- 				<div id="nodata" -->
				<!-- 					ng-class="{'show': utdata.dataCollection.length == 0}" -->
				<!-- 					class="text-center nodata"> -->
				<!-- 					<h3> -->
				<!-- 						<i class="fa fa-info-circle"></i>Info -->
				<!-- 					</h3> -->
				<!-- 					<h5 class="text-left"> -->
				<!-- 						No data available for the selected indicator and time period.<br>Please -->
				<!-- 						modify your selection. -->
				<!-- 					</h5> -->
				<!-- 					<button type="button" class="btn btn-sm btn-primary" id="closepop">OK</button> -->
				<!-- 				</div> -->
				<!-- 				No data available pop up box for selected time period -->
				<!-- 				<div id="nodataForSelectedTime" -->
				<!-- 					ng-class="{'show': utdata.dataCollection.length == 0 }" -->
				<!-- 					class="text-center nodata"> -->
				<!-- 					<h3> -->
				<!-- 						<i class="fa fa-info-circle"></i>Info -->
				<!-- 					</h3> -->
				<!-- 					<h5 class="text-left"> -->
				<!-- 						No data available for the selected indicator for {{selectedTimeperiod.value}}.<br>Please -->
				<!-- 						select another time period. -->
				<!-- 					</h5> -->
				<!-- 					<button type="button" class="btn btn-sm btn-primary" id="closepop1">OK</button> -->
				<!-- 				</div> -->
				<div id="leftfilter" class="left-div col-sm-8">
					<section class="mar-bot-15" data-html2canvas-ignore="true">
						<!-- left indicator section   -->
						<div class="btn-toolbar" role="toolbar">
							<div class="input-group samikshya-filter">
								<input type="text" placeholder="Select Sector" readonly=""
									class="form-control" ng-model="selectedSector.value"></input>
								<div class="input-group-btn" style="position: relative;">
									<button data-toggle="dropdown" id="ind-list"
										class="btn btn-primary dropdown-toggle" type="button"
										ng-click="clearList()">
										<i class="fa fa-list"></i>
									</button>

									<ul class="dropdown-menu dashboard-menu" role="menu"
										aria-labelledby="ind-list" id="ind_drop">
										<div>
											<form class="navbar-form navbar-left" role="search">
												<input class="form-control" ng-model="query" type="text"
													placeholder="search" autofocus
													onclick="event.cancelBubble=true;" id="searchText">
												<button class="btn btn-primary">
													<i class="fa fa-lg fa-search "></i>
												</button>
											</form>
										</div>
										<li
											ng-repeat="sector in subSector | filter:query | orderBy:'name'"
											title="{{sector.value}}"><a
											ng-click="selectSector(sector)" href="#">
												{{sector.value}}</a></li>
									</ul>
								</div>

							</div>
						</div>
					</section>

					<section class="mar-bot-15" data-html2canvas-ignore="true">
						<!-- left indicator section   -->
						<div class="btn-toolbar" role="toolbar">
							<div class="input-group samikshya-filter">
								<input type="text" placeholder="Select indicator" readonly=""
									class="form-control" ng-model="selectedIndicator.value"></input>
								<div class="input-group-btn" style="position: relative;">
									<button data-toggle="dropdown" id="ind-list"
										class="btn btn-primary dropdown-toggle" type="button"
										ng-click="clearList()">
										<i class="fa fa-list"></i>
									</button>
									<ul class="dropdown-menu dashboard-menu" role="menu"
										aria-labelledby="ind-list" id="indicatorWidth">
										<div>
											<form class="navbar-form navbar-left" role="search">
												<input class="form-control" ng-model="query" type="text"
													placeholder="search" autofocus
													onclick="event.cancelBubble=true;" id="searchText">
												<button class="btn btn-primary">
													<i class="fa fa-lg fa-search "></i>
												</button>
											</form>
										</div>
										<li
											ng-repeat="indicator in indicators | filter:query | orderBy:'name'"
											title="{{indicator.value}}"><a
											ng-click="selectIndicator(indicator)" href="#">
												{{indicator.value}}</a></li>
									</ul>
								</div>

							</div>
						</div>
					</section>

					<!-- left indicator section   -->

					<section class="mar-bot-15" data-html2canvas-ignore="true">
						<div class="btn-toolbar" role="toolbar">
							<div class="input-group samikshya-filter">
								<input type="text" placeholder="" class="form-control"
									readonly="" ng-model="selectedTimeperiod.value"></input>
								<div class="input-group-btn">
									<button data-toggle="dropdown" id="tp-list"
										class="btn btn-primary dropdown-toggle pull-left"
										type="button">
										<i class="fa fa-lg fa-calendar timeperiodmenu"></i>
									</button>
									<ul class="dropdown-menu dashboard-menu" role="menu"
										aria-labelledby="tp-list" id="tp_drop">
										<li ng-repeat="timeformat in timeformats"><a
											ng-click="selectTimeperiod(timeformat)" href="#">
												{{timeformat.value}}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</section>

					<button id="backBtn" type="button"
						class="btn btn-default backbtn hidden mar-top-15"
						data-html2canvas-ignore="true">
						<i class="fa fa-lg fa-arrow-circle-o-left"></i>Back
					</button>
				</div>


				<section id="tbsection" class="topthree"
					ng-show="topPerformers.length > 0">
					<div class="mar-bot-5 topPerforme768">
						<h4 class="top">Top 3</h4>
						<ul class="topperformers">
							<li ng-repeat="performer in topPerformers"><span
								class="performerValue"> {{performer}}</span></li>
						</ul>
					</div>
					<div class="topPerforme768">
						<h4 class="bottom">Bottom 3</h4>
						<ul class="bottomperformers">
							<li ng-repeat="performer in bottomPerformers"><span
								class="performerValue"> {{performer}}</span></li>
						</ul>
					</div>
				</section>


				<div class="col-sm-3 pull-right" id="right_exportshare">
					<div class="exportshare-section">
						<button type="button" id="pdfDownloadBtn"
							class="btn pdfDownloadBtn" title="Download PDF"
							ng-click="sdrc_export()" ng-disabled="!mapData.length">
							<i class="fa fa-lg fa-download"></i> &nbsp; Download PDF
			 			</button>

						<section class="selection-desc">
							<br>
							<!-- 		<h3 class="top" data-html2canvas-ignore="true">{{selectedSector.value}}</h3> -->
							<p class="selectionPercent" data-html2canvas-ignore="true">{{selectedIndicator.value}}</p>
							<!-- 						<p>{{selectedIndicator.description}}</p> -->
						</section>
						<%if((UserModel) request.getSession().getAttribute(Constants.Web.USER_PRINCIPAL)!=null 
						&& ((UserModel) request.getSession().getAttribute(Constants.Web.USER_PRINCIPAL)).getRoleId()==8 && new Boolean(request.getAttribute("showPublish").toString())){ 
					      %>
					       
							<button 
							class="btn publishBtn" ng-click="publishDashboard()" title="Publish">
							<i class="fa fa-share-square" aria-hidden="true"></i>Publish</button>
						<% } %>
					</div>
					
				</div>

				<section>
					<div class="direction">
						<img class="img-responsive" alt="Responsive image"
							src="resources/images/north-pointer.png">
					</div>
				</section>


				<section id="legendsection" class="legends"
					ng-show="legends.length > 0" ng-hide="legends.length == 0">
					<h4>LEGEND</h4>
					<ul>
						<li ng-repeat="legend in legends" class="legend_list"><span
							class="legend_key">{{legend.key}}</span> <span
							class="{{legend.value}} legnedblock"> </span></li>
					</ul>
				</section>

				<!-- End of right buttons -->

				<div class="map_popover">
					<div class="map_popover_close"></div>
					<div class="map_popover_content"></div>
				</div>

				<!-- Map loading portion -->

				<div class="col-md-12 assam-map">
					<samiksha-map ng-style="style()" class="assamMap"></samiksha-map>
				</div>

				<!---- End of map loading portion -------->


				<div id="trendDiv" class="trend-viz animate-show"
					ng-animate=" 'animate' " ng-show="isTrendVisible">
					<button class="close" aria-hidden="true" type="button"
						ng-click="closeViz()">
						<span class="glyphicon glyphicon-remove-circle"></span>
					</button>

					<div class="container-fluid">
						<div class="row show-grid">
							<div class="col-xs-10 col-md-4 col-sm-3 left">
								<h3>{{selectedArea.properties.NAME1_}}</h3>
							</div>
							<div class="col-xs-8 col-md-4 col-sm-6 middle">
								<span>Rank:</span><span class="rank uptrend">
									{{selectedArea.properties.utdata.rank}}</span><span class="rank">
									<i class="of_txt">of</i>&nbsp; {{utdata.dataCollection.length}}
								</span>
							</div>
							<div class="col-xs-4 col-md-4 col-sm-12 right"
								ng-show="PCldata.percentageChange && ldata[0].length>1">
								<span>{{PCldata.percentageChange}}%</span>
								<i class="glyphicon glyphicon-arrow-up {{PCldata.cssClass}}"
									ng-show="PCldata.isUpward && PCldata.percentageChange != 0  && ldata[0].length>1"></i> 
									<i class="glyphicon glyphicon-arrow-down {{PCldata.cssClass}}"
									ng-hide="PCldata.isUpward || PCldata.percentageChange == 0 ||  ldata[0].length==1"></i>
									<i class="glyphicon glyphicon-resize-horizontal "
									ng-show="PCldata.percentageChange == 0 && ldata[0].length>1"></i>
									
							</div>
							<div class="pdfBtnforTrend" id="trendPdfButton">
								<button type="button" id="pdfDownloadBtnForIndex"
									class="col-md-2 btn btn-link exporttoxl pdfdownloadTrend"
									title="Download PDF for Trend Chart of selected District"
									ng-click="sdrc_export()" ng-disabled="$scope.utdata.length==0">
									<i class="fa fa-lg fa-download"></i> &nbsp;
								</button>
							</div>
						</div>
						<div class="line-separator"></div>
					</div>
					<div class="row">
						<div class="col-md-10 trend_colChart">
							<div class="col-sm-6 text-center"
								ng-repeat="data in ldata track by $index">
								<samiksha-line dataprovider="data"></samiksha-line>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div Style="height: 10px;"></div>
		</div>
	</div>

	<div id="infoMessage" class="modal fade" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-body text-center">
					<div class="infohead"><img alt="" src="resources/images/icons/Messages_info_icon.svg" style="width: 25px;margin-top: -5px;">&nbsp; INFO</div>
					<div class="warnbody">{{infoMsg}}</div>
					<button type="button" data-dismiss="modal" class="btn errorOk">Ok</button>
				</div>
			</div>
		</div>
	</div>

	<spring:url value="/webjars/jquery/2.0.3/jquery.min.js" var="jQuery" />
	<script src="${jQuery}"></script>
	<script type="text/javascript" src="resources/js/jquery-ui.js"></script>
	<spring:url value="/webjars/bootstrap/3.1.1/js/bootstrap.min.js"
		var="bootstrapjs" />
	<script src="${bootstrapjs}"></script>
	<script src="resources/js/angular.min.js"></script>
	<script src="resources/js/angular-animate.min.js"></script>
	<script src="resources/js/d3.min.js"></script>
	<script src="resources/js/sdrc.dashboard.js" type="text/javascript"></script>
	<script src="resources/js/angularController/dashboardctrl.js"></script>
	<script>
		window.addEventListener("orientationchange", function() {
			// Announce the new orientation number
			location.reload();
		}, false);
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			sdrc_export.export_pdf("", "pdfDownloadBtn");
			sdrc_export.export_pdfLine("", "pdfDownloadBtnForIndex");
		});
	</script>


</body>

<jsp:include page="fragments/footer.jsp" />
</html>