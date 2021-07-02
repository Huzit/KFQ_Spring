package com.cloud.bbs.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.bbs.dao1.CommentDao;
import com.cloud.bbs.dto.CommentDto;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;
	
	
	/* Daoimpl이 없기 때문에 지금 메소드명과 동일하게 작성하고 스캔을 통해 메소드 명으로 맵퍼를 실행 
	 * root-context.xml에서 mybatis-spring:scan 태그로 베이스패키지를 설정해주면 누군가가 Dao 인터페이스에 속해있는 메소드에 접근한다면 스캔이 풀패키지를 nameSpace로 가지고 있는 mapper로 맵핑시켜준다.
	 * 이렇게 하면 DaoImpl없이 mapper와 연결이 가능하다
	 * 원래는 impl에서 sqlSession(nameSpace+ ".name", argument)로 넘겨줬지만 그럴 필요가 사라졌다.
	 */
	@Override
	public List<CommentDto> write(CommentDto comment) {
		
		commentDao.write(comment);
		
		HashMap<String, Integer> readMap = new HashMap<>();
		readMap.put("articleNum", comment.getArticleNum());
		readMap.put("commentRow", 10);
		
		return commentDao.getComments(readMap);
	}	
	
	@Override
	public List<CommentDto> read(int articleNum, int commentRow) {
		HashMap<String, Integer> readMap = new HashMap<>();
		readMap.put("articleNum", articleNum);
		readMap.put("commentRow", commentRow);
		
		return commentDao.getComments(readMap);
	}
}