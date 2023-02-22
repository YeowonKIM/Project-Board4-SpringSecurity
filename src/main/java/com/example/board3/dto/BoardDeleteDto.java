package com.example.board3.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardDeleteDto {
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
