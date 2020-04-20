<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Account" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0">
				
		<section class="jumbotron text-center">
        	<div class="container">
        	   <h1 class="jumbotron-heading">Your account page</h1>
          		<p class="lead text-muted">
          			Some text description of your account page.Some text description of your account page.
          		</p>
       		</div>
      	</section>

		<div class="container" style="margin-top: 2rem; margin-bottom: 2rem;" id="collapsing-container">
			<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>

			<div class="row d-flex justify-content-around">
				 
				<div class="col-md-3">	
					<div class="mg-10">
					
						<div id="headingFour">
							<h2 class="mb-0">
						    	<button class="btn btn-lg btn-block btn-outline-secondary btn-acc-menu shadow-sm"
						    	type="button" data-toggle="collapse" data-target="#collapseFour" aria-expanded="true" aria-controls="collapseFour">
						      		Handling bookings
							        <c:if test="${ not empty handlingBookings }">
							        	<span style="color: red;">(${ handlingBookings.size()})</span>
							        </c:if>
						        </button>
						  	</h2>
						</div>
					
						<div id="headingThree">
							<h2 class="mb-0">
						    	<button class="btn btn-lg btn-block btn-outline-secondary btn-acc-menu shadow-sm collapsed"
						    	type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
						        	Active bookings
						          	<c:if test="${ not empty activeBookings }">
						          		<span style="color: red;">(${ activeBookings.size()})</span>
						          	</c:if>
						        </button>
						    </h2>
						</div>

						<div id="headingOne">
							<h2 class="mb-0">
						    	<button class="btn btn-lg btn-block btn-outline-secondary btn-acc-menu shadow-sm collapsed" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
						        	Active booking requests
						          	<c:if test="${ not empty activeBookingRequests }">
						          		<span style="color: red;">(${ activeBookingRequests.size()})</span>
						          	</c:if>
						       	</button>
						    </h2>
						</div>

						<div id="headingTwo">
							<h2 class="mb-0">
						    	<button class="btn btn-lg btn-block btn-outline-secondary btn-acc-menu shadow-sm collapsed"
						    	type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
						        	Rejected booking requests
						          	<c:if test="${ not empty inactiveBookingRequests }">
						          		<span style="color: red;">(${ inactiveBookingRequests.size()})</span>
						          	</c:if>
						        </button>
						    </h2>
						</div>

					</div>					
				</div>
				
				<div class="col-md-8 mg-10">
				
					<div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#collapsing-container">
						<div class="card shadow-sm" style="height: 100%;">
							<div class="card-header text-center text-muted" id="headingFour">
						 		<h3><span>Active booking requests</span></h3>
						  	</div>
						  	<div class="card-body">
						  		<c:choose>
						  			<c:when test="${empty activeBookingRequests}">
						  				<div class="alert alert-danger">
						  					<span>You have no active booking requests</span>
										</div>
						  			</c:when>
						  			<c:when test="${not empty activeBookingRequests}">
										<table class="table table-hover text-center">
											<thead>  	
												<tr class="text-muted">
													<th>Booking request number</th>
												    <th>Date In</th>
												    <th>Date Out</th>
												    <th>Room Capacity</th>
												    <th>Room Class</th>
												</tr>
											</thead>
											<c:forEach var="bookingRequest" items="${activeBookingRequests}">
												<tbody>
													<tr>
														<td align="center">${bookingRequest.id}</td>
												    	<td align="center">${bookingRequest.dateIn}</td>
												        <td align="center">${bookingRequest.dateOut}</td>
												        <td align="center">${bookingRequest.capacity}</td>
												        <td align="center">${bookingRequest.roomClass.toArray()[0].title}</td>
												    </tr>
												</tbody>
											</c:forEach>
										</table>
									</c:when>
						  		</c:choose>
							</div>
						</div>
					</div>
					
					<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#collapsing-container">
						<div class="card shadow-sm" style="height: 100%;">
							<div class="card-header text-center text-muted" id="headingFour">
						 		<h3><span>Rejected booking requests</span></h3>
						  	</div>
						  	<div class="card-body">
						  		<c:choose>
									<c:when test="${empty inactiveBookingRequests}">
								  		<div class="alert alert-danger">
								  			<span>You have no rejected booking requests</span>
								  		</div>
								  	</c:when>
								  	<c:when test="${not empty inactiveBookingRequests}">
								  		<table class="table table-hover text-center">
								   			<thead>
												<tr class="text-muted">
													<th><span>Booking request number</span></th>
												    <th><span>Date In</span></th>
												    <th><span>Date Out</span></th>
												    <th><span>Room Capacity</span></th>
												    <th><span>Room Class</span></th>
												    <th></th>
												</tr>
											</thead>
											<c:forEach var="bookingRequest" items="${inactiveBookingRequests}">
											    <tbody>
													<tr>
														<td align="center">${bookingRequest.id}</td>
												    	<td align="center">${bookingRequest.dateIn}</td>
												        <td align="center">${bookingRequest.dateOut}</td>
												        <td align="center">${bookingRequest.capacity}</td>
												        <td align="center">${bookingRequest.roomClass.toArray()[0].title}</td>
												        <td align="center">
												        	<form action="controller" method="post">
																<input type="hidden" name="command" value="delete-booking-request" />
																<input type="hidden" name="booking_request_id" value="${bookingRequest.id}" />
																<input type="submit" class="btn btn-sm btn-outline-secondary" value='ok'>
															</form>
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
			
				    <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#collapsing-container">
				    	<div class="card shadow-sm" style="height: 100%;">
							<div class="card-header text-center text-muted" id="headingFour">
						 		<h3><span>Active bookings</span></h3>
						  	</div>
						  	<div class="card-body">
						  		<c:choose>
							  		<c:when test="${empty activeBookings}">
							  			<div class="alert alert-danger">
							  				<span>You have no active bookings</span>
										</div>
							  		</c:when>
							  		<c:when test="${not empty activeBookings}">
										<c:forEach var="booking" items="${activeBookings}">
											<div class="col-md-12">
						        				<div class="card mb-12 shadow-sm">
						            				<img style="height: 100%; width: 100%;" src="controller?command=view-image&image_id=${booking.room.images[0].id}" >
						                			<div class="card-body">
						                				<div class="text-center text-muted mb-4">
						                					<h4><span> Booking number : ${booking.id}</span></h4>
						                				</div>
						                				<hr class="featurette-divider">
						                				<div class="container row text-muted text-center d-flex justify-content-between align-items-stretch"
						                				style="margin: 0!important;">
						                				
							                				<div class="col-md-6">
						                						<div><span>Room details</span></div>
						                						<hr class="featurette-divider">
						                						<div><small>Room number : ${booking.room.number}</small></div>
																<div><small>Room capacity : ${booking.room.capacity}</small></div>
																<div><small>Room class : ${booking.room.roomClass.toArray()[0].title}</small></div>
																<div>
																	<small>
																		<span>
																			<a href="controller?command=view-room&room_id=${booking.room.id}" style="color:black;">
																				See all details
																			</a>
																		</span>
																	</small>
																</div>
						                					</div>
						                	
						                					<div class="col-md-6">
						                						<div><span>Dates</span></div>
						                						<hr class="featurette-divider">
						                						<div><small>Date in : ${booking.dateIn}</small></div>
						                						<div><small>Date out : ${booking.dateOut}</small></div>	 
							                  					<div><small>Total booking days : ${booking.getTotalBookingDays()}</small></div>
						                					</div>
						                				</div>
						                				
						                				<hr class="featurette-divider">
						      
						                  				<div class="d-flex justify-content-center">
							                    			<small class="text-muted">
							                    				<demo:format price="${booking.room.price}"/>
							                    				/day | 
							                    				<demo:format price="${booking.getTotalPrice()}"/>
							                   					/total
							                   				</small>
														</div>
													</div>
												</div>
											</div>
										</c:forEach>
							 		</c:when>
						  		</c:choose>
							</div>
						</div>
				    </div>
				    
				    <div id="collapseFour" class="collapse show" aria-labelledby="headingFour" data-parent="#collapsing-container">
				    	<div class="card shadow-sm" style="height: 100%;">
							<div class="card-header text-center text-muted" id="headingFour">
						 		<h3><span>Handling bookings</span></h3>
						  	</div>
						  	<div class="card-body">
						  		<c:choose>
						  			<c:when test="${ empty handlingBookings }">
						  				<div class="alert alert-danger">
						  					<span>You have no handling bookings</span>
										</div>
						  			</c:when>
						  			<c:when test="${not empty handlingBookings}">
										<c:forEach var="booking" items="${handlingBookings}">
										
											<div class="col-md-12">
						        				<div class="card mb-12 shadow-sm">
						            				<img style="height: 100%; width: 100%;" src="controller?command=view-image&image_id=${booking.room.images[0].id}" >
						                			<div class="card-body">
						                				<div class="text-center text-muted mb-4">
						                					<h4><span> Booking number : ${booking.id}</span></h4>
						                				</div>
						                				<hr class="featurette-divider">
						                				<div class="container row text-muted text-center d-flex justify-content-between align-items-stretch"
						                				style="margin: 0!important;">
						                				
							                				<div class="col-md-6">
						                						<div><span>Room details</span></div>
						                						<hr class="featurette-divider">
						                						<div><small>Room number : ${booking.room.number}</small></div>
																<div><small>Room capacity : ${booking.room.capacity}</small></div>
																<div><small>Room class : ${booking.room.roomClass.toArray()[0].title}</small></div>
																<div>
																	<small>
																		<span>
																			<a href="controller?command=view-room&room_id=${booking.room.id}" style="color:black;">
																				See all details
																			</a>
																		</span>
																	</small>
																</div>
						                					</div>
						                	
						                					<div class="col-md-6">
						                						<div><span>Dates</span></div>
						                						<hr class="featurette-divider">
						                						<div><small>Date in : ${booking.dateIn}</small></div>
						                						<div><small>Date out : ${booking.dateOut}</small></div>	 
							                  					<div><small>Total booking days : ${booking.getTotalBookingDays()}</small></div>
						                					</div>
						                					
						                					<div class="col-md-12 mt-4">
						                						<div><span> Booking details</span></div>
						                						<hr class="featurette-divider">
						                						<div><small>Date of booking : ${booking.dateOfBooking}</small></div>
																<div><small>Expiring date : ${booking.getExpiringDate()}</small></div>
							                  					<div><span><small>Booking status : ${booking.bookingStatus.toArray()[0].title}</small></span></div>
						                						<hr class="featurette-divider">
						                					</div>
						                					
						                				</div>
						      
						                  				<div class="d-flex justify-content-between align-items-center">
							                    			<div class="btn-group">
														   		<c:choose>
																	<c:when test="${booking.bookingStatus.toArray()[0].title eq 'unconfirmed'}">
																		<a class="btn btn-sm btn-outline-secondary" href="controller?command=confirm-booking&booking_id=${booking.id}">Confirm</a>
																	</c:when>
																	<c:when test="${booking.bookingStatus.toArray()[0].title ne 'unconfirmed'}">
																		<a class="btn btn-sm btn-outline-secondary" href="controller?command=view-bill&booking_id=${booking.id}">See bill</a>
																	</c:when>
																</c:choose>
																<a class="btn btn-sm btn-outline-secondary" href="controller?command=delete-booking&booking_id=${booking.id}">Reject</a>
							                    			</div>
						                    				<small class="text-muted">
						                    					<demo:format price="${booking.room.price}"/>
						                    					/day | 
						                    					<demo:format price="${booking.getTotalPrice()}"/>
						                    					/total
						                    				</small>
														</div>
													</div>
												</div>
											</div>
											
										</c:forEach>
						  			</c:when>
								</c:choose>
							</div>
						</div>
				    </div>					
				</div>	
			</div>
		</div>
	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>