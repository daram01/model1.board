package model1.board;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import common.JDBConnect;

public class BoardDAO extends JDBConnect{
	public BoardDAO(ServletContext application) {
		super(application);
	}
	
	public int selectCount(Map<String, Object> map) { // 매개변수가 Map이기 때문에 Map<String, Object> 타입
		int totalCount = 0; // 게시물 수를 담을 변수
		
		// 게시물 수를 얻어오는 쿼리문 작성
		String query = "select count(*) from board "; // ?가 들어가있지 않은 정적 쿼리문.
		if(map.get("searchWord") != null) { // 값이 있으면 검색하기 버튼을 눌렀다는 뜻이다.
			query += " where " + map.get("searchField") + " " // 컬럼 이름 검색
					+ " like '%" + map.get("searchWord") + "%'"; // 
			
		}
			try {
				stmt = con.createStatement(); // 쿼리문 생성
				rs = stmt.executeQuery(query); // 쿼리 실행
				rs.next(); // 커서를 첫 번째 행으로 이동
				totalCount = rs.getInt(1); // 첫번째 컬럼 값을 가져온다.
			} 
			catch (Exception e) {
				System.out.println("게시물 수를 구하는 중 예외 발생");
				e.printStackTrace();
			}
		return totalCount;
	}
	
	
	public List<BoardDTO> selectList(Map<String, Object> map){
		List<BoardDTO> bbs = new ArrayList<BoardDTO>();
		
		String query = "select * from board";
		if(map.get("searchWord") != null) {
			query += " where " + map.get("searchField") + " "
					+ " like '%" + map.get("searchWord") + "%'";
			query += "order by num desc"; // 최근글이 가장 위에 있게 만들어준다.
		}
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setName(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
			}
		}
		catch(Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return bbs;
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                