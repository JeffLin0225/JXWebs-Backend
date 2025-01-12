package com.blog.Repository;

import com.blog.Entity.BlogContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogContentRepository extends JpaRepository<BlogContent,Integer> {

    @Query("SELECT b FROM BlogContent b WHERE b.blogTitle.id = :titleId")
    List<BlogContent> findByBlogTitleId(@Param("titleId") Integer titleId);

    @Query("SELECT b.blogTitle.id, b.blogTitle.subject, b.id, b.subject ,b.createtime , b.updatetime FROM BlogContent b")
    List<Object[]> findAllBlogContent();



}
