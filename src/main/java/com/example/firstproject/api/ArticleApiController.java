package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.entity.Article;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired //Dependency injection -> 외부에서 가져온다
    private ArticleService articleService;

    //Get
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleService.show(id);
    }

    //Post @Requestbody : api요청 body에서 데이터를 받아와라
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm jsonarticle){
        Article created = articleService.create(jsonarticle);
        return (created != null) ? ResponseEntity.status(HttpStatus.OK).body(created) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //Patch
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm jsonarticle){

       Article updated = articleService.update(id,jsonarticle);
       return (updated != null)? ResponseEntity.status(HttpStatus.OK).body(updated) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //Delete
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){

        Article deleted = articleService.delete(id);
        return (deleted != null) ? ResponseEntity.status(HttpStatus.OK).body(null): ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> jsonarticles){
        List<Article> createdList =  articleService.createArticles(jsonarticles);
        return (createdList != null) ? ResponseEntity.status(HttpStatus.OK).body(createdList) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
