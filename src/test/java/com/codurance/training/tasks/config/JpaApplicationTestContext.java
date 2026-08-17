package com.codurance.training.tasks.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import com.codurance.training.tasks.io.springboot.ToDoListSpringBootApp;

@ComponentScan(basePackages={"com.codurance.training.tasks"}, excludeFilters= {
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= ToDoListSpringBootApp.class),
        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= com.codurance.SpringBootApplicationTest.class)})
@EntityScan(basePackages={"com.codurance.training.tasks"})

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public abstract class JpaApplicationTestContext {
}
