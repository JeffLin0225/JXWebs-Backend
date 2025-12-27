package com.blog.Controller;

import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Repository.BlogTitleRepository;
import com.blog.Service.RedisCommonService;
import com.blog.Service.WriteBlogService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/writeblog")
public class WriteBlogController {
    private final WriteBlogService writeBlogService;
    private final RedisCommonService redisCommonService;
    private final BlogTitleRepository blogTitleRepository;

    public WriteBlogController(WriteBlogService writeBlogService, RedisCommonService redisCommonService ,BlogTitleRepository blogTitleRepository) {
        this.writeBlogService = writeBlogService;
        this.redisCommonService = redisCommonService;
        this.blogTitleRepository = blogTitleRepository;
    }

    @GetMapping("/getBlogTitle/{id}")
    public String  insertTitle(@PathVariable int id){
        Optional<BlogTitle> bOptional = blogTitleRepository.findById(id);
        BlogTitle blogTitle = bOptional.get();
        String result = blogTitle.getSubject();
        return result;
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
