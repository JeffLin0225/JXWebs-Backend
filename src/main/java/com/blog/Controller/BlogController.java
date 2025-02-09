package com.blog.Controller;

import com.blog.DTO.BlogChildTitleDTO;
import com.blog.DTO.BlogTitleDTO;
import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Service.BlogService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * 查詢全部 大標題
     * @return
     */
    @GetMapping("/findAllBlogBigTitle")
    public List<BlogTitle> findAllBlogBigTitle(){
        System.out.println("findAllBlogBigTitle");
        List<BlogTitle> blogTitleList = blogService.findAllBlogBigTitle_Service();
      return blogTitleList != null ? blogTitleList : null ;
    }

    /*
    查詢全部 子標題
  */
    @GetMapping("/findAllBlogChildTitle")
    public List<BlogChildTitleDTO> findAllBlogChildTitle() {
        List<BlogChildTitleDTO> blogChildTitleList = blogService.findAllBlogChildTitle_Service();
        return blogChildTitleList != null ? blogChildTitleList : new ArrayList<>();  // 返回空列表，避免返回 null
    }

    /**
     查詢 該文章內容 HTML
     **/
    @GetMapping("/content/{id}")
    public BlogContent getContentById(@PathVariable Integer id) {
        BlogContent blogContent = blogService.getContentById_Service(id);
        return blogContent != null ? blogContent : null;
    }


}
