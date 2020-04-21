<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<style>
	<%@ include file="/css/style.css"%>
	<%@ include file="/css/font-awesome-4.7.0/css/font-awesome.min.css"%>
</style>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<main role="main" class="flex-shrink-0">

		<div class="container mt-2">
			
			<div class="shadow-sm mt-4">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
			</div>
		
			<div class="row justify-content-center align-items-center text-center p-2">
				<div class="m-5 col-sm-8 col-md-6 col-lg-4 shadow-sm p-3 mb-5 bg-white border rounded">
					<div class="pt-5 pb-5">
					<i class="fa fa-user-o fa-5x text-muted" ></i>
					
						<h2><span class="text-muted">Login</span></h2>
							<form action="controller" method="post">
								<input type="hidden" name="command" value="login"/>
							
							<div class="form-group input-group-md">
								<input type="text" class="form-control custom-input" name="login" placeholder="<fmt:message key="login_jsp.label.login"/>">
							</div>
							<div class="form-group input-group-md">
								<input type="password" class="form-control custom-input" name="password" placeholder="<fmt:message key="login_jsp.label.password"/>">
							</div>
							
							<button class="btn btn-lg btn-block btn-outline-secondary mt-4" type="submit">
		                       <fmt:message key="login_jsp.button.login"/>
		               		</button>
		               		
							<a href="#" class="float-right mt-2 text-muted">Forgot Password? </a>
						</form>
					</div>
					<a href="controller?command=view-registration" class="text-center d-block mt-2 text-muted">Create an account? </a>
				</div>
			</div>
		</div>
			
	</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>