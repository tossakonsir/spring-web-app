package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ReviewApiControllerTests {
	
	@Autowired
	private ReviewRepository reviewReposiroty;

	@Test
	public void testGetReviewById() {
		Review mockReview = new Review();
		mockReview.setReviewId((long) 2);
		mockReview.setReview("สั่งไป2 เมนู คือมัชฉะลาเต้ร้อน กับ ไอศครีมชาเขียว มัชฉะลาเต้ร้อน รสชาเขียวเข้มข้น หอม มัน แต่ไม่กลมกล่อม มันจืดแบบจืดสนิท ส่วนไอศครีมชาเขียว ทานแล้วรสมันออกใบไม้ๆมากกว่าชาเขียว แล้วก็หวานไป โดยรวมแล้วเฉยมากก ดีแค่รสชาเขียวเข้ม มีน้ำเปล่าบริการฟรี");
		Review review = reviewReposiroty.findById((long) 2).get();
		
		assertThat(review.getReview()).isEqualTo(mockReview.getReview());
	}
	
	@Test
	public void testGetReviewByText() {
		List<Review> review = reviewReposiroty.searchByReviewLike("ไอติมมันม่วง");
		
		assertThat(review.size()).isEqualTo(1);
	}
	
	@Test
	public void testEditReview() {
	reviewReposiroty.findById((long) 1).map(Review -> {
		Review.setReview("test edit review");
		return reviewReposiroty.save(Review);
	});
	Review review = reviewReposiroty.findById((long) 1).get();

		assertThat(review.getReview()).isEqualTo("test edit review");
	}
}
