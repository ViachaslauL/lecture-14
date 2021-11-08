package by.itacademy.javaenterprise.lepnikau.app.dao.implement;

import by.itacademy.javaenterprise.lepnikau.app.dao.MarkDAO;
import by.itacademy.javaenterprise.lepnikau.app.entity.Mark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarkDAOImpl implements MarkDAO {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Mark save(Mark mark) {
        String sql = "INSERT INTO `marks` (`student_id`,`mark`,`subject_id`,`date`) " +
                "VALUES (?,?,?,?)";

        int update = jdbcTemplate.update(
                sql,
                mark.getStudentId(),
                mark.getMark(),
                mark.getSubjectId(),
                mark.getDate()
        );

        if (update > 0) {
            return mark;
        }
        return null;
    }

    @Override
    public Mark get(int id) {
        String sql = "SELECT `id`, `student_id`, `mark`, `subject_id`, `date` " +
                "FROM `marks` WHERE `id` = ? ORDER BY `date`";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Mark.class), id);
    }

    @Override
    public List<Mark> getAllPageByPage(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);

        String sql = "SELECT `id`, `student_id`, `mark`, `subject_id`, `date` " +
                "FROM `marks` ORDER BY `date` LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Mark.class), pageSize, offset);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
