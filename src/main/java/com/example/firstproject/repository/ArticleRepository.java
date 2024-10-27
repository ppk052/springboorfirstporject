package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

//extends CrudRepository<관리할 엔티티,대표값 데이터타입>
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
