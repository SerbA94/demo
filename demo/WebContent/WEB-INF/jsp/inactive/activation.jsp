<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Activation" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<main role="main" class="flex-shrink-0 min-h-100">
			<div class="d-flex justify-content-center row">
		
			<div class="col-md-9 mt-4">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			</div>
		
			<div class="col-md-9 mt-4">
				<div class="card shadow-sm">
					<div class="card-header text-center text-muted">
						<h3><span>Your account inactive</span></h3>
					</div>
					<div class="card-body d-flex justify-content-center row">
						
						<div class="col-md-9 text-center text-muted mt-4">
							<h4>
								<span>Please confirm your email: </span>
								<span><c:out value="(${user.email})" /></span>
							</h4>
							<hr class="featurette-divider">
						</div>
					
						<div class="col-md-9 d-flex justify-content-center mb-4">
							<a href="controller?command=activation-mail" class="btn btn-sm btn-outline-secondary">
								<i class="fa fa-refresh spin" aria-hidden="true"></i>
								Resend email
							</a>
						</div>
						
					</div>
				</div>
			</div>

		</div>
		
		</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>