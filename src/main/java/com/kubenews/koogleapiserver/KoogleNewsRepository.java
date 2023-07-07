package com.kubenews.koogleapiserver;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KoogleNewsRepository extends JpaRepository<KoogleNews, Long> {

}
