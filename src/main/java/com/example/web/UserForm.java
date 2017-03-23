package com.example.web;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserForm {
	@NotNull
	@Size(min=1,max=30)
	private String username;
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]{4,8}$")
	private String password;
	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9]{4,8}$")
	private String password2;
}
