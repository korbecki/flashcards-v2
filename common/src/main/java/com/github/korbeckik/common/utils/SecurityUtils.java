package com.github.korbeckik.common.utils;

import com.github.korbeckik.common.dto.UserInfoUserDetails;
import com.github.korbeckik.common.entity.UsersEntity;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

@UtilityClass
public class SecurityUtils {

    public static Mono<UserInfoUserDetails> getLoggedUser(){
        return ReactiveSecurityContextHolder.getContext().map(it -> it.getAuthentication().getPrincipal())
                .cast(UserInfoUserDetails.class);
    }

    public static Mono<Long> getLoggedUserId() {
        return getLoggedUser().map(UserInfoUserDetails::getId);
    }

    public static Mono<String> getLoggedUserEmail() {
        return getLoggedUser().map(UserInfoUserDetails::getEmail);
    }
}
