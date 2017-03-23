package com.example.domain;





import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "humans")
public class Human {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable=false)
	private String name;
	
	private String sex;
	
	private Integer year;
	
	private Integer month;
	
	private Integer day;
		
	private Integer age;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true, name="username")
	private User user;
}
