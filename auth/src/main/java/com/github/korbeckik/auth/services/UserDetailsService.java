package com.github.korbeckik.auth.services;

import com.github.korbeckik.auth.dto.UserInfoUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements ReactiveUserDetailsService {

    private final UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userService.findByEmail(email)
                .map(UserInfoUserDetails::new);
    }
}
