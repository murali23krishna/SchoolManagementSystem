<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
					<li><a href="/registerOAuthClientPage">Register OAuth Client</a></li>					
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
						<c:when test="${not empty teacher.successMessage}">
							<h3 style='color: green'>${teacher.successMessage}</h3>
							<c:remove var="message" />
						</c:when>
						<c:when test="${not empty teacher.errorMessage}">
							<h3 style='color: red'>${teacher.errorMessage}</h3>
							<c:remove var="message" />
						</c:when>
					</c:choose>
				</div>
				<form:form method="post" action="/updateTeacher" modelAttribute="teacher">
					<table align="center">

						<tr>
							<td colspan="2"><h3 align="center">Update Teacher</h3></td>
						</tr>
						
						<tr>
							<td></td>
							<td><form:input type="hidden" 
									path="id" value="${teacher.id} " /></td>

						</tr>
						
						<tr>
							<td>Name:</td>
							<td><form:input type="text" class="form-control"
									path="name" id="name" /></td>

						</tr>

						<tr>
							<td>Age:</td>
							<td><form:input type="text" path="age"
									class="form-control" id="age" /></td>
						</tr>

						<tr>
							<td>Join Date:</td>
							<td><form:input type="text" class="form-control"
									path="joinDate" id="datepicker" /></td>
						</tr>
						
						<tr>
							<td>Address:</td>
							<td><form:input type="text" class="form-control"
									path="address" id="address" /></td>
						</tr>
						
						<tr>
							<td>Teacher Type:</td>
							<td><form:select path="teacherType" class="form-control">
									<form:options items="${teacherTypes}" />
								</form:select>
						</tr>

						<tr>
							<td colspan="2"><h3>Subjects</h3></td>
						</tr>

						<tr>
							<td></td>
							<td><form:select path="uISubjects" items="${subjects}" /></td>

						</tr>

					</table>
					<table align="center">
						<tr>
							<td></td>
							<td><input type="submit" value="Update" /></td>
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
