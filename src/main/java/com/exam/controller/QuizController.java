package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.service.QuizService;

@Controller
@RequestMapping("/quiz")
public class QuizController {
	
	@Autowired
	private QuizService quizService;
	
	//Add Quiz
	@PostMapping("/")
	public String addQuiz(@ModelAttribute("quiz") Quiz quiz){
		if(quiz.getTitle() == null || quiz.getTitle() == "" || quiz.getDescription() == null || quiz.getDescription() == "" || quiz.getMaxMarks() == "" || quiz.getNumberOfQuestions() == "") {
			return "error";
		}
		else {
			quizService.addQuiz(quiz);
			quiz.setNumberOfQuestions(quiz.getNumberOfQuestions()+1);
			quiz.setMaxMarks(quiz.getMaxMarks()+10);
			return "quizcreated";
		}
	}
	
	//update Quiz
		@PostMapping("/update/")
		public String updateQuiz(@ModelAttribute("quiz") Quiz quiz) {
			Quiz q = quizService.getQuiz(quiz.getQid());
			quizService.updateQuiz(quiz);
			return "quizupdated";
		}
	 
	//Get Quizzes
	@GetMapping("/")
	public ResponseEntity<?> quizzes(){
		return ResponseEntity.ok(quizService.getQuizzes());
	}
	
	//Get Quiz
	@GetMapping("/{quizId}")
	public ResponseEntity<Quiz> quiz(@PathVariable("quizId") Long quizId) {
		return ResponseEntity.ok(quizService.getQuiz(quizId));
	}
	
	//Delete Quiz
	@DeleteMapping("/{quizId}")
	public void deleteQuiz(@PathVariable("quizId") Long quizId) {
		quizService.deleteQuiz(quizId);
		Quiz quiz = quizService.getQuiz(quizId);
		quiz.setMaxMarks(Integer.parseInt(quiz.getMaxMarks())-10+"");
		quiz.setNumberOfQuestions(Integer.parseInt(quiz.getNumberOfQuestions())-1+"");
	}
}
