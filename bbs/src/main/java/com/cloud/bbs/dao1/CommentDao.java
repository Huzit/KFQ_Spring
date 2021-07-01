package com.cloud.bbs.dao1;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.cloud.bbs.dto.CommentDto;

public interface CommentDao {
	public String test(String id);
	public void write(CommentDto comment);
	public List<CommentDto> getComments(int articleNum);
}
