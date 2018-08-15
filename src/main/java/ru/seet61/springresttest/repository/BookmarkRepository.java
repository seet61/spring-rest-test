package ru.seet61.springresttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.seet61.springresttest.model.Bookmark;

import java.util.Collection;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
