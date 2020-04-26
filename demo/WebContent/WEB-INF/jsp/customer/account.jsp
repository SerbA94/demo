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
	<main role="main" class="flex-shrink-0 min-h-100">
				
		<section class="jumbotron text-center">
        	<div class="container">
        	   	<h1 class="jumbotron-heading">
        	   		<fmt:message key="account_jsp.label.account"/>
        	   	</h1>
          		<p class="lead text-muted">
          			<fmt:message key="account_jsp.label.description"/>
          		</p>
       		</div>
      	</section>

		<div class="container" style="margin-top: 2rem; margin-bottom: 2rem;" id="collapsing-container">
			
			<div class="shadow-sm mt-4">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			</div>
			
			<div class="row d-flex justify-content-around">
				 
				<div class="col-md-3">	
					<div class="mg-10">
					
						<div id="headingFour">
							<h2 class="mb-0">
						    	<button class="btn btn-lg btn-block btn-outline-secondary btn-acc-menu shadow-sm"
						    	type="button" data-toggle="collapse" data-target="#collapseFour" aria-expanded="true" aria-controls="collapseFour">
						      		<fmt:message key="account_jsp.button.handling_bookings"/>
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
						        	<fmt:message key="account_jsp.button.active_bookings"/>
						          	<c:if test="${ not empty activeBookings }">
						          		<span style="color: red;">(${ activeBookings.size()})</span>
						          	</c:if>
						        </button>
						    </h2>
						</div>

						<div id="headingOne">
							<h2 class="mb-0">
						    	<button class="btn btn-lg btn-block btn-outline-secondary btn-acc-menu shadow-sm collapsed"
						    	 type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
						        	<fmt:message key="account_jsp.button.active_requests"/>
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
						        	<fmt:message key="account_jsp.button.rejected_requests"/>
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
						 		<h3>
						 			<span>
						 				<fmt:message key="account_jsp.label.active_requests"/>
						 			</span>
						 		</h3>
						  	</div>
						  	<div class="card-body">
						  		<c:choose>
						  			<c:when test="${empty activeBookingRequests}">
						  				<div class="alert alert-danger">
						  					<span>
						  						<fmt:message key="account_jsp.label.no_active_requests"/>
						  					</span>
										</div>
						  			</c:when>
						  			<c:when test="${not empty activeBookingRequests}">
										<table class="table table-hover text-center">
											<thead>  	
												<tr class="text-muted">
													<th><fmt:message key="account_jsp.label.request_number"/></th>
												    <th><fmt:message key="account_jsp.label.date_in"/></th>
												    <th><fmt:message key="account_jsp.label.date_out"/></th>
												    <th><fmt:message key="account_jsp.label.capacity"/></th>
												    <th><fmt:message key="account_jsp.label.class"/></th>
												</tr>
											</thead>
											<c:forEach var="bookingRequest" items="${activeBookingRequests}">
												<tbody>
													<tr>
														<td align="center">${bookingRequest.id}</td>
												    	<td align="center">${bookingRequest.dateIn}</td>
												        <td align="center">${bookingRequest.dateOut}</td>
												        <td align="center">${bookingRequest.capacity}</td>
												        <td align="center">
												        	<fmt:message key="account_jsp.class.${bookingRequest.roomClass.toArray()[0].title}"/>
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
					
					<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#collapsing-container">
						<div class="card shadow-sm" style="height: 100%;">
							<div class="card-header text-center text-muted" id="headingFour">
						 		<h3>
						 			<span>
						 				<fmt:message key="account_jsp.label.rejected_requests"/>
						 			</span>
						 		</h3>
						  	</div>
						  	<div class="card-body">
						  		<c:choose>
									<c:when test="${empty inactiveBookingRequests}">
								  		<div class="alert alert-danger">
								  			<span>
								  				<fmt:message key="account_jsp.label.no_rejected_requests"/>
								  			</span>
								  		</div>
								  	</c:when>
								  	<c:when test="${not empty inactiveBookingRequests}">
								  		<table class="table table-hover text-center">
								   			<thead>
												<tr class="text-muted">
													<th><fmt:message key="account_jsp.label.request_number"/></th>
												    <th><fmt:message key="account_jsp.label.date_in"/></th>
												    <th><fmt:message key="account_jsp.label.date_out"/></th>
												    <th><fmt:message key="account_jsp.label.capacity"/></th>
												    <th><fmt:message key="account_jsp.label.class"/></th>
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
												        <td align="center">
												        	<fmt:message key="account_jsp.class.${bookingRequest.roomClass.toArray()[0].title}"/>
												        </td>
												        <td align="center">
												        	<form action="controller" method="post">
																<input type="hidden" name="command" value="delete-booking-request" />
																<input type="hidden" name="booking_request_id" value="${bookingRequest.id}" />
																<input type="submit" class="btn btn-sm btn-outline-secondary"
																 value='<fmt:message key="account_jsp.label.ok"/>'>
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
						 		<h3>
						 			<span>
						 				<fmt:message key="account_jsp.label.active_bookings"/>
						 			</span>
						 		</h3>
						  	</div>
						  	<div class="card-body">
						  		<c:choose>
							  		<c:when test="${empty activeBookings}">
							  			<div class="alert alert-danger">
							  				<span>
							  					<fmt:message key="account_jsp.label.no_active_bookings"/>
							  				</span>
										</div>
							  		</c:when>
							  		<c:when test="${not empty activeBookings}">
										<c:forEach var="booking" items="${activeBookings}">
											<div class="col-md-12">
						        				<div class="card mb-12 shadow-sm">
						            				<img style="height: 100%; width: 100%;" src="controller?command=view-image&image_id=${booking.room.images[0].id}" >
						                			<div class="card-body">
						                				<div class="text-center text-muted mb-4">
						                					<h4>
						                						<span>
						                							<fmt:message key="account_jsp.label.booking_number"/> : ${booking.id}
						                						</span>
						                					</h4>
						                				</div>
						                				<hr class="featurette-divider">
						                				<div class="container row text-muted text-center d-flex justify-content-between align-items-stretch"
						                				style="margin: 0!important;">
						                				
							                				<div class="col-md-6">
						                						<div><span><fmt:message key="account_jsp.label.room_details"/></span></div>
						                						<hr class="featurette-divider">
						                						<div>
						                							<small>
						                								<fmt:message key="account_jsp.label.room_number"/> : ${booking.room.number}
						                							</small>
						                						</div>
																<div>
																	<small>
																		<fmt:message key="account_jsp.label.room_capacity"/> : ${booking.room.capacity}
																	</small>
																</div>
																<div>
																	<small>
																		<fmt:message key="account_jsp.label.room_class"/> : 
																		<fmt:message key="account_jsp.class.${booking.room.roomClass.toArray()[0].title}"/>
																	</small>
																</div>
																<div>
																	<small>
																		<span>
																			<a href="controller?command=view-room&room_id=${booking.room.id}" style="color:black;">
																				<fmt:message key="account_jsp.label.all_details"/>
																			</a>
																		</span>
																	</small>
																</div>
						                					</div>
						                	
						                					<div class="col-md-6">
						                						<div>
						                							<span>
						                								<fmt:message key="account_jsp.label.dates"/>
						                							</span>
						                						</div>
						                						<hr class="featurette-divider">
						                						<div>
						                							<small>
						                								<fmt:message key="account_jsp.label.date_in"/> : 
						                								${booking.dateIn}
						                							</small>
						                						</div>
						                						<div>
						                							<small>
						                								<fmt:message key="account_jsp.label.date_out"/> : 
						                								${booking.dateOut}
						                							</small>
						                						</div>	 
							                  					<div>
							                  						<small>
							                  							<fmt:message key="account_jsp.label.total_days"/> : 
							                  							${booking.getTotalBookingDays()}
							                  						</small>
							                  					</div>
						                					</div>
						                				</div>
						                				
						                				<hr class="featurette-divider">
						      
						                  				<div class="d-flex justify-content-center">
							                    			<small class="text-muted">
							                    				<demo:format price="${booking.room.price}"/>
							                    				/<fmt:message key="account_jsp.label.day"/> | 
							                    				<demo:format price="${booking.getTotalPrice()}"/>
							                   					/<fmt:message key="account_jsp.label.total"/>
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
						 		<h3>
						 			<span>
						 				<fmt:message key="account_jsp.label.handling_bookings"/>
						 			</span>
						 		</h3>
						  	</div>
						  	<div class="card-body">
						  		<c:choose>
						  			<c:when test="${ empty handlingBookings }">
						  				<div class="alert alert-danger">
						  					<span>
						  						<fmt:message key="account_jsp.label.no_handling_bookings"/>
						  					</span>
										</div>
						  			</c:when>
						  			<c:when test="${not empty handlingBookings}">
										<c:forEach var="booking" items="${handlingBookings}">
										
											<div class="col-md-12">
						        				<div class="card mb-12 shadow-sm">
						            				<img style="height: 100%; width: 100%;" src="controller?command=view-image&image_id=${booking.room.images[0].id}" >
						                			<div class="card-body">
						                				<div class="text-center text-muted mb-4">
						                					<h4>
						                						<span> 
						                							<fmt:message key="account_jsp.label.booking_number"/> : ${booking.id}
						                						</span>
						                					</h4>
						                				</div>
						                				<hr class="featurette-divider">
						                				<div class="container row text-muted text-center d-flex justify-content-between align-items-stretch"
						                				style="margin: 0!important;">
						                				
							                				<div class="col-md-6">
						                						<div>
						                							<span>
						                								<fmt:message key="account_jsp.label.room_details"/>
						                							</span>
						                						</div>
						                						<hr class="featurette-divider">
						                						<div>
						                							<small>
						                								<fmt:message key="account_jsp.label.room_number"/> : 
						                								${booking.room.number}
						                							</small>
						                						</div>
																<div>
																	<small>
																		<fmt:message key="account_jsp.label.room_capacity"/> : 
																		${booking.room.capacity}
																	</small>
																</div>
																<div>
																	<small>
																		<fmt:message key="account_jsp.label.room_class"/> : 
																		<fmt:message key="account_jsp.class.${booking.room.roomClass.toArray()[0].title}"/>
																	</small>
																</div>
																<div>
																	<small>
																		<span>
																			<a href="controller?command=view-room&room_id=${booking.room.id}" style="color:black;">
																				<fmt:message key="account_jsp.label.all_details"/>
																			</a>
																		</span>
																	</small>
																</div>
						                					</div>
						                	
						                					<div class="col-md-6">
						                						<div>
						                							<span>
						                								<fmt:message key="account_jsp.label.dates"/>
						                							</span>
						                						</div>
						                						<hr class="featurette-divider">
						                						<div>
						                							<small>
						                								<fmt:message key="account_jsp.label.date_in"/> : 
						                								${booking.dateIn}
						                							</small>
						                						</div>
						                						<div>
						                							<small>
						                								<fmt:message key="account_jsp.label.date_out"/> : 
						                								${booking.dateOut}
						                							</small>
						                						</div>	 
							                  					<div>
							                  						<small>
							                  							<fmt:message key="account_jsp.label.total_days"/> : 
							                  							${booking.getTotalBookingDays()}
							                  						</small>
							                  					</div>
						                					</div>
						                					
						                					<div class="col-md-12 mt-4">
						                						<div>
						                							<span>
						                								<fmt:message key="account_jsp.label.booking_details"/>
						                							</span>
						                						</div>
						                						<hr class="featurette-divider">
						                						<div>
						                							<small>
						                								<fmt:message key="account_jsp.label.date_of_booking"/> : 
						                								${booking.dateOfBooking}
						                							</small>
						                						</div>
																<div>
																	<small>
																		<fmt:message key="account_jsp.label.expiring_date"/> : 
																		${booking.getExpiringDate()}
																	</small>
																</div>
							                  					<div>
							                  						<span>
							                  							<small>
							                  								<fmt:message key="account_jsp.label.booking_status"/> : 
							                  								
							                  								<c:choose>
							                  									<c:when test="${
							                  											booking.bookingStatus.toArray()[0].title ne 'unconfirmed' or
							                  											booking.bookingStatus.toArray()[0].title ne 'expired' or
							                  											booking.bookingStatus.toArray()[0].title ne 'active'}">
							                  											
							                  										<fmt:message key="account_jsp.booking_status.not_paid"/>
							                  									</c:when>
							                  									<c:when test="${
							                  											booking.bookingStatus.toArray()[0].title eq 'unconfirmed' or
							                  											booking.bookingStatus.toArray()[0].title eq 'expired' or
							                  											booking.bookingStatus.toArray()[0].title eq 'active'}">
							                  											
							                  										<fmt:message key="account_jsp.booking_status.${booking.bookingStatus.toArray()[0].title}"/>
							                  									</c:when>
							                  								</c:choose>
							                  								
							                  							</small>
							                  						</span>
							                  					</div>
						                						<hr class="featurette-divider">
						                					</div>
						                					
						                				</div>
						      
						                  				<div class="d-flex justify-content-between align-items-center">
							                    			<div class="btn-group">
														   		<c:choose>
																	<c:when test="${booking.bookingStatus.toArray()[0].title eq 'unconfirmed'}">
																		<a class="btn btn-sm btn-outline-secondary"
																		 href="controller?command=confirm-booking&booking_id=${booking.id}">
																		 	<fmt:message key="account_jsp.button.confirm"/>
																		 </a>
																	</c:when>
																	<c:when test="${booking.bookingStatus.toArray()[0].title ne 'unconfirmed'}">
																		<a class="btn btn-sm btn-outline-secondary" 
																		href="controller?command=view-bill&booking_id=${booking.id}">
																			<fmt:message key="account_jsp.button.see_bill"/>
																		</a>
																	</c:when>
																</c:choose>
																<a class="btn btn-sm btn-outline-secondary" 
																href="controller?command=delete-booking&booking_id=${booking.id}">
																	<fmt:message key="account_jsp.button.reject"/>
																</a>
							                    			</div>
						                    				<small class="text-muted">
						                    					<demo:format price="${booking.room.price}"/>
						                    					/<fmt:message key="account_jsp.label.day"/> | 
						                    					<demo:format price="${booking.getTotalPrice()}"/>
						                    					/<fmt:message key="account_jsp.label.total"/>
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