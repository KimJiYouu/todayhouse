package com.today.house.command;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CpExUploadVO {
	private Integer upload_num;
	private String filename; //실제파일명
	private String filepath; //폴더명
	private String uuid; //난수값
	private LocalDateTime regdate;
	private Integer cp_ex_num;
}
