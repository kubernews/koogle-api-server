package com.kubenews.koogleapiserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private KoogleNewsRepository repository;

    @GetMapping("/get")
    public ResponseEntity<?> testGet(@RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "") String keyword) {
        List<KoogleNews> all = repo sitory.findAll();
        return ResponseEntity.ok(all);
    }
}
