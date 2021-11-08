package by.itacademy.javaenterprise.lepnikau.app.dao.implement;

import by.itacademy.javaenterprise.lepnikau.app.dao.ParentDAO;
import by.itacademy.javaenterprise.lepnikau.app.entity.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParentDAOImpl implements ParentDAO {

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Parent save(Parent parent) {
        String sql = "INSERT INTO `parents` (`student_id`,`last_name`,`first_name`,`patronymic`) " +
                "VALUE (:studentId, :lastName, :firstName, :patronymic)";

        int update = namedJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("studentId", parent.getStudentId())
                .addValue("lastName", parent.getLastName())
                .addValue("firstName", parent.getFirstName())
                .addValue("patronymic", parent.getPatronymic())
        );

        if (update > 0) {
            return parent;
        }
        return null;
    }

    @Override
    public Parent get(int id) {
        String sql = "SELECT `id`, `student_id`, `last_name`, `first_name`, `patronymic` FROM `parents` " +
                "ORDER BY `last_name` WHERE `id` = :id";

        return namedJdbcTemplate.queryForObject(sql, new MapSqlParameterSource()
                        .addValue("id", id),
                new BeanPropertyRowMapper<>(Parent.class));
    }

    @Override
    public List<Parent> getAllPageByPage(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);

        String sql = "SELECT `id`, `student_id`, `last_name`, `first_name`, `patronymic` FROM `parents` " +
                "ORDER BY `last_name` LIMIT :pageSize OFFSET :offset";

        return namedJdbcTemplate.query(sql, new MapSqlParameterSource()
                        .addValue("pageSize", pageSize)
                        .addValue("offset", offset),
                new BeanPropertyRowMapper<>(Parent.class));
    }

    @Autowired
    public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }
}
