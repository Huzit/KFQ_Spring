package com.cloud.bbs.common;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaveHelper {
	@Resource(name="saveDir")
	private String saveDir;
	
	public String save(MultipartFile uploadFile) {
		String storedFname = UUID.randomUUID().toString()+'_'+uploadFile.getOriginalFilename();
		try {
			uploadFile.transferTo(new File(saveDir + storedFname));
		}
		catch (Exception e) {
		}
		return storedFname;
	}
}