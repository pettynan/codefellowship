package com.tynan.codefellowship.codefellowship;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    AppUser author;
    String body;
    Timestamp createdAt;

    public long getId() {
        return id;
    }

    public AppUser getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
