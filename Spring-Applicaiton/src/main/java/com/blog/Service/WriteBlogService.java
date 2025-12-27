package com.blog.Service;

import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Repository.BlogContentRepository;
import com.blog.Repository.BlogTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class WriteBlogService {

    private final BlogTitleRepository blogTitleRepository;
    private final BlogContentRepository blogContentRepository;

    public WriteBlogService(BlogTitleRepository blogTitleRepository, BlogContentRepository blogContentRepository) {
        this.blogTitleRepository = blogTitleRepository;
        this.blogContentRepository = blogContentRepository;
    }

    public String insertTitle_Service(BlogTitle blogTitle){
        if (blogTitle != null){
            blogTitleRepository.save(blogTitle);
            return "新增標題成功！！";
        }
        return "沒有給BlogTitle參數";
    }

    public String  insertBlogContent_Serivce(BlogContent blogContent){
        BlogTitle blogTitle = blogTitleRepository.findById(blogContent.getBlogTitle().getId())
                .orElseThrow(() -> new IllegalArgumentException("BlogTitle not found"));
        blogContent.setBlogTitle(blogTitle);
        blogContentRepository.save(blogContent);
        return "新增文章成功！！";
    }

    public String modifyBlogContent_Serivce(BlogContent blogContent) {
        Optional<BlogContent> result = blogContentRepository.findById(blogContent.getId());
        if(result.isEmpty()){
            return "沒有該文章";
        }
        BlogContent theBlogContent =  result.get();
        theBlogContent.setSubject(blogContent.getSubject());
        theBlogContent.setContent(blogContent.getContent());
        theBlogContent.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        blogContentRepository.save(theBlogContent);
        return "更新文章成功";
    }

    public String deleteBlogById_Serive(Integer id ){
        try {
            Optional<BlogContent> result = blogContentRepository.findById(id);
            if(result.isEmpty()){
                return "沒有該部落格文章";
            }
            blogContentRepository.deleteById(id);
            return "刪除文章成功！！！";
        } catch (Exception e) {
            return "刪除錯誤";
        }
    }


}
