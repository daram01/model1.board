<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="model1.board.BoardDTO" %>
<%@ page import="model1.board.BoardDAO" %>
<%
	// DAO를 생성해 DB에 연결
	BoardDAO dao = new BoardDAO(application); // 어플리케이션을 생성자의 매개변수로 전달해준다.

	// 사용자가 입력한 검색 조건을 Map에 저장
	//   키워드,	서치
	Map<String, Object> param = new HashMap<String, Object>();
	String searchField = request.getParameter("searchField");
	String searchWord = request.getParameter("searchWord");
	if(searchWord != null){ // 검색하기 버튼이 눌려진 상태 + 파라메터 값(서치워드가 입력이 된 상황)이 넘어온 상황
		param.put("searchField", searchField); // map에 저장. 값은 requst로 받아온 값을 변수로 담은 것.
		param.put("searchWord", searchWord); // map에 저장
	}
	
	// 전체 게시글 수 가져오기
	int totalCount = dao.selectCount(param); // 게시물 수 확인
	List<BoardDTO> boardLists = dao.selectList(param); // 게시물 목록 받기
	dao.close(); // DB연결 닫기
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="./common/Link.jsp"></jsp:include>
	<h2>목록 보기(List)</h2>
	<!-- 검색폼 -->
	<form method="get"> URL에 입력된 정보값을 쿼리스트링 형태로 서버로 보낸다는 것. action값이 없다는 것이 특징. 
	<table border="1" width="90%">
	<tr>
		<td align="center">
			<select name="searchField">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchWord" />
			<input type="submit" value="검색하기" />
		</td>
	</tr>
	</table>
	</form>
	검색어 입력한 것들 데이터베이스로 전달하는 것
	
	<!-- 게시물 목록 테이블 (표) -->
	<table border="1" width="90%">
		<!-- 각 컬럼의 이름 -->
		<tr>
			<th width="10%">번호</th>
			<th width="50%">제목</th>
			<th width="15%">작성자</th>
			<th width="10%">조회수</th>
			<th width="15%">작성일</th>
		</tr>
		
				<!-- 목록의 내용 -->
		<%
		if( boardLists.isEmpty() ){			
			// 게시물이 하나도 없을 때
		%>
		<tr>
			<td colspan="5" align="center">
				등록된 게시물이 없습니다. ^^*
			</td>
		</tr>
		<%
		}
		else {
			// 게시물이 있을 때
			int virtualNum = 0; // 화면상에서의 게시물 번호
			for(BoardDTO dto : boardLists)
			{
				virtualNum = totalCount--; // 전체 게시물 수에서 시작해 1씩 감소
		%>
				<tr align="center">
				<td><%=virtualNum %></td>
				<td align="left">
					<a href="View.jsp?num<%= dto.getNum() %>"><%= dto.getTitle() %>
					</a>
				</td>
				<td align="center"><%= dto.getId() %></td> <!-- 작성자 아이디 -->
				<td align="center"><%= dto.getVisitcount() %></td> <!-- 조회수 -->
				<td align="center"><%= dto.getPostdate() %></td> <!-- 작성일 -->
				</tr>
		<%
			}
		}
		%>

	</table>
	
	
	<!-- 목록 하단의 [글쓰기] 버튼 -->
	<table border="1" width="90%">
		<tr align="right">
			<td><button type="button" onclick="location.href='Write.jsp';">글쓰기
				</button></td>
		</tr>
	</table>
	
</body>
</html>