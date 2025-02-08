package com.blog.DTO;

import java.sql.Timestamp;

public class BlogTitleDTO {
    private Integer id;
    private String subject;
    private Integer blogTitleId;
    private Timestamp createtime;
    private Timestamp updatetime;

    public BlogTitleDTO(Integer id, String subject, Integer blogTitleId , Timestamp createtime, Timestamp updatetime) {
        this.id = id;
        this.subject = subject;
        this.blogTitleId = blogTitleId;
        this.createtime = createtime;
        this.updatetime = updatetime;
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

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}
