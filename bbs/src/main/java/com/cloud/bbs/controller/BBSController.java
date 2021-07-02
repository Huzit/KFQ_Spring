package com.cloud.bbs.controller;

import java.nio.file.FileSystem;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.bbs.dto.BBSDto;
import com.cloud.bbs.service.BBSService;

@Controller
public class BBSController {
	
	@Autowired //스프링이 제공하는 어노테이션
	private BBSService bbsService;
	
	//BBS테이블의 내용을 뿌려줌
	@RequestMapping("/list.bbs")
	public void list(Model model) {
		//뷰에 함수 리턴값을 articleList로 전송
		model.addAttribute("articleList", bbsService.list());
	}
	
	
	//get방식으로 로그인데이터가 없으면 login.jsp로 이동
	@GetMapping("/write.bbs")
	public String writeForm() {
		return "writeForm";
	}
	

	
	//post방식으로 넘어왔을 경우 세션에서 id값을 받아 글작성페이지로 넘어간다.
	@PostMapping("/write.bbs")
	public String write(BBSDto article, HttpSession session, @RequestPart("fileUpload") List<MultipartFile> fileUpload) { 
//		article.setId((String)session.getAttribute("id"));
		//파일업로드도 같이
		bbsService.write(article, fileUpload);
		return "redirect:/list.bbs";
	}
	
	@GetMapping("/login.bbs")
	public String login() {
		return "login";
	}
	//login.jsp에 requestParam을 통해 입력된 값을 받아서 로그인
	@PostMapping("/login.bbs")
	public String login(@RequestParam("id") String id, @RequestParam("pass") String pass, HttpSession session) { 
		return bbsService.login(id, pass, session);
	}
	
	//콘텐츠 뿌리는 페이지
	@GetMapping("/content.bbs")
	public void content(@RequestParam("articleNum") String articleNum, Model model) {
		model.addAttribute("article", bbsService.content(articleNum));
		//업로드한 파일을 콘텐츠에 표시
		model.addAttribute("fileList", bbsService.getFiles(articleNum));
		model.addAttribute("commentCount", bbsService.getCommentCount(articleNum));
	}
	
	//업데이트 서식 작성
	@GetMapping("/update.bbs")
	public void updateForm(@RequestParam("articleNum") String articleNum, Model model) {
		model.addAttribute("article", bbsService.updateForm(articleNum));
	}
	
	//업데이트
	@PostMapping("/update.bbs")
	public String updateForm(BBSDto article) {
		bbsService.update(article);
		return "redirect:/content.bbs?articleNum="+article.getArticleNum();
	}
	
	//삭제
	@GetMapping("/delete.bbs")
	public String delete(@RequestParam("articleNum") String articleNum, Model model) {
		bbsService.delete(articleNum);
		return "redirect:/list.bbs";
		
	}
	
	//콘텐츠 다운로드
	@GetMapping("/download.bbs")
	@ResponseBody //클래스를 HTTP 응답 본문의 객체(ex.JSON)로 변환하는 컨버터 클라이언트가 요청했던 데이터 형식으로 변경해서 줘야되기 때문
	public FileSystemResource download(@RequestParam("savedFileName") String savedFileName, HttpServletResponse response) {
		return bbsService.download(savedFileName, response);	
		
	}
}	