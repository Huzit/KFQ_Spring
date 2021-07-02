package com.cloud.bbs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cloud.bbs.dto.CommentDto;

public interface CommentService {
	public List<CommentDto> write(CommentDto comment);
	public List<CommentDto> read(int articleNum, int commentRow);
}
