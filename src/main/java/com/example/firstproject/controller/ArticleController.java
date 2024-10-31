package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j //로깅을 위한 어노테이션(@)
public class ArticleController {

    @Autowired //스프링부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결!
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //println대신 logging기능으로
        log.info(form.toString());
        //System.out.println(form.toString());

        //1. Dto를 Entity로 변환 ArticleFrom은 dto, Article은 entity
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());

        //2. Repository에게 Entity를 DB에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = "+id);

        //1. id로 데이터를 가져옴!
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2. 가져온 데이터를 모델에 등록
        model.addAttribute("article",articleEntity);

        //3. 보여줄 페이시 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        //1. 모든 article을 가져온다
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 가져온 article묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntityList);

        //3. 뷰 페이지 설정
        return "articles/index";
    }
}
