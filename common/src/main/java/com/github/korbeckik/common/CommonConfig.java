package com.github.korbeckik.common;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@ComponentScan(basePackages = {"com.github.korbeckik.common.*"})
@EntityScan(basePackages = {"com.github.korbeckik.common.*"})
@EnableR2dbcRepositories
public class CommonConfig {

}
