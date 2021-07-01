package com.cloud.bbs.service;

import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.bbs.common.FileSaveHelper;
import com.cloud.bbs.dao.BBSDao;
import com.cloud.bbs.dto.BBSDto;
import com.cloud.bbs.dto.FileDto;

@Service
public class BBSServiceImpl implements BBSService {
	
	@Autowired
	private BBSDao bbsDao;
	
	@Autowired
	private FileSaveHelper fileSaveHelper;
	
	@Resource(name="saveDir")
	private String saveDir;
	
	@Override
	public List<BBSDto> list(){
		return bbsDao.list();
	}
	
	@Override
	public void write(BBSDto article, @RequestPart("fileUpload") List<MultipartFile> fileUpload) {
		bbsDao.write(article);
		
		if(!fileUpload.get(0).isEmpty()) {
			for(MultipartFile multi : fileUpload) {
				FileDto fileDto = new FileDto();
				String savedFileName=fileSaveHelper.save(multi);
				
				fileDto.setOriginalFileName(multi.getOriginalFilename());
				fileDto.setSavedFileName(savedFileName);
				//여기서 ArticleNum을 넣을 때 동기로 넣어주기 위해서 mapper에서 keyProperty로 리턴해줌
				fileDto.setArticleNum(article.getArticleNum());
				bbsDao.insertFile(fileDto);
			}
		}
			
	}
	
	@Override
	public String login(String id, String pass, HttpSession session) {
		String dbPass = bbsDao.login(id);
		
		if(dbPass == null) {
			System.out.println("회원아님");
			return null;
		}
		else if(dbPass.equals(dbPass)) {
			session.setAttribute("id", id);
			//다시 서버에 요청
			return "redirect:/list.bbs";
		}
		else {			
			return "login";
		}
	}	
	
	@Override
	public BBSDto content(String articleNum) {
		return bbsDao.content(articleNum);
	
	}
	
	@Override
	public List<FileDto> getFiles(String articleNum) {
		return bbsDao.getFiles(articleNum);
	}
	
	@Override
	public BBSDto updateForm(String articleNum) {
		return bbsDao.updateForm(articleNum);
	}
	
	@Override
	public void update(BBSDto article) {
		bbsDao.update(article);
	}
	
	@Override
	public void delete(String articleNum) {
		bbsDao.delete(articleNum);
	}
	
	@Override
	public FileSystemResource download(String savedFileName, HttpServletResponse response) {
		//setContentType는 컨텐츠의 유형을 말한다.
		response.setContentType("application/download");
		//DB로 부터 savedFileName을 보내서 OriginalFileName을 받아옴
		String originalFileName = bbsDao.getOriginalFileName(savedFileName);
		
		try { //URLEncoder는 originalFilename를 UTF-8로 변경 (하지않으면 한글이 깨진다. POST방식일경우 깨지지만 둘다 사용하므로 적어주자), replace 문자열 치환 
			originalFileName = URLEncoder.encode(originalFileName, "utf-8").replace("+", "%20").replace("%28", "(").replace("%29", " ");
		} catch (Exception e) {
		}
		
		//헤더 셋팅
		response.setHeader("Content-Disposition",  "attachment;" + " filename=\""+ originalFileName +"\";");
		//패러미터로 넣은 경로를 통해 새로운 파일 리소스를 생성 왜? 다운로드는 다른 파일을 만들어서 보내기 때문
		FileSystemResource fsr = new FileSystemResource(saveDir+savedFileName);
		return fsr;
	}
}
