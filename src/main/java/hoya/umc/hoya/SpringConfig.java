package hoya.umc.hoya;

import hoya.umc.hoya.repository.BoardRepository;
import hoya.umc.hoya.repository.MySqlJdbcRepository;
import hoya.umc.hoya.service.BoardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public BoardService boardService(){
        return new BoardService(boardRepository());
    }

    @Bean
    public BoardRepository boardRepository(){
        return new MySqlJdbcRepository(dataSource);
        // return new JdbcMemberRepository(dataSource);
    }
}
