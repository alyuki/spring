package com.example.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class HumanForm {
	@NotNull
    @Size(min = 1, max = 30)
	private String name;
	@NotNull
	private String sex;
	@NotNull
	private Integer year;
	@NotNull
	private Integer month;
	@NotNull
	private Integer day;
	
	private Integer age;
	
	private Integer max;
}
