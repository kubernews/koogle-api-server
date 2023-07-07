package com.kubenews.koogleapiserver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="koogle_news")
public class KoogleNews {
    @Id
    private Long kid;
    @Column(name="publish_date")
    private LocalDate publishDate;
    private String title;
    private String content;
    private String url;

    public Long getKid() {
        return kid;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

}
