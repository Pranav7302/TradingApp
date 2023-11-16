package com.example.Contact.Repository;

import com.example.Contact.Entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession,Long> {

    UserSession findByUserId(Long id);

    void deleteByUserId(Long id);
}
