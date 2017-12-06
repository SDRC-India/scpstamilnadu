// Angular app creation
//var app = angular.module("esamapp",
//		[ 'chieffancypants.loadingBar', 'ngAnimate' ]);


var app = angular.module("esamapp",
		[ 'ngAnimate' ]);
app.config([ "$httpProvider", function($httpProvider) {
	$httpProvider.interceptors.push('apis');
} ]);


//app.config(function(cfpLoadingBarProvider) {
//	// true is the default, but I left this here as an example:
//	cfpLoadingBarProvider.includeSpinner = true;
//});

//rootURL = "http://localhost:8080/scpstamilnadu";
app.factory('apis', function() {
	return {
		request : function(config) {

			// need more controlling when there is more than 1 domain involved
			config.url =  "api/" + config.url;
			return config;
		}
	};

});
app.currentStateUrl = function(scope) {
	var scope = angular.element($("#containerId")).scope();

	return "dashboard?" + "st=" + scope.selectedTimeperiod.key
			+ "%26ss=" + scope.selectedSector.key + "%26so="
			+  scope.selectedSource.key + "%26si="
			+ scope.selectedIndicator.description + "%26sg="
			+ scope.selectedGranularity.key + "%26sgn="
			+ scope.selectedGranularity.value + "%26sl="
			+ scope.selectedChildAreaLevel + "%26sd=" + scope.shouldDrilldown
			+ "%26smap=" + scope.selectedMapAreaType;
};

app.currentStateMailUrl = function(scope) {
	var scope = angular.element($("#containerId")).scope();

	// To be deleted
	// return "http://" + location.hostname
	// + (location.port ? ":" + location.port : "")
	return "dashboard?" + "st=" + scope.selectedTimeperiod.key
			+ "&ss=" + scope.selectedSector.key + "&so="
			+  scope.selectedSource.key + "&si="
			+ scope.selectedIndicator.description + "&sg="
			+ scope.selectedGranularity.key + "&sgn="
			+ scope.selectedGranularity.value + "&sl="
			+ scope.selectedChildAreaLevel + "&sd=" + scope.shouldDrilldown
			+ "&smap=" + scope.selectedMapAreaType;
};

