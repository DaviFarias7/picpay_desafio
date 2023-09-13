package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserTYpe() ==UserType.MERCHANT){
           throw new Exception("O usuário do tipo lojista não está autorizado a realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0){
            throw  new Exception("Usuário não tem saldo");
        }
    }
}
