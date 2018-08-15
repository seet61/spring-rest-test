package ru.seet61.springresttest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.seet61.springresttest.model.Account;
import ru.seet61.springresttest.model.Bookmark;
import ru.seet61.springresttest.repository.AccountRepository;
import ru.seet61.springresttest.repository.BookmarkRepository;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
public class SpringRestTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestTestApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
		return args ->
			Arrays.asList("jhoeller","dsyer","pwebb","ogierke","rwinch","mfisher","mpollack","jlong")
					.forEach(username -> {
						System.out.println(username + " " + accountRepository.findByUsername(username).isPresent());
						if (!accountRepository.findByUsername(username).isPresent()) {
							Account account = accountRepository.save(new Account(username, "password"));
							bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + username, "A description one"));
							bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + username, "A description two"));
						}
					});

	}
}
