package com.onevision.books.repository;

import com.onevision.books.dto.AuthorDTO;
import com.onevision.books.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookDTO> findAllByOrderByTitleDesc() {
        String sql = "SELECT id, title, author, description FROM book ORDER BY title DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new BookDTO(rs.getLong("id"), rs.getString("title"), rs.getString("author"), rs.getString("description")));
    }

    public BookDTO add(BookDTO book) {
        String check = "SELECT COUNT(*) FROM panel.book WHERE title = ? AND author = ?";
        int count = jdbcTemplate.queryForObject(check, Integer.class, book.getTitle(), book.getAuthor());

        if (count > 0) {
            throw new DuplicateKeyException("Книга с этими данными существует");
        }

        String sql = "INSERT INTO panel.book (title, author, description) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getDescription());

        if (rowsAffected > 0) {
            // Запрос для получения сохраненной книги
            String selectSql = "SELECT * FROM panel.book WHERE id = (SELECT MAX(id) FROM book)";
            return jdbcTemplate.queryForObject(selectSql, (rs, rowNum) ->
                    new BookDTO(rs.getLong("id"), rs.getString("title"), rs.getString("author"), rs.getString("description")));
        } else {
            return null;
        }
    }

    public Map<String, List<BookDTO>> findAllByGroupedAuthorList() {
        String sql = "SELECT author, id, title, description FROM panel.book ORDER BY author";

        Map<String, List<BookDTO>> groupedBooks = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            String author = rs.getString("author");
            BookDTO book = new BookDTO(rs.getLong("id"), rs.getString("title"), rs.getString("description"));

            groupedBooks.computeIfAbsent(author, k -> new ArrayList<>()).add(book);

        });

        return groupedBooks;
    }

    public List<AuthorDTO> findAuthorsWithSymbol(String character) {
        String sql = "SELECT author, SUM(LENGTH(title) - LENGTH(REPLACE(LOWER(title), LOWER(?), ''))) as symbolCount " +
                "FROM panel.book " +
                "WHERE LOWER(title) LIKE ? " +
                "GROUP BY author " +
                "ORDER BY symbolCount DESC";
        return jdbcTemplate.query(sql, new Object[]{character, "%" + character.toLowerCase() + "%"}, (rs, rowNum) ->
                new AuthorDTO(rs.getString("author"), rs.getInt("symbolCount")));
    }

}
