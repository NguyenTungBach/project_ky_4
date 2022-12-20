package com.example.project_sem_4.database.jdbc_query;

import com.example.project_sem_4.database.dto.search.account.AccountSearchDTO;
import com.example.project_sem_4.database.entities.FeedBack;
import com.example.project_sem_4.database.search_body.AccountSearchBody;
import com.example.project_sem_4.database.search_body.FeedBackSearchBody;
import com.example.project_sem_4.util.HelpConvertDate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

@Log4j2
public class QueryFeedBackByJDBC {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<FeedBack> filterWithPaging(FeedBackSearchBody searchBody){
        return  jdbcTemplate.query(stringQuery(searchBody) + " LIMIT "
                + searchBody.getLimit() + " OFFSET " + searchBody.getLimit() * (searchBody.getPage()-1),new BeanPropertyRowMapper<>(FeedBack.class));
    }

    public List<FeedBack> filterWithNoPaging(FeedBackSearchBody searchBody){
        return  jdbcTemplate.query(stringQuery(searchBody),new BeanPropertyRowMapper<>(FeedBack.class));
    }

    public String stringQuery(FeedBackSearchBody searchBody){
        StringBuilder sqlQuery = new StringBuilder("Select * ");
        sqlQuery.append(" FROM feed_backs Where 1=1 ");

        if (searchBody.getTitle() != null && searchBody.getTitle().length() > 0){
            sqlQuery.append(" AND feed_backs.title Like '%" + searchBody.getTitle() + "%'");
        }

        if (searchBody.getEmail() != null && searchBody.getEmail().length() > 0){
            sqlQuery.append(" AND feed_backs.email Like '%" + searchBody.getEmail() + "%'");
        }

        if (searchBody.getStatus() != -1){
            sqlQuery.append(" AND feed_backs.status = " + searchBody.getStatus());
        }

        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){
            LocalDate date = HelpConvertDate.convertStringToLocalDate(searchBody.getStart());
            sqlQuery.append(" AND feed_backs.created_at >= '" + date + " 00:00:00' ");
        }

        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
            LocalDate date = HelpConvertDate.convertStringToLocalDate(searchBody.getEnd());
            sqlQuery.append(" AND feed_backs.created_at <= '" + date + " 23:59:59' ");
        }

        sqlQuery.append(" ORDER BY feed_backs.id ");
        if (searchBody.getSort().equals("asc")){
            sqlQuery.append(" ASC ");
        }
        if (searchBody.getSort().equals("desc")){
            sqlQuery.append(" DESC ");
        }
        log.info("check query: "+ sqlQuery);
        return sqlQuery.toString();
    }
}
