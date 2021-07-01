package com.cloud.bbs.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.cloud.bbs.dto.BBSDto;
import com.cloud.bbs.dto.FileDto;

@Repository
public class BBSDaoImpl implements BBSDao {
	
	@Inject //J2EE가 지원하는 어노테이션
	private SqlSession sqlSession;
	
	private final String nameSpace = "com.cloud.bbs.dao.BBSDao";
	
	@Override
	public List<BBSDto> list() {
		return sqlSession.selectList(nameSpace+".list");
	}
	
	@Override
	public void write(BBSDto article) {
		sqlSession.insert(nameSpace+".write", article);
	}
	
	@Override
	public String login(String id) {
		return sqlSession.selectOne(nameSpace+".login", id);
	}
	
	@Override
	public BBSDto content(String articleNum) {
		return sqlSession.selectOne(nameSpace+".content", articleNum);
	}
	
	@Override
	public List<FileDto> getFiles(String articleNum) {
		return sqlSession.selectList(nameSpace+".getFiles", articleNum);
	}
	
	@Override
	public BBSDto updateForm(String articleNum) {
		return sqlSession.selectOne(nameSpace+".updateForm", articleNum);
	}
	
	@Override
	public void update(BBSDto article) {
		sqlSession.update(nameSpace+".update", article);
	}
	
	@Override
	public void delete(String articleNum) {
		sqlSession.delete(nameSpace+".delete", articleNum);
	}
	
	@Override
	public void insertFile(FileDto fileDto) {
		sqlSession.insert(nameSpace+".insertFile", fileDto);
	}
	
	@Override
	public String getOriginalFileName(String savedFileName) {
		return sqlSession.selectOne(nameSpace+".getOriginalFileName", savedFileName);
	}
}	