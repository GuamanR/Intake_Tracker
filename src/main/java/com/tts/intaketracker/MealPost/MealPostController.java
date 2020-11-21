package com.tts.intaketracker.MealPost;


import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MealPostController {

	@Autowired
	private MealPostRepository mealPostRepository;

	private List<MealPost> posts = new ArrayList<>();

	// localhost:8080
	// Handle get request to forward slash
	@GetMapping(value = "/")
	public String index(MealPost mealPost, Model model) {
		// Remove all current posts inside of the arraylist called posts, from line 22
		posts.removeAll(posts);

		// This for each loop goes over the entire repository(all blog posts), and for
		// each one it adds them
		// to the posts array list
		for (MealPost postFromDB : mealPostRepository.findAll()) {
			posts.add(postFromDB);
		}

		// this line makes the posts array list available to the webpage to be viewed
		model.addAttribute("posts", posts);

		return "blogpost/index";
	}

	@GetMapping(value = "/blogpost/new")
	public String newBlog(MealPost mealPost) {
		return "blogpost/new";
	}

	@PostMapping(value = "/blogpost")
	public String addNewBlogPost(MealPost mealPost, Model model) {
		// We do not want to create a new instance everytime,
		// instead we can pass in the blogPost as is.
		// Springboot is doing the hard work for us in the background
		mealPostRepository.save(mealPost);

		model.addAttribute("title", mealPost.getTitle());
		model.addAttribute("author", mealPost.getAuthor());
		model.addAttribute("blogEntry", mealPost.getBlogEntry());
		return "blogpost/result";
	}

	@PostMapping(value = "/blogpost/update/{id}")
	public String updateExistingPost(@PathVariable Long id, MealPost mealPost, Model model) {
		Optional<MealPost> post = mealPostRepository.findById(id);

		if (post.isPresent()) {
			// Created a
			MealPost actualPost = post.get();

			actualPost.setTitle(mealPost.getTitle());
			actualPost.setAuthor(mealPost.getAuthor());
			actualPost.setBlogEntry(mealPost.getBlogEntry());

			mealPostRepository.save(actualPost);

			model.addAttribute("blogPost", actualPost);

		} else {
			// Handle the error here
		}

		return "blogpost/result";

	}

	@RequestMapping(value = "/blogpost/delete/{id}")
	public String deletePostWithId(@PathVariable Long id, MealPost mealPost) {
		// Takes id from the URL path, passes it into deleteById from the CRUD
		// repository
		mealPostRepository.deleteById(id);

		return "blogpost/delete";
	}

	// This is the mapping to edit a specific post
	@RequestMapping(value = "/blogpost/edit/{id}")
	public String editPostWithId(@PathVariable Long id, Model model) {
		// Use blogPostRepo to find post by id
		// It returns an Optional<T>
		// Use a variable to store the the blogPost if its there
		Optional<MealPost> editPost = mealPostRepository.findById(id);

		// Initalize a variable to be filled by the post if it exists
		MealPost result;

		// use Optional method, to check if the post came through
		if (editPost.isPresent()) {
			// if the post came through, store it in result
			result = editPost.get();
			// add attribute to page, accessible through model
			model.addAttribute("blogPost", result);
		} else {
			// Need to handle error here, you could use a html error page
			return "Error";
		}

		// Show browser the blogpost/edit page
		return "blogpost/edit";
	}

}

