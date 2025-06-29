
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.Query;
import com.example.demo.repository.QueryRepository;

@RestController
@RequestMapping("/api")
public class QueryController{

    @Autowired
    private QueryRepository queryRepository;

    @GetMapping("/query/{id}")
    public ResponseEntity<Query> getQueryById(@PathVariable("id") Long id){
        Optional<Query> query=queryRepository.findById(id);
        if(query.isPresent()){
            return new ResponseEntity<>(query.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/query")
    public ResponseEntity<Query> createQuery(@RequestBody Query query){
        try {
            Query query2=new Query(query.getStudentName(),query.getRoomNumber(),query.getQueryType(),query.getDescription(),query.getStatus());
            Query _query=queryRepository.save(query2);

            return new ResponseEntity<>(_query,HttpStatus.OK);
        } 
        catch (Exception e) {
            return new ResponseEntity<Query>((Query)null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("query/{id}")
    public ResponseEntity<Query> updateQuery(@PathVariable("id") Long id,@RequestBody Query query){
        Optional<Query> queryData=queryRepository.findById(id);
        if(queryData.isPresent()){
            Query _query=queryData.get();
            _query.setStudentName(query.getStudentName());
            _query.setRoomNumber(query.getRoomNumber());
            _query.setQueryType(query.getQueryType());
            _query.setDescription(query.getDescription());
            _query.setStatus(query.getStatus());

            return new ResponseEntity<>(queryRepository.save(_query),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/query/{id}")
    public ResponseEntity<HttpStatus> deleteQuery(@PathVariable("id") Long id){
        try {
            queryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/query")
    public ResponseEntity<HttpStatus> deleteQuery(){
        try {
            queryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/query")
    public ResponseEntity<List<Query>> getAllQuery(@RequestParam(required = false) String status){
        try {
            List<Query> queries=new ArrayList<>();
            queryRepository.findAll().forEach(queries::add);
            return new ResponseEntity<>(queries,HttpStatus.OK);
        } 
        catch (Exception e) {
            return new ResponseEntity<>((List<Query>)null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("query/status/{status}")
    public ResponseEntity<List<Query>> getQueryByStatus(@PathVariable String status){
        try{
            List<Query> queries=queryRepository.findByStatus(status.toUpperCase());
            return new ResponseEntity<>(queries, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>((List<Query>)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
