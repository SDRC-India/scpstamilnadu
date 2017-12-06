$(".download-container").hover(function(){
	if(parseFloat($(this).css("right").slice(0, -2)) < -50)
	$(this).animate({right: 0});
}, function(){
	var self = this;
	if(parseFloat($(this).css("right").slice(0, -2)) > -50)
		$(this).animate({right: '-108px'});
	else
		setTimeout(function(){
			$(self).animate({right: '-108px'});
		}, 2000);
});
$(".download-container-excel").hover(function(){
	if(parseFloat($(this).css("right").slice(0, -2)) < -50)
	$(this).animate({right: 0});
}, function(){
	var self = this;
	if(parseFloat($(this).css("right").slice(0, -2)) > -50)
	$(this).animate({right: '-114px'});
	else
		setTimeout(function(){
			$(self).animate({right: '-114px'});
		}, 2000);
});
myAppConstructor.directive('onlySixDigits', function () {

  return {
      restrict: 'A',
      require: '?ngModel',
      link: function(scope, element, attrs, ngModelCtrl) {
			if(!ngModelCtrl) {
				return; 
			}
			
			ngModelCtrl.$parsers.push(function(val) {
				if (angular.isUndefined(val)) {
					var val = '';
				}
				
				var clean = val.replace(/[^0-9]/g, '');
				if(!angular.isUndefined(clean)) {
	            	 var num=0;
	            	 if(clean.length>5 ){
	            		 num =clean.slice(0,6);
	            		 clean= num;
	            	 }
	            		 
	             }
				
				if (val !== clean) {
					ngModelCtrl.$setViewValue(clean);
					ngModelCtrl.$render();
				}
				return clean;
			});
			
			element.bind('keypress', function(event) {
				if(typeof InstallTrigger !== 'undefined'){
					if(event.charCode === 101 || event.charCode === 46 || event.charCode === 32) {
						event.preventDefault();
					}
				}
				else{
					if(event.keyCode === 101 || event.keyCode === 46 || event.keyCode === 32) {
						event.preventDefault();
					}
				}
				
			});
		}
  };
});
myAppConstructor.
directive('onlySevenDigits', function () {

  return {
      restrict: 'A',
      require: '?ngModel',
      link: function(scope, element, attrs, ngModelCtrl) {
			if(!ngModelCtrl) {
				return; 
			}
			
			ngModelCtrl.$parsers.push(function(val) {
				if (angular.isUndefined(val)) {
					var val = '';
				}
				
				var clean = val.replace(/[^0-9]/g, '');
				if(!angular.isUndefined(clean)) {
	            	 var num=0;
	            	 if(clean.length>6 ){
	            		 num =clean.slice(0,7);
	            		 clean= num;
	            	 }
	            		 
	             }
				
				if (val !== clean) {
					ngModelCtrl.$setViewValue(clean);
					ngModelCtrl.$render();
				}
				return clean;
			});
			
			element.bind('keypress', function(event) {
				if(typeof InstallTrigger !== 'undefined'){
					if(event.charCode === 101 || event.charCode === 46 || event.charCode === 32) {
						event.preventDefault();
					}
				}
				else{
					if(event.keyCode === 101 || event.keyCode === 46 || event.keyCode === 32) {
						event.preventDefault();
					}
				}
				
			});
		}
  };
});



myAppConstructor.directive('hundredfiftyCharactersValidation', function () {

  return {
      restrict: 'A',
      require: '?ngModel',
      link: function(scope, element, attrs, ngModelCtrl) {
			if(!ngModelCtrl) {
				return; 
			}
			
			ngModelCtrl.$parsers.push(function(val) {
				if (angular.isUndefined(val)) {
					val = '';
				}
				
				var clean = val;
				if(!angular.isUndefined(clean)) {
	            	 var num=0;
	            	 if(clean.length>150 ){
	            		 num =clean.slice(0,150);
	            		 clean= num;
	            	 }
	            	 clean = clean.replace(/[^a-zA-z ,()]/g, '');
	            	clean = clean.replace('^', '');
	            	clean = clean.replace(/\\/g, '');
	            	clean = clean.replace('[', '');
	            	clean = clean.replace(']', '');
	            	clean = clean.replace('_', '');
	            	
	            	
	             }
				
				if (val !== clean) {
					ngModelCtrl.$setViewValue(clean);
					ngModelCtrl.$render();
				}
				return clean;
			});
		}
  };
});

myAppConstructor.directive('fourhundredCharactersValidation', function () {
  return {
      restrict: 'A',
      require: '?ngModel',
      link: function(scope, element, attrs, ngModelCtrl) {
			if(!ngModelCtrl) {
				return; 
			}
			
			ngModelCtrl.$parsers.push(function(val) {
				if (angular.isUndefined(val)) {
					val = '';
				}
				
				var clean = val;
				if(!angular.isUndefined(clean)) {
	            	 var num=0;
	            	 if(clean.length>400 ){
	            		 num =clean.slice(0,400);
	            		 clean= num;
	            	 }
	            	 clean = clean.replace(/[^a-zA-z ,()]/g, '');
	            	clean = clean.replace('^', '');
	            	clean = clean.replace(/\\/g, '');
	            	clean = clean.replace('[', '');
	            	clean = clean.replace(']', '');
	            	clean = clean.replace('_', '');
	            	
	            	
	             }
				
				if (val !== clean) {
					ngModelCtrl.$setViewValue(clean);
					ngModelCtrl.$render();
				}
				return clean;
			});
		}
  };
});

myAppConstructor.directive('activeLink', function () {
    return {
        link: function (scope, element, attrs) {
            element.find('.nav a').on('click', function () {
                angular.element(this)
                    .parent().siblings('.selectedActive')
                    .removeClass('selectedActive');
                angular.element(this)
                    .parent()
                    .addClass('selectedActive');
            });
        }
    };
});
//myAppConstructor.directive('hideZero', function() {
//    return {
//        link: function(scope, element) {
//            element.on('input change', function() {
//                if (this.value === '0') {
//                    this.value = '';
//                }
//            })
//        }
//    };
//})