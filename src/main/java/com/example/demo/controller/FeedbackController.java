package com.example.demo.controller;

import com.example.demo.modal.Feedback;
import com.example.demo.repository.FeedbackRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id") Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return feedback.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback newFeedback = new Feedback(feedback.getStudentName(), feedback.getRoomNumber(), feedback.getDescription());
            Feedback savedFeedback = feedbackRepository.save(newFeedback);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/feedback/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable("id") Long id, @RequestBody Feedback feedback) {
        Optional<Feedback> feedbackData = feedbackRepository.findById(id);
        if (feedbackData.isPresent()) {
            Feedback existingFeedback = feedbackData.get();
            existingFeedback.setStudentName(feedback.getStudentName());
            existingFeedback.setRoomNumber(feedback.getRoomNumber());
            existingFeedback.setDescription(feedback.getDescription());
            return new ResponseEntity<>(feedbackRepository.save(existingFeedback), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<HttpStatus> deleteFeedbackById(@PathVariable("id") Long id) {
        try {
            feedbackRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/feedback")
    public ResponseEntity<HttpStatus> deleteAllFeedback() {
        try {
            feedbackRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        try {
            List<Feedback> feedbacks = feedbackRepository.findAll();
            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
