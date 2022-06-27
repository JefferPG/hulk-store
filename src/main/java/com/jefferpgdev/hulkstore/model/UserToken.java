package com.jefferpgdev.hulkstore.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERTOKENS")
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdat;

    @Column(nullable = false)
    private LocalDateTime expiresat;

    private LocalDateTime confirmedat;

    @Column(nullable = false)
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserToken userToken = (UserToken) o;
        return id != null && Objects.equals(id, userToken.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
