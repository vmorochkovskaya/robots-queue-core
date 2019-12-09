package by.game.core.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


public class DatabaseUtil {
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:robotsdb;DB_CLOSE_ON_EXIT=TRUE";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static final SessionFactory SESSION_FACTORY;
    private static final JdbcTemplate JDBC_TEMPLATE;
    private static final DataSource DATA_SOURCE;
    static {
        try {
            SESSION_FACTORY = getConfiguration().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    static{
        DATA_SOURCE = new org.springframework.jdbc.datasource.DriverManagerDataSource(URL,USERNAME,PASSWORD);
    }
    static{
        JDBC_TEMPLATE = new JdbcTemplate(DATA_SOURCE);
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    @Bean
    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration().setProperty("hibernate.connection.driver_class", DRIVER_CLASS)
                .setProperty("hibernate.connection.url", URL).setProperty("hibernate.connection.username", USERNAME)
                .setProperty("hibernate.connection.password", PASSWORD)
                .setProperty("hibernate.connection.autocommit", "false")
                .setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider")
                .setProperty("hibernate.cache.use_second_level_cache", "false")
                .setProperty("hibernate.cache.use_query_cache", "false")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.current_session_context_class",
                        "org.hibernate.context.internal.ThreadLocalSessionContext")
                .setProperty("hibernate.enable_lazy_load_no_trans", "true")
                .setProperty("hibernate.temp.use_jdbc_metadata_defaults", "true")
                .addAnnotatedClass(by.game.core.entity.Log.class);

        return configuration;
    }

    @Bean(name = "jdbc template")
    public static JdbcTemplate getJdbcTemplate(){
        return JDBC_TEMPLATE;
    }

    @Bean
    public static DataSource getDataSource(){
        return DATA_SOURCE;
    }

    public static void dataBaseInitialization(){
        String createUsers = "CREATE TABLE USERS (ID SERIAL, EMAIL CHARACTER VARYING UNIQUE NOT NULL, PRIMARY KEY (ID));";
        String createSubjects = "CREATE TABLE SUBJECTS (ID SERIAL, SUBJECT CHARACTER VARYING NOT NULL, INITIATOR BIGINT NOT NULL REFERENCES USERS(ID), ISOPEN BOOLEAN NOT NULL, PRIMARY KEY (ID));";
        String createItems = "CREATE TABLE ITEMS (ID SERIAL, SUBJECTID BIGINT NOT NULL, TEXT TEXT NOT NULL, SUM INT NOT NULL DEFAULT 0, PRIMARY KEY(ID));";
        String createVotes = "CREATE TABLE VOTETICKETS (ID SERIAL, USERID BIGINT NOT NULL REFERENCES USERS (ID), SUBJECTID BIGINT NOT NULL REFERENCES SUBJECTS (ID), ISVOTED BOOLEAN NOT NULL DEFAULT FALSE, KEYCODE TEXT NOT NULL, PRIMARY KEY (ID));";
        String createSequence = "CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;";
        try{
            System.out.println("create table users");
            JDBC_TEMPLATE.execute(createUsers);
            System.out.println("table users created");
        }catch(Exception e){
            System.out.println("table users is already exists");
        }
        try{
            System.out.println("create table subjects");
            JDBC_TEMPLATE.execute(createSubjects);
            System.out.println("subjects is created");
        }catch(Exception e){
            System.out.println("table subjects is already exists");
        }
        try{
            System.out.println("create table items");
            JDBC_TEMPLATE.execute(createItems);
            System.out.println("items is created");
        }catch(Exception e){
            System.out.println("table items is already exists");
        }
        try{
            System.out.println("create table votetickets");
            JDBC_TEMPLATE.execute(createVotes);
            System.out.println("table votetickets is created");
        }catch(Exception e){
            System.out.println("table votetickets is already exists");
        }
        try{
            System.out.println("create hibernate sequence");
            JDBC_TEMPLATE.execute(createSequence);
            System.out.println("sequence is created");
        }catch(Exception e){
            System.out.println("hibernate sequence is already exists");
        }
    }
}

