<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Welcome" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<style><%@ include file="/style/style.css" %></style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0">

    <div class="img-container">
      	<img src="img/main6.jpeg" alt="main6" style="width:100%;">
  			<div class="text-block">
    			<h1>Welcome to Demo hotel</h1>
    			<h4>Here you can find best rooms to book</h4>
    			<p>to see more just scroll down...</p>
  			</div>
		</div>

  <div class="card border-0  my-5">
    <div class="card-body p-5">

    	<div><h1><span>Demo hotel</span></h1></div>

      <h1 class="font-weight-light">Located in the heart of DreamCity</h1>
      <p class="lead">Offers wide variety of comfortable rooms to book</p>
      <p class="lead">See them below...</p>

		  <div class="row">
		  	<div class="column">
		  		<c:set var="counter" value= "${ 1 }" />
				<c:forEach begin="1" end="7" step="1">
		    		<img src="img/img-${ counter }.jpeg" style="width:100%" onclick="openModal();currentSlide(${counter})" class="hover-shadow cursor">
					<c:set var="counter" value= "${counter + 1}" />
				</c:forEach>
		  	</div>
		  	<div class="column">
				<c:forEach begin="1" end="5" step="1">
		    		<img src="img/img-${ counter }.jpeg" style="width:100%" onclick="openModal();currentSlide(${counter})" class="hover-shadow cursor">
					<c:set var="counter" value= "${counter + 1}" />
				</c:forEach>
		  	</div>
		  	<div class="column">
				<c:forEach begin="1" end="7" step="1">
		    		<img src="img/img-${ counter }.jpeg" style="width:100%" onclick="openModal();currentSlide(${counter})" class="hover-shadow cursor">
					<c:set var="counter" value= "${counter + 1}" />
				</c:forEach>
		  	</div>
		  	<div class="column">
				<c:forEach begin="1" end="7" step="1">
		    		<img src="img/img-${ counter }.jpeg" style="width:100%" onclick="openModal();currentSlide(${counter})" class="hover-shadow cursor">
					<c:set var="counter" value= "${counter + 1}" />
				</c:forEach>
		  	</div>
		  </div>


<div id="myModal" class="modal">
  <span class="close cursor" onclick="closeModal()">&times;</span>
  <div class="modal-content" style="width:100%">

  	<c:set var="counter" value= "${ 1 }" />
	<c:forEach begin="1" end="26" step="1">
		<div class="mySlides">
      		<div class="numbertext">${ counter } / 26</div>
      		<img src="img/img-${ counter }.jpeg" style="width:100%">
    	</div>
		<c:set var="counter" value= "${counter + 1}" />
	</c:forEach>

    <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
    <a class="next" onclick="plusSlides(1)">&#10095;</a>

    <div class="caption-container">
      <p id="caption"></p>
    </div>
  </div>
</div>

    </div>
  </div>

	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>


<script><%@ include file="/js/main.js" %></script>

</body>
</html>