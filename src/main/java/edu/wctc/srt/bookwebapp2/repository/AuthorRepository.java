package edu.wctc.srt.bookwebapp2.repository;

import edu.wctc.srt.bookwebapp2.entity.Author;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jlombardo
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    @Query("SELECT a FROM Author a JOIN FETCH a.bookSet WHERE a.authorId = (:id)")
    public Author findByIdAndFetchBooksEagerly(@Param("id") Integer authorId);
    
}
