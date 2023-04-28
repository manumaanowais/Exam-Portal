package com.exam.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Username cannot be blank!")
	private String username;
	@NotBlank(message = "Password cannot be blank!")
	private String password;
	@NotBlank(message = "Firstname cannot be blank!")
	private String firstName;
	@NotBlank(message = "Lastname cannot be blank!")
	private String lastName;
	@NotBlank(message = "Email cannot be blank!")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a valid email!")
	private String email;
	@NotBlank(message = "Phone number cannot be blank!")
	private String phone;
	@AssertTrue(message = "Must agree the terms and conditions!")
	private boolean agreed;

	private boolean enabled = true;

	private String profile = "default.png";

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserRole> userRoles = new HashSet<>(); // A user can have many user roles
	public User() {
	}
	public User(Long id, @NotBlank(message = "Username cannot be blank!") String username,
			@NotBlank(message = "Password cannot be blank!") String password,
			@NotBlank(message = "Firstname cannot be blank!") String firstName,
			@NotBlank(message = "Lastname cannot be blank!") String lastName,
			@NotBlank(message = "Email cannot be blank!") @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Enter a valid email!") String email,
			String phone, @AssertTrue(message = "Must agree the terms and conditions!") boolean agreed, boolean enabled,
			String profile, Set<UserRole> userRoles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.agreed = agreed;
		this.enabled = enabled;
		this.profile = profile;
		this.userRoles = userRoles;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getProfile() {
		return profile;
	}

	public String getUsername() {
		return username;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public boolean isAgreed() {
		return agreed;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setAgreed(boolean agreed) {
		this.agreed = agreed;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
}