package com.example.batch.repository;

import com.example.batch.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor_id(Long author_id);

    List<Post> findByScheduled(String scheduled);
}