app
		.directive(
				"samikshaMap",
				function($window) {
					function link(scope, el) {
						var el = el[0];
						var DELAY = 300, clicks = 0, timer = null;
						var w = scope.getWindowDimensions();

						// this is to clear map popover if it's visible.
						d3.select(".trend-viz").on("mouseover", function() {
							d3.select(".map_popover").style("display", "none");
						});

						$(".backbtn").click(function() {
											console.log("map area type"+ scope.selectedMapAreaType);
											scope.selectMapAreaType(scope.selectedMapAreaType);
											scope.shoulddisappear = "true";
						});
						function onmousemove(d) {
							d3
									.select(".map_popover")
									.style("display", "block")
									.style("left", (d3.event.pageX) - 80 + "px")// TODO
									// make it dynamic so that position would be according to the text length
									.style("top", (d3.event.pageY - 80) + "px")
									.style("opacity", "1");

						}

						function onover(d) {
							d3.selectAll(".activehover").classed("activehover",
									false);
							var rank;
							var value;
							d3.select(".map_popover_content").html(
									"<strong>Area Name:</strong> <span style='color:#fec02c'>"
											+ d.properties.NAME1_ + "</span>");

							if (d.properties.utdata && d.properties.utdata.rank) {
								rank = d.properties.utdata.rank;
								value=d.properties.utdata.value;
							} else {
								rank = "Not Available";
								value= "Not Available";
							}
							
							if(w.w < 992)
								{
								d3.select(".map_popover_close").html(
										"<strong>Value:</strong> <span style='color:#fec02c>"
												+ value + "</span> &nbsp &nbsp" + 
										"<strong>Rank:</strong> <span style='color:#fec02c'>"
												+ rank + "</span>");
								}
							else
							{
								d3.select(".map_popover_close").html(
										"<strong>Rank:</strong> <span style='color:#fec02c'>"
												+ rank + "</span>");
							}
								
							d3.select(this.parentNode.appendChild(this))
									.classed("activehover", true);
						}

						function drilldown(d) {

						//	if (d.properties && d.properties.NAME1_ == "TamilNadu") {
								scope.shoulddisappear = false;

								if (d.properties.NAME1_
										&& scope.shouldDrilldown) {
									scope.shoulddisappear = false;

									scope.closeViz();
									d3.select(".map_popover").style("display",
											"none");
									scope.selectedGranularity = new ValueObject(
											d.properties.ID_,
											d.properties.NAME1_);
									scope.selectedChildAreaLevel = scope.selectedChildAreaLevel + 1;
									var url = "resources/geomaps/"
											+ d.properties.NAME1_ + ".json";
									scope.mapSetup(url, function() {
										scope.getutdata();
									});

									$(".backbtn").toggleClass("hidden");

								}
							//}
						}
						scope.isIndiaMap=true;
						scope.mapSetup = function(url, callbackMethod) {
							// console.log(url);
							// alert(result);
							d3.select(el).selectAll("*").remove();
							
							var feature = "";
							var height = w.h - 190, centered
							if(w.w > 992)
							{
								width = 1000;
							}
							else
							{
								var width = w.w;
							}
							var projection = d3.geo.mercator().scale(1);
							var path = d3.geo.path().projection(projection);

							var svg = d3.select(el).append("svg").attr("id","mapsvg").attr("width", width).attr("height", height);
							svg.append("rect").attr({style : "fill:none;pointer-events:all;"})
							.attr("width", width)
							.attr("height", height)
							.on("click", clicked)
							.on("mouseover",function() {
										d3.select(".map_popover").style("display", "none");
										d3.selectAll(".activehover").classed("activehover", false);
							});

							var g = svg.append("g").attr("id", "mapg");

							d3.json(url,function(error, us) {
												feature = topojson.feature(us,us.objects.layer);

												var b = path.bounds(feature), s = 0.95 / Math.max((b[1][0] - b[0][0])/ width,(b[1][1] - b[0][1])/ height);
												projection.scale(s);
												b = d3.geo.bounds(feature);
												projection.center([(b[1][0] + b[0][0]) / 2,(b[1][1] + b[0][1]) / 2 ]);
												projection.translate([ 600,height / 2 ]);

												g.append("g").attr("id", "districts")
														.selectAll("path")
														.data(topojson.feature(us,us.objects.layer).features)
														.enter()
														.append("path")
														.attr("d", path)
														.on("mousedown",clickHandler)
														.on("mouseover", onover);

												g.on("mousemove", onmousemove);
												
												// --------------------------------------------------------------
												// Labeling for bihar state
												// only//////
												//var result = url.match(/TamilNadu/i);
//												if (result) {
													scope.isIndiaMap=false;
													g.selectAll("text")
													.data(topojson.feature(us,us.objects.layer).features)
													.enter().append("svg:text")
													.attr("x",function(d) {
														if (d.properties.NAME1_ == "Goalpara") {
															return (path.centroid(d)[0])/1.01;
														}else if (d.properties.NAME1_ == "Nagaon") {
															return (path.centroid(d)[0])/1.02;
														}else if (d.properties.NAME1_ == "Golaghat") {
															return (path.centroid(d)[0])/0.98;
														}else if (d.properties.NAME1_ == "Kamrup Metro") {
															return (path.centroid(d)[0])/0.98;
														}else {
															return path.centroid(d)[0];
														}
													})
													.attr("y",function(d) {
														if (d.properties.NAME1_ == "Barpeta") {
															return (path.centroid(d)[0])/1.99;
														
														}else if (d.properties.NAME1_ == "Kamrup") {
															return (path.centroid(d)[0])/1.90;
														
														}else if (d.properties.NAME1_ == "Karimganj") {
															return (path.centroid(d)[0])/1.40;
														
														}else if (d.properties.NAME1_ == "Goalpara") {
															return (path.centroid(d)[0])/1.63;
														}else {
															return path.centroid(d)[1];
														}
													})
													.attr('font-size','10px')
													.on("mousedown", clickHandler)
													.on("mouseover", onover)
													.on("mousemove", onmousemove);
//												} else {
//													scope.isIndiaMap=true;
//													g.selectAll("text")
//													.data(topojson.feature(us,us.objects.layer).features)
//													.enter()
//													.append("svg:text")
//													.attr("id",function(d) {
//														return d.properties.NAME1_;
//													})
//													.attr("x",function(d) {
//														return path.centroid(d)[0];
//													})
//													.attr("y",function(d) {
//														return path.centroid(d)[1];
//													})
//													.attr('font-size','10px')
//													.on("mousedown", clickHandler)
//													.on("mouseover", onover)
//													.on("mousemove", onmousemove);
//
//												}

												if (callbackMethod)
													callbackMethod();
											});

							function clickHandler(d) {
								clicks++; // count clicks

								if (clicks === 1) {

									timer = setTimeout(function() {

										clicked(d); // perform
										// single-click
										// action
										clicks = 0; // after action performed,
										// reset counter
									}, DELAY);

								} else {
									clearTimeout(timer); // prevent
									// single-click
									// action

									drilldown(d); // perform
									// double-click
									// action
									clicks = 0; // after action performed, reset
									// counter
								}
							}

							function clicked(d) {

								var x, y, k;
								if (d && centered !== d) {
									var centroid = path.centroid(d);
									x = centroid[0];
									y = centroid[1];
									k = 2.5;
									centered = d;
								} else {
									x = (width / 2) - 36;// this is to fix
									// the movement of
									// map when clicked.
									y = height / 2;
									k = 1;
									centered = null;
								}

								g.selectAll("path").classed("active",
										centered && function(d) {
											return d === centered;
										});

								g.transition().duration(750).attr(
										"transform",
										"translate(" + width / 2 + "," + height
												/ 2 + ")scale(" + k
												+ ")translate("
												+ (-x - width * 3 / 100) + ","
												+ -y + ")").style(
										"stroke-width", 1.5 / k + "px");
								scope.disablePdf = (d == null) ? false
										: scope.selectedArea == null ? true
												: d == scope.selectedArea ? false
														: true;
								scope.showViz(d);
							}

						};

						scope.$watch("utdata",function() {
							
							var insertLinebreaks = function (d) {
								if(d.properties.utdata){
									 var el = d3.select(this);
									    if(scope.isIndiaMap){
									    	//if(d.properties.NAME1_ == "Tamil Nadu"){
									    		el.selectAll("*").remove();
									    		el.append('tspan').style("text-anchor","middle").text(d.properties.SHORTNAME1_);
											    el.append('tspan').style("text-anchor","middle").text(parseFloat(d.properties.utdata.value)).attr('x', el.attr("x")).attr('dy', '15');
									    	//}
									    }else{
									    	el.selectAll("*").remove();
									    	if(w.w > 992)
									    		{
									    	el.append('tspan').style("text-anchor","middle").text(d.properties.SHORTNAME1_);
										    el.append('tspan').style("text-anchor","middle").text((d.properties.utdata.value)).attr('x', el.attr("x")).attr('dy', '15');
									    		}
									    }
								}
							};
							d3.select("#mapsvg").selectAll("text").selectAll("*").remove();				
							d3.select("#mapsvg").selectAll("path")
							.attr("class",function(d) {
								if (!(scope.utdata && scope.utdata.dataCollection)) {
									d.properties.utdata = null;
									return;
								}
								for (var i = 0; i < scope.utdata.dataCollection.length; i++) {
									if (d.properties && d.properties.ID_ == scope.utdata.dataCollection[i].areaCode) {
										d.properties.utdata = scope.utdata.dataCollection[i];
										return scope.utdata.dataCollection[i].cssClass;
									} else {
										if (d.properties) {
											d.properties.utdata = null;
										}
									}
								}
							});
							d3.select("#mapsvg").selectAll("text").each(insertLinebreaks);
							
						}, true);						
						scope.$watch(scope.getWindowDimensions, function(
								newValue, oldValue) {
							scope.svgHeight = (newValue.h - 190);
							scope.svgWidth = (newValue.w);
							scope.style = function() {
								return {
									'height' : (newValue.h - 190) + 'px',
									'width' : (newValue.w) + 'px'
								};
							};
							w = scope.getWindowDimensions();
							width = w.w, height = w.h;
//							d3.select("#mapsvg").attr({
//								width : width,
//								height : height - 190
//							});
//							d3.select("#mapsvg").selectAll("rect").attr({
//								width : width,
//								height : height - 190
//							});

						}, true);
					}
					return {
						link : link,
						restrict : "E"
					};
				});

