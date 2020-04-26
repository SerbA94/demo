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
        	    <h1 class="jumbotron-heading"><fmt:message key="room_list_jsp.label.rooms"/> </h1>
          		<p class="lead text-muted">
          			<fmt:message key="room_list_jsp.label.description"/>
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
							<div class="text-muted"><h3><span><fmt:message key="room_list_jsp.label.sort"/></span></h3></div>
							<div style="margin-left:20px;margin-right:20px;">
								<select name="orderBy" class="custom-select">
									<c:choose>
										<c:when test="${ not empty orderBy }">
											<option value="${ orderBy }">
												<c:choose>
													<c:when test="${orderBy eq 'price'}">
														<fmt:message key="room_list_jsp.label.price"/>
														[<fmt:message key="room_list_jsp.label.selected"/>]
													</c:when>
													<c:when test="${orderBy eq 'capacity'}">
														<fmt:message key="room_list_jsp.label.capacity"/>
														[<fmt:message key="room_list_jsp.label.selected"/>]
													</c:when>
													<c:when test="${orderBy eq 'room_class_title'}">
														<fmt:message key="room_list_jsp.label.class"/>
														[<fmt:message key="room_list_jsp.label.selected"/>]
													</c:when>
													<c:when test="${userRole.title eq 'admin' and orderBy eq 'room_status_title'}">
														<fmt:message key="room_list_jsp.label.status"/>
														[<fmt:message key="room_list_jsp.label.selected"/>]
													</c:when>
												</c:choose>
											</option>
										</c:when>
										<c:when test="${ empty orderBy }">
											<option value=""><fmt:message key="room_list_jsp.label.no_sorting"/></option>
										</c:when>
									</c:choose>
									<option value="price"><fmt:message key="room_list_jsp.label.price"/></option>
									<option value="capacity"><fmt:message key="room_list_jsp.label.capacity"/></option>
									<option value="room_class_title"><fmt:message key="room_list_jsp.label.class"/></option>
									<c:if test="${ userRole.title eq 'admin' }">
										<option value="room_status_title"><fmt:message key="room_list_jsp.label.status"/></option>
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
							<div class="alert alert-danger">
								<span><fmt:message key="room_list_jsp.label.no_free_rooms"/></span>
							</div>
						</c:when>
						<c:when test="${not empty rooms}">
						
							<c:forEach var="room" items="${rooms}">
								<div class="col-md-4">
						        	<div class="card mb-4 shadow-sm">
						            	<img class="card-img-top" src="controller?command=view-image&image_id=${room.images[0].id}" >
						                <div class="card-body">
						                	<p class="card-text text-muted text-center">
						                		<fmt:message key="room_list_jsp.class.${room.roomClass.toArray()[0].title}"/>,
						                		<fmt:message key="room_list_jsp.label.capacity"/> : ${room.capacity}
						                  		<c:if test="${userRole.title == 'admin'}">
													<br/> <fmt:message key="room_list_jsp.label.status"/> : 
													<fmt:message key="room_list_jsp.status.${room.roomStatus.toArray()[0].title}"/>
												</c:if>
						                  	</p>
						                  	<div class="d-flex justify-content-between align-items-center">
							                    <div class="btn-group">
							                    
							                    	<c:choose>
							                    		<c:when test="${empty userRole or userRole.title eq 'customer'}">
							                   				<a href="controller?command=view-booking-create&room_id=${room.id}" 
							                   				class="btn btn-sm btn-outline-secondary">
							                   					<fmt:message key="room_list_jsp.button.book"/>
							                   				</a>
							                   				<a href="controller?command=view-room&room_id=${room.id}" 
							                   				class="btn btn-sm btn-outline-secondary">
							                   					<fmt:message key="room_list_jsp.button.view"/>
							                   				</a>
														</c:when>
														<c:when test="${userRole.title == 'admin'}">
							                   				<a href="controller?command=view-room-edit&edit_room_id=${room.id}" 
							                   				class="btn btn-sm btn-outline-secondary">
							                   					<fmt:message key="room_list_jsp.button.edit"/>
							                   				</a>
														</c:when>
							                    	</c:choose>
							                      	
							                    </div>
						                    	<small class="text-muted">
						                    		<demo:format price="${room.price}"/>/
						                    		<fmt:message key="room_list_jsp.label.day"/>
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