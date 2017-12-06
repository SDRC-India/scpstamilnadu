/*
 * @author Swarna(swarnaprava@sdrc.co.in) 
 */

function loginController($scope, $http, $timeout){
	
	 $scope.IsVisible = false;
	 $scope.setpassIsVisible = false;
	 $scope.invalidOTP = false;
	 $scope.ifOtpmatched = false;
	 $scope.username = null;
	 $scope.otpfornewPassword = null;
	 $scope.UserOtp = null;
	 $scope.infoMsg = null;
     $scope.sendOTP = function (name,errorId) {
    	 
    	 //if username is blank
    	 
     	 if(name == undefined || name == " " )
    		 {
     		 $scope.IsVisible =  false;	
     		 $scope.setpassIsVisible = false;
    		 document.getElementById(errorId).innerHTML = "Please enter your Username";
    		 } 
     	 else
     		 {
     		 document.getElementById(errorId).innerHTML = " ";
     		 
     		 // sending otp to username
     		 
    		 $http.post("forgotPassword?username=" +name).then(function(result) {
    			 $("#loader-mask").show();
    			 $scope.username= name;
    			 document.getElementById('usernameError').innerHTML = "";
    			 $scope.UserOtp = result.data;
    			 $scope.IsVisible =  true;	
    			 $scope.setpassIsVisible = false;
			if($scope.UserOtp.valid == "true"){
				document.getElementById(errorId).innerHTML = $scope.UserOtp.errorMessage;
				}$("#loader-mask").fadeOut();
    		}, function(error){
    			document.getElementById('usernameError').innerHTML = error.data;
    			console.log(error.data);
    	    }
    		 
    		 );
     		 }
     	     }
     
     // resend otp
     $scope.resendOTP = function (name,errorId) {
    	 
    	 //if username is blank
    	 
     	 if(name == undefined || name == " " )
    		 {
     		 $scope.IsVisible =  false;	
     		 $scope.setpassIsVisible = false;
    		 document.getElementById(errorId).innerHTML = "Please enter your Username";
    		 } 
     	 else
     		 {
     		 document.getElementById(errorId).innerHTML = " ";
     		 
     		 // sending otp to username
     		 
    		 $http.post("forgotPassword?username=" +name).then(function(result) {
    			 $scope.username= name;
    			 $scope.UserOtp = result.data;
    			 $scope.IsVisible =  true;	
    			 $scope.ifOtpmatched = false;
    			 $scope.setpassIsVisible = false;
    			 document.getElementById("otpinvalid").innerHTML = "";
    			 document.getElementById("otpverified").innerHTML = "";
    			 $scope.forgotpassOTP = "";
			if($scope.UserOtp.valid == "true"){
				document.getElementById(errorId).innerHTML = $scope.UserOtp.errorMessage;
				}
    		});
     		 }
     	     }
     
     $scope.validateOTPforforgotPass = function(model,errorId,errorInvalid)
     {
    	 if(model.length == 6)
    		 {
    		 
    		 //validating otp
    		 
    		 $http.get("validateOtp?otp=" +model+ '&username=' +$scope.username).then(function(result)
    				 {
    			 		$scope.otpfornewPassword = model;
//    			        var data = result.data;
    			 		if(result.data.status == '200')
    			 			{
    			 			$scope.ifOtpmatched = true;
    			 			 $scope.setpassIsVisible = true;
    		    		 	document.getElementById(errorId).innerHTML = result.data.message;
    		    		 	document.getElementById(errorInvalid).innerHTML = "";
    			 			}else 
    			 				{
    			 				$scope.ifOtpmatched = false;
    			 				 $scope.setpassIsVisible = false;
    			 				document.getElementById(errorInvalid).innerHTML = result.data.message;
    			 				document.getElementById(errorId).innerHTML = "";
    			 				}
    				 })
    		 }
     };
     
     
     // set new password
     
     $scope.setPass = function(model, errorId, successId)
     {
    	 if(model == undefined || model == "")
    		 {
    		 	document.getElementById(errorId).innerHTML = "Please set your new password !";
    		 }
    	 else
    		 {
    		 document.getElementById(errorId).innerHTML = " ";
    		 //set new password
    		 $http.post("resetPassword?password=" +model+ '&username=' +$scope.username+ '&otp=' +$scope.otpfornewPassword).then(function(result) 
    				 {
    			 		$("#loader-mask").show();
    			 		document.getElementById(successId).innerHTML = "Your Password has been changed successfully !";
    			 		$scope.infoMsg = "Your Password has been changed successfully !";
    			 		$("#loader-mask").fadeOut();
    			 		$("#myModalForm").modal('hide');
    			 		$("#infoMessage").modal('show');
    				 });
    		 }
     };
     
     
     $scope.clearErrormsg = function(name, errorId)
     {
    	 if(name != undefined || name == "")
    		 {
    		 	document.getElementById(errorId).innerHTML = "";
    		 }
     }
     
     $scope.clearInput = function()
     {
    	 document.getElementById("myForm").reset();
    	 document.getElementById('emailOtpError').innerHTML = "";
    	 document.getElementById('otpverified').innerHTML = "";
    	 $scope.IsVisible =  false;	
    	 $scope.ifOtpmatched = false;
    	 $scope.setpassIsVisible = false;
    	 window.location.reload();
     }
     
     $scope.redirect = function(){
			$("#infoMessage").modal("hide");
			window.location.reload();
	};
}


