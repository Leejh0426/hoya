package hoya.umc.hoya.controller;


import hoya.umc.hoya.domain.Board;
import hoya.umc.hoya.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }



    @GetMapping("/boards/form")
    public String createForm(){return "boards/createBoardForm.html";}


    //CREATE
    @PostMapping("/boards")
    @ResponseBody
    public Board create(@ModelAttribute BoardForm form){
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContents(form.getContents());

        return boardService.write(board);

    }

    //READ
    @GetMapping("/boards")
    @ResponseBody
    public List<Board> list(){
        return boardService.findBoards();
    }





    //UPDATE

    @PutMapping("/boards")
    @ResponseBody
    public Board update(@ModelAttribute BoardForm form){
        Board board = new Board();
        board.setBoard_idx(form.getId());
        board.setTitle(form.getTitle());
        board.setContents(form.getContents());

        return boardService.update(board);
    }


    //Delete

    @DeleteMapping("/boards")
    @ResponseBody
    public List<Board> delete(@RequestParam int id){
        return boardService.deleteId(id);
    }




}
