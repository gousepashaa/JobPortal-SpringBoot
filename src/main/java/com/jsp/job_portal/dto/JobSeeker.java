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
	private Long mobile;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message="* Enter minimum 8 characters, one upper case, one lower case letter, one digit and one special character")
	private String password;
	
	@Transient
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message= "* Enter minimum 8 characters, one upper case, one lower case letter, one digit and one special character")
	private String confirmPassword;
	
	private boolean verified;
}
