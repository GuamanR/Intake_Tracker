package com.tts.intaketracker.MealPost;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MealPost{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	private String author;
	private String mealEntry;

	public MealPost() {
	}

	public MealPost (String title, String author, String mealEntry) {
		this.title = title;
		this.author = author;
		this.mealEntry = mealEntry;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMealEntry() {
		return mealEntry;
	}

	public void setMealEntry(String mealEntry) {
		this.mealEntry = mealEntry;
	}

	@Override
	public String toString() {
		return "MealPost [author=" + author + ", mealEntry=" + mealEntry + ", id=" + id + ", title=" + title + "]";
	}

}