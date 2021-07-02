package com.cloud.bbs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.bbs.dto.CommentDto;
import com.cloud.bbs.service.CommentService;

@Controller
public class CommentController {
	
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/commentWrite.comment") 
	@ResponseBody
	public List<CommentDto> commentWrite(CommentDto comment, HttpSession session, Model model) {
		//세션id를 받아서 넣어줌
		comment.setId(session.getAttribute("id").toString());
		return commentService.write(comment);
	}
	
	@PostMapping("/commentRead.comment") 
	@ResponseBody
	public List<CommentDto> commentRead(@RequestParam("articleNum") int articleNum, @RequestParam("commentRow") int commentRow) {
		//세션id를 받아서 넣어줌
		return commentService.read(articleNum, commentRow);
	}
}