package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendItemVO {
    private String type;
    private String blogId;
    private String url;
    private String title;
    private String author;
    private String authorId;
    private String content;
    private String pubDate;
    private double weight;
    private String originSite;

    public enum TYPE {
        LOCAL("LOCAL"),
        EXTERNAL("EXTERNAL");
        private String name;

        TYPE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
