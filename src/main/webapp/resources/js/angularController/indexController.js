/**
 * @author - Swarna (swarnaprava@sdrc.co.in)
 */

var app = angular.module("icpsIcpo",
		[  'ngAnimate' ]);

//for angular loader


// for excel download of tables
app.factory('Excel',function($window){
	var uri='data:application/vnd.ms-excel;base64,',
		template='<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
		base64=function(s){return $window.btoa(unescape(encodeURIComponent(s)));},
		format=function(s,c){return s.replace(/{(\w+)}/g,function(m,p){return c[p];});};
	return {
		tableToExcel:function(tableId,worksheetName){
			var table=$(tableId),
				ctx={worksheet:worksheetName,table:table.html()},
				href=uri+base64(format(template,ctx));
			return href;
		}
	};
});

app.controller('factSheetController', function(Excel, $scope, $timeout, $http,$filter) {
	
	//initializing variables
    $scope.stateList = [];
    $scope.ccFlag = true;
    $scope.hrFlag = false;
    $scope.icpsFlag  = false;
    $scope.checkedAll = false;
    $scope.id='cc';
    $scope.showDistrictListFlag = false;
    $scope.selectedDistrictList = [];
 
    $("#loader-mask").show();
    
    //fetching master data like state, district etc.
    $http.get("getPrefetchData").
    then(function(result){
    	$scope.areaList = result.data.AreaList;
    	$scope.areaData = convert($scope.areaList).children;
    	$scope.area = $scope.areaData[0].children;
    	$scope.selectedState = $scope.area[0];
    	$scope.timePeriodList = result.data.TimePeriod;
    	$scope.selectedTimeperiod = $scope.timePeriodList[0];
    	$scope.sourceList = result.data.sourceList;
    	$scope.selectedSource = $scope.sourceList[0];
    	$scope.totalSectorList = result.data.sectorList;
    	$scope.sectorList = convertSector($scope.totalSectorList).children;
    	$scope.selectedSector = $scope.sectorList[0];
    	$scope.indicatorsList = result.data.indiactorList;
    	$scope.indexList=result.data.indexList;
    	$scope.sctorTableSecondRow = [];
    	for(var i=0; i<$scope.selectedSector.children.length;i++){
    		$scope.sctorTableSecondRow = $scope.sctorTableSecondRow.concat($scope.selectedSector.children[i].children);
    	}
    	
    	setIndicators();
    	formatTimeperiod();
    	$scope.changeDistrictList();
    	
    }, function(error){
    	console.log(error);
    });
    
    $scope.selectState = function(state){
    	$scope.selectedState = state;
    };
    $scope.selectTimeperiod = function(tp){
    	$scope.selectedTimeperiod = tp;
    };
    
    // show and hide district list on click of district input field
    $scope.showDistrictList = function(){
    	if($scope.showDistrictListFlag == true)
    		$scope.showDistrictListFlag = false;
    	else
    		$scope.showDistrictListFlag = true;
    };
    
    // on state change this function will be called
    $scope.changeDistrictList = function() {
    	$scope.districtList = [];
    	$scope.selectedDistrictList = [];
    	$scope.allDistrictObject = { 
    			area_NId : 0,
    			area_ID	: "All Districts",
				area_Name : "All Districts",
				area_Level : 3,
				selected : true
    	};
    	$scope.districtList.push($scope.allDistrictObject); 
    	for(var i=0; i<$scope.selectedState.children.length; i++){
    		$scope.selectedState.children[i].selected = true;
    		$scope.districtList.push($scope.selectedState.children[i]);
    		$scope.selectedDistrictList.push($scope.selectedState.children[i]);
    	}
    	$scope.selectedDistrict = $scope.allDistrictObject;
        $scope.changeData();
    };
    
    
    //on selection of districts in ui this function is called
    $scope.countSelectedDistricts = function(dist){
    	$scope.tempDistList = [];
    	$scope.selectedDistrictList = [];
    	
    	if(dist.area_Name == "All Districts"){		// If all district selected
    		$scope.districtList.forEach(function (v) {
                v.selected = dist.selected;
            });
    	}else if(dist.selected == false){			// If any 1 false then all district false
    		$scope.districtList[0].selected = false;
    	}
    	
    	$scope.districtList.forEach(function (v) {	// creating a array of selected districts
            if(v.selected === true){
            	$scope.tempDistList.push(v);
            	$scope.selectedDistrictList.push(v);
            }
        });
    	
    	if($scope.tempDistList.length >= $scope.districtList.length - 1){ // display name changed accordingly
    		$scope.selectedDistrict = {area_Name : "All Districts"};
    		$scope.districtList[0].selected = true;
    	}
    	else if($scope.tempDistList.length > 1)
    		$scope.selectedDistrict = {area_Name : $scope.tempDistList.length + " Selected"};
    	else if($scope.tempDistList.length == 1)
    		$scope.selectedDistrict = $scope.tempDistList[0];
    	else if($scope.tempDistList.length == 0)
    		$scope.selectedDistrict = {area_Name : "None Selected"};
    };
    
    $scope.selectAll = function () {
    	$scope.checkedAll = $scope.checkedAll == false ? true : false;
        $scope.districtList.forEach(function (v) {
            v.selected = $scope.checkedAll;
        });
    };
    
    // checking which sector is selected in ui
    $scope.checkData = function(sector){
    	$("#loader-mask").show();
    	if(sector.ic_Name == "Results for Children"){
    		$scope.ccFlag = true;
		    $scope.hrFlag = false;
		    $scope.icpsFlag  = false;
//		    $scope.id='cc';
    	}else if(sector.ic_Name == "Human Resource"){
    		$scope.ccFlag = false;
		    $scope.hrFlag = true;
		    $scope.icpsFlag  = false;
//		    $scope.id='hr';
    	}else if(sector.ic_Name == "ICPS Structure and Functionality"){
    		$scope.ccFlag = false;
		    $scope.hrFlag = false;
		    $scope.icpsFlag  = true;
//		    $scope.id='icps';
    	}
    	
    	$scope.changeData();
    	console.log(sector);
    };
    
    
    $scope.myCustomOrderBy = function(k){
        return k; //what you intent to here play on your own
    }
    
    //on sector change the second table data will be fetched from the back-end
    $scope.changeData = function(){
    	
    	var finalDistrictList = [];
    	var selectedState = JSON.parse(JSON.stringify($scope.selectedState));
    	var selectedDistrict = JSON.parse(JSON.stringify($scope.selectedDistrict));
    	var selectedSector = JSON.parse(JSON.stringify($scope.selectedSector));
    	selectedState.children = "";
    	selectedDistrict.children = "";
    	selectedSector.children = "";
    	$scope.sctorTableSecondRow = [];
    
    	for(var i=0; i<$scope.selectedSector.children.length;i++){
    		$scope.sctorTableSecondRow = $scope.sctorTableSecondRow.concat($scope.selectedSector.children[i].children);
    	}
    	
    	$scope.selectedDistrictList.forEach(function(dist){
    		dist.children = "h";
    		if(dist.area_NId != 0)
    			finalDistrictList.push(dist);
    	});
    	
    	$scope.infoObject = {
    			state :	selectedState,
    			districtList : finalDistrictList,
    			timePeriod : $scope.selectedTimeperiod,
    			sector : selectedSector
    	};
    	
    	if(finalDistrictList.length)
    		{
    		$("#loader-mask").show();
    	$http.post("factSheetData", $scope.infoObject).
    	then(function(result){
    		$('[data-toggle="tooltip"]').tooltip();
    		$scope.factSheetData = result.data.sectorData;
    		$scope.subSectorData = result.data.indicatorData;
    		$scope.Keys=[];
    		if($scope.subSectorData != undefined){
    		for( i=0;i<Object.keys($scope.subSectorData[0].sectorData).length;i++)
    			{
    			var key =
    				{
    				id:	"1",
    				keyValue:Object.keys($scope.subSectorData[0].sectorData)[i],
    				sector:Object.keys($scope.subSectorData[0].sectorData)[i].split('_')[1],
    				indicator:Object.keys($scope.subSectorData[0].sectorData)[i].split('_')[0],
    				sorting:(Object.keys($scope.subSectorData[0].sectorData)[i].split('_')[1]).concat("_").concat(Object.keys($scope.subSectorData[0].sectorData)[i].split('_')[0])
    				}
        		$scope.Keys.push(key);
    			}
    	}
    		
    		
    		
    		$("#loader-mask").fadeOut();
    		console.log(result);
    	
    	}, function(error){
    		console.log(error);
    	});
    		}
    	
    	else
    		{
    			$scope.errorMsg = "Select atleast one district";
    			$('#errorMessage').modal("show");
    		}
    	
    	
    	
	
    };
    
    // creating parent child relation for area.
    function convert(array){
	    var map = {};
	    for(var i = 0; i < array.length; i++){
	        var obj = array[i];
	        if(obj.area_Parent_NId == -1)
	        	obj.area_Parent_NId =  null;
	        if(!(obj.area_NId in map)){
	            map[obj.area_NId] = obj;
	            map[obj.area_NId].children = [];
	        }

	        if(typeof map[obj.area_NId].area_Name == 'undefined'){
	            map[obj.area_NId].area_NId = String(obj.area_NId);
	            map[obj.area_NId].area_Name = obj.area_Name;
	            map[obj.area_NId].area_Parent_NId= String(obj.area_Parent_NId);
	        }

	        var parent = obj.area_Parent_NId || '-';
	        if(!(parent in map)){
	            map[parent] = {};
	            map[parent].children = [];
	        }

	        map[parent].children.push(map[obj.area_NId]);
	    }
	    return map['-'];
	}
    
    // creating parent child relation for sectors and sub-sectors
    function convertSector(array){
	    var map = {};
	    for(var i = 0; i < array.length; i++){
	        var obj = array[i];
	        if(obj.ic_Parent_NId == -1)
	        	obj.ic_Parent_NId =  null;
	        if(!(obj.ic_NId in map)){
	            map[obj.ic_NId] = obj;
	            map[obj.ic_NId].children = [];
	        }

	        if(typeof map[obj.ic_NId].ic_Name == 'undefined'){
	            map[obj.area_NId].ic_NId = String(obj.ic_NId);
	            map[obj.area_NId].ic_Name = obj.ic_Name;
	            map[obj.area_NId].ic_Parent_NId= String(obj.ic_Parent_NId);
	        }

	        var parent = obj.ic_Parent_NId || '-';
	        if(!(parent in map)){
	            map[parent] = {};
	            map[parent].children = [];
	        }

	        map[parent].children.push(map[obj.ic_NId]);
	    }
	    return map['-'];
	}
    
    // assigning indicators w.r.t sectors
    function setIndicators(){
    	for(var i=0; i<$scope.sectorList.length; i++){
    		for(var j=0; j<$scope.sectorList[i].children.length; j++){
    			for(var k=0; k<$scope.indicatorsList.length; k++){
    				if($scope.indicatorsList[k].ic_NId == $scope.sectorList[i].children[j].ic_NId)
    					$scope.sectorList[i].children[j].children.push($scope.indicatorsList[k]);
    			}
    		}
    	}
    }
    
    // changing the format of time-period to display in the ui
    function formatTimeperiod(){
    	var temp = [];
    	for(var i=0; i<$scope.timePeriodList.length; i++){
    		temp = $scope.timePeriodList[i].timePeriod.split(".");
    		formatMonth(temp, i);
    	}
    }
    
//    function rebuildProgramMultiSelect(){
//		$('#districtSelection').empty();
//		$("#districtSelection option:selected").removeAttr("selected");
//		for(var k = 0; k < $scope.districtList.length; k++){
//			$('#districtSelection').append($('<option>', {
//				value : $scope.districtList[k].area_NId,
//				text : $scope.districtList[k].area_Name
//			}));
//		}	
//		$('#districtSelection').multiselect('rebuild');
//	}
    
    function formatMonth(temp, i){
    	var month = "";
    	switch(temp[1]){
    	case "01":
    		month = "January";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "02":
    		month = "Februray";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "03":
    		month = "March";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "04":
    		month = "April";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "05":
    		month = "May";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "06":
    		month = "June";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "07":
    		month = "July";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "08":
    		month = "August";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "09":
    		month = "September";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "10":
    		month = "October";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "11":
    		month = "November";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	case "12":
    		month = "December";
    		$scope.timePeriodList[i].timePeriod = temp[0]+" "+month;
    		break;
    	}
    }
    
    $scope.exportToExcel=function(tableId){
		$scope.exportHref=Excel.tableToExcel(tableId,'Factsheet');
		$timeout(function(){location.href=$scope.exportHref;},100);
	};
	
	
	$scope.exportTableData = function(id){
		var link = document.createElement("a");
    	var htmls = "";
        var uri = 'data:application/vnd.ms-excel;base64,';
        var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'; 
        var base64 = function(s) {
            return window.btoa(unescape(encodeURIComponent(s)));
        };

        var format = function(s, c) {
            return s.replace(/{(\w+)}/g, function(m, p) {
                return c[p];
            });
        };
        if(id=='sectorData'){
        	link.download = "index"+".xls";
        	var tab_text="<div style='font-size:20px;'>Timeperiod : <b style='color:#0054a5; margin-right: 20px'>"+$scope.selectedTimeperiod.timePeriod;
        }
        else {
        	link.download = $scope.selectedSector.ic_Name+".xls";
        	var tab_text="<div style='font-size:20px;'>Sector : <b style='color:#0054a5; margin-right: 20px'>"+$scope.selectedSector.ic_Name+"</b> Timeperiod : <b style='color:#0054a5; margin-right: 20px'>"+
        	$scope.selectedTimeperiod.timePeriod;
        }
        var tab_text=tab_text +"<table border='2px'><tr bgcolor='#87AFC6'>";
        var textRange; var j=0;
        tab = document.getElementById(id); // id of table

        for(j = 0 ; j < tab.rows.length ; j++) 
        {     
        	   
        	if(j==0)
			tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
        	else
            tab_text=tab_text+"<tr style='text-align:center'>"+tab.rows[j].innerHTML+"</tr>";
            //tab_text=tab_text+"</tr>";
        }

        tab_text=tab_text+"</table>";
        tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
        tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
        tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params


        var ctx = {
            worksheet : 'Worksheet',
            table : tab_text
        };

        link.href = uri + base64(format(template, ctx));
        if(typeof InstallTrigger === 'undefined'){
        	link.click();
        }
        else{
        	window.location.href = link.href;
        }
    };
	
	// tooltip for indicators value
    
    $scope.tooltipTitle = function(key)
    {
    	return "Indicator :" + key.indicator  + "    Sector :" + key.sector; 
    }
    
	
//    $scope.$watch("$viewContentLoaded", function(){
//		setTimeout(function(){
//			$('[data-toggle="tooltip"]').tooltip();
//		},20);
//	});
	
	
	
});

