package com.kubenews.koogleapiserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsDataApiController {
    @Autowired
    private KoogleNewsRepository repository;

    @GetMapping("/list")
    public ResponseEntity<PagingResponse<List<KoogleNews>>> getNewsData(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                        @RequestParam(required = false, defaultValue = "") String keyword,
                                                                        @RequestParam(required = false, defaultValue = "publishDate") String sort) {
        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(sort).descending());
        Page<KoogleNews> koogleNewsPage = repository.findPageByContentContains(pageable, keyword);
        PagingResponse<List<KoogleNews>> pagingResponse = PagingResponse.success(koogleNewsPage);
        return ResponseEntity.ok(pagingResponse);
    }
}
