package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm jsonarticle){
        Article article = jsonarticle.toEntity();
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm jsonarticle){
        //수정할 dto을 entity로 변경
        Article article = jsonarticle.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        //대상 entity찾기
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()){
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        //업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id){
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //대상 삭제
        //잘못된요청처리
        if(target == null){
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> jsonarticles){
        //dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = jsonarticles.stream().map(jsonarticle -> jsonarticle.toEntity()).collect(Collectors.toList());

        //entity 묶음을 DB로 저장
        articleList.stream().forEach(article -> articleRepository.save(article));

        //강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("저장 실패!"));

        //결과값 반환
        return articleList;
    }
}