app
		.directive(
				"samikshaLine",
				function($window) {
					function link(scope, el) {

						var el = el[0];
						var clicks = 0;

						// Render graph based on 'data'
						scope.$watch("dataprovider", function(data) {
							// remove all
							d3.select("#trendsvg").remove();
							var margin = {
								top : 20,
								right : 55,
								bottom : 60,
								left : 80
							}, width = $(document).outerWidth() * 39 / 100

							- margin.left - margin.right, height = 250
									- margin.top - margin.bottom;

							// set the ranges
							var x = d3.scale.ordinal().rangeRoundBands(
									[ 0, width ], 1.0);
							var y = d3.scale.linear().rangeRound([ height, 0 ]);

							// define the axis
							var xAxis = d3.svg.axis().scale(x).orient("bottom")
									.ticks(5);
							var yAxis = d3.svg.axis().scale(y).orient("left")
									.ticks(5);

							// // Define the line
							var lineFunction = d3.svg.line().x(function(d) {
								return x(d.date);
							}).y(function(d) {
								return y(d.value);
							}).interpolate("line");

							// Adds the svg canvas
							var svg = d3.select(el).append("svg").attr("id",
									"trendsvg").attr("width",
									width + margin.left + margin.right).attr(
									"height",
									height + margin.top + margin.bottom)
									.append("g").attr(
											"transform",
											"translate(" + margin.left + ","
													+ margin.top + ")").style(
											"fill", "#fff");

							// Get the data
//							data.forEach(function(d) {
//								d.date = d.date;
//								d.value = +d.value;
//							});

							x.domain(data.map(function(d) {
								return d.date;
							}));
							y.domain([ 0, d3.max(data, function(d) {
								return parseFloat(d.value);
							}) ]);

							// Nest the entries by symbol
							var dataNest = d3.nest().key(function(d) {
								return d.source;
							}).entries(data);

							// Loop through each symbol / key
							var color = d3.scale.category10(); // d3.scale.ordinal().range(
							// [ "#bcbd22", "#e377c2" , "#2ca02c",
							// "#475003", "#9c8305" ,"#101b4d" , "#17becf"]);;
							// Add the X Axis
							svg.append("g").attr("class", "x axis").attr(
									"transform", "translate(0," + height + ")")
									.call(xAxis).append("text").attr("x",
											width - margin.right).attr("y",
											margin.bottom).attr("dx", ".60em")
									.style("text-anchor", "right").text(
											"Time Period").style("fill",
											"#FFFFFF");

							d3.selectAll(".tick text").style("text-anchor",
									"end").attr("dx", "-.8em").attr("dy",
									".15em").attr("transform", function(d) {
								return "rotate(-45)";
							});

							// xsvg;

							// Add the Y Axis
							svg.append("g").attr("class", "y axis").call(yAxis)
									.append("text").attr("transform",
											"rotate(-90)").attr("y", 7).attr(
											"dy", ".60em").style("text-anchor",
											"end").text("Value").style("fill",
											"#FFFFFF");
							
							
							  
							// adding multiple line chart

//							for (var index = 0; index <  dataNest.length; index++) {
							 dataNest.forEach(function(d, i){

								var series = svg.selectAll(".series")
								.data(dataNest[i].values)
								.enter()
								.append("g")
								.attr("class", "series")
								.attr("id","tag" + dataNest[i].key);

								
								series.select(".line").data(function() {
									return dataNest[i].values;
								}).enter().append("path")
								.attr("class", "line")
								.attr("id", "tag" + dataNest[i].key)
								.attr("d",
										function(d) {
											return lineFunction(dataNest[i].values);
												}).style("stroke", function(d) {
											return color(dataNest[i].key);
										}).style("stroke-width", "2px").style(
												"fill", "none");

								series.select(".point").data(function() {
									return dataNest[i].values;
								}).enter().append("circle").attr("id",
										"tag" + dataNest[i].key).attr(
										"class", "point").attr("cx",
										function(d) {
											return x(d.date);
										}).attr("cy", function(d) {
									return y(d.value);
								}).attr("r", "3px").style("fill", function(d) {
									return color(dataNest[i].key);
								}).style("stroke", "grey").style(
										"stroke-width", "2px").on("mouseover",
										function(d) {
											showPopover.call(this, d);
										}).on("mouseout", function(d) {
									removePopovers();
								});

								svg.append("text").attr("x", width - 25)// 
								.attr("y", (i * 30) + 10)// ("y", height
								// +
								// (margin.right
								// / 2) + 5)

								.attr("class", "legend").style("fill",
										function() {
											return color(dataNest[i].key);
										}).text(dataNest[i].key);

								svg.append("rect").attr("x", width + 4)// author
								// anyatama
								.attr("y", i * 30).attr("rx", 2).attr("ry",
										2).attr("width", 10).attr("height", 10)
										.style("fill", function(d) {
											return color(dataNest[i].key);
										}).attr("id", 'rext' + i).attr(
												"key", dataNest[i].key)
										.style("stroke", "grey")
										.on("click", function() { // ************author:kamal***
											// Determine if current line is
											// visible
											rectClickHandler.call(this);
										});
								
							});

							// End adding multiple line chart

							// click handler for hiding series data
							function rectClickHandler() {

								var disName;
								var fillColor;
								if (d3
										.select(
												"#tag"
														+ dataNest[parseInt($(this)[0].id
																.substr($(this)[0].id.length - 1))].key)
										.style("display") == "none") {
									disName = "block";
									fillColor = color(dataNest[parseInt($(this)[0].id
											.substr($(this)[0].id.length - 1))].key);
								} else {
									disName = "none";
									fillColor = "#464646";
								}
								svg.selectAll("#" + $(this)[0].id + "").style(
										"fill", fillColor);
								svg
										.selectAll(
												"#tag"
														+ dataNest[parseInt($(this)[0].id
																.substr($(this)[0].id.length - 1))].key)
										.style("display", disName);
							}

							function removePopovers() {
								$('.popover').each(function() {
									$(this).remove();
								});
							}
							function showPopover(d) {
								$(this).popover(
										{
											title : '',
											placement : 'auto top',
											container : 'body',
											trigger : 'manual',
											html : true,
											content : function() {
												return "Time Period: " + d.date
														+ "<br/>Value: "
														+ d.value;
											}
										});
								$(this).popover('show');
							}
				});
					}
					return {
						restrict : "E",
						scope : {
							dataprovider : "="
						},
						link : link
					};

				});
