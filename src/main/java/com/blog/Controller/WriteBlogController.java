package com.blog.Controller;

import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Repository.BlogContentRepository;
import com.blog.Repository.BlogTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/writeblog")
@CrossOrigin
public class WriteBlogController {

    @Autowired
    private BlogTitleRepository blogTitleRepository;

    @Autowired
    private BlogContentRepository blogContentRepository;

    @PostMapping("/insertBlogTitle")
    public String  insertTitle(@RequestBody BlogTitle blogTitle){
        blogTitleRepository.save(blogTitle);
        return "新增標題成功！！";
    }

    @PostMapping("/insertBlogContent")
    public String  insertBlogContent(@RequestBody BlogContent blogContent){
        BlogTitle blogTitle = blogTitleRepository.findById(blogContent.getBlogTitle().getId())
                .orElseThrow(() -> new IllegalArgumentException("BlogTitle not found"));

        blogContent.setBlogTitle(blogTitle); // 设置关联关系
        blogContentRepository.save(blogContent); // 保存内容

        return "新增文章成功！！";
    }

    @PutMapping("/modifyBlogContent")
    public String putMethodName(@RequestBody BlogContent blogContent) {
        Optional<BlogContent> result = blogContentRepository.findById(blogContent.getId());
        if(result.isEmpty()){
            return "沒有該文章";
        }
        BlogContent theBlogContent =  result.get();
        theBlogContent.setSubject(blogContent.getSubject());
        theBlogContent.setContent(blogContent.getContent());
        theBlogContent.setUpdatetime(new Timestamp(0));
        blogContentRepository.save(theBlogContent); // 保存内容
        
        return "更新文章成功";
    }

    @DeleteMapping("/deleteBlog/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable("id") Integer id ){
        try {
            Optional<BlogContent> result = blogContentRepository.findById(id);
            if(result.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("沒有該部落格文章");
            }
            blogContentRepository.deleteById(id);
            return ResponseEntity.ok("刪除文章成功！！！");
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("刪除錯誤");
        }
    }
}
