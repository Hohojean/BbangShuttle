package com.bbang.shuttle;

import lombok.RequiredArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

// application.properties 설정 대신 좀더 상세한 설정을 위한 config
@Configuration
@RequiredArgsConstructor
@MapperScan(basePackages = "com.bbang.shuttle.mapperInterface")
public class MybatisConfig {

  @Autowired
  private DataSource dataSource;

  @Bean
  public DataSource dataSource() {
    // 데이터베이스 설정
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=Asia/Seoul");
    dataSource.setUsername("root");
    dataSource.setPassword("password");

    return dataSource;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);

    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sessionFactory.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
    return sessionFactory.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSession() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }

}
