<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.io.StringWriter"%>

<html lang="en">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/style.css">

<body>
<!-- 	<div id="wrapper"> -->
<div id="wrapper">
<jsp:include page="fragments/header.jsp"></jsp:include>
		
		<div class="content exceptioinErrorBottom">
			<div class="container-fluid margintopFromheader">
				<spring:url value="/resources/images/oops.jpg" var="exceptionImage" />
				<div class="row text-center">
					<h2>
						<img class="oppsImg" src="${exceptionImage}" alt="exception image"
						 style="height: 330px;"/>
					</h2>

					<h2 class="text404 excptionmsg">Oops !!! Looks like you are caught in a wrong place ...</h2>

					<p class="text404" style="color: #c9302c;">${exception.message}</p>
					
					
					
					<h4 class="text404 excptionmsg"><a href="${pageContext.request.contextPath}/login">Back to login page</a></h4>

					<%
						Logger logger = Logger.getLogger(Exception.class);

						RuntimeException rte = (RuntimeException) (request
								.getAttribute("exception"));
						StackTraceElement[] stes = rte != null ? rte.getStackTrace() : null;

						if (stes != null && stes.length > 0) {

							StringWriter stringWritter = new StringWriter();
							PrintWriter printWritter = new PrintWriter(stringWritter, true);
							((RuntimeException) (request.getAttribute("exception")))
									.printStackTrace(printWritter);
							printWritter.flush();
							stringWritter.flush();

							logger.error("An exception occourred , Stack Trace :"
									+ stringWritter.toString());
			
						}
					%>
					<!-- Exception: ${exception.message}.
		  	<c:forEach items="${exception.stackTrace}" var="stackTrace"> 
				${stackTrace} 
			</c:forEach>
	  	-->
				</div>
			</div>
			<div class="clearfooter"></div>
		</div>
		</div>
		
<!-- 	</div> -->
<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>



</html>
