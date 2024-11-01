package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//Restapi용 컨트롤러 뷰를 반환하는게 아닌 json을 반환(다른것도 반환가능 데이터 등등)
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World";
    }
}
