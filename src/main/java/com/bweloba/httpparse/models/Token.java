package com.bweloba.httpparse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Table
@AllArgsConstructor
@Getter
@Setter
public class Token {
    @Id
    @Column
    private int Id;
    @Column
    private String token;

    public Token() {

    }
}
