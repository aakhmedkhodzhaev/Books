package com.onevision.books.service;

import com.onevision.books.dto.AuthorDTO;
import com.onevision.books.dto.BookDTO;
import com.onevision.books.dto.wrappers.RestResponse;
import com.onevision.books.repository.BookRepository;
import com.onevision.books.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    // Список всех книг, которые содержатся в таблице book, отсортированный в обратном алфавитном порядке значения колонки book.title
    @Override
    public RestResponse<List<BookDTO>> findAllByTitleOrderDesc() {
        List<BookDTO> list = repository.findAllByOrderByTitleDesc();
        return new RestResponse<>(list);
    }

    // Добавление новой книги
    @Override
    public ResponseEntity<RestResponse<?>> add(BookDTO dto) {
        try {
            dto = repository.add(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new RestResponse<>(dto));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestResponse("Книга с такими параметрами уже существует, Автор: " + dto.getAuthor() + " , Наименование: " + dto.getTitle() + " , Описание: " + dto.getDescription()));
        }
    }

    // Список всех книг, сгруппированных по автору
    @Override
    public RestResponse<Map<String, List<BookDTO>>> findBooksGroupedByAuthor() {
        Map<String, List<BookDTO>> result = repository.findAllByGroupedAuthorList();
        return new RestResponse<>(result);
    }

    @Override
    public RestResponse<List<AuthorDTO>> findTopAuthorsBySymbol(String symbol) {
        return new RestResponse<>(repository.findAuthorsWithSymbol(symbol));
    }

}