/*app.filter('toArray', function() { return function(obj) {
    if (!(obj instanceof Object)) return obj;
    return _.map(obj, function(val, key) {
        return Object.defineProperty(val, '$key', {__proto__: null, value: key});
    });
}});*/

app.directive(
		"sdrcTableHeaderFix",
		function($window, $compile) {
			function link(scope, el) {
				var tableUniqClass = "";
				
				scope.$watch("tableuniqueclass", function(uniqClass) {
					tableUniqClass = uniqClass;
					function createStaticHeader(uniqClass){
						$("."+uniqClass).before('<div class="static-header-container"><div class="static-header"></div></div>')
					}
					if(uniqClass)
					createStaticHeader(uniqClass);	
				});
				
				scope.$watch("tabledata", function(data) {
					function fixTableHeader(uniqClass) {
						setTimeout(function(){
							
							if($("."+uniqClass)[0].offsetWidth >= $("."+uniqClass)[0].clientWidth){
								var i=0;rowWidthList=[];
								$("."+uniqClass).siblings(".static-header-container").find(".static-header").html($("."+uniqClass).html());
								$("."+uniqClass).siblings(".static-header-container").find(".static-header").find("table thead").css("visibility", "visible");
								$("."+uniqClass).scrollLeft(0);
								$("."+uniqClass).siblings(".static-header-container").height($("."+uniqClass).siblings(".static-header-container").find(".static-header table thead").height());
								$("."+uniqClass).css("margin-top", -$("."+uniqClass).siblings(".static-header-container").find(".static-header table thead").height()+"px");
//								$("."+uniqClass).parent()
								$("."+uniqClass).siblings(".static-header-container").css({
									 'overflow-x': 'hidden',
									 'overflow-y': 'hidden',
									 'position': 'relative',
									 'clear': 'both',
									 'border-bottom':'1px solid #ddd'
								});
								$("."+uniqClass).siblings(".static-header-container").find(".static-header").css({
									 'overflow-x': 'auto',
									 'overflow-y': 'hidden',
									 'background-color': '#FFF',
									 'margin-right': 17
								})
								$("."+uniqClass).find("table").css({"margin-bottom": "0px"});
								$("."+uniqClass).find("table thead").css("visibility", "hidden");
								if($(window).width() > 845){
									if($("."+uniqClass)[0].offsetWidth == $("."+uniqClass)[0].clientWidth){
										if($("."+uniqClass).find("table").height() > $("."+uniqClass).height())
											$("."+uniqClass).siblings(".static-header-container").css({'margin-right': "0px"});
										else
											$("."+uniqClass).siblings(".static-header-container").css({'margin-right': "0px"});

									}
									else{
										if($("."+uniqClass).find("table").height() + 17 > $("."+uniqClass).height())
											$("."+uniqClass).siblings(".static-header-container").css({'margin-right': "0px"});
										else
											$("."+uniqClass).siblings(".static-header-container").css({'margin-right': "0px"});

									}
																	}
//								$compile(angular.element(".static-header-container .static-header thead"))(angular.element("body").scope());
								var ignoreTableBodyScroll = false;
								var ignoreTableHeadScroll = false;
								$(".header-fixed-table").scroll(function(){
										if(!ignoreTableBodyScroll){
											ignoreTableHeadScroll = true;
										$(this).prev().find(".static-header").scrollLeft($(this).scrollLeft());
										
										}
									
										ignoreTableBodyScroll = false;
										
								}); 
								$(".static-header").scroll(function(){
									if(!ignoreTableHeadScroll){
										ignoreTableBodyScroll = true;
									$(this).parent().siblings(".header-fixed-table").scrollLeft($(this).scrollLeft());
									
									}
									ignoreTableHeadScroll = false;
								})
							}
						}, 200);
					};
					if(tableUniqClass && data)
						fixTableHeader(tableUniqClass);
				});
				
			}
			return {
				restrict : "A",
				scope : {
					tableuniqueclass : "=",
					tabledata : "="
				},
				link : link
			};
});

app.controller('bodyHeaderController', function($scope, $timeout, $http) {
	
	$scope.login = function(){
		$('#loginModal').modal('show');
	};
	
	$scope.validateLogin = function(){
		$http.post("validateLogin?username="+ $scope.username +"&password="+ $scope.password).then(function(result){
			console.log("Successfull");
		}, function(error){
			console.log(error);
		});
	};
	
	$scope.logout = function(){
		$http.get("logout").then(function(result){
			console.log(result);
		}, function(error){
			console.log(error);
		});
	};
});
