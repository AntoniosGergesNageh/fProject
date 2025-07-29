package com.VirtualBankingSystem.UserService.Entity;

import com.VirtualBankingSystem.UserService.Constants.UserTableConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = UserTableConstants.USER_TABLE_NAME)
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = UserTableConstants.USER_ID, nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = UserTableConstants.USER_FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = UserTableConstants.USER_LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = UserTableConstants.USER_EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = UserTableConstants.USER_NAME, nullable = false, unique = true)
    private String username;

    @Column(name = UserTableConstants.USER_PASSWORD, nullable = false)
    private String password;

    @Column(name = UserTableConstants.USER_CREATED_AT, nullable = false)
    private Timestamp createdAt;
}
