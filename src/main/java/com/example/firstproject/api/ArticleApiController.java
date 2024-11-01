package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired //Dependency injection -> 외부에서 가져온다
    private ArticleRepository articleRepository;

    //Get
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    //Post @Requestbody : api요청 body에서 데이터를 받아와라
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm jsonarticle){
        Article article = jsonarticle.toEntity();
        return articleRepository.save(article);
    }

    //Patch
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm jsonarticle){

        //수정용 엔티티 반환 json을 Articleform으로 받아와서 엔티티로 변경
        Article article = jsonarticle.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        //대상 엔티티 조회
        Article target =  articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리(대상이 없거나, id가 다른경우)
        if(target == null || id != article.getId()){
            //400, 잘못된 요청 응답
            log.info("잘못된 요청! id={}, article={}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //Delete
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //대상 삭제
        //잘못된요청처리
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        articleRepository.delete(target);

        //삭제완료 반환
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