app
		.directive(
				"sdrcBarChart",
				function($window) {
					function link(scope, el) {
						var el = el[0];
						
						scope.$watch("dataprovider", function(data) {

							// =========================================================Start
							// kamal code
							// ===========================================
							d3.select("#columnbarChart").remove();
							var margin = {
								top : 20,
								right : 55,
								bottom : 30,
								left : 40
							}, width = $(document).outerWidth() * 39 / 100

							- margin.left - margin.right, height = 240
									- margin.top - margin.bottom;

							var x0 = d3.scale.ordinal().rangeRoundBands(
									[ 0, width ], .1);
							var x1 = d3.scale.ordinal();
							var y = d3.scale.linear().range([ height, 0 ]);
							var color = d3.scale.category10();// d3.scale.ordinal().range(["#98abc5",
							// "#8a89a6",
							// "#7b6888",
							// "#6b486b",
							// "#a05d56",
							// "#d0743c",
							// "#ff8c00"]);

							var xAxis = d3.svg.axis().scale(x0)
									.orient("bottom");
							var yAxis = d3.svg.axis().scale(y).orient("left")
									.tickFormat(d3.format(".2s"));

							var svg = d3.select(el).append("svg").attr("id",
									"columnbarChart").attr("width",
									width + margin.left + margin.right).attr(
									"height",
									height + margin.top + margin.bottom)
									.append("g").attr(
											"transform",
											"translate(" + margin.left + ","
													+ margin.top + ")").style(
											"fill", "#FFFFFF");
							var ageNames = [];
							// console.log(ageNames);
							data.forEach(function(d) {
								//multi column chart//
								
								//modifications by anya
								var ageName = d3.keys(d).filter(
										function(key) {
											return key !== "timePeriod"
													&& key !== "dataVaule";
										});
								ageNames.push(ageName[0]);
								// console.log(d);
								d.dataVaule = ageName.map(function(name) {
									return {
										name : name,
										value : +d[name]
									};
								});
							});

							x0.domain(data.map(function(d) {
								return d.timePeriod;
							}));
							x1.domain(ageNames).rangeRoundBands(
									[ 0, x0.rangeBand() ]);
							y.domain([ 0, d3.max(data, function(d) {
								return d3.max(d.dataVaule, function(d) {
									return d.value;
								});
							}) ]);

							svg.append("g").attr("class", "x axis").attr(
									"transform", "translate(0," + height + ")")
									.call(xAxis).append("text").attr("x",
											width - margin.right).attr("y",
											margin.bottom).attr("dx", ".71em")
									.style("text-anchor", "middle").text(
											"Time Period").style("fill",
											"#FFFFFF");
							svg.append("g").attr("class", "y axis").call(yAxis)
									.append("text").attr("transform",
											"rotate(-90)").attr("y", 6).attr(
											"dy", ".71em").style("text-anchor",
											"end").text("Value");// To be
							// changed

							var state = svg.selectAll(".state").data(data)
									.enter().append("g").attr("class", "g")
									.attr(
											"transform",
											function(d) {
												return "translate("
														+ x0(d.timePeriod)
														+ ",0)";
											});

							state.selectAll("rect").data(function(d) {
								return d.dataVaule;
							}).enter().append("rect").attr("id", function(d) {
								return d.name;
							}).attr("width", x0.rangeBand()).attr("x",
									function(d) {
										return x0(d.name);
									}).attr("class", function(d) {
								return x0(d.name);
							}).attr("y", function(d) {
								return y(d.value);
							}).attr("height", function(d) {
								return height - y(d.value);
							}).on("mouseover", function(d) {
								showPopover.call(this, d);
							}).on("mouseout", function(d) {
								removePopovers();

							}).style("fill", function(d) {
								return color(d.name);
							});
							//modifications by anya
							var uniqueNames = [];
							$.each(ageNames, function(i, el){
							    if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
							});
							
							var legend = svg.selectAll(".legend").data(
									uniqueNames.slice().reverse()).enter().append(
									"g").attr("class", "legend").attr(
									"transform", function(d, i) {
										return "translate(0," + i * 20 + ")";
									});

							legend.append("rect").attr("x", width + 42).attr(
									"rx", 2).attr("ry", 2).attr("width", 10)
									.attr("height", 10).attr("id", function(d) {
										return "rect_" + d;
									}).on(
											"click",
											function(d) {
												var disName;
												var fillColor;
												if (d3.select("#" + d).style(
														"display") == "none") {
													disName = "block";
													fillColor = color;
												} else {
													disName = "none";
													fillColor = "#464646";
												}
												d3.selectAll("#" + d) // *********
												.transition().duration(100) // ************
												.style("display", disName); // ************
												svg.selectAll("#rect_" + d)
														.style("fill",
																fillColor);
											}).style("fill", color).style(
											"stroke", "grey");

							legend.append("text").attr("x", width + 35).attr(
									"y", 6).attr("dy", ".35em").style(
									"text-anchor", "end").text(function(d) {
								return d;
							}).style("fill", color);
							// =========================================================End
							// kamal code
							// =============================================
							function removePopovers() {
								$('.popover').each(function() {
									$(this).remove();
								});
							}
							function showPopover(d) {
								$(this).popover(
										{
											title : '',
											placement : 'auto top',
											container : 'body',
											trigger : 'manual',
											html : true,
											content : function() {
												return "Source :" + d.name
														+ "<br/>"
														+ "Data Value : "
														+ d.value;
											}
										});
								$(this).popover('show');
							}
						
							
						});

					}
					return {
						restrict : "E",
						scope : {
							dataprovider : "="
						},
						link : link
					};

				});


