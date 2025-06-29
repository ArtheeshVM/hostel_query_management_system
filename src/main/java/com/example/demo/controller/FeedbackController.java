package com.example.demo.controller;

import com.example.demo.modal.Feedback;
import com.example.demo.repository.FeedbackRepository;

import java.util.ArrayList;
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
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id") Long id){
        Optional<Feedback> feedback=feedbackRepository.findById(id);

        if(feedback.isPresent()){
        return new ResponseEntity<>(feedback.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> createFeedbackById(@RequestBody Feedback feedback){
        try {
            Feedback fb=new Feedback(feedback.getQueryId(),feedback.getUserId(),feedback.getRating(),feedback.getComment());
            Feedback _feedback=feedbackRepository.save(fb);
            
            return new ResponseEntity<>(_feedback,HttpStatus.CREATED);
        } 
        catch (Exception e) {
            return new ResponseEntity<>((Feedback)null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/feedback/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable("id") Long id,@RequestBody Feedback feedback){
            Optional<Feedback> feedbackData=feedbackRepository.findById(id);
            if(feedbackData.isPresent()){
                Feedback feedback2=feedbackData.get();
                feedback2.setComment(feedback.getComment());
                feedback2.setQueryId(feedback.getQueryId());
                feedback2.setRating(feedback.getRating());
                feedback2.setUserId(feedback.getUserId());

                return new ResponseEntity<>(feedbackRepository.save(feedback2),HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }


    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<HttpStatus> deleteFeedbackById(@PathVariable("id") Long id){
        try{
            feedbackRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback(@RequestParam(required = false) String queryId){
        try {
            List<Feedback> feedbacks=new ArrayList<>();
            feedbackRepository.findAll().forEach(feedbacks::add);

            return new ResponseEntity<>(feedbacks,HttpStatus.OK);
        } 
        catch (Exception e) {
            return new ResponseEntity<>((List<Feedback>)null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/feedback")
    public ResponseEntity<HttpStatus> deleteAllFeedback(){
        try{
            feedbackRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
} 
