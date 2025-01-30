<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Report Application</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">

<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h3 class="pb-3 pt-3">Report Application</h3>


		<form:form action="search" modelAttribute="search">

			<table>

				<tr>

					<td>Plan Name</td>
					<td><form:select path="planName">
							<form:option value="">--Select--</form:option>
							<form:options items="${names}" />
						</form:select></td>

					<td>Plan Status</td>
					<td><form:select path="planStatus">
							<form:option value="">--Select--</form:option>
							<form:options items="${status}" />
						</form:select></td>


					<td>Gender</td>
					<td><form:select path="gender">
							<form:option value="">--Select--</form:option>
							<form:option value="Male">Male</form:option>
							<form:option value="Fe-Male">Fe-Male</form:option>
						</form:select></td>

				</tr>
				<tr>

					<td>Start Date</td>
					<td><form:input type="date" path="startDate" /></td>
					<td>End Date</td>
					<td><form:input type="date" path="endDate" /></td>

				</tr>
				<tr>
					<td><a href="/01_Reports_App/" class="btn btn-secondary">Reset</a>

					<td><input type="submit" value="Search"
						class="btn btn-primary"></td>
				</tr>

			</table>

		</form:form>

		<hr />
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th>SNo</th>
					<th>ID</th>
					<th>Name</th>
					<th>Gender</th>
					<th>Plan Name</th>
					<th>Plan Status</th>
					<th>Start Date</th>
					<th>End Date</th>
					<th>Benefit Amount</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${plans}" var="plan" varStatus="index">
					<tr>
						<td>${index.count}</td>
						<td>${plan.id}</td>
						<td>${plan.name}</td>
						<td>${plan.gender}</td>
						<td>${plan.planName}</td>
						<td>${plan.planStatus}</td>
						<td>${plan.planStartDate}</td>
						<td>${plan.planEndDate}</td>
						<td>${plan.benefitAmt}</td>
					</tr>
				</c:forEach>

				<tr>
					<c:if test="${empty plans}">
						<td colspan="9" style="text-align: center">No Records Found</td>
					</c:if>

				</tr>
			</tbody>
		</table>


		<hr />

		Export : <a href="excel">EXCEL</a> &nbsp; <a href="pdf">PDF</a>


	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>
</body>
</html>