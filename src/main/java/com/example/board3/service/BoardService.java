package com.example.board3.service;

import com.example.board3.dto.BoardDeleteDto;
import com.example.board3.dto.BoardRequestDto;
import com.example.board3.dto.BoardResponseDto;
import com.example.board3.dto.MsgResponseDto;
import com.example.board3.entity.Board;
import com.example.board3.entity.User;
import com.example.board3.entity.UserRoleEnum;
import com.example.board3.jwt.JwtUtil;
import com.example.board3.repository.BoardRepository;
import com.example.board3.repository.UserRepository;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    // 게시글 작성
    @Transactional
    public ResponseEntity<BoardResponseDto> createBoard(BoardRequestDto boardRequestDto, User user) {
        Board board = new Board(boardRequestDto, user);
        boardRepository.save(board);
//      BoardResponseDto boardResponseDto = new BoardResponseDto(board); // 1
        return ResponseEntity.ok().body(new BoardResponseDto(board)); //2

    }

    // 예외처리
    private ResponseEntity<MsgResponseDto> exceptionRes(String msg) {
        MsgResponseDto msgResponseDto = new MsgResponseDto(msg, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(msgResponseDto);
    }


    // 게시글 전체 조회
    public List<BoardResponseDto> getBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> boardResponseDto = new ArrayList<>();
        for (Board board : boardList) {
            BoardResponseDto tmpBoardResponseDto = new BoardResponseDto(board);
            boardResponseDto.add(tmpBoardResponseDto);
        }
        return boardResponseDto;
    }

    // 게시글 지정 조회
    public BoardResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("존재하지 않습니다.")
        );
        return new BoardResponseDto(board);
    }


    // 게시글 수정
    @Transactional
    public ResponseEntity updateBoard(Long id, BoardRequestDto boardRequestDto, User user){

        // id로 게시글 있는지 확인
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            return exceptionRes("게시글이 존재하지 않습니다.");
        }

        // 게시글 작성자와 토큰에서의 정보가 일치하는지 확인, 일피하면 게시글 수정
        // Admin인 경우 수정 권한
        Optional<Board> compare = boardRepository.findByIdAndUser(id, user);
        if (compare.isEmpty() && user.getRole()== UserRoleEnum.USER) {
            return exceptionRes("작성자 정보가 없습니다.");
        } else if (user.getRole() == UserRoleEnum.ADMIN) {
            board = boardRepository.findById(id);
        }

        // 이외의 경우는 게시글 수정 가능
        board.get().update(boardRequestDto);
        return ResponseEntity.ok().body(new BoardResponseDto(board.get()));


    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity deleteBoard(Long id, User user) {

        // id로 게시글 있는지 확인
        Optional<Board> board = boardRepository.findById(id);
        if (board.isEmpty()) {
            return exceptionRes("게시글이 존재하지 않습니다.");
        }

        // 게시글 작성자와 토큰에서의 정보가 일치하는지 확인, 일피하면 게시글 삭제
        // Admin인 경우 수정 권한
        Optional<Board> compare = boardRepository.findByIdAndUser(id, user);
        if (compare.isEmpty() && user.getRole()== UserRoleEnum.USER) {
            return exceptionRes("작성자 정보가 없습니다.");
        } else if (user.getRole() == UserRoleEnum.ADMIN) {
            board = boardRepository.findById(id);
        }

        // 이외의 경우는 게시글 삭제 가능
        boardRepository.deleteById(id);
        return ResponseEntity.ok().body(new BoardResponseDto(board.get()));

    }

}
