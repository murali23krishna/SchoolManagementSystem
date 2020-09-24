<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>SchoolManagementSystem</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  </script>
<style>

/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Set height of the grid so .sidenav can be 100% (adjust as needed) */
.row.content {
	height: 450px
}

/* Set gray background color and 100% height */
.sidenav {
	padding-top: 20px;
	background-color: #f1f1f1;
	height: 100%;
}

/* Set black background color, white text and some padding */
footer {
	background-color: #555;
	color: white;
	padding: 15px;
}

/* On small screens, set height to 'auto' for sidenav and grid */
@media screen and (max-width: 767px) {
	.sidenav {
		height: auto;
		padding: 15px;
	}
	.row.content {
		height: auto;
	}
}
</style>
</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/home"><b>SMS</b></a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li><a href="/createStudentPage">Create Student</a></li>
					<li><a href="/createTeacherPage">Create Teacher</a></li>
					<li><a href="/searchUserPage">Update User</a></li>
					<li><a href="/searchStudentPage">Update Student</a></li>
					<li><a href="/searchTeacherPage">Update Teacher</a></li>					
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a>Welcome: <security:authorize
								access="isAuthenticated()">
								<security:authentication var="loggedInUser" property="principal" />
								${loggedInUser.username}
							</security:authorize></a></li>
					<li><a href="/logout"><span
							class="glyphicon glyphicon-log-in"></span> Logout</a></li>
					<!-- <li><a href="/registerPage""><span class="glyphicon glyphicon-log-in"></span> </a></li> -->
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid text-center">
		<div class="row content">
			<div class="col-sm-8 text-left">
				<div align="center">
					<c:choose>
						<c:when test="${not empty oauthClient.successMessage}">
							<h3 style='color: green'>${oauthClient.successMessage}</h3>
							<c:remove var="message" />
						</c:when>
						<c:when test="${not empty oauthClient.errorMessage}">
							<h3 style='color: red'>${oauthClient.errorMessage}</h3>
							<c:remove var="message" />
						</c:when>
					</c:choose>
				</div>
				<form:form method="post" action="/registerOAuthClient" modelAttribute="oauthClient">
					<table align="center">

						<tr>
							<td colspan="2"><h3 align="center">OAUTH CLIENT REGISTRATION</h3></td>
						</tr>

						<tr>
							<td>Client Id:</td>
							<td><form:input type="text" class="form-control"
									path="client_id" id="client_id" /></td>

						</tr>

						<tr>
							<td>Client Secret:</td>
							<td><form:input type="password" path="client_secret"
									class="form-control" id="client_secret" /></td>
						</tr>

						<tr>
							<td>Redirect URI:</td>
							<td><form:input type="text" class="form-control"
									path="web_server_redirect_uri" id="web_server_redirect_uri" /></td>
						</tr>
						
						<tr>
							<td>Scope:</td>
							<td><form:input type="text" class="form-control"
									path="scope" id="scope" /></td>
						</tr>
						
						<tr>
							<td>AccessToken validity:</td>
							<td><form:input type="text" class="form-control"
									path="access_token_validity" id="access_token_validity" /></td>
						</tr>
						
						<tr>
							<td>RefreshToken validity:</td>
							<td><form:input type="text" class="form-control"
									path="refresh_token_validity" id="refresh_token_validity" /></td>
						</tr>
						
						<tr>
							<td>Resource Ids:</td>
							<td><form:input type="text" class="form-control"
									path="resource_ids" id="resource_ids" /></td>
						</tr>
						
						<tr>
							<td>GrantTypes:</td>
							<td><form:input type="text" class="form-control"
									path="authorized_grant_types" id="authorized_grant_types" /></td>
						</tr>
						
						
						<tr>
							<td>Authorities:</td>
							<td><form:input type="text" class="form-control"
									path="authorities" id="authorities" /></td>
						</tr>
						
						<tr>
							<td>Additional Information:</td>
							<td><form:input type="text" class="form-control"
									path="additional_information" id="additional_information" /></td>
						</tr>
						
						<tr>
							<td>Autoapprove:</td>
							<td><form:input type="text" class="form-control"
									path="autoapprove" id="autoapprove" /></td>
						</tr>
						
						

						

					</table>
					<table align="center">
						<tr>
							<td></td>
							<td><input type="submit" value="Create" /></td>
						</tr>
					</table>
				</form:form>

			</div>

		</div>
	</div>

	<footer class="container-fluid text-center">
		<p>This site is copy right by SMS @ 2020</p>
	</footer>

</body>
</html>
