package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {

        //예상
        Article a = new Article(1L,"안녕","안녕하세요");
        Article b = new Article(2L,"수정","수정할게요");
        Article c = new Article(3L,"싸울때","맞다이로들어와");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));

        //실제
        List<Article> articles = articleService.index();

        //비교
        assertEquals(expected.toString(), articles.toString());

    }

    @Test
    void show_성공___존재하는_id_입력() {
        //예상
        Long id =1L;
        Article expected = new Article(id,"안녕","안녕하세요");

        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected.toString(),article.toString());

    }

    @Test
    void show_실패__존재하지않는id입력(){
        //예상
        Long id =-1L;
        Article expected = null;

        //실제
        Article article = articleService.show(id);

        //비교
        assertEquals(expected,article);
    }

    @Test
    @Transactional
    void create_성공_title과content만있는dto입력() {
        //예상
        String title = "daf";
        String content = "fefadssfsf";
        ArticleForm dto = new ArticleForm(null,title,content);
        Article expected = new Article(4L,title,content);

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected.toString(),article.toString());
    }

    @Test
    void create_실패_id가포함된dto입력() {
        //예상
        String title = "daf";
        String content = "fefadssfsf";
        ArticleForm dto = new ArticleForm(4L,title,content);
        Article expected = null;

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected,article);

    }
}