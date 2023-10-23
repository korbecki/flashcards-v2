package com.github.korbeckik.service.mapper;

import com.github.korbeckik.service.dto.response.PagesListResponse;
import com.github.korbeckik.service.entity.PagesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {PagesEntityToPagesListResponse.class})
public abstract class PagesEntityToPagesListResponse implements com.github.korbeckik.common.mapper.Mapper<PagesEntity, PagesListResponse> {
    @Override
    public Class<PagesEntity> getSource() {
        return PagesEntity.class;
    }

    @Override
    public Class<PagesListResponse> getDestination() {
        return PagesListResponse.class;
    }

    public abstract PagesListResponse sourceToDestination(PagesEntity source);

    @Mapping(ignore = true, target = "flashcardId")
    public abstract PagesEntity destinationToSource(PagesListResponse dest);

    @Override
    public PagesListResponse sourceToDestination(Object source) {
        if (source instanceof PagesEntity flashcardsEntity) {
            return sourceToDestination(flashcardsEntity);
        }
        return null;
    }

    @Override
    public PagesEntity destinationToSource(Object dest) {
        if (dest instanceof PagesListResponse flashcardsListResponse) {
            return destinationToSource(flashcardsListResponse);
        }
        return null;
    }
}
