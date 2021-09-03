package com.tazdev.myhome.repository;

import com.tazdev.myhome.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);

    //제목과 내용을 통해서 검색하는 기능
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
