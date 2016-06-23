<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
label {
	margin-top: 10px;
}
</style>

<br />
<br />
<center>
	<a href="/drive/home/user_join.pd"><img
		src="/drive/res/images/user_join.png"></a>&emsp;<img
		src="/drive/res/images/p_join.png" data-toggle="modal"
		data-target=".join">
	<form:form method="post" modelAttribute="user">
		<div class="modal fade join" tabindex="1" role="dialog"
			aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-sm">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">교수 인증</h4>
				</div>
				<div class="modal-body">
					<label>교수인증번호</label>
					<form:input path="professor_code" />
					<br>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				<button class="btn" type="submit" name="cmd" value="p_code">확인</button>
			</div>
		</div>
	</form:form>
	<br>
</center>
<br />
<br />