app
.directive(
		"sdrcTableHeaderFix",
		function($window, $compile) {
			function link(scope, el) {
				var tableUniqClass = "";
				
				scope.$watch("tableuniqueclass", function(uniqClass) {
					tableUniqClass = uniqClass;
					function createStaticHeader(uniqClass){
						$("."+uniqClass).before('<div class="static-header-container"><div class="static-header ' + uniqClass +'-static-head"></div></div>');
					}
					if(uniqClass)
					createStaticHeader(uniqClass);	
				});
				
				scope.$watch("tabledata", function(data) {
					function fixTableHeader(uniqClass) {
						setTimeout(function(){
							
							if($("."+uniqClass)[0].offsetWidth >= $("."+uniqClass)[0].clientWidth){
								var i=0;rowWidthList=[];
//								angular.element("."+uniqClass+"-static-head").html($("."+uniqClass).html());
								$("."+uniqClass).find("table").clone().appendTo("."+uniqClass+"-static-head");
//								$("."+uniqClass).siblings(".static-header-container").find(".static-header").find("table thead").css("visibility", "visible");
								$("."+uniqClass).scrollLeft(0);
								$("."+uniqClass).siblings(".static-header-container").height($("."+uniqClass).siblings(".static-header-container").find(".static-header table thead").height());
								$("."+uniqClass).css("margin-top", -$("."+uniqClass).siblings(".static-header-container").find(".static-header table thead").height()+"px");
//								$("."+uniqClass).parent()
								$("."+uniqClass).siblings(".static-header-container").css({
									 'overflow-x': 'hidden',
									 'overflow-y': 'hidden',
									 'position': 'relative',
									 'clear': 'both'	 
								});
								$("."+uniqClass).siblings(".static-header-container").find(".static-header").css({
									 'overflow-x': 'auto',
									 'overflow-y': 'hidden'
								})
								$("."+uniqClass).find("table").css({"margin-bottom": "0px"});
//								$("."+uniqClass).find("table thead").css("visibility", "hidden");
								if($(window).width() > 845){
									if($("."+uniqClass)[0].offsetWidth == $("."+uniqClass)[0].clientWidth){
										if($("."+uniqClass).find("table").height() > $("."+uniqClass).height())
											$("."+uniqClass).siblings(".static-header-container").css({'margin-right': "17px"});
										else
											$("."+uniqClass).siblings(".static-header-container").css({'margin-right': "0px"});

									}
									else{
										if($("."+uniqClass).find("table").height() + 17 > $("."+uniqClass).height())
											$("."+uniqClass).siblings(".static-header-container").css({'margin-right': "17px"});
										else
											$("."+uniqClass).siblings(".static-header-container").css({'margin-right': "0px"});

									}
																	}
//								$compile(angular.element(".static-header-container thead th "))(angular.element("body").scope());
								var ignoreTableBodyScroll = false;
								var ignoreTableHeadScroll = false;
								/*$("."+uniqClass+"-static-head").find("table thead th").click(function(){
									angular.element("body").scope().order($(this).text().trim());
								})*/
								$(".header-fixed-table").scroll(function(){
									if($(this).scrollTop() > 2){
										$(this).find("thead").css("visibility", "hidden");
										$(this).prev().find(".static-header table thead").css("visibility", "visible");
									}
									else{
										$(this).find("thead").css("visibility", "visible");
										$(this).prev().find(".static-header table thead").css("visibility", "hidden");
									}
										if(!ignoreTableBodyScroll){
											ignoreTableHeadScroll = true;
										$(this).prev().find(".static-header").scrollLeft($(this).scrollLeft());
										
										}
									
										ignoreTableBodyScroll = false;
										 $('[data-toggle="tooltip"]').tooltip();
								}); 
								$(".static-header").scroll(function(){
									if(!ignoreTableHeadScroll){
										ignoreTableBodyScroll = true;
									$(this).parent().siblings(".table-header-fixed, .header-fixed-table ").scrollLeft($(this).scrollLeft());
									
									}
									ignoreTableHeadScroll = false;
									 $('[data-toggle="tooltip"]').tooltip();
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

