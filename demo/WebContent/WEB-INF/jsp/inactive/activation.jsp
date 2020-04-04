<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>
<c:set var="title" value="Activation" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body class="d-flex flex-column h-100">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<main role="main" class="flex-shrink-0">
			<div class="container">
				<%@ include file="/WEB-INF/jspf/error_handling.jspf"%>
				<div>
					<h1>
						<span>Please confirm your email: </span>
						<span><c:out value="(${user.email})" /></span>
					</h1>
				</div>
				<div><a href="controller?command=activation-mail">resend email</a></div>
			</div>
		</main>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>

</body>
</html>