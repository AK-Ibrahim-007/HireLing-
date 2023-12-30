package com.hireling.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Worker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotBlank(message="First Name field is required!!")
	@Size(min = 3,max = 20, message = "min 3 and max 20 character are allowed!!")
	private String firstName;
	@NotBlank(message="Last Name field is required!!")
	private String lastName;
	@NotBlank(message="email field is required!!")
	@Column(unique = true)
	private String email;
	@NotBlank(message="phone field is required!!")
	@Size(min = 10,max = 10, message = "Phone Number must be 10 digits!!")
	private String phone;
	@NotBlank(message="password field is required!!")
	private String password;	
	private String gender;
	private boolean enabled;
	private String imageUrl;
	private String role;
	private String discription;
	private String workType;
	private int workExpe;
	@ManyToOne 
	private Country countryWorker;
	@ManyToOne 
	private State state;
	@ManyToOne 
	private Distric distric;	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "worker")
	private List<WorkImage> workImages=new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	
	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}
 
	public int getWorkExpe() {
		return workExpe;
	}

	public void setWorkExpe(int workExpe) {
		this.workExpe = workExpe;
	}

	public Country getCountryWorker() {
		return countryWorker;
	}

	public void setCountryWorker(Country countryWorker) {
		this.countryWorker = countryWorker;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Distric getDistric() {
		return distric;
	}

	public void setDistric(Distric distric) {
		this.distric = distric;
	}
	

	public List<WorkImage> getWorkImages() {
		return workImages;
	}

	public void setWorkImages(List<WorkImage> workImages) {
		this.workImages = workImages;
	}

	@Override
	public String toString() {
	    return "Worker [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
	            + ", phone=" + phone + ", password=" + password + ", gender=" + gender + ", enabled=" + enabled
	            + ", imageUrl=" + imageUrl + ", role=" + role + ", discription=" + discription + ", workType="
	            + workType + ", workExpe=" + workExpe + "]";
	}
	
	

	

	
}
