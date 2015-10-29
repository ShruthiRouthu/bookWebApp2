package edu.wctc.srt.bookwebapp2.service;

import edu.wctc.srt.bookwebapp2.entity.Book;
import edu.wctc.srt.bookwebapp2.repository.AuthorRepository;
import edu.wctc.srt.bookwebapp2.repository.BookRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * @author jlombardo
 */
@Repository("bookService")
@Transactional(readOnly = true)
public class BookService {
    private transient final Logger LOG = LoggerFactory.getLogger(BookService.class);
    
    @Inject
    private BookRepository bookRepo;
    
    @Inject
    private AuthorRepository authorRepo;

    public BookService() {
    }
    
    public List<Book> findAll() {
        return bookRepo.findAll();
    }
    
    public Book findById(String id) {
        return bookRepo.findOne(new Integer(id));
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Book book) {
        LOG.debug("Deleting book: " + book.getTitle());
        bookRepo.delete(book);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Book edit(Book book) {
        return bookRepo.saveAndFlush(book);
    }
    
}
