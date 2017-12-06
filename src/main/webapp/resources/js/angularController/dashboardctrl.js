/****
@author Swarna (swarnaprava@sdrc.co.in) tamilnadu
*****/

function ValueObject(key, value) {
	this.key = key;
	this.value = value;
}
app.controller('DashboardController', function($scope, $http, $window, $timeout,$filter) {
	$("#loader-mask").show();
	var w = angular.element($window);
	$scope.getWindowDimensions = function() {
		return {
			"h" : w.height(),
			"w" : w.width()
		};
	};
	// this is to make sure that scope gets changes as window get resized.
	w.on("resize", function() {
		$scope.$apply();
	});
	// Scope properties
	// $scope.indicators = [ new ValueObject("1", "Select Indicator") ];---
	$scope.indicators = [];
	$scope.timeformats = [];
	$scope.sectors = [];// [ new ValueObject("1", "Basic Info") ];
	$scope.utdata = [];
	$scope.legends = [];
	$scope.topPerformers = [];
	$scope.bottomPerformers = [];
	$scope.sources = [];
	$scope.ldata = [];
	$scope.PCldata = "";
	$scope.cldata = [];
	// select the first user of the list
	$scope.selectedMapAreaType = "District";
	$scope.selectedTimeperiod = $scope.timeformats[0];
	$scope.selectedSector = $scope.sectors[0];
	$scope.selectedIndicator = $scope.indicators[0];
	$scope.selectedSource = $scope.sources[0];
	$scope.selectedGranularity =null; //= new ValueObject("IND033", "TamilNadu");
	$scope.selectedChildAreaLevel = 3;
	$scope.isTrendVisible = true;
	$scope.selectedArea = [];
	$scope.show = false;
	$scope.shouldDrilldown = true;
	$scope.disablePdf = false;
	$scope.shoulddisappear=true;
	$scope.isColumnVisible = false;
	$scope.isLineVisible = false;
	$scope.primary_url = "";
	$scope.query = "";
	$scope.mapUrl = "";
	// Scope methods
	// expose a callable method for sectors
	
	document.getElementById('attachAgencyIdInDashboard').href = "dashboard";
	
	$scope.selectParentSector=function(parentSector)
	{
		$scope.selectedParentSector=parentSector;
		$scope.subSector=$filter('filter')($scope.sectors, {description:$scope.selectedParentSector.key},true);
		$scope.selectSector($scope.subSector[0]);
	}

	
	$scope.initMap = function() {
		$http.get("findMapUrl")
		.success(function(data) {
			var areaCode = data.areaCode;
			var jsonFileName = data.mapJsonName;
			$scope.selectedGranularity = new ValueObject(areaCode, jsonFileName);
			$scope.mapUrl = data.url;
		}
		
		)
		.error(function(data){
			console.log(data);
		})
	};
	
	$scope.selectSector = function(sector)
	{
		$("#loader-mask").show();
		console.log("sector called")
		$scope.selectedSector = sector;
		$scope.getindicators();
	};
	
	// expose a callable method to the view
	$scope.selectIndicator = function(indicator) 
	{
		$("#loader-mask").show();
		$scope.selectedIndicator = indicator;
		$scope.initMap();
		$scope.getsources();
	};
	
	// expose a callable method to the source
	$scope.selectSource = function(source) 
	{
		$("#loader-mask").show();
		$scope.selectedSource = source;
		$scope.gettimeperiods();
	};
	
	// expose a callable method for time period
	$scope.selectTimeperiod = function(timeformat) {
		$("#loader-mask").show();
		$scope.selectedTimeperiod = timeformat;
		$scope.getutdata();
	};
	
	$scope.clearList = function()
	{
		$("#searchText")[0].value = "";
		$scope.query = "";
//		$scope.$apply();
	};
	
	// expose a callable method Map areaType
	$scope.selectMapAreaType = function(mapAreaType, sg, sl, sd)
	{
		$scope.closeViz();
		$scope.selectedMapAreaType = mapAreaType;

		$(".backbtn").toggleClass("hidden", true);

		$scope.primary_url = $scope.mapUrl;

		if (sg && sl) {

			$scope.selectedChildAreaLevel = sl;
			$scope.shouldDrilldown = sd;
			$scope.selectedGranularity = sg;
			$scope.primary_url = "resources/geomaps/"
					+ $scope.selectedGranularity.value + ".json";
			console.log($scope.primary_url);
		}
		$scope.mapSetup($scope.primary_url, function() {
			$("#loader-mask").show();
			$scope.getutdata();
		});
	};
	$scope.showViz = function(areacode) {
		
		if (areacode && $scope.selectedArea != areacode) {
			$("#loader-mask").show();
			document.getElementsByClassName("trend-viz animate-show")[0].style.display = "none";
			setTimeout(function () {
			      $("#loader-mask").fadeOut();
			   }, 2000);
			$scope.isTrendVisible = true;
			document.getElementsByClassName("trend-viz animate-show")[0].style.display = "block";
			// visualization in angular context. 
			$scope.selectedArea = areacode;
			$scope.cldata = [];
			$scope.ldata = [];
			$scope.PCldata ="";
			if($scope.selectedArea.properties.utdata != null){
			if( $scope.timeformats.length >1){
				$scope.lineChartValue($scope.selectedArea.properties.utdata.areaNid);
				$scope.isColumnVisible = false;
			}
			else{
				$scope.lineChartValue($scope.selectedArea.properties.utdata.areaNid);
				$scope.isColumnVisible = true;
			}
			}
			else{
				$scope.closeViz();
			}
		} else {

			$scope.isTrendVisible = false;
			$scope.selectedArea = [];
		}
		$scope.$apply();
	};
	$scope.closeViz = function() {
		$scope.isTrendVisible = false;
		$scope.cldata = [];
		$scope.ldata = [];
		$scope.PCldata ="";
	};

	$scope.start = function() {
		$("#loader-mask").show();
		$(".right-arrow").click(function() {
			if (processStatus.requestRunning) {
				return;
			}
			processStatus.startProcess();
			if ($(".active-sector").length == 0) {
				$(".sectorlist").first().addClass("active-sector");
			}
			var activeElement = $(".active-sector");
			$visiblesec_width = $(".sector_wrap").outerWidth(true);

			$prevWidth = 0;
			activeElement.prevAll().each(function() {
				$prevWidth += parseInt($(this).outerWidth(true), 10);
			});

			$ulwidth = 0;
			$(".sectorlist").each(function(index) {
				$ulwidth += parseInt($(this).outerWidth(true), 10);
			});

			// get length of all lists present before active lists
			if ($visiblesec_width + $prevWidth < $ulwidth) {
				$(".sectorlists").animate({
					"left" : "-=" + activeElement.outerWidth(true)
				}, 300, function() {
					activeElement.removeClass("active-sector");
					activeElement.next().addClass("active-sector");
					processStatus.endProcess();
				});
			} else {
//				$(".right-arrow").addClass("disable");
				processStatus.endProcess();
			}
		});
		$(".left-arrow").click(function() {

			if (processStatus.requestRunning) {
				return;
			}
			processStatus.startProcess();

			var activeElement = $(".active-sector");
			if (activeElement.prev()) {
				$prevWidth = 0;
				activeElement.prevAll().each(function() {
					$prevWidth += parseInt($(this).outerWidth(true), 10);
				});

				if ($prevWidth > 0) {
					$(".sectorlists").animate({
						"left" : "+=" + activeElement.prev().outerWidth(true)
					}, 300, function() {
						activeElement.removeClass("active-sector");
						activeElement.prev().addClass("active-sector");
						processStatus.endProcess();
					});
				} else {
//					$(".left-arrow").addClass("disable");
					processStatus.endProcess();
				}
			} else {
//				$(".left-arrow").addClass("disable");
				processStatus.endProcess();
			}

		});

					$http.get("sectors").success(function(data) {
						$scope.initMap();
										$scope.sectors = data;
										$scope.parentSectors=$filter('filter')($scope.sectors, {description:-1},true);
										if ($scope.sectors) {
											$scope.selectedParentSector =  $scope.parentSectors[0];
											$scope.subSector=$filter('filter')($scope.sectors, {description:$scope.selectedParentSector.key},true);
											$scope.selectedSector=$scope.subSector[0];
											$http.get("indicators?sector="+ $scope.selectedSector.key)
													.success(function(data) {
																$scope.indicators = data;
																if ($scope.indicators.length > 0) {
																	
																	$scope.selectedIndicator = $scope.indicators[0];
																	
																	$http.get("sources?iusnid="+ $scope.selectedIndicator.description ).success(function(data) {
																		$scope.sources = data;
																		if($scope.sources.length > 0) {
																			$scope.selectedSource = $scope.sources[0];
																			$http.get("timeperiod?iusnid="+ $scope.selectedIndicator.description + "&sourceNid="+ $scope.selectedSource.key).success(function(data){
																				$scope.timeformats = data;
																				if($scope.timeformats && $scope.timeformats.length>0){
																					$scope.selectedTimeperiod =  $scope.timeformats[0];
																					var sl =  $scope.selectedChildAreaLevel;
																					var smap =  $scope.selectedMapAreaType;
																					var sg =  $scope.selectedGranularity;
																					var sd =  $scope.shouldDrilldown;
																					$scope.selectMapAreaType(smap,sg,sl,sd);
																				}else{
																					var sl = $scope.selectedChildAreaLevel;
																					var smap =  $scope.selectedMapAreaType;
																					var sg =  $scope.selectedGranularity;
																					var sd =  $scope.shouldDrilldown;
																					$scope.selectMapAreaType(smap,sg,sl,sd);
																				}
																				
																			});
																			
																		}else{
																			var sl = $scope.selectedChildAreaLevel;
																			var smap =  $scope.selectedMapAreaType;
																			var sg =  $scope.selectedGranularity;
																			var sd =  $scope.shouldDrilldown;
																			$scope.selectMapAreaType(smap,sg,sl,sd);
																			$("#loader-mask").fadeOut();
																		}
																	});
																	
																}else{
																	var sl = $scope.selectedChildAreaLevel;
																	var smap =  $scope.selectedMapAreaType;
																	var sg =  $scope.selectedGranularity;
																	var sd =  $scope.shouldDrilldown;
																	$scope.selectMapAreaType(smap,sg,sl,sd);
																	$("#loader-mask").fadeOut();
																}
															});
										}
									});

//				});

	};

	$scope.getindicators = function() {
		var url= "indicators";
		var query = "";
		if ($scope.selectedSector)
			query += "sector=" + $scope.selectedSector.key ;
		if (query != "")
			url += "?" + query.trim("&");
		$http.get(url).success(function(data) {
					$scope.indicators = data;
					if ($scope.indicators) {
						$scope.selectedIndicator = $scope.indicators[0];
						$scope.getsources();
					}else{
						$scope.getsources();
					}
					if($scope.selectedArea.length != 0){
					if($scope.selectedArea.properties.utdata != null ){
					if( $scope.timeformats.length >1 ){
						$scope.lineChartValue();
						$scope.isColumnVisible = false;
					

					}
					else{
						$scope.columnChartdataValue();
						$scope.isColumnVisible = true;
						
					}
					}
					}
				});
	};
	
	$scope.getsources = function() {
		var url= "sources";
		var query = "";
		if ($scope.selectedIndicator)
			query += "iusnid=" + $scope.selectedIndicator.description;
		if (query != "")
			url += "?" + query.trim("&");
		$http.get(url).success(function(data) {
					$scope.sources = data;
					if ($scope.sources) {
						$scope.selectedSource = $scope.sources[0];
						$scope.gettimeperiods();
					}else{
						$scope.gettimeperiods();
					}
					if($scope.selectedArea.length != 0){
					if($scope.selectedArea.properties.utdata != null){
					if( $scope.timeformats.length >1){
						$scope.lineChartValue();
						$scope.isColumnVisible = false;
					

					}
					else{
						$scope.columnChartdataValue();
						$scope.isColumnVisible = true;
					}
					}
					}
				});

	};
	
	$scope.gettimeperiods = function() {
		var url= "timeperiod";
		var query = "";
		if ($scope.selectedIndicator)
			query += "iusnid=" + $scope.selectedIndicator.description+ "&";
		if ($scope.selectedSource)
			query += "sourceNid=" + $scope.selectedSource.key+ "&";
		if (query != "")
			url += "?" + query.trim("&");
		$http.get(url).success(function(data) {
					$scope.timeformats = data;
					if ($scope.timeformats) {
						$scope.selectedTimeperiod = $scope.timeformats[0];
						$scope.getutdata();
					}else{
						$scope.getutdata();
					}
					if($scope.selectedArea.length != 0){
					if($scope.selectedArea.properties.utdata != null){
					if( $scope.timeformats.length >1){
						$scope.lineChartValue();
						$scope.isColumnVisible = false;
					

					}
					else{
						$scope.columnChartdataValue();
						$scope.isColumnVisible = true;
					}
					}
					}
				});

	};

	$scope.getutdata = function() {
		// resetting all the data
		$("#loader-mask").show();
		$scope.utdata = [];
		$scope.legends = [];
		$scope.topPerformers = [];
		$scope.bottomPerformers = [];
		var url = "data";

		 $scope.query = "";
		if ($scope.selectedIndicator)
			$scope.query += "indicatorId=" + $scope.selectedIndicator.description
					+ "&";
		if ($scope.selectedGranularity)
			$scope.query += "areaId=" + $scope.selectedGranularity.key + "&";
		if ($scope.selectedSource)
			$scope.query += "sourceNid=" + $scope.selectedSource.key+ "&";
		if ($scope.selectedTimeperiod)
			$scope.query += "timeperiodId=" + $scope.selectedTimeperiod.key + "&";
		
		if ($scope.selectedChildAreaLevel)
			$scope.query += "childLevel=" + $scope.selectedChildAreaLevel;
		if ($scope.query != "")
			url += "?" + $scope.query.trim("&");
		$http.get(url).success(function(data) {
			$scope.utdata = data;
			$scope.legends = data.legends ? data.legends : [];
			$scope.mapData=data.dataCollection;
			
			//console.log(data.legends);
			
			/*if ($scope.legends && $scope.legends.length > 0) {
				for (var i = 0; i < data.legends.length; i++) {
					$scope.legends[i].key = data.legends[i].key == 'Not Available' ? data.legends[i].key
							: parseFloat(data.legends[i].key
									.split(' - ')[0])
									+ ' - '
									+ parseFloat(data.legends[i].key
											.split(' - ')[1]);
				}
			}*/
			$scope.topPerformers = data.topPerformers;
			if($scope.topPerformers ){
				for (var i = 0; i < data.topPerformers.length; i++) {
					$scope.topPerformers[i] = data.topPerformers[i].split(' - ')[0] + ' - ' + data.topPerformers[i].split(' - ')[1];
				}
			}
			$scope.bottomPerformers = data.bottomPerformers;
			if($scope.bottomPerformers){
				for (var i = 0; i < data.bottomPerformers.length; i++) {
					$scope.bottomPerformers[i] = data.bottomPerformers[i].split(' - ')[0] + ' - ' + data.bottomPerformers[i].split(' - ')[1];
				}
			}
			
			document.getElementById("legendsection").style.display = $scope.legends.length > 0 ? 'block' : 'none'; 
			document.getElementById("tbsection").style.display = $scope.legends.length > 0 ? 'block' : 'none'; 
			$("#loader-mask").fadeOut();
		});
	};
	$scope.lineChartValue = function() {
		$http.get(

				'lineData?iusNid=' + $scope.selectedIndicator.description
						+ '&areaNid=' + $scope.selectedArea.properties.utdata.areaNid)

				.success(function(data) {
					if(data.length <=100){
//					 $scope.isnodata = false;
					$scope.cldata = [];
					$scope.ldata = data;
					$scope.PCldata = data[0][0];
//					if($scope.PCldata.percentageChange == 0.00){
//						$scope.PCldata ="";
//					}
					$scope.isColumnVisible = false;
//					$scope.lineChartValue();
					if(data.length == 0){
						$scope.closeViz();
					}
					}
					// console.log(data);
				});
	};

	
	$scope.columnChartdataValue = function() {
		$http.get(

				'ColData?iusNid=' + $scope.selectedIndicator.description
						+ '&areaNid=' + $scope.selectedArea.properties.utdata.areaNid)

				.success(function(data) {
//					 $scope.isnodata = false;
					$scope.ldata = [];
					$scope.cldata = data;
					$scope.PCldata ="";
					$scope.isColumnVisible = true;
//					$scope.columnChartdataValue();
					if(data.length == 0){
						$scope.closeViz();
					}
					// console.log(data);
				});
	};
	
	$scope.startLoader = function() {
		//cfpLoadingBar.start();
	};
	
	$scope.complete = function() {
		//cfpLoadingBar.complete();
	};

	// fake the initial load so first time users can see the bar right away:
	$scope.start();
//	$scope.fakeIntro = true;
//	$timeout(function() {
//		$scope.complete();
//		$scope.fakeIntro = false;
//	}, 30000);
	$scope.style = function() {

	};
	
	/* for publish dashboard success modal */
	
	$scope.publishDashboard = function (){
		$http.get('publishData').
		success(function(response){
			$scope.infoMsg = response.message;
			$("#infoMessage").modal("show");
		},function(error){
			console.log(error);
		});
	};
	
});
