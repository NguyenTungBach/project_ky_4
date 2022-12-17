package com.example.project_sem_4.database.jdbc_query;

import com.example.project_sem_4.database.dto.search.service.ServiceSearchDTO;
import com.example.project_sem_4.database.search_body.ServiceSearchBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryServiceByJDBC {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ServiceSearchDTO> filterWithPaging(ServiceSearchBody serviceSearchBody){
        return  jdbcTemplate.query(sqlQuery(serviceSearchBody) + " LIMIT "
                + serviceSearchBody.getLimit() + " OFFSET " + serviceSearchBody.getLimit() * (serviceSearchBody.getPage()-1),new BeanPropertyRowMapper<>(ServiceSearchDTO.class));
    }

    public List<ServiceSearchDTO> filterWithNoPaging(ServiceSearchBody serviceSearchBody){
        return  jdbcTemplate.query(sqlQuery(serviceSearchBody),new BeanPropertyRowMapper<>(ServiceSearchDTO.class));
    }

    public String sqlQuery(ServiceSearchBody serviceSearchBody) {
        StringBuilder sql = new StringBuilder("SELECT s.id as service_id, s.name as service_name, s.description as description, s.thumbnail as thumbnail, " +
                "ts.id as typeServiceStr FROM services s JOIN type_services ts ON s.type_service_id = ts.id WHERE 1 = 1 ");
        if(serviceSearchBody.getName() != null && serviceSearchBody.getName().trim().length() > 0) {
            sql.append(" AND s.name LIKE '%" + serviceSearchBody.getName() + "%'");
        }
        if(serviceSearchBody.getTypeServiceId() != null && serviceSearchBody.getTypeServiceId() > 0) {
            sql.append(" AND ts.id = " + serviceSearchBody.getTypeServiceId());
        }
        return sql.toString();
    }
}
