package com.blog.Repository;

import com.blog.Entity.BlogTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTitleRepository extends JpaRepository<BlogTitle , Integer> {
}
