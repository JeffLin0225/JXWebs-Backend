package com.blog.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "BlogContent")
public class BlogContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    private Timestamp createtime;
    private Timestamp updatetime;

    @ManyToOne
    @JoinColumn(name = "blog_title_id", nullable = false)
    private BlogTitle blogTitle;

    @PrePersist
    public void setCreatetime() {
        if (createtime == null) {
            this.createtime = new Timestamp(System.currentTimeMillis());
        }
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public BlogTitle getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(BlogTitle blogTitle) {
        this.blogTitle = blogTitle;
    }
}