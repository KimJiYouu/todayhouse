package com.today.house.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultantVO {


	private Integer consultant_num;
	private String user_id;
	private String cp_id;
	private String consultant_house_size;
	private String consultant_house_adr;
	private String consultant_phone;
	private String consultant_content;
	private String consultant_response;







}
