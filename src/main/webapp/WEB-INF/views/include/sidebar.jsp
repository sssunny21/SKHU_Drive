<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<sec:authorize access="authenticated">
	<div class="col-sm-3 col-md-2 sidebar">
		<ul class="nav nav-sidebar">
			<div class="dropdown">
				<button class="btn btn-default dropdown-toggle" type="button"
					id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="true">
					학과 선택 <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
					<li><a href="/drive/pdrive/main.pd?d_id=1">소프트웨어공학과</a></li>
					<li><a href="/drive/pdrive/main.pd?d_id=2">컴퓨터공학과</a></li>
					<li><a href="/drive/pdrive/main.pd?d_id=3">정보통신공학과</a></li>
					<li><a href="/drive/pdrive/main.pd?d_id=4">글로컬it학과</a></li>
				</ul>
			</div>
		</ul>
		<ul class="nav nav-sidebar">
			<li>나의 드라이브</li>
			<c:forEach var="mydrive" items="${mydrive}">
				<a href="/drive/pdrive/folderList.pd?dr_id=${mydrive.drive_id }">
					${mydrive.drive_name }</a>
				<hr />
			</c:forEach>
			<li>나의 폴더</li>
			<c:forEach var="myfolder" items="${myfolder}">
				<a href="/drive/pdrive/folderList2.pd?fd_id=${myfolder.folder_id }">
					${myfolder.folder_name }</a>
				<hr />
			</c:forEach>
		</ul>
	</div>
</sec:authorize>