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
@RequestMapping("/bookmarks/{userName}")
public class BookmarkRestController {
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    Collection<Bookmark> readBookmarks(@PathVariable String userName){
        this.validateUser(userName);

        return this.bookmarkRepository.findByAccountUsername(userName);
    }

    @PostMapping
    ResponseEntity<?> add(@PathVariable String userName, @RequestBody Bookmark input) {
        this.validateUser(userName);

        return this.accountRepository.findByUsername(userName)
                .map(account -> {
                    Bookmark result = this.bookmarkRepository.save(new Bookmark(account, input.getUri(), input.getDescription()));
                    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();
                    return ResponseEntity.created(location).build();
                }).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String userName, @PathVariable Long bookmarkId) {
        this.validateUser(userName);

        return this.bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new BookmarkNotFoundException(bookmarkId));
    }

    /**
     * Verify the {@literal userName} exists.
     *
     * @param userName
     */
    private void validateUser(String userName) {
        this.accountRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
    }


}
