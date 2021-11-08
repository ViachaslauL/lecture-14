package by.itacademy.javaenterprise.lepnikau.app;

import by.itacademy.javaenterprise.lepnikau.app.config.SchoolDiaryConfig;
import by.itacademy.javaenterprise.lepnikau.app.dao.DAO;
import by.itacademy.javaenterprise.lepnikau.app.dao.MarkDAO;
import by.itacademy.javaenterprise.lepnikau.app.dao.implement.MarkDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {


        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SchoolDiaryConfig.class);

        MarkDAO markDAO = context.getBean(MarkDAOImpl.class);

        getAllPageByPageTest(markDAO, 5, 1);
    }

    private static void getAllPageByPageTest(DAO dao, int limit, int offset) {
        StringBuilder sBuilder = new StringBuilder();
        List<Object> allPageByPage = dao.getAllPageByPage(limit, offset);

        if (!allPageByPage.isEmpty()) {
            for (Object o : allPageByPage) {
                sBuilder.append("\n").append(o);
            }
            sBuilder.append("\n--Page--");
        }

        log.info(sBuilder.toString());
    }
}
