
<!-- 
@author Swarna (swarnaprava@sdrc.co.in)
-->

<footer class="footer_width loginPage exceptionpage">
		<div class="container-fluid alignwithBodyContainer" >
			<div class="row">
				<div class="col-md-4 col-sm-5 col-xs-12 foot-lft widthintab">

					<h6 class="footer_lft" ><span class="foot_support">Supported by </span> 
					 <a href="http://unicef.in/" target="_blank" class="text_deco_none">
					 <img class="image-responsive footer-logo-unicef img_footr"
					  src="resources/images/unicef-white.png" alt="UNICEF"></a>
					</h6>					
				</div>
				<div class="col-md-4 col-sm-4 col-xs-12 footer_mddl widthintab" >
					<span class="blck_dse text-center">&copy; SCPSTamilNadu 2017&nbsp;&nbsp;1.0.0 </span>&nbsp;&nbsp;&nbsp;
						</div>
						
						<div class="col-md-4 col-sm-3 col-xs-12 footer_rght text-right widthintab" >
					<span class="footerfont">Powered by </span><a
						href="http://sdrc.co.in/" target="_blank" class="text_deco_none">&nbsp;&nbsp;<span style="" class="poweredbysdrc">SDRC</span>
						</a>
				</div>

			</div>
		</div>
	</footer>

<script src="resources/js/topojson.v1.min.js"></script>

<script src="resources/js/html2canvas.js"></script>

<!--  Site specific scripts down bellow -->

<script src="resources/js/sdrc.export.js"></script>
<script type="text/javascript">
$(function() {
    // this will get the full URL at the address bar
    var url = window.location.href;

    // passes on every "a" tag
    $("#myNavbar a").each(function() {
        // checks if its the same on the address bar
        if (url == (this.href)) {
            $(this).closest("li").addClass("activePage");
            //for making parent of submenu active
           $(this).closest("li").parent().parent().addClass("activePage");
        }
    });
});        
</script>

