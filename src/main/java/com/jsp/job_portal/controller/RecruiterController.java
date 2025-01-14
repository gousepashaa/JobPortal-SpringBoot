package com.jsp.job_portal.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.job_portal.dto.Job;
import com.jsp.job_portal.dto.Recruiter;
import com.jsp.job_portal.repository.JobRepository;
import com.jsp.job_portal.service.RecruiterService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {
	
	@Autowired
	RecruiterService recruiterService;
	
	@Autowired
	JobRepository jobRepository;
	
	@GetMapping("/register")
	public String loadRegister(Recruiter recruiter,ModelMap map) {
		map.put("recruiter", recruiter);
		return "recruiter-register.html";
	}
	
	@PostMapping("/register")
	public String register(@Valid Recruiter recruiter, BindingResult result, HttpSession session) {
		return recruiterService.register(recruiter, result, session);
	}
	
	@GetMapping("/otp/{id}")
	public String loadOtp(ModelMap map, @PathVariable("id") int id) {
		map.put("id", id);
		return "recruiter-otp.html";
	}
	
	@PostMapping("/otp")
	public String otp(@RequestParam("id") int id, @RequestParam("otp") int otp, HttpSession session) {
		return recruiterService.otp(id, otp, session);
	}
	
	@GetMapping("/resend-otp/{id}")
	public String resendOtp(@PathVariable("id") int id, HttpSession session) {
		return recruiterService.resendOtp(id, session);
	}
	
	@GetMapping("/home")
	public String loadHome(HttpSession session) {
		if (session.getAttribute("recruiter") != null) {
			return "recruiter-home.html";
		} else {
			session.setAttribute("error", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}
	
	@GetMapping("/post-job")
	public String loadPostJob(HttpSession session) {
		if (session.getAttribute("recruiter") != null) {
			return "post-job.html";
		} else {
			session.setAttribute("error", "Invalid Session, Login Again");
			return "redirect:/login";
		}
	}
	
	 @PostMapping("/post-job")
	    public String postJob(@ModelAttribute Job job, HttpSession session) {
	        if (session.getAttribute("recruiter") != null) {
	            // Get the recruiter from session
	            Recruiter recruiter = (Recruiter) session.getAttribute("recruiter");
	            job.setRecruiter(recruiter);
	            
	            // Save the job
	            jobRepository.save(job);

	            // Provide success feedback
	            session.setAttribute("success", "Job Posted Successfully");
	            return "redirect:/recruiter/home";
	        } else {
	            session.setAttribute("error", "Invalid Session, Login Again");
	            return "redirect:/login";
	        }
	    }

	 @GetMapping("/manage-jobs")
		public String manageJob(HttpSession session,ModelMap map) {
			if (session.getAttribute("recruiter") != null) {
				Recruiter recruiter=(Recruiter) session.getAttribute("recruiter");
				List<Job> jobs=jobRepository.findByRecruiter(recruiter);
				if(jobs.isEmpty()) {
					session.setAttribute("failure", "No Jobs Added Yet");
					return "redirect:/recruiter/home";
				}else {
					map.put("jobs", jobs);
					return "recruiter-jobs.html";
				}
			} else {
				session.setAttribute("error", "Invalid Session, Login Again");
				return "redirect:/login";
			}
		}
	 

	 
	 @GetMapping("/delete-job/{id}")
		public String deleteJob(@PathVariable("id") int id,HttpSession session) {
			if (session.getAttribute("recruiter") != null) {
				jobRepository.deleteById(id);
				session.setAttribute("error", "Job Removed Success");
				return "redirect:/recruiter/manage-jobs";
			} else {
				session.setAttribute("error", "Invalid Session, Login Again");
				return "redirect:/login";
			}
		}
	 
	 @GetMapping("/edit-job/{id}")
		public String editJob(@PathVariable("id") int id,ModelMap map,HttpSession session) {
			if (session.getAttribute("recruiter") != null) {
				Job job=jobRepository.findById(id).get();
				map.put("job", job);
				return "edit-job.html";
			} else {
				session.setAttribute("error", "Invalid Session, Login Again");
				return "redirect:/login";
			}
		}

		@PostMapping("/update-job")
		public String updateJob(Job job, HttpSession session) {
			if (session.getAttribute("recruiter") != null) {
				Recruiter recruiter = (Recruiter) session.getAttribute("recruiter");
				job.setRecruiter(recruiter);
				jobRepository.save(job);
				session.setAttribute("success", "Job Updated Success");
				return "redirect:/recruiter/manage-jobs";
			} else {
				session.setAttribute("error", "Invalid Session, Login Again");
				return "redirect:/login";
			}
		}
		
	// Show Edit Job Form
//	 @GetMapping("/edit-job/{id}")
//	 public String editJob(@PathVariable("id") int id, HttpSession session, ModelMap map) {
//	     if (session.getAttribute("recruiter") != null) {
//	         Job job = jobRepository.findById(id).orElse(null);
//	         if (job != null) {
//	             map.put("job", job); // Add the job object to the model
//	             return "edit-job"; // Render the edit job page
//	         } else {
//	             session.setAttribute("error", "Job not found.");
//	             return "redirect:/recruiter/manage-jobs";
//	         }
//	     } else {
//	         session.setAttribute("error", "Invalid session. Please login.");
//	         return "redirect:/login";
//	     }
//	 }
//
//
//	    // Handle Job Update
//	    @PostMapping("/edit-job")
//	    public String updateJob(Job  job, HttpSession session, ModelMap map) {
//	        if (session.getAttribute("recruiter") != null) {
//	            try {
//	                jobRepository.save(job); // Save updated job to the database
//	                session.setAttribute("success", "Job updated successfully!"); // Success message
//	                return "redirect:/recruiter/manage-jobs"; // Redirect to manage jobs page
//	            } catch (Exception e) {
//	                map.put("error", "Failed to update job. Error: " + e.getMessage());
//	                return "edit-job"; // Stay on the edit job page in case of an error
//	            }
//	        } else {
//	            session.setAttribute("error", "Invalid session. Please login.");
//	            return "redirect:/login";
//	        }
//	    }
//
//	    @GetMapping("/edit-job")
//	    public String showEditJobForm(@RequestParam("id") int id, ModelMap map) {
//	        Optional<Job> job = jobRepository.findById(id); // Fetch job details
//	        map.addAttribute("job", job);
//	        return "edit-job"; // Thymeleaf template name
//	    }


}
