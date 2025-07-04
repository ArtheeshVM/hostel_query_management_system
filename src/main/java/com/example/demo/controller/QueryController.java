package com.example.demo.controller;

import com.example.demo.modal.Query;
import com.example.demo.repository.QueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QueryController {

    @Autowired
    private QueryRepository queryRepository;

    @GetMapping("/query/{id}")
    public ResponseEntity<Query> getQueryById(@PathVariable("id") Long id) {
        Optional<Query> query = queryRepository.findById(id);
        return query.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/query")
    public ResponseEntity<Query> createQuery(@RequestBody Query query) {
        try {
            Query newQuery = new Query(query.getStudentName(), query.getRoomNumber(), query.getQueryType(), query.getDescription(), query.getStatus());
            Query savedQuery = queryRepository.save(newQuery);
            return new ResponseEntity<>(savedQuery, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/query/{id}")
    public ResponseEntity<Query> updateQuery(@PathVariable("id") Long id, @RequestBody Query query) {
        Optional<Query> queryData = queryRepository.findById(id);
        if (queryData.isPresent()) {
            Query existingQuery = queryData.get();
            existingQuery.setStudentName(query.getStudentName());
            existingQuery.setRoomNumber(query.getRoomNumber());
            existingQuery.setQueryType(query.getQueryType());
            existingQuery.setDescription(query.getDescription());
            existingQuery.setStatus(query.getStatus());
            return new ResponseEntity<>(queryRepository.save(existingQuery), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/query/{id}")
    public ResponseEntity<HttpStatus> deleteQueryById(@PathVariable("id") Long id) {
        try {
            queryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/query")
    public ResponseEntity<HttpStatus> deleteAllQueries() {
        try {
            queryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/query")
    public ResponseEntity<List<Query>> getAllQueries(@RequestParam(required = false) String status) {
        try {
            List<Query> queries = new ArrayList<>();
            if (status == null) {
                queryRepository.findAll().forEach(queries::add);
            } else {
                queries = queryRepository.findByStatus(status);
            }
            return new ResponseEntity<>(queries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
