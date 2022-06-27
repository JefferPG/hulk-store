package com.jefferpgdev.hulkstore.repository;

import com.jefferpgdev.hulkstore.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {

    Optional<UserToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USERTOKENS SET CONFIRMEDAT=:confirmedat WHERE TOKEN=:token", nativeQuery = true)
    int updateConfirmedAt(@Param("token") String token, @Param("confirmedat") LocalDateTime confirmedat);

}
