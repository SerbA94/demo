<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Welcome" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0 min-h-100">
	
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
		
        	<ol class="carousel-indicators">
          		<li data-target="#myCarousel" data-slide-to="0" class=""></li>
          		<li data-target="#myCarousel" data-slide-to="1" class="active"></li>
          		<li data-target="#myCarousel" data-slide-to="2" class=""></li>
        	</ol>
        	
        	<div class="carousel-inner">
          		<div class="carousel-item active carousel-item-left">
            		<img class="first-slide" src="img/photo/main-3.jpeg" style="width:100%" alt="First slide"/>
            		<c:if test="${ userRole.title ne 'admin' and userRole.title ne 'manager' }">
            			<div class="container">
	              			<div class="carousel-caption text-left shadowtext">
	                			<h1><fmt:message key="welcome_jsp.anchor.car_1_title"/></h1>
	                			<p><fmt:message key="welcome_jsp.anchor.car_1_description"/></p>
	                			<p>
	                				<a class="btn btn-lg btn-secondary mb1 black bg-gray" 
	                				href="controller?command=view-booking-request-create" role="button">
	                					<fmt:message key="welcome_jsp.anchor.car_1_button"/>
	                				</a>
	                			</p>
	             			</div>
	            		</div>
            		</c:if>
          		</div>
          		<div class="carousel-item carousel-item-next carousel-item-left">
            		<img class="second-slide" src="img/photo/main-1.jpeg" style="width:100%" alt="Second slide">
          		</div>
          		<div class="carousel-item">
            		<img class="third-slide" src="img/photo/main-2.jpeg" style="width:100%" alt="Third slide">
            		<c:if test="${ userRole.title ne 'admin' and userRole.title ne 'manager' }">
	             		<div class="container">
	              			<div class="carousel-caption text-left shadowtext" >
	                			<h1><fmt:message key="welcome_jsp.anchor.car_3_title"/></h1>
	                			<p><fmt:message key="welcome_jsp.anchor.car_3_description"/></p>
	                			<p>
	                				<a class="btn btn-lg btn-secondary mb1 black bg-gray" 
	                				href="controller?command=view-room-list" role="button">
	                					<fmt:message key="welcome_jsp.anchor.car_3_button"/>
	                				</a>
	                			</p>
	              			</div>
	            		</div>
           			</c:if>
          		</div>
        	</div>
        	
        	<a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
          		<span class="carousel-control-prev-icon" aria-hidden="true"></span>
          		<span class="sr-only">Previous</span>
        	</a>
        	<a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
          		<span class="carousel-control-next-icon" aria-hidden="true"></span>
          		<span class="sr-only">Next</span>
        	</a>
        	
      	</div>
	
		<div class="d-flex justify-content-center mg-10"><a href="#opening"><i class="icon arrow-down-25" ></i></a></div>
	
		<div id="opening" class="mg-50">
			<hr class="featurette-divider"/>
			<div class="row featurette">
          		<div class="col-md-7">
            		<h2 class="featurette-heading">
            			<fmt:message key="welcome_jsp.restaurant.head"/>
            			<span class="text-muted"><fmt:message key="welcome_jsp.restaurant.title"/></span></h2>
            		<p class="lead"><fmt:message key="welcome_jsp.restaurant.description"/></p>
            	</div>
          		<div class="col-md-5">
            		<img class="featurette-image img-fluid mx-auto"  style="width: 500px; height: 500px;" src="img/photo/restaurant.jpeg">
          		</div>
        	</div>
        
    		<hr class="featurette-divider"/>
    
    		<div class="row featurette">
          		<div class="col-md-7 order-md-2">
            		<h2 class="featurette-heading">
            			<fmt:message key="welcome_jsp.conference_room.head"/>
            			<span class="text-muted"><fmt:message key="welcome_jsp.conference_room.title"/></span></h2>
            		<p class="lead"><fmt:message key="welcome_jsp.conference_room.description"/></p>
            	</div>
          		<div class="col-md-5 order-md-1">
           			<img class="featurette-image img-fluid mx-auto"  style="width: 500px; height: 500px;" src="img/photo/conference-room.jpeg">
          		</div>
        	</div>
        
    		<hr class="featurette-divider">
    
    		<div class="row featurette">
          		<div class="col-md-7">
            		<h2 class="featurette-heading">
            		<fmt:message key="welcome_jsp.gym.head"/>
            		<span class="text-muted"><fmt:message key="welcome_jsp.gym.title"/></span></h2>
           			<p class="lead"><fmt:message key="welcome_jsp.gym.description"/></p>
           		</div>
          		<div class="col-md-5">
            		<img class="featurette-image img-fluid mx-auto"  style="width: 500px; height: 500px;" src="img/photo/gym.jpeg">
          		</div>
        	</div>
        
    		<hr class="featurette-divider">
    
  		</div>

  		<div class="card border-0 my-5">
    		<div class="card-body p-5">
	      		<h1><span><fmt:message key="welcome_jsp.hotel_description.head"/></span></h1>
	      		<h1 class="font-weight-light"><fmt:message key="welcome_jsp.hotel_description.title"/></h1>
	      		<p class="lead"><fmt:message key="welcome_jsp.hotel_description.description"/></p>
	      
			  	<div class="row">
			  		<div class="column">
			  			<c:set var="counter" value= "${ 1 }" />
						<c:forEach begin="1" end="7" step="1">
			    			<img src="img/photo/img-${ counter }.jpeg" style="width:100%" 
			    			onclick="openModal();currentSlide(${counter})" class="hover-shadow cursor">
							<c:set var="counter" value= "${counter + 1}" />
						</c:forEach>
			  		</div>
			  		<div class="column">
						<c:forEach begin="1" end="5" step="1">
				    		<img src="img/photo/img-${ counter }.jpeg" style="width:100%" 
				    		onclick="openModal();currentSlide(${counter})" class="hover-shadow cursor">
							<c:set var="counter" value= "${counter + 1}" />
						</c:forEach>
			  		</div>
			  		<div class="column">
						<c:forEach begin="1" end="7" step="1">
				    		<img src="img/photo/img-${ counter }.jpeg" style="width:100%" 
				    		onclick="openModal();currentSlide(${counter})" class="hover-shadow cursor">
							<c:set var="counter" value= "${counter + 1}" />
						</c:forEach>
			  		</div>
				  	<div class="column">
						<c:forEach begin="1" end="7" step="1">
				    		<img src="img/photo/img-${ counter }.jpeg" style="width:100%" 
				    		onclick="openModal();currentSlide(${counter})" class="hover-shadow cursor">
							<c:set var="counter" value= "${counter + 1}" />
						</c:forEach>
				  	</div>
			  	</div>
	
				<div id="myModal" class="modal">
	  				<span class="close cursor" onclick="closeModal()">&times;</span>
	 				<div class="modal-content" style="width:480px">
	
	  					<c:set var="counter" value= "${ 1 }" />
						<c:forEach begin="1" end="26" step="1">
							<div class="mySlides">
					      		<div class="numbertext">${ counter } / 26</div>
					      		<img src="img/photo/img-${ counter }.jpeg" style="width:100%">
					    	</div>
							<c:set var="counter" value= "${counter + 1}" />
						</c:forEach>
	
					    <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
					    <a class="next" onclick="plusSlides(1)">&#10095;</a>
	
	    				<div class="caption-container"><p id="caption"></p></div>
	  				</div>
				</div>

    		</div>
  		</div>
 
	</main>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
<script>
	<%@ include file="/js/main.js"%>
</script>

</body>
</html>