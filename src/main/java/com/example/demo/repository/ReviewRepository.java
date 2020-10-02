package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	@Query("FROM review  WHERE review LIKE %:review%")
	List<Review> searchByReviewLike(@Param("review") String review);

}
