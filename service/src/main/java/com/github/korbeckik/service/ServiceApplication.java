package com.github.korbeckik.service;

import com.github.korbeckik.common.mapper.MapFactory;
import com.github.korbeckik.service.mapper.FlashcardsEntityToFlashcardsList;
import com.github.korbeckik.service.mapper.PageDtoToPagesEntityMapper;
import com.github.korbeckik.service.mapper.PagesEntityToPagesListResponse;
import com.github.korbeckik.service.mapper.SaveFlashardsRequestToFlashcardsEntityMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@RequiredArgsConstructor
@EnableAspectJAutoProxy
public class ServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @PostConstruct
    public void registerMappers() {
        MapFactory.registerMappers(
                Mappers.getMapper(PageDtoToPagesEntityMapper.class),
                Mappers.getMapper(SaveFlashardsRequestToFlashcardsEntityMapper.class),
                Mappers.getMapper(FlashcardsEntityToFlashcardsList.class),
                Mappers.getMapper(PagesEntityToPagesListResponse.class)
        );
    }
}
