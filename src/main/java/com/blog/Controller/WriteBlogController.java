package com.blog.Controller;

import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Repository.BlogContentRepository;
import com.blog.Repository.BlogTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

}
