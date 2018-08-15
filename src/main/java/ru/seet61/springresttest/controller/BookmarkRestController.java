package ru.seet61.springresttest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.seet61.springresttest.exception.BookmarkNotFoundException;
import ru.seet61.springresttest.exception.UserNotFoundException;
import ru.seet61.springresttest.model.Bookmark;
import ru.seet61.springresttest.repository.AccountRepository;
import ru.seet61.springresttest.repository.BookmarkRepository;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/bookmarks/{userId}")
public class BookmarkRestController {
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    Collection<Bookmark> readBookmarks(@PathVariable String userId, @RequestBody Bookmark input){
        this.validateUser(userId);

        return (Collection<Bookmark>) this.accountRepository.findByUsername(userId)
                .map(account -> {
                    Bookmark result = this.bookmarkRepository.save(new Bookmark(account, input.getUri(), input.getDescription()));
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();
                    return ResponseEntity.created(location).build();
                }).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);

        return this.bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new BookmarkNotFoundException(bookmarkId));
    }

    /**
     * Verify the {@literal userId} exists.
     *
     * @param userId
     */
    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }


}
