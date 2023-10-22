package com.github.korbeckik.service.mapper;

import com.github.korbeckik.common.mapper.Mapper;
import com.github.korbeckik.service.dto.request.SaveFlashardsRequest;
import com.github.korbeckik.service.entity.FlashcardsEntity;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(uses = {SaveFlashardsRequestToFlashcardsEntityMapper.class})
public abstract class SaveFlashardsRequestToFlashcardsEntityMapper implements Mapper<SaveFlashardsRequest, FlashcardsEntity> {

    @Override
    public Class<SaveFlashardsRequest> getSource() {
        return SaveFlashardsRequest.class;
    }

    @Override
    public Class<FlashcardsEntity> getDestination() {
        return FlashcardsEntity.class;
    }

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdBy")
    @Mapping(ignore = true, target = "createdAt")
    public abstract FlashcardsEntity sourceToDestination(SaveFlashardsRequest source);

    @Mapping(ignore = true, target = "pages")
    public abstract SaveFlashardsRequest destinationToSource(FlashcardsEntity dest);

    @Override
    public FlashcardsEntity sourceToDestination(Object source) {
        if (source instanceof SaveFlashardsRequest saveFlashardsRequest) {
            return this.sourceToDestination(saveFlashardsRequest);
        }
        return null;
    }

    @Override
    public SaveFlashardsRequest destinationToSource(Object dest) {
        if (dest instanceof FlashcardsEntity flashcardsEntity) {
            return destinationToSource(flashcardsEntity);
        }
        return null;
    }


}
