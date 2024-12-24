package com.jsp.job_portal.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Job {
	@Id
	@GeneratedValue(generator = "job_id")
	@SequenceGenerator(name = "job_id", initialValue = 100121001, allocationSize = 1)
	private int id;
	
	@NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Location is required")
    private String location;

    private int ctc;
    private int experience;
    private boolean approved;
    
    @NotBlank(message = "Company Name is required")
    private String companyName;

    @Pattern(regexp = "^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)$", 
             message = "Invalid image URL. Provide a valid URL for JPG, PNG, or GIF image.")
    private String companyPictureUrl; // URL for the company logo
	
	@ManyToOne
	private Recruiter recruiter;
}
