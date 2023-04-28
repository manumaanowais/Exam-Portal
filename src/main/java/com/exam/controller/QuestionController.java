package com.exam.controller;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;

@Controller
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	//Add Question
	@PostMapping("/")
	public String addQuestion(@ModelAttribute("question") Question question){
		if(question.getContent() == null || question.getContent() == "" || question.getOption1() == null || question.getOption1() == "" || question.getOption2() == null || question.getOption2() == "" || question.getOption3() == null || question.getOption3() == "" || question.getOption4() == null || question.getOption4() == "" || question.getAnswer() == null || question.getAnswer() == "") {
			return "error";
		}else {
			questionService.addQuestion(question);
			return "questionadded";
		}
	}
	
	//Update question
	@PostMapping("/update/")
	public String updateQuestion(@ModelAttribute("question") Question question){
		Question q = questionService.getQuestion(question.getQuesId());
		questionService.updateQuestion(question);
		return "questionupdated";
	}
	
	
	
	
	  //Get Questions of quiz
	  
	  @GetMapping("/quiz/{qId}") 
	  public ResponseEntity<Set<Question>> getQuestionsOfQuiz(@PathVariable("qId") Long qId,Model model){ 
		  Quiz quiz = quizService.getQuiz(qId); 
		  Set<Question> questions = quiz.getQuestions();
		  //List<Question> list = new ArrayList<Question>(questions); 
		 // if(list.size() > Integer.parseInt(quiz.getNumberOfQuestions())){ 
		//	  list = list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1)); 
		 // }
		  //Collections.shuffle(list); 
		  //return ResponseEntity.ok(list);
		  //model.addAttribute("qsn",questions);
		  return ResponseEntity.ok(questions);
	  }
	 
		/*
		 * @PostMapping("/get/") public String MethodTest(Set<Question> questions, Model
		 * model) { Set<Question> quest = questions; for(Question q : questions) {
		 * System.out.println("*******************"); System.out.println(q.getQuesId());
		 * System.out.println(q.getContent()); System.out.println(q.getAnswer());
		 * System.out.println("*******************"); }
		 * 
		 * model.addAttribute("qsn", quest); return "question"; }
		 */
	
	
	/*
	 * //Get Questions of quiz
	 * 
	 * @GetMapping("/quiz/{qId}") public Set<Question>
	 * getQuestionsOfQuiz(@PathVariable("qId") Long qId,Model model){ Quiz quiz =
	 * quizService.getQuiz(qId); Set<Question> questions = quiz.getQuestions();
	 * model.addAttribute("question",questions); return questions; }
	 */
	
	//Get Questions
	@GetMapping("/")
	public ResponseEntity<Set<Question>> getQuestions(){
		return ResponseEntity.ok(questionService.getQuestions());
	}
	
	//Get Question
	@GetMapping("/{quesId}")
	public ResponseEntity<Question> get(@PathVariable("quesId") Long quesId) {
		return ResponseEntity.ok(questionService.getQuestion(quesId));
	}

	//Delete Question
	@DeleteMapping("/{quesId}")
	public void delete(@PathVariable("quesId") Long quesId) {
		questionService.deleteQuestion(quesId);
	}
}
