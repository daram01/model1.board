<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="model1.board.BoardDAO" %>
<%@ page import ="model1.board.BoardDTO" %>
<%@ include file="./IsLoggedIn.jsp"%> 
<%
	String num = request.getParameter("num");
	System.out.println("num" + num);

	BoardDTO dto = new BoardDTO();
	BoardDAO dao = new BoardDAO(application);
	dto = dao.selectView(num);
	
	System.out.println("dto" + dto.getId());
	
	String sessionId = session.getAttribute("UserId").toString();
	
	System.out.println(sessionId);
	
	int delResult = 0;
	
	if (sessionId.equals(dto.getId())){
		dto.setNum(num);
		delResult = dao.deletePost(dto);
		dao.close();
		
		if(delResult == 1){
			JSFunction.alertLocation("삭제되었습니다.", "List.jsp", out);
		} else {
			JSFunction.alertBack("삭제에 실패하였습니다.", out);
		}
	} else {
		JSFunction.alertBack("본인만 삭제할 수 있습니다.", out);
		
		return;
	}
%>
