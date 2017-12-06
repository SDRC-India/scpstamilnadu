var sdrc_export = new function() {
	"use strict";
	this.root = "http://localhost/";
	this.init = function(rootUri) {
		this.root = rootUri;
		//console.log("in init");
	};

	// Please give container Id and export pdf btn ids
	this.export_pdf = function(containerId, exportPdfbtn) {
		$("#" + exportPdfbtn)
				.on("click",function(event) {
					$('html, body').animate({
					    scrollTop: $('#legendsection').offset().top
					}, 1000);
					var $scope = angular.element("body").scope();
							event.preventDefault();
							var topBottom;
							var legend;
							$('#pdfDownloadBtn').html('<i class="fa fa-lg fa-download"></i> Download PDF <img src="resources/images/preloader.gif" />');
							
							var areaName = $("#area").val();
							{
							d3.selectAll("svg").attr("version", 1.1).attr("xmlns", "http://www.w3.org/2000/svg");
							
							d3.select("#mapsvg").selectAll("path").attr("style",function(d) {
								return  "fill:"+ $(this).css('fill')+";stroke:"+$(this).css('stroke');
							});
							var samikshaMapg = $("samiksha-map").html().replace(/\&nbsp;/g, " ");
							//var legendImage = "<svg>" + $(".legendssection").html().replace("nbsp;", "");
							//var topBottomContainer = "<svg>" + $("#legendssection").html().replace("nbsp;", "");
							//remove style
							d3.select("#mapsvg").selectAll("path").attr('style', null);
							
						{
							   
							    var topBottomContainer = $("#tbsection");
								var legendContainer = $("#legendsection");
								var svgs=[] ;
								html2canvas($("#tbsection"),
										{
											useCORS : true,
											onrendered : function(canvas) {
												//var serverUrl = sdrc_export.root+ "/exportToPdf";
												//console.log(canvas.toDataURL('image/png', 1.0));
												svgs.push(canvas.toDataURL('image/png', 1.0));
												topBottom = canvas.toDataURL('image/png', 1.0);
												//$("#imageTopBottomBase64").val(canvas.toDataURL("image/png"));
												
												html2canvas(
														$("#legendsection"),
														{
															useCORS : true,
															onrendered : function(canvas) {
															//	var serverUrl = sdrc_export.root+ "/exportToPdf";

																legend = canvas.toDataURL('image/png', 1.0);
																/*if($scope.topPerformers.length==0)
																	{
																	svgs=[];
																	svgs.push(canvas.toDataURL('image/png', 1.0));
																	}*/
																svgs.push(canvas.toDataURL('image/png', 1.0));
																
//																$("#exportForm").attr("action",serverUrl).submit();
																
																//console.log(topBottom)
																svgs.push(samikshaMapg);
																
																$scope.SDRCExportModel={
																		"svgs" :  svgs , 
																		 "mapData" : $scope.mapData
																}
																
																svgs.push($(window).width());
																
																var serverUrl="api/exportPDF?"+$scope.query.trim("&");
																$.ajax({
																	url:serverUrl,
																	method:'POST',
																	data:JSON.stringify(svgs),
																	contentType : 'application/json',
																	 success:function(result){
																		 if(result ==null || result.trim()=="")
																			 {
																			 $scope.formError="Something Went Wrong";
																			 }
																		 else
																		{	 
																		var data = {"fileName" :result};
																		$.download("downloadPDF",data,'POST');
																		}
																		
																		$('#pdfDownloadBtn').html('<i class="fa fa-lg fa-download"></i> Download PDF ');
																  	}// end
																		// of
																		// success
																});
															}
														});
												
											}
//										});
//							  	}//end of success
							});
								}
							
							}
				});
	};
	
	$.download = function(url, data, method) {
		// url and data options required
		if (url && data) {
			// data can be string of parameters or array/object
			data = typeof data == 'string' ? data : jQuery.param(data);
			// split params into form inputs
			var inputs = '';
			jQuery.each(data.split('&'), function() {
				var pair = this.split('=');
				inputs += '<input type="hidden" name="' + pair[0] + '" value="'	+ pair[1] + '" />';
			});
			// send request
			jQuery(
					'<form action="' + url + '" method="' + (method || 'post')
							+ '">' + inputs + '</form>').appendTo('body')
					.submit().remove();
			$(".loader").css("display", "none");
		}
	};
	this.export_pdfLine = function(containerId, exportPdfbtn) {
		$("#" + exportPdfbtn)
		.on("click",function(event) {
//			$(".loader").css("display", "block");
			var $scope = angular.element("body").scope();
			$scope.startLoader();
			event.preventDefault();
			$scope.lineChartImage=undefined;
			
			
//			$('#pdfDownloadBtnForIndex').html('<i class="fa fa-lg fa-download"></i><img src="resources/images/preloader.gif" />');
			
			d3.selectAll("svg").attr("version", 1.1).attr("xmlns", "http://www.w3.org/2000/svg");
			
			d3.select("#trendsvg").selectAll("path").attr("style",function(d) {
				return  "fill:"+ $(this).css('fill')+";stroke:"+$(this).css('stroke');
			});
			var trendSvg = $("samiksha-line").html();
			//d3.select("#trendsvg").selectAll("path").attr('style', null);
			$('#trendsvg').css('display', "none");
			$('#trendPdfButton').css('display', "none");
			 html2canvas($("#trendDiv"), {
		            useCORS: true,
		            onrendered: function(canvas) {
		            	$scope.lineChartImage = canvas.toDataURL('image/png', 1.0);
		                sendData();
		        
		            }
		        });
			 
			 function sendData()
			 {
					$('#trendsvg').css('display', "block");
					$('#trendPdfButton').css('display', "block");
				 var serverURL='exportLineChart?iusNid=' + $scope.selectedIndicator.description
					+ '&areaNid=' + $scope.selectedArea.properties.utdata.areaNid
					var svgs=[];
				 svgs.push($scope.lineChartImage);
				 svgs.push($scope.selectedArea.properties.utdata.areaName);
				 svgs.push($scope.selectedIndicator.value);
				 svgs.push(trendSvg);
				 svgs.push($(window).width());
				 $.ajax({
						url : serverURL, 
						method : 'POST',
						data : JSON.stringify(svgs),
						contentType : 'application/json',
						success : function(result) {
							if(result ==null || result.trim()=="")
							 {
							 $scope.formError="Something Went Wrong";
							 }
						 else
						{	 
						var fileName = {"fileName" :result};
						$.download("downloadPDF", fileName, 'POST');
//						$('#pdfDownloadBtnForIndex').html('<i class="fa fa-lg fa-download"></i>');
						}
							$scope.complete();
						}
					});
			 }
	
		});
		
	};

};
