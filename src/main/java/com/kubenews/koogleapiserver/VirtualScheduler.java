package com.kubenews.koogleapiserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VirtualScheduler {
    @Autowired
    private KoogleNewsRepository koogleNewsRepository;
    private Logger log = LoggerFactory.getLogger(VirtualScheduler.class);
    @Autowired
    private ObjectMapper ob;

    @Scheduled(cron = "3 * * * * *")
    void virtualRead() {
        Random random = new Random();
        int page = random.nextInt() % 3;
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<KoogleNews> pageByContentContains = koogleNewsRepository.findPageByContentContains(pageRequest, "");
        try {
            log.info(ob.writeValueAsString(pageByContentContains));
        } catch (JsonProcessingException e) {
            log.error("cannot write json");
            throw new RuntimeException(e);
        }
    }
}
