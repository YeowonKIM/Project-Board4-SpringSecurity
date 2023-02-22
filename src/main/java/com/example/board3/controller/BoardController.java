package com.example.board3.controller;

import com.example.board3.dto.BoardDeleteDto;
import com.example.board3.dto.BoardRequestDto;
import com.example.board3.dto.BoardResponseDto;
import com.example.board3.security.UserDetailsImpl;
import com.example.board3.service.BoardService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/post")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(boardRequestDto, userDetails.getUser());
    }

    @GetMapping("/api/posts")
    public List<BoardResponseDto> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("/api/post/{id}")
    public BoardResponseDto getBorad(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/api/post/{id}")
    public ResponseEntity updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, boardRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }
}
