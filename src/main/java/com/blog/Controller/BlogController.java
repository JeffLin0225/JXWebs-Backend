package com.blog.Controller;

import com.blog.DTO.BlogTitleDTO;
import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Repository.BlogContentRepository;
import com.blog.Repository.BlogTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin
public class BlogController {

    @Autowired
    private BlogTitleRepository blogTitleRepository;

    @Autowired
    private BlogContentRepository blogContentRepository;

    @GetMapping("/findBlogTitle")
    public List<BlogTitle> findBlogTitle(){
        List<BlogTitle> result =  blogTitleRepository.findAll();
        return result;
    }

    @PostMapping("/insertBlogTitle")
    public String  insertTitle(@RequestBody BlogTitle blogTitle){
        blogTitleRepository.save(blogTitle);
        return "新增標題成功！！";
    }

    @GetMapping("/findBlogContent")
    public List<BlogContent> findBlogContent(){
        List<BlogContent> result =  blogContentRepository.findAll();
        return result;
    }

    @PostMapping("/insertBlogContent")
    public String  insertBlogContent(@RequestBody BlogContent blogContent){
        BlogTitle blogTitle = blogTitleRepository.findById(blogContent.getBlogTitle().getId())
                .orElseThrow(() -> new IllegalArgumentException("BlogTitle not found"));

        blogContent.setBlogTitle(blogTitle); // 设置关联关系
        blogContentRepository.save(blogContent); // 保存内容

        return "新增文章成功！！";
    }


    @GetMapping("/titles")
    public List<Map<String, Object>> getAllTitles() {
        System.out.println("查詢");
        List<Object[]> titles = blogContentRepository.findAllBlogContent();
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        for (Object[] title : titles) {
            Integer blogTitleId = (Integer) title[0];
            String blogTitleSubject = (String) title[1];
            Integer contentId = (Integer) title[2];
            String contentSubject = (String) title[3];

            Map<String, Object> blogTitle = map.getOrDefault(blogTitleId, new HashMap<>());
            blogTitle.putIfAbsent("id", blogTitleId);
            blogTitle.putIfAbsent("subject", blogTitleSubject);

            List<Map<String, Object>> children = (List<Map<String, Object>>) blogTitle.getOrDefault("children", new ArrayList<>());
            Map<String, Object> child = new HashMap<>();
            child.put("id", contentId);
            child.put("subject", contentSubject);
            children.add(child);
            blogTitle.put("children", children);

            map.put(blogTitleId, blogTitle);
        }

        return new ArrayList<>(map.values());
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<BlogContent> getContentById(@PathVariable Integer id) {
        Optional<BlogContent> content = blogContentRepository.findById(id);
        return content.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
