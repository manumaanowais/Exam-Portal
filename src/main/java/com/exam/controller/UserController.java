package com.exam.controller;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.model.exam.Category;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.CategoryService;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.UserService;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuestionService questionService;

	@PostMapping("/addUser")
	public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return "redirect:/signup";
		} else if (userService.getUser(user.getUsername()) != null) {
			return "usererror";
		} else {
			// Encrypting Password
			user.setPassword(userService.encrypt(user.getPassword()));
			// End of Encrypting Password

			Set<UserRole> roles = new HashSet<>();
			Role role = new Role();
			role.setRoleId(45L);
			role.setRoleName("NORMAL");

			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);

			roles.add(userRole);
			userService.createUser(user, roles);
			return "accountcreated";
		}
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		userService.deleteUser(userId);
	}

	@GetMapping("/signup")
	public String getFormData(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@GetMapping("/signin")
	public String getLoginForm(Model model) {
		model.addAttribute("user", new User());
		return "signin";
	}

	@GetMapping("/roles")
	public String getRole(Model model, @ModelAttribute("user") User user) {
		Set<UserRole> roles = userService.getUser(user.getUsername()).getUserRoles();
		System.out.println(roles);
		for (UserRole r : roles) {
			System.out.println(r);
		}
		model.addAttribute("roles", roles);
		return "uroles";
	}

	@GetMapping("/{username}")
	public User getUser(@PathVariable("username") String username) {
		return userService.getUser(username);
	}

	
	public static int questionscounter = 0;
	public static int markscounter = 0;
	@PostMapping("/dashboard")
	public String login(@ModelAttribute("user") User user, Model model) throws Exception {
		User auser = userService.getUser(user.getUsername());
		if (auser == null || auser.equals(null) || auser.getUsername() == "" || userService.decrypt(auser.getPassword()) == "" || auser.getUsername().length() == 0
				|| userService.decrypt(auser.getPassword()).length() == 0) {
			return "loginerror";
		} else {
			//System.out.println("Decrypted Password = "+userService.decrypt(auser.getPassword()));
			if ((user.getUsername().equals(auser.getUsername())) && (user.getPassword().equals(userService.decrypt(auser.getPassword()))
					&& (user.getUsername().length() == auser.getUsername().length())
					&& (user.getPassword().length() == userService.decrypt(auser.getPassword()).length()))) {
				model.addAttribute("firstname", userService.getUser(user.getUsername()).getFirstName());
				model.addAttribute("lastname", userService.getUser(user.getUsername()).getLastName());
				model.addAttribute("username", userService.getUser(user.getUsername()).getUsername());
				model.addAttribute("email", userService.getUser(user.getUsername()).getEmail());
				model.addAttribute("phone", userService.getUser(user.getUsername()).getPhone());

				Set<UserRole> roles = userService.getUser(user.getUsername()).getUserRoles();
				for (UserRole r : roles) {
					model.addAttribute("rname", r.getRole().getRoleName());
				}

				
				Set<Category> categories = categoryService.getCategories();
				for(Category c: categories) {
					System.out.println("categories : " + c.getCid());
					
				}
				model.addAttribute("category",categories);
				
				
				
				Set<Quiz> quizzes = quizService.getQuizzes();
				for(Quiz q: quizzes) {
					System.out.println("Quizzes Id : " + q.getQid());
					/*
					 * Set<Question> questions = q.getQuestions(); for(Question ques : questions) {
					 * System.out.println("Quiz Id :" + ques.getQuiz().getQid());
					 * System.out.println("Questions : " + ques.getQuesId());
					 * System.out.println("Content : "+ ques.getContent()); }
					 * model.addAttribute("question",questions);
					 */
				}
				model.addAttribute("quiz",quizzes);
				
				
				Set<Question> questions = questionService.getQuestions(); 
				for(Question q : questions) { 
					System.out.println("Questions : " + q.getQuesId());
				}
				model.addAttribute("question",questions);
				 
				
				Set<Quiz> countquiz = quizService.getQuizzes();
				for(Quiz i : countquiz) {
					Set<Question> countquestion = questionService.getQuestions();
					for(Question j : countquestion) {
						if(i.getQid() == j.getQuiz().getQid()) {
							questionscounter = questionscounter+1;
							markscounter = markscounter+10;
						}
					}
					System.out.println("No.of questions in "+i.getTitle()+" quiz are : "+questionscounter);
					System.out.println("No.of marks in "+i.getTitle()+" quiz are : "+markscounter);
					String noofquestions = questionscounter+"";
					i.setNumberOfQuestions(noofquestions);
					String marks = markscounter+"";
					i.setMaxMarks(marks);
					questionscounter = 0;
					markscounter=0;
				}
				
				
				
				return "dashboard";
			} else if ((user.getPassword().equals(userService.decrypt(auser.getPassword())) == false)
					|| (user.getPassword().length() != userService.decrypt(auser.getPassword()).length())) {
				return "passworderror";
			} else {
				return "loginerror";
			}
		}
	}	
		
	@PostMapping("/forgotPassword")
	public String forgotPassword(@ModelAttribute("user") User user) throws Exception {
		User auser = userService.getByEmail(user.getEmail());
		if (auser == null || auser.equals(null) || auser.getEmail() == "" || auser.getEmail().length() == 0) {
			return "loginerror";
		} else {
			if ((user.getEmail().equals(auser.getEmail())) && (user.getEmail().length() == auser.getEmail().length())) {
				auser.setPassword(userService.encrypt(user.getPassword()));
				
				Set<UserRole> roles = new HashSet<>();
				Role role = new Role();
				role.setRoleId(45L);
				role.setRoleName("NORMAL");

				UserRole userRole = new UserRole();
				userRole.setUser(auser);
				userRole.setRole(role);

				roles.add(userRole);
				userService.updateUser(auser, roles);
				return "passwordchanged";
			} else {
				return "loginerror";
			}
		}
	}
}
