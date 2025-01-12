package com.blog.Controller;

import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Repository.BlogContentRepository;
import com.blog.Repository.BlogTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
