package com.github.korbeckik.mail;

import com.github.korbeckik.mail.entity.ActivateEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivateRepository extends CrudRepository<ActivateEntity, Long> {

    @Query("SELECT * from activate inner join public.users u on activate.activate_id = u.activate_id WHERE u.user_id=:userId")
    Optional<ActivateEntity> findByUserId(@Param("userId") Long userId);
}
