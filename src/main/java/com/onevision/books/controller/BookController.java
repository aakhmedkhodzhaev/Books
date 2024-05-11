package com.onevision.books.controller;

import com.onevision.books.dto.AuthorDTO;
import com.onevision.books.dto.BookDTO;
import com.onevision.books.dto.wrappers.RestResponse;
import com.onevision.books.service.interfaces.BookService;
import com.onevision.books.utils.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/books")
public class BookController {

    private final BookService service;

    private final UtilService util;

    @Autowired
    public BookController(BookService service, UtilService util) {
        this.service = service;
        this.util = util;
    }


    @GetMapping("/sorted-by-title-desc")
    public ResponseEntity<RestResponse<List<BookDTO>>> getAllBooksSortedByTitleDesc() {
        return util.createResponse(service.findAllByTitleOrderDesc());
    }

    @PostMapping("/add")
    public ResponseEntity<RestResponse<?>> addBook(@RequestBody BookDTO bookDto) {
        return service.add(bookDto);
    }

    @GetMapping("/grouped-by-author")
    public ResponseEntity<RestResponse<Map<String, List<BookDTO>>>> getBooksGroupedByAuthor() {
        return util.createResponse(service.findBooksGroupedByAuthor());
    }

    @GetMapping("/top-authors-by-symbol")
    public ResponseEntity<RestResponse<List<AuthorDTO>>> getTopAuthorsBySymbol(@RequestParam("search") String symbol) {
        return util.createResponse(service.findTopAuthorsBySymbol(symbol));
    }

}
