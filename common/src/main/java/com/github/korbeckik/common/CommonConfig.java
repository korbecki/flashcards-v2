package com.github.korbeckik.common;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.github.korbeckik.common.*"})
@EntityScan(basePackages = {"com.github.korbeckik.common.*"})
public class CommonConfig {

}
