<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table border="1" width="90%">
	<tr>
		<td align="center">
		<% if(session.getAttribute("UserId") == null){ %>
			<a href="./Session/LoginForm.jsp">로그인</a>
		<% } else { %>
			<a href="./Session/Logout.jsp">로그아웃</a>
		<% } %>
		
		&nbsp;&nbsp;&nbsp;
		<a href=".Board/List.jsp">게시판(페이징x)</a>
		&nbsp;&nbsp;&nbsp;
		<a href=".PagingBoard/List.jsp">게시판(페이징o)</a>
		</td>
	</tr>
</table>
