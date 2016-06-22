<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">
	<div class="header clearfix" style="margin-top: 0px;">
		<nav>
			<ul class="nav nav-pills pull-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">정렬 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">이름 오름차순</a></li>
						<li><a href="#">이름 내림차순</a></li>
						<li><a href="#">날짜 오름차순</a></li>
						<li><a href="#">날짜 내림차순</a></li>
					</ul></li>
			</ul>
		</nav>
		<c:forEach var="department" items="${department}">
			<h5 class="text-muted">학과 ${department.d_name }</h5>
		</c:forEach>
	</div>
	<h4 class="sub-header">
		<c:forEach var="drive" items="${main}" begin="0" end="0">
			${drive.d_name }
		</c:forEach>
	</h4>
	<div class="row">
		<form:form method="post" id="formDrive">
		<button class="btn" type="submit" name="cmd" value="saveFavorites" style="background-color: rgba(255, 255, 255, 0);">
			<img class="btn-img" src="/drive/res/images/star.png" style="width: 20px; height: 20px;">
		</button>
			<hr>
			<c:forEach var="drive" items="${main}">
				<c:if test="${drive.d_auth eq 2 }">
					<div class="img-thumbnail" style="margin-bottom: 40px; margin-right: 80px;">
					<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
							<label><input type="checkbox" name="drive_id" value="${drive.drive_id}"></label>
					</div>
					<a href="folderList.pd?dr_id=${drive.drive_id }">
					<img data-src="folderList.pd?dr_id=${drive.drive_id }"
						alt="${drive.drive_name}"
						src="/drive/res/images/drive.png" 
						style="width: 120px; height: 120px;">
					</a>
						<h6>${drive.drive_name}</h6>
					</div>
				</c:if>
			</c:forEach>
		</form:form>
	</div>
</div>
<div id="popupCreate" style="display: none;"></div>
