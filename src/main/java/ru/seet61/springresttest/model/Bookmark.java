package ru.seet61.springresttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bookmark {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Account account;

    private String uri;

    private String description;

    private Bookmark() {}

    public Bookmark(final Account account, final String uri, final String description) {
        this.account = account;
        this.uri = uri;
        this.description = description;
    }

    public static Bookmark from(Account account, Bookmark bookmark) {
        return new Bookmark(account, bookmark.uri, bookmark.getDescription());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", account=" + account +
                ", uri='" + uri + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
