package com.blog.Controller;

import com.blog.DTO.BlogContentDTO;
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
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogTitleRepository blogTitleRepository;

    @Autowired
    private BlogContentRepository blogContentRepository;

    /*
    查詢全部 大標題
     */
    @GetMapping("/findAllBlogBigTitle")
    public List<BlogTitle> findAllBlogBigTitle(){
        List<BlogTitle> result =  blogTitleRepository.findAll();
        return result;
    }

    /*
    查詢全部 子標題
     */
    @GetMapping("/findAllBlogChildTitle")
    public List<Map<String, Object>> findAllBlogChildTitle() {
        System.out.println("查詢");
        List<Object[]> titles = blogContentRepository.findAllBlogContent();
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        for (Object[] title : titles) {
            Integer blogTitleId = (Integer) title[0];
            String blogTitleSubject = (String) title[1];
            Integer contentId = (Integer) title[2];
            String contentSubject = (String) title[3];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createtime = ((Timestamp) title[4]).toLocalDateTime().format(formatter);
            String updatetime = title[5] == null ? null : ((Timestamp) title[5]).toLocalDateTime().format(formatter);

            Map<String, Object> blogTitle = map.getOrDefault(blogTitleId, new HashMap<>());
            blogTitle.putIfAbsent("id", blogTitleId);
            blogTitle.putIfAbsent("subject", blogTitleSubject);

            List<Map<String, Object>> children = (List<Map<String, Object>>) blogTitle.getOrDefault("children", new ArrayList<>());
            Map<String, Object> child = new HashMap<>();
            child.put("id", contentId);
            child.put("subject", contentSubject);
            child.put("createtime", createtime);
            child.put("updatetime", updatetime);
            children.add(child);
            blogTitle.put("children", children);

            map.put(blogTitleId, blogTitle);
        }

        return new ArrayList<>(map.values());
    }

    /**
     查詢 該文章內容 HTML
     **/
    @GetMapping("/content/{id}")
    public ResponseEntity<BlogContent> getContentById(@PathVariable Integer id) {
        Optional<BlogContent> content = blogContentRepository.findById(id);
        return content.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
