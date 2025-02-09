package com.blog.Controller;

import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Repository.BlogContentRepository;
import com.blog.Repository.BlogTitleRepository;
import com.blog.Service.RedisCommonService;
import com.blog.Service.WriteBlogService;
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
public class WriteBlogController {
    private final WriteBlogService writeBlogService;
    private final RedisCommonService redisCommonService;

    public WriteBlogController(WriteBlogService writeBlogService, RedisCommonService redisCommonService) {
        this.writeBlogService = writeBlogService;
        this.redisCommonService = redisCommonService;
    }

    @PostMapping("/insertBlogTitle")
    public String  insertTitle(@RequestBody BlogTitle blogTitle){
        String result = writeBlogService.insertTitle_Service(blogTitle);
        redisCommonService.deleteRedisByKey("blogTitleList");
        return result;
    }

    @PostMapping("/insertBlogContent")
    public String  insertBlogContent(@RequestBody BlogContent blogContent){
        String result = writeBlogService.insertBlogContent_Serivce(blogContent);
        redisCommonService.deleteRedisByKey("blogChildTitleList");
        return result;
    }

    @PutMapping("/modifyBlogContent")
    public String modifyBlogContent(@RequestBody BlogContent blogContent) {
        String result = writeBlogService.modifyBlogContent_Serivce(blogContent);
        redisCommonService.deleteRedisByKey("BlogContent:" + blogContent.getId());
        redisCommonService.deleteRedisByKey("blogChildTitleList");
        return result;
    }

    @DeleteMapping("/deleteBlog/{id}")
    public String deleteBlog(@PathVariable("id") Integer id ){
        String result = writeBlogService.deleteBlogById_Serive(id);
        redisCommonService.deleteRedisByKey("BlogContent:" + id);
        redisCommonService.deleteRedisByKey("blogChildTitleList");
        return result;
    }
}
