package com.github.korbeckik.service.mapper;

import com.github.korbeckik.service.dto.request.SaveAttemptRequest;
import com.github.korbeckik.service.dto.response.FlashcardsListResponse;
import com.github.korbeckik.service.entity.FlashcardsEntity;
import com.github.korbeckik.service.entity.ResolvedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {SaveAttemptRequestToResolvedEntity.class})
public abstract class SaveAttemptRequestToResolvedEntity implements com.github.korbeckik.common.mapper.Mapper<SaveAttemptRequest, ResolvedEntity> {
    @Override
    public Class<SaveAttemptRequest> getSource() {
        return SaveAttemptRequest.class;
    }

    @Override
    public Class<ResolvedEntity> getDestination() {
        return ResolvedEntity.class;
    }

    @Mapping(ignore = true, target = "userId")
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    public abstract ResolvedEntity sourceToDestination(SaveAttemptRequest source);

    public abstract SaveAttemptRequest destinationToSource(ResolvedEntity dest);

    @Override
    public ResolvedEntity sourceToDestination(Object source) {
        if (source instanceof SaveAttemptRequest flashcardsEntity) {
            return sourceToDestination(flashcardsEntity);
        }
        return null;
    }

    @Override
    public SaveAttemptRequest destinationToSource(Object dest) {
        if (dest instanceof ResolvedEntity flashcardsListResponse) {
            return destinationToSource(flashcardsListResponse);
        }
        return null;
    }
}
