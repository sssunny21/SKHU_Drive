<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">
	<div class="header clearfix" style="margin-top: 0px;">
		<nav>
			<ul class="nav nav-pills pull-right">
				<!--
				<li role="presintation"><a href="#" onload="createFdPopup()"
					onclick="createFdPopup(${drive.drive_id})">폴더생성</a></li>
				-->
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">정렬 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">이름 오름차순</a></li>
						<li><a href="#">이름 내림차순</a></li>
						<li><a href="#">날짜 오름차순</a></li>
						<li><a href="#">날짜 내림차순</a></li>
					</ul>
				</li>
			</ul>
		</nav>
		<c:forEach var="department" items="${department}">
			<h5 class="text-muted">학과  ${department.d_name }</h5>
		</c:forEach>
	</div>
	<h2 class="sub-header">세부사항</h2>
	<div class="table-responsive">
		<form:form method="post" id="formDrive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>
							<div class="checkbox"
								style="margin-top: 0px; margin-bottom: 0px;">
								<label><input type="checkbox"></label>
							</div>
						</th>
						<th>드라이브 이름</th>
						<th>교수 이름</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="drive" items="${main}">
						<tr data-url="folderList.pd?dr_id=${drive.drive_id }">
							<td>
								<div class="checkbox"
									style="margin-top: 0px; margin-bottom: 0px;">
									<label><input type="checkbox" name="drive_id"
										value="${drive.drive_id}"></label>
								</div>
							</td>
							<td>${drive.drive_name}</td>
							<td>${drive.u_name}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<button class="btn" type="submit" name="cmd" value="saveFavorites">즐겨찾기</button>
		</form:form>
	</div>
</div>
<div id="popupCreate" style="display: none;"></div>

