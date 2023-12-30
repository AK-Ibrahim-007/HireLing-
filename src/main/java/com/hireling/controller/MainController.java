package com.hireling.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hireling.helper.Message;
import com.hireling.dao.WorkerRepo;
import com.hireling.entites.Worker;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private WorkerRepo workerRepo;
	
	@ModelAttribute
	public void workerDashBoard(Model model,Principal principal) {
		
		if(principal !=null) {
			String userName=principal.getName();
			System.out.println("Email : "+userName);
			
			Worker worker= workerRepo.getWorkerByUserNameWorker(userName);
			System.out.println("Detail :"+worker);
			
			model.addAttribute("worker", worker);
		}
	}
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home Page");

		return "home";
	}

	@RequestMapping("/registraion-form")
	public String about(Model model) {

		model.addAttribute("title", "Registration Form");
		model.addAttribute("worker", new Worker());
		return "registraion-form";
	}

	@RequestMapping(value = "/further_register", method = RequestMethod.POST)
	public String registration(@Valid @ModelAttribute("worker") Worker worker,BindingResult result,
			Model model,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, HttpSession session) {

		try {
			
			if(result.hasErrors()) {
				System.out.println("Errors "+result.toString());
				model.addAttribute("worker",worker);
				return "registraion-form";
			}
			
			worker.setEnabled(true);
			worker.setImageUrl("default.png");
			worker.setRole("ROLE_WORKER");
			worker.setPassword(passwordEncoder.encode(worker.getPassword()));

			System.out.println("Agreement  " + agreement);
			System.out.println("Worker " + worker);
			if (!agreement) {
				System.out.println("You have not agreed term and Condition!!");
				throw new Exception("You have not agreed term and Condition!!");
			}
			Worker res = this.workerRepo.save(worker);
			System.out.println("Saving Data in DB "+res);
			model.addAttribute("worker", new Worker());
			session.setAttribute("message" ,new Message("Successfully Registered !!", "alert-success") );
    	    return "registraion-form";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("worker",worker);
			session.setAttribute("message", new Message("Something went wrong !!"+ e.getMessage(),"alert-danger"));
			return "registraion-form";
		}
	}

	@GetMapping("/signin")
	public String loginUser(Model model) {
		model.addAttribute("title", "Login Page");
		return "signin";
	}
}
