<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
#files {
	width: 0;
	height: 0;
}

input[type=file] {
	width: 600px;
	margin: 5px 0 5px 0;
}
li.right{float:right;}
button.right{
	float:right;
	background-image: none;
	border: 0px solid transparent;
}
</style>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
	style="margin-left: 0px;">
	<div class="header clearfix" style="margin-top: 0px;">
		<nav>
			<ul class="nav nav-pills">
				<li><h4 class="text-muted">홈 > ${sn.sfolder_name}</h4></li>
				<li class="right" style="margin-bottom: 10px;margin-right: 10px;">
					<form id="uploadform" method="post" enctype="multipart/form-data">
						<img src="/drive/res/images/upload.png"
							style="width: 40px; height: 40px;"
							onclick="document.getElementById('file').click();" /> <input
							type="file" id="file" name="file" style="display: none;"
							onchange="document.getElementById('uploadform').value=this.value" />
					</form>
					<h6>업로드</h6>
				</li>
				<!--<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">정렬 <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="#">이름 오름차순</a></li>
						<li><a href="#">이름 내림차순</a></li>
						<li><a href="#">날짜 오름차순</a></li>
						<li><a href="#">날짜 내림차순</a></li>
					</ul></li>-->

			</ul>
		</nav>
	</div>
	<div class="table-responsive">
		<form:form method="post" id="formfiles">
				<button class="right" type="submit" name="cmd" value="deleteFiles"style="background-color: rgba(255, 255, 255, 0);">
				<img class="btn-img" src="/drive/res/images/delete.png" style="width: 40px; height: 40px;">
				<h6>파일 삭제</h6>
				</button>
			<c:forEach var="fd" items="${fd}">
				<div class="img-thumbnail" style="margin-bottom: 40px; margin-right: 60px;">
					<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
						<label><input type="checkbox" name="files_id" value="${fd.files_id}"></label>
					</div>
					<center>
					<a href="/drive/pdrive/s_download.pd?id=${fd.sfiles_id}">
					<c:url var="imageUrl" value="/pdrive/downloadImage2.pd?id=${ fd.sfiles_id }" />
						<img src="${ imageUrl }" style="width: 100px; height: 100px;" />
					</a>
					<h6>${fd.sfiles_name}
					<a href="/drive/pdrive/download.pd?id=${fd.sfiles_id}">
					<img src="/drive/res/images/download.png" style="width: 20px; height: 20px; margin-left: 10px;" >
					</a>
					</h6></center>
				</div>
			</c:forEach>
		</form:form>
	</div>
</div>