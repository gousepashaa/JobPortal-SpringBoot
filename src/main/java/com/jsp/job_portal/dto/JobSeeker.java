package com.jsp.job_portal.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class JobSeeker {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Size(min=3, max=30, message="* Enter between 3~30 characters")
	private String name;
	
	@Email(message="* Enter valid email")
	@NotEmpty(message = "* It is required field")
	private String email;
	
	@DecimalMin(value = "6000000000", message="* Enter proper mobile number")
	@DecimalMax(value = "9999999999", message="* Enter proper mobile number")
	@NotNull(message = "* It is Required Field")
	private Long mobile;
	
	@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "* Enter minimum 8 charecter, one uppercase, one lowercase, one number and one special charecter")
	private String password;
	
	@Transient
	@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "* Enter minimum 8 charecter, one uppercase, one lowercase, one number and one special charecter")
	private String confirmPassword;
	
	private Boolean verified;
	private Integer otp;
}
