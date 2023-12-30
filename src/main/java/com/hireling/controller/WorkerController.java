package com.hireling.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hireling.dao.CountryRepo;
import com.hireling.dao.DistricRepo;
import com.hireling.dao.StateRepo;
import com.hireling.dao.WorkerRepo;
import com.hireling.entites.Country;
import com.hireling.entites.Distric;
import com.hireling.entites.State;
import com.hireling.entites.WorkImage;
import com.hireling.entites.Worker;
import com.hireling.helper.Message;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/worker")
public class WorkerController {

	@Autowired
	private WorkerRepo workerRepo;
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private DistricRepo districRepo;

	@ModelAttribute
	public void workerDetails(Model model, Principal principal) {

		if (principal != null) {
			String userName = principal.getName();

			Worker worker = workerRepo.getWorkerByUserNameWorker(userName);

			model.addAttribute("worker", worker);
		}
	}

	@GetMapping("/index")
	public String workerDashBoard() {
		return "/home";
	}

	@GetMapping("/profile")
	public String viewProfile(Model model) {
		model.addAttribute("title", "Your Profile");
		return "workers/profile";
	}

	// open edit-profile handler
	@PostMapping("/edit-profile/{id}")
	public String openEditProfileForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("title", "Edit Profile");
		Worker worker = this.workerRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid worker ID"));
		model.addAttribute("worker", worker);
		List<Country> countries = countryRepo.findAll();
		model.addAttribute("countries", countries);

		return "workers/edit-profile";
	}

	// processing update worker profile
	@PostMapping("/update-worker")
	public String updateWorker(@ModelAttribute Worker worker, @RequestParam("profileImage") MultipartFile file,
			@RequestParam("country") int countryId, @RequestParam("state") int stateId,
			@RequestParam("distric") int districId, Model model, HttpSession session) {

		try {
			if (!file.isEmpty()) {

				// file upload
				worker.setImageUrl(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			// Retrieve the managed country entity or create a new instance if it doesn't
			// exist
			Country country = this.countryRepo.findById(countryId).orElse(new Country());
			State state = this.stateRepo.findById(stateId).orElse(new State());
			Distric distric = this.districRepo.findById(districId).orElse(new Distric());
			// Set the country for the worker
			worker.setCountryWorker(country);
			worker.setState(state);
			worker.setDistric(distric);

			// Save the updated worker data to the database
			this.workerRepo.save(worker);

//		    this.workerRepo.save(worker);

			// message success.........
			session.setAttribute("message", new Message("You Profile is Updated Successfully", "success"));

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();

			// message error.........
			session.setAttribute("message", new Message("Something went wrong try again...", "danger"));

		}

		return "workers/edit-profile";
	}

	@GetMapping(value = "/states/{countryId}")
	@ResponseBody
	public List<State> getStatesByCountry(@PathVariable("countryId") int countryId) {
		Country country = countryRepo.findById(countryId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid country ID"));
		List<State> states = stateRepo.findByCountryState(country);
		return states;
	}

	@GetMapping(value = "/distric/{stateId}")
	@ResponseBody
	public List<Distric> getDistricByState(@PathVariable("stateId") int stateId) {
		State state = stateRepo.findById(stateId).orElseThrow(() -> new IllegalArgumentException("Invalid state ID"));

		List<Distric> districts = districRepo.findByStateDistrics(state);
		return districts;
	}

	// uploading work image....
	@GetMapping("/upload-Images")
	public String uploadWorkImage(Model model) {
		model.addAttribute("title", "Upload Images");
		model.addAttribute("workerImage", new WorkImage());
		return "workers/upload-work-image";
	}

	@PostMapping("/save-image")
	public String saveWorkImage(@ModelAttribute WorkImage workImage, @RequestParam("workimage") MultipartFile file,
			Principal principal, HttpSession session) {
		try {
			String name = principal.getName();
			Worker worker = this.workerRepo.getWorkerByUserNameWorker(name);

			if (file.isEmpty()) {
				System.out.println("File is Empty!!!");
			} else {
				workImage.setImageName(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}

			worker.getWorkImages().add(workImage);
			workImage.setWorker(worker);
			this.workerRepo.save(worker);

			// message success.........
			session.setAttribute("message", new Message("You file is Uploading Successfully", "success"));

		} catch (Exception e) {
			System.out.println("ERROR :" + e.getMessage());
			e.printStackTrace();
			// message error.........
			session.setAttribute("message", new Message("Something went wrong try again...", "danger"));

		}
		return "workers/upload-work-image";
	}
	
	@GetMapping("/gallery")
	public String imageGallery(@ModelAttribute Worker worker,Model model) {
		model.addAttribute("title", "Gallery");
		List<String> imageName=new ArrayList<>();
		for(WorkImage workImage:worker.getWorkImages() ) {
			imageName.add(workImage.getImageName());
		}
		model.addAttribute("imageNames", imageName);
		return "workers/image-gallery";
	}

}
