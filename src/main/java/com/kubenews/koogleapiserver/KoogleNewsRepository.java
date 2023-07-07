package com.kubenews.koogleapiserver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KoogleNewsRepository extends JpaRepository<KoogleNews, Long> {
    Page<KoogleNews> findPageByContentContains(Pageable pageable, String content);
}
