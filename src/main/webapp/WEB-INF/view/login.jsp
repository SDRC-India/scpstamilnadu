
<!--  
 Author Swarna(swarnaprava@sdrc.co.in)
  -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="serror" uri="/WEB-INF/ErrorDescripter.tld"%>
<!DOCTYPE html>

<html>
<head>

<title>SCPSTamilNadu-login</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/customLoader.css">
<link rel="stylesheet" href="resources/css/style.css">
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

</head>

<style>

 .footer_width 
 { 
  position: fixed; 
  bottom: 0; */
  width: 100%; 
 } 
</style>
<jsp:include page="fragments/header.jsp"></jsp:include>
<body ng-app="loginApp" ng-controller="loginController">
	<div id="errMsg" class="text-center">
		<serror:Error id="msgBox" errorList="${formError}"
			cssInfClass="${className}">
		</serror:Error>
	</div>
	<div class="container tabletLoginiew">
		<div class="row">
			<div class="col-sm-6 col-md-offset-4 col-xs-offset-3 ipadPotraitView">
				<div class="panel panel-default panelBox ">
					<div class="panel-body">
						<fieldset>
							<div class="row">
								<div class="center-block">
									<img class="cpisloginsvgheight" src="resources/images/scps_icon_2.svg">
									<h3 class="sign-up-title">SCPS Tamilnadu</h3>
								</div>
							</div>
							<form method="post" action="webLogin">
								<div class="row">
									<div class="col-sm-12 col-md-10  col-md-offset-1 ">
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-user"></i>
												</span> <input required class="form-control" placeholder="Username"
													name="username" type="text" autofocus>
											</div>
										</div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"> <i
													class="glyphicon glyphicon-lock"></i>
												</span> <input required class="form-control" placeholder="Password"
													name="password" type="password" value="">
											</div>
										</div>
										<div class="form-group">
											<button type="submit"
												class="btn btn-lg btn-primary btn-block loginBtn"
												value="LOGIN">LOGIN</button>
										</div>
										<div class="form-group">
											<h5>
												<a class="forgotPass" data-toggle="modal"
													data-target="#myModalForm">Forgot password ?</a>
											</h5>
										</div>
									</div>
								</div>
							</form>
						</fieldset>
					</div>
				</div>
				
				<br>
				&nbsp;<br>&nbsp;
				
			</div>
		</div>
	</div>
	<!-- forgot password modal  -->
	<div class="modal fade" id="myModalForm" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header forgotPass-header">
					<h4 class="modal-title" id="myModalLabel">Forgot Password ?</h4>
				</div>

				<!-- Modal Body -->
				<div class="modal-body">
					<form role="form" id="myForm" method="post">
						<div class="form-group">
							<div class="col-md-12 forgotPassUsername">
								<label>Enter Your Username</label>
							</div>
							<div class="col-md-8 col-sm-8 col-xs-8">
								<input type="text" class="form-control" id="username"
									placeholder="Enter username" ng-model="forgotpassUsername" />
							<div style="display: inline-block; margin-left: 10px; color: red; vertical-align: middle;"
								id="usernameError" class="error-style">
							</div>
						   </div>
							<button type="submit" class="btn btn-default"
								ng-blur="clearErrormsg(forgotpassUsername,'emailOtpError')"
								ng-click="sendOTP(forgotpassUsername,'emailOtpError')">Generate OTP
							</button>
							<div style="display: inline-block; margin-left: 10px; color: red; vertical-align: middle;"
								id="emailOtpError" class="error-style">
							</div>
						</div>
						<div class="form-group" ng-show="IsVisible">
							<div class="col-md-12 col-sm-10 forgotPassUsername">
								<label>OTP sent to your email Id</label>
							</div>
							<div class="col-md-8 col-sm-8 col-xs-8">
								<input type="text" class="form-control otpmatchedpng" id="otp" only-six-digits
									placeholder="Enter OTP" ng-model="forgotpassOTP" ng-change="validateOTPforforgotPass(forgotpassOTP,'otpverified','otpinvalid')"/> 
									<span class="correctMark otpmatchedsign" ng-show="ifOtpmatched">
									<svg height="24" width="24" viewBox="0 0 24 24"
										xmlns="http://www.w3.org/2000/svg" class="">
										<path d="M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z"
											stroke="#388e3c">
										</path>
									</svg>
									</span>
								<div style="display: inline-block; margin-left: 0px; color: #46b746; vertical-align: middle;"
									id="otpverified" class="error-style">
								</div>
								<div style="display: inline-block; margin-left: 0px; color: red;; vertical-align: middle;"
									id="otpinvalid" class="error-style">
								</div>
							</div>
							<button type="submit" class="btn btn-default"
								ng-click="resendOTP(forgotpassUsername,'emailOtpError')">Resend OTP</button>
						</div>
						
						<div class="form-group" ng-show="setpassIsVisible">
							<div class="col-md-12 col-sm-10 forgotPassUsername">
								<label>Set Password</label>
							</div>
							<div class="col-md-8 col-sm-8 col-xs-8">
								<input type="password" class="form-control" id="newpasswordId" 
									placeholder="Enter your new password" ng-model="newpassword" data-toggle="password" />
								<div style="display: inline-block; margin-left: 0px; color: red; vertical-align: middle;"
									id="setpassError" class="error-style">
								</div>
								<div style="display: inline-block; margin-left: 0px; color: #46b746; vertical-align: middle;"
									id="setpassSuccessmsg" class="error-style">
								</div>
							</div>
							<button type="submit" class="btn btn-default"
								ng-click="setPass(newpassword,'setpassError','setpassSuccessmsg')">Submit</button>
						</div>
					</form>
				</div>
				<!-- Modal Footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" ng-click="clearInput()">
						Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-- end forgot password modal  -->
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
	<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
<script type="text/javascript"
	src="resources/js/angularController/loginController.js"></script>
	<script type="text/javascript" 
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-show-password/1.0.3/bootstrap-show-password.min.js"></script>
<script type="text/javascript">
var app = angular.module("loginApp", []);
var myAppConstructor = angular.module("loginApp");
myAppConstructor.controller("loginController",
		loginController);
</script>
<script type="text/javascript">
	$("#password").password('toggle');
</script>
<script type="text/javascript"
		src="resources/js/angularDirective/directive.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	if( $(".alert")[0] != undefined){
		$("#errMsg").fadeTo(2000, 500).slideUp(500, function(){
		    $("#errMsg").slideUp(500);
		});
	}
});
</script>
</html>