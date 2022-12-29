package com.example.project_sem_4.database.jdbc_query;

import com.example.project_sem_4.database.entities.Blog;
import com.example.project_sem_4.database.entities.Blog;
import com.example.project_sem_4.database.search_body.BlogSearchBody;
import com.example.project_sem_4.database.search_body.BlogSearchBody;
import com.example.project_sem_4.util.HelpConvertDate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@Repository
public class QueryBlogByJDBC {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Blog> filterWithPaging(BlogSearchBody searchBody){
        return  jdbcTemplate.query(stringQuery(searchBody) + " LIMIT "
                + searchBody.getLimit() + " OFFSET " + searchBody.getLimit() * (searchBody.getPage()-1),new BeanPropertyRowMapper<>(Blog.class));
    }

    public List<Blog> filterWithNoPaging(BlogSearchBody searchBody){
        return  jdbcTemplate.query(stringQuery(searchBody),new BeanPropertyRowMapper<>(Blog.class));
    }

    public String stringQuery(BlogSearchBody searchBody){
        StringBuilder sqlQuery = new StringBuilder("Select bl.* ");
        sqlQuery.append(" FROM blogs as bl join accounts as acc ON bl.account_id = acc.id Where 1=1 ");

        if (searchBody.getAuth_name() != null && searchBody.getAuth_name().length() > 0){
            sqlQuery.append(" AND acc.name Like '%" + searchBody.getAuth_name() + "%'");
        }

        if (searchBody.getTitle() != null && searchBody.getTitle().length() > 0){
            sqlQuery.append(" AND bl.title Like '%" + searchBody.getTitle() + "%'");
        }

        if (searchBody.getStatus() != null){
            sqlQuery.append(" AND bl.status = " + searchBody.getStatus());
        } else {
            sqlQuery.append(" AND bl.status != -1");
        }

        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){
            LocalDate date = HelpConvertDate.convertStringToLocalDate(searchBody.getStart());
            sqlQuery.append(" AND bl.created_at >= '" + date + " 00:00:00' ");
        }

        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
            LocalDate date = HelpConvertDate.convertStringToLocalDate(searchBody.getEnd());
            sqlQuery.append(" AND bl.created_at <= '" + date + " 23:59:59' ");
        }

        sqlQuery.append(" ORDER BY bl.id ");
        if (searchBody.getSort() != null && searchBody.getSort().equals("asc")){
            sqlQuery.append(" ASC ");
        }
        if (searchBody.getSort() != null && searchBody.getSort().equals("desc")){
            sqlQuery.append(" DESC ");
        }
        log.info("check query: "+ sqlQuery);
        return sqlQuery.toString();
    }
}
