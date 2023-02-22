package com.example.board3.dto;

import com.example.board3.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    @Builder
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUser().getUsername();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
    }

}
