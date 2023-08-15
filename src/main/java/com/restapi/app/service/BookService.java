package com.restapi.app.service;

import com.restapi.app.entity.Book;
import com.restapi.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        if(bookRepository.findById(id).isPresent()) {
            return bookRepository.findById(id).get();
        }
        else return new Book();
    }

    public Book createBookRecord(Book bookRecord) {
        return bookRepository.save(bookRecord);
    }

    public Book updateBookRecord(Book bookRecord) throws Exception {
        if(bookRecord == null || bookRecord.getBookId() == null) {
            throw new Exception("Book Record or ID should not be null!");
        }

        Optional<Book> bookRecordToBeFound =bookRepository.findById(bookRecord.getBookId());
        if(!bookRecordToBeFound.isPresent()) {
            throw new Exception("Book with ID: " + bookRecord.getBookId() + " does not exist!");
        }

        Book existingBookRecord = bookRecordToBeFound.get();

        existingBookRecord.setName(bookRecord.getName());
        existingBookRecord.setRating(bookRecord.getRating());
        existingBookRecord.setSummary(bookRecord.getSummary());

        return bookRepository.save(existingBookRecord);
    }
}
