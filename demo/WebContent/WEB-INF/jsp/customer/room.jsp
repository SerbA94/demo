<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Room" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0 min-h-100">
		
		<div class="position-relative overflow-hidden text-center room-main-img shadowtext">

		  <div class="col-md-5 p-lg-5 mx-auto my-5">
		    <h1 class="display-4 font-weight-normal">
		   		<fmt:message key="room_jsp.label.room"/>
		    </h1>
		    <p class="lead font-weight-normal">
		    	<span>
		    		${description}
		    	</span>
		    </p>
		    <div>
		   		<span>
		   			<fmt:message key="room_jsp.label.room_class"/> : 
		   		</span>
		   		<span>
		   			<fmt:message key="room_jsp.class.${room.roomClass.toArray()[0].title}"/>
		   		</span>
		   		<span>
		   			, <fmt:message key="room_jsp.label.capacity"/> : 
		   		</span>
		   		<span>
		   			${room.capacity}.
		   		</span>
			</div>
			<div>
				<span>
					<fmt:message key="room_jsp.label.price"/>
				</span>
				<span>
					<demo:format price="${room.price}"/>/<fmt:message key="room_jsp.label.day"/>
				</span>
			</div>
		    					
		    <c:if test="${room.roomStatus.toArray()[0].title eq 'free'}">
				<a class="btn btn-lg btn-secondary mb1 black bg-gray" href="controller?command=view-booking-create&room_id=${room.id}">
					<span>
						<fmt:message key="room_jsp.button.book_now"/>
					</span>
				</a>
			</c:if>
		    
		  </div>

		</div>
		
		<c:if test="${not empty room.images}">

			<c:set var="counter" value= "${ 0 }" />
			<c:forEach begin="0" end="${room.images.size()}" step="2" varStatus="loop">
		   		<div class="d-md-flex flex-md-equal w-100 my-md-3 pl-md-3">
				   		 	
			   		<c:if test="${ counter < room.images.size() }">
				   		<div class="img-con">
				   			<img class="room-side-img" src="controller?command=view-image&image_id=${room.images.get(counter).id}" >
							<c:set var="counter" value= "${ counter + 1 }" />
				 		</div>
				   	 </c:if>
					 <c:if test="${ counter < room.images.size() }">
				   	 	<div class="img-con">
							<img class="room-side-img" src="controller?command=view-image&image_id=${room.images.get(counter).id}" >
							<c:set var="counter" value= "${ counter + 1 }" />
						</div>
			   		 </c:if>
			   		 	
		   		 </div>
		   	</c:forEach>
		   	
		</c:if>

	</main>
	
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
</body>

</html>