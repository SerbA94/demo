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

	<main role="main" class="flex-shrink-0 min-h-100">
	
		<div class="container">
		
			<div class="shadow-sm mt-4">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			</div>
		
			<div class="card shadow-sm mt-4 mb-4">
				<div class="card-header text-center text-muted" id="headingFour">
					<h3>
						<span>
							<fmt:message key="booking_request_list_jsp.label.booking_requests"/>
						</span>
					</h3>
				</div>
				<div class="card-body">
						  	
					<c:choose>
						<c:when test="${empty bookingRequests}">
							<div class="alert alert-danger">
								<span>
									<fmt:message key="booking_request_list_jsp.label.no_requests"/>
								</span>
							</div>
						</c:when>
						<c:when test="${not empty bookingRequests}">
							<table class="table table-hover text-center">
								<thead>
							    	<tr>
							    		<th><fmt:message key="booking_request_list_jsp.label.request_number"/></th>
							        	<th><fmt:message key="booking_request_list_jsp.label.date_in"/></th>
							        	<th><fmt:message key="booking_request_list_jsp.label.date_out"/></th>
							        	<th><fmt:message key="booking_request_list_jsp.label.room_capacity"/></th>
							        	<th><fmt:message key="booking_request_list_jsp.label.room_class"/></th>
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
								            <td align="center">
								            	<fmt:message key="booking_request_list_jsp.class.${bookingRequest.roomClass.toArray()[0].title}"/>
								            </td>
								            <td align="center">
								            	<a class="btn btn-sm btn-outline-secondary" 
								            	href="controller?command=view-booking-request&booking_request_id=${bookingRequest.id}">
								            		<span>
								            			<fmt:message key="booking_request_list_jsp.button.select_room"/>
								            		</span>
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