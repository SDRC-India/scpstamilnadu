/*
 * @author Swarna(swarnaprava@sdrc.co.in) 
 * @author Pratyush(pratyush@sdrc.co.in)
 */

function indicatorManagementController($scope, $http, $log) {
	$("#loader-mask").show();
	$scope.activeMenu = "indicator-management";
	$scope.pageName = "Indicator Management";
	$scope.indicatorMgmt = {};
	$scope.isSelected = false;
	$scope.metadataLimit = 400;
	$http.get('initIndicatorManagementView').then(
			function(result) {
				$log.info(result.data);
				$scope.sector = result.data.sectors;
				$scope.roles = result.data.roles;
				$scope.subgroups = result.data.subgroups;
				$scope.units = result.data.units;
				$scope.highIsGood = result.data.highIsGood;
				$scope.monthYears = [];

				angular.forEach(result.data.indicatorPublishMonthYear,
						function(value, key) {
							$scope.monthYears.push(value);
						});
				$("#loader-mask").fadeOut();
			}, function(error) {
				$log.error(error);
				$("#loader-mask").fadeOut();
			});
	
	
	$scope.selectSector = function(sector) {
		if ($scope.selectedSector != sector) {
			$scope.selectedSubSector = null;
			$scope.selectedSector = sector;
			if ($scope.selectedSector.isIndex) {
				$scope.selectedRole = undefined;
				$("#role-id").attr("disabled", "disabled");
			}
			$scope.selectedUnit = undefined;
			$scope.subSector = sector.subSectors;
//			if ($scope.selectedSubSector == null) {
//				$scope.indicatorMgmt.indicatorName = "";
//				$scope.indicatorMgmt.numeratorName = "";
//				$("#numerator").removeAttr('disabled');
//				$scope.indicatorMgmt.denominatorName = "";
//				$("#denominator").removeAttr('disabled');
//				$scope.indicatorMgmt.metadata = "";
//				$("#meta-data").removeAttr('disabled');
//				$scope.selectedRole = undefined;
//				$scope.selectedhighisGood = undefined;
//				if (!$scope.selectedSector.isIndex) {
//					$("#role-id").removeAttr('disabled');
//					$("#high-is-good-id").removeAttr('disabled');
//				}
//			}
		}
	};
	var indicators = [];
	$scope.selectSubSector = function(subSector) {
		indicators = [];
		$scope.selectedSubSector = subSector;
		for (var i = 0; i < subSector.indicators.length; i++) {
			indicators.push(subSector.indicators[i].name);
		}
		$("#indicator").autocomplete({
			source : indicators,
		});
		$scope.indicatorMgmt.indicatorName = "";
		$scope.indicatorMgmt.numeratorName = "";
		$("#numerator").removeAttr('disabled');
		$scope.indicatorMgmt.denominatorName = "";
		$("#denominator").removeAttr('disabled');
		$scope.indicatorMgmt.metadata = "";
		$("#meta-data").removeAttr('disabled');
		$scope.selectedRole = undefined;
		$scope.selectedSubgroup = undefined;
		$scope.selectedUnit = undefined;
		$scope.selectedhighisGood = undefined;
		$scope.selectedMonth = undefined;
		$scope.selectedYear = undefined;

		if ($scope.selectedSector.isIndex) {
			for (var i = 0; i < $scope.units.length; i++) {
				if ($scope.units[i].indexType) {
					$scope.selectedUnit = $scope.units[i];
					$("#unit-id").attr("disabled", "disabled");
					break;
				}
			}
			for (var i = 0; i < $scope.highIsGood.length; i++) {
				if ($scope.highIsGood[i].value == true) {
					$scope.selectedhighisGood = $scope.highIsGood[i];
					$("#high-is-good-id").attr("disabled", "disabled");
					break;
				}
			}

		} else {
			// if sector is not index type, by default we set unit to
			// percentage
			for (var i = 0; i < $scope.units.length; i++) {
				if ($scope.units[i].indexType == false) {
					$scope.selectedUnit = $scope.units[i];
					$("#unit-id").attr("disabled", "disabled");
					break;
				}
			}

		}

	};

	/** *** existed indicator list **** */

	$("#indicator")
			.autocomplete(
					{
						// source: indicators,
						select : function(key, value) {
							$scope.isSelected = true;
							for (var i = 0; i < $scope.subSector.length; i++) {
								for (var j = 0; j < $scope.subSector[i].indicators.length; j++) {
									if ($scope.subSector[i].indicators[j].name == value.item.label) {
										if (!$scope.subSector[i].indicators[j].isAdded) {
											$scope.indicatorMgmt.numeratorName = $scope.subSector[i].indicators[j].numerator;
											$("#numerator").attr("disabled",
													"disabled");
											$scope.indicatorMgmt.denominatorName = $scope.subSector[i].indicators[j].denominator;
											$("#denominator").attr("disabled",
													"disabled");
											$scope.indicatorMgmt.metadata = $scope.subSector[i].indicators[j].metadata;
											// $("#meta-data").attr("disabled",
											// "disabled");
											angular
													.forEach(
															$scope.roles,
															function(value, key) {
																if (value.roleId == $scope.subSector[i].indicators[j].roleId) {
																	$scope
																			.selectRole(value);
																	// $("#role-id").attr("disabled",
																	// "disabled");
																}
															});
											$scope.$apply();
										} else {
											$('#warningMessage').modal("show");
										}
									}
								}
							}
						}
					});

	$scope.clrIndicator = function() {
		$scope.indicatorMgmt.indicatorName = "";
	};

	$scope.clearText = function(isSelected, model) {
		if (isSelected) {
			$scope.indicatorMgmt.indicatorName = "";
			$scope.indicatorMgmt.numeratorName = "";
			$("#numerator").removeAttr('disabled');
			$scope.indicatorMgmt.denominatorName = "";
			$("#denominator").removeAttr('disabled');
			$scope.indicatorMgmt.metadata = "";
			$("#meta-data").removeAttr('disabled');
			$scope.selectedRole = undefined;
			$scope.selectedSubgroup = undefined;
			$scope.selectedUnit = undefined;
			$scope.selectedhighisGood = undefined;
			$scope.selectedMonth = undefined;
			$scope.selectedYear = undefined;
			if (!$scope.selectedSector.isIndex)
				$("#role-id").removeAttr('disabled');
		}
		$scope.isSelected = false;
	};

	$scope.selectSubgroup = function(subgroup) {
		$scope.selectedSubgroup = subgroup;
	};

	$scope.selectUnit = function(unit) {
		$scope.selectedUnit = unit;
	};

	$scope.selectHighisgood = function(highisGood) {
		$scope.selectedhighisGood = highisGood;
	};

	/** *** end indicator list **** */

	$scope.selectRole = function(role) {
		$scope.selectedRole = role;
	};

	$scope.scrollBottom = function() {
		window.scrollTo(0, document.body.scrollHeight);
	};

	$scope.selectMonth = function(monthYear) {
		$scope.selectedMonth = monthYear.monthName;
		$scope.selectedYear = monthYear.year;
	};

	$scope.submitForm = function() {
		if ($scope.selectedSector == undefined) {
			$scope.validationMsg = "Please Select Sector";
			$('#errorMessage').modal("show");
		} else if ($scope.selectedSubSector == undefined) {
			$scope.validationMsg = "Please Select Subsector";
			$('#errorMessage').modal("show");
		} else if ($scope.indicatorMgmt.indicatorName == undefined
				|| $scope.indicatorMgmt.indicatorName.trim() == "") {
			$scope.validationMsg = "Please Enter Indicator name";
			$('#errorMessage').modal("show");
		} else if (!$scope.selectedSector.isIndex
				&& ($scope.indicatorMgmt.numeratorName == undefined || $scope.indicatorMgmt.numeratorName
						.trim() == "")) {
			$scope.validationMsg = "Please Enter Numerator name";
			$('#errorMessage').modal("show");
		} else if (!$scope.selectedSector.isIndex
				&& ($scope.indicatorMgmt.denominatorName == undefined || $scope.indicatorMgmt.denominatorName
						.trim() == "")) {
			$scope.validationMsg = "Please Enter Denominator name";
			$('#errorMessage').modal("show");
		} else if ($scope.selectedSubgroup == undefined) {
			$scope.validationMsg = "Please Select Subgroup";
			$('#errorMessage').modal("show");
		} else if ($scope.selectedUnit == undefined) {
			$scope.validationMsg = "Please Select Unit";
			$('#errorMessage').modal("show");
		} else if ($scope.selectedhighisGood == undefined) {
			$scope.validationMsg = "Please Select High is good";
			$('#errorMessage').modal("show");
		} else if (!$scope.selectedSector.isIndex
				&& $scope.selectedRole == undefined) {
			$scope.validationMsg = "Please Select Role";
			$('#errorMessage').modal("show");
		} else if ($scope.selectedMonth == undefined) {
			$scope.validationMsg = "Please Select Month";
			$('#errorMessage').modal("show");
		} else {
			$("#loader-mask").show();
			var finalObj = {};

			finalObj.sectorId = $scope.selectedSector.sectorId;
			finalObj.subsectorId = $scope.selectedSubSector.sectorId;
			finalObj.indicatorName = $scope.indicatorMgmt.indicatorName;
			finalObj.indicatorMetadata = $scope.indicatorMgmt.metadata;
			finalObj.highIsGood = $scope.selectedhighisGood.value;
			finalObj.numeratorName = $scope.indicatorMgmt.numeratorName;
			finalObj.denominatorName = $scope.indicatorMgmt.denominatorName;
			finalObj.unitId = $scope.selectedUnit.unitId;
			finalObj.subgroupId = $scope.selectedSubgroup.subgroupId;
			finalObj.index = $scope.selectedSector.isIndex;
			finalObj.overallIndex = $scope.selectedSubSector.isOverallIndex;
			finalObj.roleId = !finalObj.index ? $scope.selectedRole.roleId
					: null;
			finalObj.publishMonth = $scope.selectedMonth;
			finalObj.publishYear = $scope.selectedYear;

			$http.post('addNewIndicator', finalObj).success(function(result) {
				$log.info(result);
				
				if (result.key = '200') {
					$scope.infoMsg = result.value;
					$("#infoMessage").modal("show");
				}else{
					$scope.validationMsg =result.value;
					$("#errorMessage").modal("show");
				}
				$("#loader-mask").fadeOut();
			}, function(error) {
				$log.error(error);
				$("#loader-mask").fadeOut();
			});
		}
	};
	console.log("url: " + agencyUrl);
	$scope.redirect = function() {
		window.location = "";
	};
}