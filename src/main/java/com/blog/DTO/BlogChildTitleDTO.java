package com.blog.DTO;

public class BlogChildTitleDTO {

    private Integer id;
    private String subject;
    private BlogTitleDTO blogTitleDTO;
    private String createtime;
    private String updatetime;

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

    public BlogTitleDTO getBlogTitleDTO() {
        return blogTitleDTO;
    }

    public void setBlogTitleDTO(BlogTitleDTO blogTitleDTO) {
        this.blogTitleDTO = blogTitleDTO;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
