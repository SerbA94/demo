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
	       		<h1 class="jumbotron-heading">
	       			<fmt:message key="booking_request_jsp.label.booking_request"/>
	       		</h1>
	          	<div class="lead text-muted">
	          		<div>
	          			<small>
	          				<fmt:message key="booking_request_jsp.label.request_number"/> : ${bookingRequest.id}
	          			</small>
	          		</div>
					<div>
						<small>
							<fmt:message key="booking_request_jsp.label.date_in"/> : ${bookingRequest.dateIn}
						</small>
					</div>
					<div>
						<small>
							<fmt:message key="booking_request_jsp.label.date_out"/> : ${bookingRequest.dateOut}
						</small>
					</div>
					<div>
						<small>
							<fmt:message key="booking_request_jsp.label.room_class"/> : 
							<fmt:message key="booking_request_jsp.class.${bookingRequest.roomClass.toArray()[0].title}"/>
						</small>
					</div>
					<div>
						<small>
							<fmt:message key="booking_request_jsp.label.capacity"/> : ${bookingRequest.capacity}
						</small>
					</div>
	          	</div>
	       	</div>
		</section>
	      	
	      	
		<div class="container"  style="margin-top: 2rem; margin-bottom: 2rem;">
	      	
	      	<div class="shadow-sm mt-4">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			</div>
	      	
			<div class="card shadow-sm mt-4 mb-4">
				<div class="card-header text-center text-muted" id="headingFour">
					<h3>
						<span>
							<fmt:message key="booking_request_jsp.label.matched_rooms"/>
						</span>
					</h3>
				</div>
				<div class="card-body">
						  	
					<c:choose>
						<c:when test="${empty rooms}">
							<div class="alert alert-danger">
								<span>
									<fmt:message key="booking_request_jsp.label.no_matched_rooms"/>
								</span>
							</div>
							<div class="d-flex justify-content-center">
								<a href="controller?command=inactivate-booking-request&booking_request_id=${bookingRequest.id}"
								class="btn btn-md btn-outline-secondary">
									<fmt:message key="booking_request_jsp.button.reject"/>
								</a>          	
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
								    		<th><fmt:message key="booking_request_jsp.label.image"/></th>
								        	<th><fmt:message key="booking_request_jsp.label.number"/></th>
								        	<th><fmt:message key="booking_request_jsp.label.capacity"/></th>
								        	<th><fmt:message key="booking_request_jsp.label.class"/></th>
								        	<th><fmt:message key="booking_request_jsp.label.status"/></th>
								        	<th><fmt:message key="booking_request_jsp.label.price"/></th>
								        	<th></th>
								    	</tr>
								    </thead>
								    <c:forEach var="room" items="${rooms}">
									    <tbody>
									    	<tr>
									        	<td align="center"><img src="controller?command=view-image&image_id=${room.images[0].id}" class="img-table"></td>
									            <td align="center">${room.number}</td>
									            <td align="center">${room.capacity}</td>
									            <td align="center">
									           		<fmt:message key="booking_request_jsp.class.${room.roomClass.toArray()[0].title}"/>
									            </td>
									            <td align="center">
									           		<fmt:message key="booking_request_jsp.status.${room.roomStatus.toArray()[0].title}"/>
									            </td>
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
										<input type="submit" class="btn btn-sm btn-outline-secondary" value="<fmt:message key="booking_request_jsp.button.confirm"/>"/>
										<a href="controller?command=inactivate-booking-request&booking_request_id=${bookingRequest.id}"
										 class="btn btn-md btn-outline-secondary">
										 	<fmt:message key="booking_request_jsp.button.reject"/>
										 </a>          	
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