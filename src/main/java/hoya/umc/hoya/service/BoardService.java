package hoya.umc.hoya.service;

import hoya.umc.hoya.domain.Board;
import hoya.umc.hoya.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BoardService {
    private final BoardRepository boardRepository;


    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /*
    create) 글쓰기
     */
    public Board write(Board board){
        return boardRepository.save(board);
    }

    /*
    read) 전체 게시글 조회
     */
    public List<Board> findBoards(){
        return boardRepository.findALL();
    }

    public Optional<Board> findOneBoard(int id){
        return boardRepository.findById(id);
    }

    /*
    update) 업데이트
     */
    public Board update(Board board){
        boardRepository.update(board);
        return boardRepository.findById(board.getBoard_idx()).get();

    }

    /*
    delete)삭제
     */

    public List<Board> deleteId(int id){
        boardRepository.delete(id);
        return boardRepository.findALL();
    }


}
