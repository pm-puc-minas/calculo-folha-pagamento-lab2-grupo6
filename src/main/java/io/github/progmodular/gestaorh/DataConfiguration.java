package io.github.progmodular.gestaorh;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DataConfiguration {
    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // 1. Driver
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // 2. URL de Conexão
        dataSource.setUrl("jdbc:mysql://localhost:3306/apprh?useTimezone=true&serverTimezone=UTC");
        // 3. Usuário
        dataSource.setUsername("root");
        // 4. Senha
        dataSource.setPassword("PucDataBase123");

        return dataSource;
    }
    @Bean
    public JpaVendorAdapter jpaVendorAdapter()
    {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        adapter.setPrepareConnection(true);
        return adapter;
    }
}
