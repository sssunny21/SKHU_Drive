<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h3>My Page</h3>

<sec:authorize access="authenticated">
	<div class="col-md-6">
		<h3>my info</h3>
		<table class="table table-bordered">
			<tr>
				<td>이름</td>
				<td><sec:authentication property="user.u_name" /></td>
			</tr>

			<tr>
				<td>이메일</td>
				<td><sec:authentication property="user.u_email" /></td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><sec:authentication property="user.u_tel" /></td>
			</tr>
			<tr>
				<td>학년</td>
				<td><sec:authentication property="user.u_grade" /></td>
			</tr>
			<tr>
				<td>학과</td>
				<td><sec:authentication property="user.d_id" /></td>
			</tr>

		</table>
		<a href="/drive/user/myinfo_edit.pd" class="btn btn-primary">개인정보
			수정</a> <a href="/drive/user/myinfo_pw.pd" class="btn btn-primary">비밀번호
			변경</a>
	</div>
	<div class="col-md-6">
		<h3>my favorite</h3>
		<hr>
		<form:form method="post" id="formMypage">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>
						<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
							<label></label>
						</div>
					</th>
					<th>즐겨찾는 드라이브</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="mydrive" items="${mydrive}">
					<tr
						data-url="/drive/pdrive/folderList.pd?dr_id=${mydrive.drive_id }"
						data-id="${mydrive.drive_id}">
						<td>
							<div class="checkbox"
								style="margin-top: 0px; margin-bottom: 0px;">
								<label><input type="checkbox" name="drive_id"
									value="${mydrive.drive_id}"></label>
							</div>
						</td>
						<td>${mydrive.drive_name }</td>
					</tr>
				</c:forEach>
			</tbody>
			<thead>
				<tr>
					<th>
						<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
							<label></label>
						</div>
					</th>
					<th>즐겨찾는 폴더</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="myfolder" items="${myfolder}">
					<tr
						data-url="/drive/pdrive/folderList2.pd?fd_id=${myfolder.folder_id }"
						data-id="${myfolder.folder_id}">
						<td>
							<div class="checkbox"
								style="margin-top: 0px; margin-bottom: 0px;">
								<label><input type="checkbox" name="folder_id"
									value="${myfolder.folder_id}"></label>
							</div>
						</td>
						<td>${myfolder.folder_name }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<button class="btn" type="submit" name="cmd" value="deleteFavorite">폴더 삭제</button>
		<button class="btn" type="submit" name="cmd" value="deleteFavorite2">드라이브 삭제</button>
	</form:form>
	</div>
</sec:authorize>

