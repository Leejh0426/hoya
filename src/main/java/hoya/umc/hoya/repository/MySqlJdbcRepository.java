package hoya.umc.hoya.repository;

import hoya.umc.hoya.domain.Board;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlJdbcRepository implements BoardRepository {

    private final DataSource dataSource;

    public MySqlJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Board save(Board board) {
        String sql = "insert into Board(title,contents) values(?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1,board.getTitle());
            pstmt.setString(2,board.getContents());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();


            if(rs.next()){
                board.setBoard_idx(rs.getInt(1));
            }else{
                throw new SQLException("id 조회 실패");
            }
            return board;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }finally{
            close(conn,pstmt,rs);
        }
    }

    @Override
    public void update(Board board){
        String sql = "update board set title=?,contents=? where board_idx= ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,board.getTitle());
            pstmt.setString(2,board.getContents());
            pstmt.setInt(3,board.getBoard_idx());

            pstmt.executeUpdate();



        } catch (Exception e) {
            throw new IllegalStateException(e);
        }finally{
            close(conn,pstmt,rs);
        }

    }

    public void delete(int id){
        String sql = "delete from board where board_idx = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id);

            pstmt.executeUpdate();




        } catch (Exception e) {
            throw new IllegalStateException(e);
        }finally{
            close(conn,pstmt,rs);
        }

    }

    @Override
    public List<Board> findALL() {
        String sql = "select * from board";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Board> boards = new ArrayList<>();
            while(rs.next()) {
                Board board = new Board();
                board.setBoard_idx(rs.getInt("board_idx"));
                board.setTitle(rs.getString("title"));
                board.setContents(rs.getString("contents"));
                boards.add(board);
            }
            return boards;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Board> findById(int id) {
        String sql = "select * from board where board_idx=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                Board board = new Board();
                board.setBoard_idx(rs.getInt("board_idx"));
                board.setTitle(rs.getString("title"));
                board.setContents(rs.getString("contents"));
                return Optional.of(board);
            }else{
                return Optional.empty();
            }
        }catch(Exception e){
            throw new IllegalStateException(e);
        }finally{
            close(conn,pstmt,rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
