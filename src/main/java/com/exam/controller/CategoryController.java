package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.exam.model.exam.Category;
import com.exam.service.CategoryService;


@Controller
@RequestMapping("/dashboard")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//add Category
	@PostMapping("/")
	public String addCategory(@ModelAttribute("category") Category category){
		if(category.getTitle() == null || category.getTitle() == "" || category.getDescription() == null || category.getDescription() == "") {
			return "titledescerror";
		}
		else {
			categoryService.addCategory(category);
			return "categorycreated";
		}
	}
	
	//get Category
	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategory(@PathVariable("categoryId") Long categoryId) {
		return ResponseEntity.ok(categoryService.getCategory(categoryId));
	}
	
	//get All Categories
	@GetMapping("/")
	public ResponseEntity<?> getCategories(){
		return ResponseEntity.ok(categoryService.getCategories());
	}
	
	//update Category
	@PostMapping("/update/")
	public String updateCategory(@ModelAttribute("category") Category category) {
		Category cat = categoryService.getCategory(category.getCid());
		categoryService.updateCategory(category);
		return "categoryupdated";
	}
	
	//delete Category
	@DeleteMapping("/delete/{categoryId}")
	public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
		categoryService.deleteCategory(categoryId);
	}
}
