package com.jhones.booklist.repository;

import com.jhones.booklist.model.Book;
import com.jhones.booklist.model.BookList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookListRepository extends JpaRepository<BookList, Long> {
    @Query(value = "select * from tb_list b where b.user_login = :login", nativeQuery = true)
    List<BookList> findAllById(@Param("login") String login);
}
