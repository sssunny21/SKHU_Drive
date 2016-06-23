<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
#share_t {
	display: none;
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
				<li><h4 class="text-muted">홈 > ${dn.drive_name}</h4></li>
				<li class="right">
				<center>
				<img src="/drive/res/images/create_folder.png"
					style="width: 40px; height: 40px;" data-toggle="modal"
					data-target=".bs-example-modal-sm">
				<h6>폴더 생성</h6></center>
				</li>
				<form:form method="post" modelAttribute="folder">
					<div class="modal fade bs-example-modal-sm" tabindex="-1"
						role="dialog" aria-labelledby="mySmallModalLabel">
						<div class="modal-dialog modal-sm">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title">폴더 생성</h4>
							</div>
							<div class="modal-body">
								<label>공유 여부 </label>
								<div class="checkbox">
									<input type="checkbox" id="share_tn" name="share" value=1>공유폴더
								</div>
								<div id="share_n">
									<label>폴더 이름 </label>
									<form:input path="folder_name" />
									<br>
									<form:hidden path="drive_id" id="dr_id" name="dr_id" />
								</div>
								<div id="share_t">
									<label>폴더 이름 </label>
									<form:input path="sfolder_name" />
									<br> <label>폴더 암호 </label>
									<form:input path="sfolder_pw" />
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">닫기</button>
								<button class="btn" type="submit" name="cmd"
									value="createfolder">만들기</button>
							</div>
						</div>
					</div>
				</form:form>
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
		<form:form method="post" id="formFavorites">
		<button class="right" type="submit" name="cmd" value="deleteFolder"style="background-color: rgba(255, 255, 255, 0); padding-right: 0px;">
			<img class="btn-img" src="/drive/res/images/delete.png" style="width: 40px; height: 40px;">
			<h6>폴더 삭제</h6>
		</button>
		<button class="right" type="submit" name="cmd" value="saveFavorites" style="background-color: rgba(255, 255, 255, 0); margin-right: 25px;">
			<img class="btn-img" src="/drive/res/images/star.png" style="width: 40px; height: 40px;">
			<h6>즐겨찾기</h6>
		</button>
		
			<c:forEach var="dr1" items="${dr1}">
				<c:choose>
					<c:when test="${dr1.parent_id==0}">
						<div class="img-thumbnail" style="margin-bottom: 40px; margin-right: 60px;">
							<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
								<label><input type="checkbox" name="folder_id" value="${dr1.folder_id}"></label>
							</div>
							<a href="folderList2.pd?fd_id=${dr1.folder_id}&dr_id=${dr1.drive_id}">
								<img data-src="folderList.pd?dr_id=${drive.drive_id }"
								alt="${dr1.folder_name}" src="/drive/res/images/folder.png"
								style="width: 100px; height: 100px;">
							</a>
							<center>
							<h6>${dr1.folder_name}<img src="/drive/res/images/edit.png"
									style="width: 20px; height: 20px; margin-left: 10px;" data-toggle="modal"
									data-target=".${dr1.folder_id}">
							</h6>
							</center>
							<!--<form:form method="post" modelAttribute="folder">
								<div class="modal fade ${dr1.folder_id}" tabindex="1" role="dialog" aria-labelledby="mySmallModalLabel">
									<div class="modal-dialog modal-sm">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title">폴더 수정</h4>
										</div>
										<div class="modal-body">
											<label>폴더 이름 </label>
											<form:input path="folder_name" />
											<br>
											<form:hidden path="drive_id" id="dr_id" name="dr_id" />
											<form:hidden path="folder_id" name="folder_id" value="${dr1.folder_id}" />
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">닫기</button>
										<button class="btn" type="submit" name="cmd"
											value="editfolder">수정</button>
									</div>
								</div>
							</form:form>-->
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
			<hr style="margin-top: 0px;">
			<button class="right" type="submit" name="cmd" value="deleteSFolder"style="background-color: rgba(255, 255, 255, 0); padding-right: 0px;">
			<img class="btn-img" src="/drive/res/images/delete.png" style="width: 40px; height: 40px;">
			<h6>공유 폴더 삭제</h6>
		</button>
			<c:forEach var="dr2" items="${dr2}">
				<div class="img-thumbnail"
					style="margin-bottom: 40px; margin-right: 60px;">
					<div class="checkbox" style="margin-top: 0px; margin-bottom: 0px;">
						<label><input type="checkbox" name="sfolder_id" value="${dr2.sfolder_id}"></label>
					</div>
					<a a href="sfolderList2.pd?fd_id=${dr2.sfolder_id }&dr_id=${dr2.drive_id}">
					<img alt="${dr2.sfolder_name}"
						src="/drive/res/images/lock.png"
						style="width: 100px; height: 100px;" data-toggle="modal"
						data-target=".in${dr2.sfolder_id}">
					</a>
						<!-- <form:form method="post" modelAttribute="folder">
							<div class="modal fade in${dr2.sfolder_id}" tabindex="1"
								role="dialog" aria-labelledby="mySmallModalLabel">
								<div class="modal-dialog modal-sm">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">비밀번호 입력</h4>
									</div>
									<div class="modal-body">
										<label>폴더 비밀번호</label>
										<form:input path="sfolder_pw" />
										<br>
										<form:hidden path="drive_id" id="dr_id" name="dr_id" />
										<form:hidden path="sfolder_id" name="sfolder_id"
											value="${dr2.sfolder_id}" />
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">닫기</button>
									<button class="btn" type="submit" name="cmd" value="insfolder">확인</button>
								</div>
							</div>
						</form:form> -->
					<center>
					<h6>${dr2.sfolder_name}
						<img src="/drive/res/images/edit.png"
							style="width: 20px; height: 20px; margin-left: 10px;" data-toggle="modal"
							data-target=".a${dr2.sfolder_id}">
					</h6>
					</center>
					<!--<form:form method="post" modelAttribute="folder">
						<div class="modal fade a${dr2.sfolder_id}" tabindex="1"
							role="dialog" aria-labelledby="mySmallModalLabel">
							<div class="modal-dialog modal-sm">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title">폴더 수정</h4>
								</div>
								<div class="modal-body">
									<label>폴더 이름 </label>
									<form:input path="sfolder_name" />
									<label>폴더 패스워드</label>
									<form:input path="sfolder_pw" />
									<br>
									<form:hidden path="drive_id" id="dr_id" name="dr_id" />
									<form:hidden path="sfolder_id" name="sfolder_id"
										value="${dr2.sfolder_id}" />
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">닫기</button>
								<button class="btn" type="submit" name="cmd" value="editsfolder">수정</button>
							</div>
						</div>
					</form:form>-->
				</div>
			</c:forEach>
			<br />
		</form:form>
	</div>

</div>