package com.today.house.command;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseopenUploadVO {

	private Integer upload_num;
	private String filename; //실제파일명
	private String filepath; //폴더명
	private String uuid; //난수값
	private LocalDateTime regdate;
	private Integer house_num;
}
