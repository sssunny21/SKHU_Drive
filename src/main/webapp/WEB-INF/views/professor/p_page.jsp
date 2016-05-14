<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<h3>현재 사용자</h3>

<sec:authorize access="authenticated">
	<table class="table table-bordered" style="width: 500px;">
		<tr>
         <td>로그인ID</td>
         <td><sec:authentication property="professor.p_id" /></td>
      </tr>
      <tr>
         <td>이름</td>
         <td><sec:authentication property="professor.p_name" /></td>
      </tr>

      <tr>
         <td>이메일</td>
         <td><sec:authentication property="professor.p_email" /></td>
      </tr>
      <tr>
         <td>전화번호</td>
         <td><sec:authentication property="professor.p_tel" /></td>
      </tr>
  
      <tr>
         <td>학과</td>
         <td><sec:authentication property="professor.d_id2" /></td>
      </tr>
      
	</table>
	<a href="/drive/user/myinfo_edit.pd" class="btn btn-primary">개인정보 수정</a>
</sec:authorize>

