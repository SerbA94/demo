<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Room list" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<main role="main" class="flex-shrink-0 min-h-100">
		<section class="jumbotron text-center">
        	<div class="container">
        	   <h1 class="jumbotron-heading">Rooms Page</h1>
          		<p class="lead text-muted">
          			Some text description of room list page.Some text description of room list page.
         			<br/>Some text description of room list page.Some text description of room list page.Some text description of room list page.
          		</p>
       		</div>
      	</section>
      
		<div class="d-flex justify-content-center bg-light-gray">
			<a href="#sort" data-toggle="collapse"><i class="icon arrow-down-25" ></i></a>
		</div>
      
		<div id="sort" class="collapse">
			<div class="d-flex justify-content-center">
				<div class="col-md-4" style="margin-top: 1.5rem!important;">
            		<div class="card mb-4 shadow-sm text-center">
            
          				<form action="controller">
							<input type="hidden" name="command" value="view-room-list" />
							<div class="text-muted"><h3><span>Order by</span></h3></div>
							<div style="margin-left:20px;margin-right:20px;">
								<select name="orderBy" class="custom-select">
									<c:choose>
										<c:when test="${ not empty orderBy }">
											<option value="${ orderBy }">
												<c:choose>
													<c:when test="${orderBy eq 'price'}">Price[SELECTED]</c:when>
													<c:when test="${orderBy eq 'capacity'}">Capacity[SELECTED]</c:when>
													<c:when test="${orderBy eq 'room_class_title'}">Class[SELECTED]</c:when>
													<c:when test="${userRole.title eq 'admin' and orderBy eq 'room_status_title'}">Status[SELECTED]</c:when>
												</c:choose>
											</option>
										</c:when>
										<c:when test="${ empty orderBy }">
											<option value="">No sorting</option>
										</c:when>
									</c:choose>
									<option value="price">Price</option>
									<option value="capacity">Capacity</option>
									<option value="room_class_title">Class</option>
									<c:if test="${ userRole.title eq 'admin' }">
										<option value="room_status_title">Status</option>
									</c:if>
								</select>
							</div>
							<input type="submit" class="btn btn-sm btn-outline-secondary" style="margin-top: 1em;" value='submit'>
						</form>
						
					</div>
         		</div>
  			</div>
  		</div>

		<div class="album py-5 bg-light">
	        <div class="container">
	        	<div class="row">
	         
					<c:choose>
						<c:when test="${empty rooms}">
							<div><span>No free rooms</span></div>
						</c:when>
						<c:when test="${not empty rooms}">
						
							<c:forEach var="room" items="${rooms}">
								<div class="col-md-4">
						        	<div class="card mb-4 shadow-sm">
						            	<img class="card-img-top" src="controller?command=view-image&image_id=${room.images[0].id}" >
						                <div class="card-body">
						                	<p class="card-text text-muted text-center">
						                  		${room.roomClass.toArray()[0]}, Capacity : ${room.capacity}
						                  		<c:if test="${userRole.title == 'admin'}">
													<br/> Status : ${room.roomStatus.toArray()[0]}
												</c:if>
						                  	</p>
						                  	<div class="d-flex justify-content-between align-items-center">
							                    <div class="btn-group">
							                    
							                    	<c:choose>
							                    		<c:when test="${empty userRole or userRole.title eq 'customer'}">
							                   				<a href="controller?command=view-booking-create&room_id=${room.id}" class="btn btn-sm btn-outline-secondary">Book</a>
							                   				<a href="controller?command=view-room&room_id=${room.id}" class="btn btn-sm btn-outline-secondary">View</a>
														</c:when>
														<c:when test="${userRole.title == 'admin'}">
							                   				<a href="controller?command=view-room-edit&edit_room_id=${room.id}" class="btn btn-sm btn-outline-secondary">Edit</a>
														</c:when>
							                    	</c:choose>
							                      	
							                    </div>
						                    	<small class="text-muted"><demo:format price="${room.price}"/>/day</small>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
							    
						</c:when>
					</c:choose>
				</div>
			</div>
			
			<c:if test="${ not empty rooms}">
				<div class="d-flex justify-content-center">
					<div class="d-flex justify-content-center" style="height:76px;width:76px;">
						<a href="#" ><i class="fa fa-arrow-circle-o-up fa-5x icon-color" ></i></a>
					</div>				
				</div>
			</c:if>
			
		</div>

	</main>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>