package com.today.house.command;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseopenVO {
	private Integer house_num;
	private String house_type;
	private String house_size;
	private String house_adr;
	private String house_fam;
	private String house_remo;
	private String house_title;
	private String house_content;
	private Timestamp house_regdate;
	private String user_id;
	

}
