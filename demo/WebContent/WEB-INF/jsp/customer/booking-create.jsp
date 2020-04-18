<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<c:set var="title" value="Booking create" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>


<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0">
		<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>

		<div class="container mt-2">
			<div class="row justify-content-center align-items-center text-center p-2">
				<div class="m-5 col-sm-8 col-md-6 col-lg-6 shadow-sm mb-5 bg-white border rounded" style="padding: 0;">
					<div class="pt-5" >
						<i class="fa fa-address-book-o fa-5x text-muted" ></i>
						<h2 style="margin: 0;"><span class="text-muted">Book</span></h2>
					</div>
					
					<div class="pb-5 p-3" >

						<form action="controller" method="post">
							<input type="hidden" name="command" value="create-booking" />
							<input type="hidden" name="room_id" value="${room.id}" />
		
							<div class="row d-flex justify-content-around mt-4"> 
								<div class="col-lg-6 align-self-center text-muted">		
									<div><h5><span>Room class : ${room.roomClass.toArray()[0].title}</span></h5></div>
									<div><h5><span>Room number : ${room.number}</span></h5></div>
									<div><h5><span>Capacity : ${room.capacity}</span></h5></div>
								
								</div>
											
								<div class="col-lg-6"> 
									<div>
										<h5><label for="dateIn"><span class="text-muted">Date in</span></label></h5>
										<input type="date" name="dateIn" id="dateIn" class="custom-select" value="${nextDateIn}">
									</div>
									<div class="mt-3">
										<h5><label for="dateOut"><span class="text-muted">Date out</span></label></h5>
										<input type="date" id="dateOut" name="dateOut" class="custom-select"  value="${nextDateOut}">
									</div>
								</div>
							</div>
							<div class="mt-4"><h5><span class="text-muted">Price : <demo:format price="${room.price}"/>/day</span></h5></div>
							<button class="btn btn-lg btn-block btn-outline-secondary mt-4" type="submit">Confirm</button>
						</form>
						
					</div>
					
				</div>
			</div>
		</div>
			
	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>

</html>