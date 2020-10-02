package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;

@RestController
public class ReviewApiController {
	@Autowired
	private ReviewRepository repository;

	public ReviewRepository getRepository() {
		return repository;
	}

	public void setRepository(ReviewRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/reviews/{id}")
	Review getReviewById(@PathVariable Long id) {
		return repository.findById(id).get();
	}

	@PutMapping("/reviews/{id}")
	Review updateReview(@RequestBody Review newReview, @PathVariable Long id) {
		return repository.findById(id).map(Review -> {
			Review.setReview(newReview.getReview());
			return repository.save(Review);
		}).orElseGet(() -> {
			newReview.setReviewId(id);
			return repository.save(newReview);
		});
	}
	
	@GetMapping(value = "/reviews")
	public List<Review> getReviewsByText(@RequestParam(name = "query") String query) {
		List<Review> reviews= repository.searchByReviewLike(query);
		for (int i=0; i < reviews.size(); i++){
			reviews.get(i).setReview(reviews.get(i).getReview().replace(query, "<keyword>"+query+"</keyword>"));
		}

		return reviews;
	}
	
}
