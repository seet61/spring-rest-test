package ru.seet61.springresttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.seet61.springresttest.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
