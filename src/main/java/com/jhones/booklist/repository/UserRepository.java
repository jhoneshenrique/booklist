package com.jhones.booklist.repository;

import com.jhones.booklist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByLogin(String login);
}
