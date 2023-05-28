package hoya.umc.hoya.repository;

import hoya.umc.hoya.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);
    List<Board> findALL();
    Optional<Board> findById(int id);
    void update(Board board);

    void delete(int id);

}
