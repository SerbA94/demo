<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking request" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0 min-h-100">
	
		<section class="jumbotron text-center">
	    	<div class="container">
	       		<h1 class="jumbotron-heading">Booking request</h1>
	          	<div class="lead text-muted">
	          		<div><small>Booking request number : ${bookingRequest.id}</small></div>
					<div><small>Date in : ${bookingRequest.dateIn}</small></div>
					<div><small>Date out : ${bookingRequest.dateOut}</small></div>
					<div><small>Room class : ${bookingRequest.roomClass.toArray()[0].title}</small></div>
					<div><small>Capacity : ${bookingRequest.capacity}</small></div>
	          	</div>
	       	</div>
		</section>
	      	
	      	
		<div class="container"  style="margin-top: 2rem; margin-bottom: 2rem;">
	      	<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			<div class="card shadow-sm mt-4 mb-4">
				<div class="card-header text-center text-muted" id="headingFour">
					<h3><span>Matched rooms</span></h3>
				</div>
				<div class="card-body">
						  	
					<c:choose>
						<c:when test="${empty rooms}">
							<div class="alert alert-danger">
								<span>No matched rooms</span>
							</div>
							<div class="d-flex justify-content-center">
								<a href="controller?command=inactivate-booking-request&booking_request_id=${bookingRequest.id}" class="btn btn-md btn-outline-secondary">Reject</a>          	
							</div>
						</c:when>
						<c:when test="${not empty rooms}">
							<form action="controller" method="post">
								<input type="hidden" name="command" value="create-booking" />
								<input type="hidden" name="user_id" value="${bookingRequest.user.id}" />
								<input type="hidden" name="booking_request_id" value="${bookingRequest.id}" />
	
								<table class="table table-hover text-center">
									<thead>
								    	<tr>
								    		<th>Image</th>
								        	<th>Number</th>
								        	<th>Capacity</th>
								        	<th>Class</th>
								        	<th>Status</th>
								        	<th>Price</th>
								        	<th></th>
								    	</tr>
								    </thead>
								    <c:forEach var="room" items="${rooms}">
									    <tbody>
									    	<tr>
									        	<td align="center"><img src="controller?command=view-image&image_id=${room.images[0].id}" class="img-table"></td>
									            <td align="center">${room.number}</td>
									            <td align="center">${room.capacity}</td>
									            <td align="center">${room.roomClass}</td>
									            <td align="center">${room.roomStatus}</td>
									            <td align="center"><demo:format price="${room.price}"/></td>
									            <c:choose>
									            	<c:when test="${room.id eq rooms[0].id}">
									            		<td align="center"><input type="radio" name="room_id" value="${ room.id }" checked="checked"></td>
									            	</c:when>
									            	<c:when test="${room.id ne rooms[0].id}">
									            		<td align="center"><input type="radio" name="room_id" value="${ room.id }"></td>
									            	</c:when>
									            </c:choose>
									       	</tr>
									   	</tbody>
									</c:forEach>
								</table>
	
								<div class="d-flex justify-content-center">
									<div class="btn-group">
										<input type="submit" class="btn btn-sm btn-outline-secondary" value="Confirm"/>
										<a href="controller?command=inactivate-booking-request&booking_request_id=${bookingRequest.id}" class="btn btn-md btn-outline-secondary">Reject</a>          	
									</div>
								</div>
								
							</form>
						</c:when>
					</c:choose>
					
				</div>
			</div>
		</div>

	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>

</html>