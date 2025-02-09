package com.blog.Service;

import com.blog.DTO.BlogChildTitleDTO;
import com.blog.DTO.BlogTitleDTO;
import com.blog.Entity.BlogContent;
import com.blog.Entity.BlogTitle;
import com.blog.Repository.BlogContentRepository;
import com.blog.Repository.BlogTitleRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.blog.Config.RedisSetting.TTL_DEFAULT;

@Service
public class BlogService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final BlogTitleRepository blogTitleRepository;
    private final BlogContentRepository blogContentRepository;

    public BlogService(RedisTemplate<String, Object> redisTemplate, BlogTitleRepository blogTitleRepository, BlogContentRepository blogContentRepository) {
        this.redisTemplate = redisTemplate;
        this.blogTitleRepository = blogTitleRepository;
        this.blogContentRepository = blogContentRepository;
    }

    public List<BlogTitle> findAllBlogBigTitle_Service() {
        System.out.println("findAllBlogBigTitle_Service");
        // 先從 redis 找
        List<Object> blogTitleList_Cache = redisTemplate.opsForList().range("blogTitleList", 0, -1);
        System.out.println("1"+blogTitleList_Cache);

        if (!blogTitleList_Cache.isEmpty()) {

            return blogTitleList_Cache.stream().map(blogTitle -> (BlogTitle) blogTitle).collect(Collectors.toList());
        }
        List<BlogTitle> blogTitleList = blogTitleRepository.findAll();
        System.out.println("2"+blogTitleList);

        if (blogTitleList.isEmpty()) {
            return null;
        }
        redisTemplate.opsForList().rightPushAll("blogTitleList", blogTitleList.toArray());
        redisTemplate.expire("blogTitleList", TTL_DEFAULT.getValues() , TimeUnit.SECONDS);
        System.out.println(blogTitleList);

        return blogTitleList;
    }

    public List<BlogChildTitleDTO> findAllBlogChildTitle_Service() {
        // 先從 Redis 查找
        List<Object> blogChildTitleList_Cache = redisTemplate.opsForList().range("blogChildTitleList", 0, -1);
        if (!blogChildTitleList_Cache.isEmpty()) {
            return blogChildTitleList_Cache.stream()
                    .map(blogChildTitleDTO -> (BlogChildTitleDTO) blogChildTitleDTO)
                    .collect(Collectors.toList());
        }

        List<Object[]> blogContentList = blogContentRepository.findAllBlogContent();
        if (blogContentList.isEmpty()) {
            return new ArrayList<>();
        }

        List<BlogChildTitleDTO> blogChildTitleList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Object[] blogContent : blogContentList) {
            Integer blogTitleId = (Integer) blogContent[0];
            String blogTitleSubject = (String) blogContent[1];
            String blogTitleCreatetime = ((Timestamp) blogContent[2]).toLocalDateTime().format(formatter);

            Integer contentId = (Integer) blogContent[3];
            String contentSubject = (String) blogContent[4];
            String createtime = ((Timestamp) blogContent[5]).toLocalDateTime().format(formatter);
            String updatetime = blogContent[6] == null ? null : ((Timestamp) blogContent[6]).toLocalDateTime().format(formatter);

            // 創建母標題 DTO
            BlogTitleDTO blogTitleDTO = new BlogTitleDTO();
            blogTitleDTO.setId(blogTitleId);
            blogTitleDTO.setSubject(blogTitleSubject);
            blogTitleDTO.setCreatetime(blogTitleCreatetime);

            // 創建子標題 DTO，並設定母標題
            BlogChildTitleDTO children = new BlogChildTitleDTO();
            children.setId(contentId);
            children.setSubject(contentSubject);
            children.setBlogTitleDTO(blogTitleDTO);
            children.setCreatetime(createtime);
            children.setUpdatetime(updatetime);
            blogChildTitleList.add(children);
            redisTemplate.opsForList().rightPush("blogChildTitleList", children);

        }

        // 存入 Redis
        redisTemplate.expire("blogChildTitleList", TTL_DEFAULT.getValues(), TimeUnit.SECONDS);

        return blogChildTitleList;
    }

    public BlogContent getContentById_Service(Integer id) {
        Object blogContent_Cache = redisTemplate.opsForValue().get("BlogContent:" + id);
        if (blogContent_Cache != null) {
            return (BlogContent) blogContent_Cache;
        }
        Optional<BlogContent> content = blogContentRepository.findById(id);
        if (content.isEmpty()){
            return null;
        }
        redisTemplate.opsForValue().set("BlogContent:" + id , content.get() , TTL_DEFAULT.getValues() , TimeUnit.SECONDS);
        return content.get();
    }

}
