package by.itacademy.javaenterprise.lepnikau.app.dao.implement;

import by.itacademy.javaenterprise.lepnikau.app.dao.StudentDAO;
import by.itacademy.javaenterprise.lepnikau.app.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDAOImpl implements StudentDAO {

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Student save(Student student) {
        String sql = "INSERT INTO `students` (`last_name`,`first_name`,`patronymic`,`class_id`) " +
                "VALUE (:lastName, :firstName, :patronymic, :classId)";

        int update = namedJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("lastName", student.getLastName())
                .addValue("firstName", student.getFirstName())
                .addValue("patronymic", student.getPatronymic())
                .addValue("classId", student.getClassId())
        );

        if (update > 0) {
            return student;
        }
        return null;
    }

    @Override
    public Student get(int id) {
        String sql = "SELECT `id`, `last_name`, `first_name`, `patronymic`, `class_id` FROM `students` " +
                "WHERE `id` = :id ORDER BY `last_name`";

        return namedJdbcTemplate.queryForObject(
                sql,
                new MapSqlParameterSource().addValue("id", id),
                new BeanPropertyRowMapper<>(Student.class)
        );
    }

    @Override
    public List<Student> getAllPageByPage(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);

        String sql = "SELECT `id`, `last_name`, `first_name`, `patronymic`, `class_id` FROM `students` " +
                "ORDER BY `last_name` LIMIT :pageSize OFFSET :offset";

        return namedJdbcTemplate.query(sql, new MapSqlParameterSource()
                        .addValue("pageSize", pageSize)
                        .addValue("offset", offset),
                new BeanPropertyRowMapper<>(Student.class));
    }

    @Autowired
    public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }
}
