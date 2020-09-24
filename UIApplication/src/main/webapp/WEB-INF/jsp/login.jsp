<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" %>
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
				<a class="navbar-brand" href="#"><b>SMS</b></a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/home">Home</a></li>
					<li><a href="/home">About</a></li>
					<li><a href="#">Projects</a></li>
					<li><a href="#">Contact</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
							Login</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid text-center">
		<div class="row content">			
			<div class="col-sm-8 text-left">				

				<div class="container" align="center">
					<div class="d-flex justify-content-center h-100">
						<div class="card">
							<div class="card-header">
								<h3>Sign In</h3>
								<div class="d-flex justify-content-end social_icon">
									<span><i class="fab fa-facebook-square"></i></span> <span><i
										class="fab fa-google-plus-square"></i></span> <span><i
										class="fab fa-twitter-square"></i></span>
								</div>
							</div>

							<div align="center">
								<c:choose>
									<c:when test="${not empty user.successMessage}">
										<h3 style='color: green'>${user.successMessage}</h3>
										<c:remove var="message" />
									</c:when>
									<c:when test="${not empty user.errorMessage}">
										<h3 style='color: red'>${user.errorMessage}</h3>
										<c:remove var="message" />
									</c:when>
								</c:choose>
							</div>

							<div class="card-body">
								<form method="POST" action="/login">
									<div class="input-group form-group">
										<div class="input-group-prepend">
											<span class="input-group-text"><i class="fas fa-user"></i></span>
										</div>
										<input type="text" name="username" class="form-control" placeholder="username" />

									</div>
									<div class="input-group form-group">
										<div class="input-group-prepend">
											<span class="input-group-text"><i class="fas fa-key"></i></span>
										</div>
										<input type="password" name="password" class="form-control"
											placeholder="password" />
									</div>
									<div class="row align-items-center remember">
										<input type="checkbox">Remember Me
									</div>
									<div class="form-group">
										<button type="submit" value="login"
											class="btn float-right login_btn">login</button>
									</div>
								</form>
							</div>
							<div class="card-footer">
								<div class="d-flex justify-content-center links">
									Don't have an account?<a href="/registerPage">Sign Up</a>
								</div>
								<div class="d-flex justify-content-center">
									<a href="#">Forgot your password?</a>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
			
		</div>
	</div>

	<footer class="container-fluid text-center">
		<p>This site is copy right by SMS @ 2020</p>
	</footer>

</body>
</html>
