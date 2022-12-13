package com.bweloba.httpparse.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    TokenRepository tokenRepository;

    public Token getTokenById(int id) {
        return tokenRepository.findById(id).get();
    }

    public void updateToken(String token) {
        Token updatedToken = new Token(1, token);
        tokenRepository.save(updatedToken);

    }
 
}
