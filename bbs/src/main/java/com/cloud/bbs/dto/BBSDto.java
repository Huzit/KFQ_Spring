package com.cloud.bbs.dto;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString

public class BBSDto {
	private int articleNum;
	private String id;
	private String title;
	private String content;
	private Timestamp writeDate;
	
}
