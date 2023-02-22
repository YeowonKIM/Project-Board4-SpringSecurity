package com.example.board3.repository;

import com.example.board3.entity.Board;
import com.example.board3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
    Optional<Board> findByIdAndUser(Long id, User user);
}
