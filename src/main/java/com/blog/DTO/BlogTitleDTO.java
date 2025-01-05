package com.blog.DTO;

public class BlogTitleDTO {
    private Integer id;
    private String subject;
    private Integer blogTitleId;

    public BlogTitleDTO(Integer id, String subject, Integer blogTitleId) {
        this.id = id;
        this.subject = subject;
        this.blogTitleId = blogTitleId;
    }

    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getBlogTitleId() {
        return blogTitleId;
    }

    public void setBlogTitleId(Integer blogTitleId) {
        this.blogTitleId = blogTitleId;
    }
}
