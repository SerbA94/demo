<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking request list" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0 h-100">
	
		<div class="container">
			<div class="card shadow-sm mt-4 mb-4">
				<div class="card-header text-center text-muted" id="headingFour">
					<h3><span>Booking requests</span></h3>
				</div>
				<div class="card-body">
						  	
					<c:choose>
						<c:when test="${empty bookingRequests}">
							<div class="alert alert-danger">
								<span>No active booking requests</span>
							</div>
						</c:when>
						<c:when test="${not empty bookingRequests}">
							<table class="table table-hover text-center">
								<thead>
							    	<tr>
							    		<th>Booking request number</th>
							        	<th>Date In</th>
							        	<th>Date Out</th>
							        	<th>Room Capacity</th>
							        	<th>Room Class</th>
							        	<th></th>
							    	</tr>
						    	</thead>
						    	<c:forEach var="bookingRequest" items="${bookingRequests}">
								    <tbody>
								        <tr>
								        	<td align="center">${bookingRequest.id}</td>
								            <td align="center">${bookingRequest.dateIn}</td>
								            <td align="center">${bookingRequest.dateOut}</td>
								            <td align="center">${bookingRequest.capacity}</td>
								            <td align="center">${bookingRequest.roomClass.toArray()[0].title}</td>
								            <td align="center">
								            	<a class="btn btn-sm btn-outline-secondary" 
								            	href="controller?command=view-booking-request&booking_request_id=${bookingRequest.id}">
								            		<span>Select room</span>
								            	</a>
								            </td>
								        </tr>
							    	</tbody>
						    	</c:forEach>
							</table>
						</c:when>
					</c:choose>
					
				</div>
			</div>
		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>