<%@ page import="org.sdrc.scpstamilnadu.util.Constants"%>
<%@ page import="org.sdrc.scpstamilnadu.model.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!--logo part end-->
<!-- spinner -->
<%
Integer role = 0;
UserModel user = null; Boolean hasLR = false;
List<String> features = new ArrayList<String>();
List<String> permissions = new ArrayList<String>();
String agencyUrl = null;

if(request.getSession().getAttribute(Constants.Web.USER_PRINCIPAL) != null){
		user = (UserModel) request.getSession().getAttribute(Constants.Web.USER_PRINCIPAL);
		role=user.getRoleId();
		agencyUrl = "http://www.socialdefence.tn.gov.in/";
}
%>
<script>
	var hasLR = <%=hasLR%>;
	var agencyUrl = "<%=agencyUrl%>";
</script>


<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="col-md-5 col-sm-12 navbar-header">
      
      <img class="scpsTlogo" src="resources/images/logos/SCPS LOGO 1.png" />
      <h2 class="scps_header">State Child Protection Society
      </h2>
      <h3 class="scps_subheader">Tamil Nadu
      </h3>
			<button type="button" class="navbar-toggle menu-toggle"
				data-toggle="collapse" data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
    <div class="col-md-6 col-sm-12 collapse navbar-collapse custom-width" id="myNavbar">
    <ul class="nav navbar-nav navMenus nav override-topheader-font-size" id="menu">
				<%if (user != null) {%><li><a>Welcome<b> <%=user.getUsername()%></b></a></li><%}%>
				
				<%if (user == null) {%><li class="loginbtn"><a href="login">Login</a></li><%}%>
				
				     <li class="nav override-topheader-font-size"><a class="scroll" href="dashboard" id="attachAgencyIdInDashboard">Dashboard</a></li>
                     <li class="nav override-topheader-font-size"><a class="scroll" href="index" id="index">Index</a></li>
				<%if (user != null && user.getRoleId() != 8) {%><li class ="override-topheader-font-size"><a href="dataEntry">Data Entry</a></li><%}%>
				<%if (user != null && user.getRoleId() != 8) {%><li class="override-topheader-font-size"><a href="submissionManagement">Submission Management</a></li><%}%>
				<%if (user != null && user.getRoleId() == 8) {%><li class="override-topheader-font-size"><a href="indicatorManagement">Indicator Management</a></li><%}%>
				<%if((UserModel) request.getSession().getAttribute(Constants.Web.USER_PRINCIPAL)!=null 
						&& ((UserModel) request.getSession().getAttribute(Constants.Web.USER_PRINCIPAL)).getRoleId()==8 ){ 
					      %>
				<li class="nav"><a class="scroll" href="misReport" id="misReport">Report</a></li>
						
						<% } %>
				<%if (user != null) {%><li><a href="webLogout">Logout</a></li><%}%>
			</ul>
    </div>
   <div class="col-md-1 col-sm-1 tamillogo-pos">
    <img class="tamilGlogo" src="resources/images/logos/1200px-TamilNadu_Logo.svg.png" />
    </div>
    <div class="clear"></div>
  </div>
        
 
   
</nav>


<div class="loaderMask" id="loader-mask" style="display: none;">
	<div class="windows8">
		<div class="wBall" id="wBall_1">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_2">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_3">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_4">
			<div class="wInnerBall"></div>
		</div>
		<div class="wBall" id="wBall_5">
			<div class="wInnerBall"></div>
		</div>
	</div>
</div>


