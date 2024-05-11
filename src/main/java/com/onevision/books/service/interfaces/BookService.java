package com.onevision.books.service.interfaces;

import com.onevision.books.dto.AuthorDTO;
import com.onevision.books.dto.BookDTO;
import com.onevision.books.dto.wrappers.RestResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BookService {

    RestResponse<List<BookDTO>> findAllByTitleOrderDesc();

    ResponseEntity<RestResponse<?>> add(BookDTO dto);

    RestResponse<Map<String, List<BookDTO>>> findBooksGroupedByAuthor();

    RestResponse<List<AuthorDTO>> findTopAuthorsBySymbol(String symbol);

}
