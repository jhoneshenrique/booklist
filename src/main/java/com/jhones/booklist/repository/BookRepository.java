package com.jhones.booklist.repository;

import com.jhones.booklist.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //Custom query
    @Query(value = "select * from tb_book b where b.book_list_id = :id", nativeQuery = true)
    List<Book> findAllById(@Param("id") Long id);
}
