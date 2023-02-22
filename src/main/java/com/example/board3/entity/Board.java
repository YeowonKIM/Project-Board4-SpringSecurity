package com.example.board3.entity;

import com.example.board3.dto.BoardRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Board extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Board(BoardRequestDto boardRequestDto, User user) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.user = user;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.content = boardRequestDto.getContent();
        this.title = boardRequestDto.getTitle();
        this.user = user;
    }

}

