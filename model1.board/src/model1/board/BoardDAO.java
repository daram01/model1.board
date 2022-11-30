package model1.board;
import java.sql.DriverManager;
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
		
		String query = "select * from board "; 
		// board 테이블 전체를 조회하는 문장을 변수 query에 담는다.
		if(map.get("searchWord") != null) {
		// searchWord의 value값이 null이 아니면, 즉 사용자가 검색어를 입력하고 검색하기 버튼을 눌렀다면.
			query += " where " + map.get("searchField") + " "
					+ " like '%" + map.get("searchWord") + "%' ";
			// query = where title like '%사용자가 입력한 검색어%' 또는
			// query = where content like '%사용자가 입력한 검색어%'
			query += "order by num desc"; // 최근글이 가장 위에 있게 만들어준다.
			// query = order by num desc -> 내림차순
			// 결과적으로 query문에는 
			// select * from board
			// where title like '%안녕%'
			// order by num desc; 가 작성되어있는 것이다.
		}
		
		try {
			stmt = con.createStatement(); 
			// ? 가 없는 동적 쿼리문이기 때문에 Statement()메서드 사용.
			// con = DriverManager.getConnection(url, "musthave", "1234"); 
			rs = stmt.executeQuery(query);
			// query문을 실행하고 ResultSet 객체로 반환.
			
			while(rs.next()) {
			// 조건식이 false가 나올 때 까지 while문 반복. 즉 데이터의 값이 없을 때 까지.
				BoardDTO dto = new BoardDTO();
				// dto 객체 생성
				
				dto.setNum(rs.getString("num")); 
				// set메서드로 rs객체를 사용하여 컬럼명 num에 들어있는 데이터 값으로 num을 초기화한다. BoardDTO에 있는 num변수에 값이 들어감.
				// getString("실제 sql 테이블에 있는 컬럼명");
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
				// 쿼리문으로 받아온 게시물 목록이 List컬렉션인 변수 bbs에 저장된다.
			}
		}
		catch(Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return bbs;
	}
	
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		
		try {
			String query = "insert into board ( "
						+ " num,title,content,id,visitcount) "
						+ " values ( "
						+ " seq_board_num.NEXTVAL,?,?,?,0)";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 게시물을 조회하기 위한 메서드
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		String query = "select b.*, m.name "
					+ " from member m inner join board b "
					+ " on m.id=b.id "
					+ " where num=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setNum(rs.getString("1")); 
				dto.setTitle(rs.getString("2"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("6"));
				dto.setName(rs.getString("name"));
			}
		}
		catch(Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		return dto;
	}
	
	
	// 조회수 1 증가 메서드
	public void updateVisitCount(String num) {
		String query = "update board set "
					+ " visitcount=visitcount+1 "
					+ " where num=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			psmt.executeQuery();
		}
		catch(Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                