package by.game.core.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;


public class DatabaseUtil {
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:robotsdb;DB_CLOSE_ON_EXIT=TRUE";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static final SessionFactory SESSION_FACTORY;
    static {
        try {
            SESSION_FACTORY = getConfiguration().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
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
}

