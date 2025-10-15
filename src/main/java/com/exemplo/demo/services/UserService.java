package com.exemplo.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.exemplo.demo.models.User;
import com.exemplo.demo.repositories.UserRepository;
import com.exemplo.demo.services.exceptions.DataBindingViolationException;
import com.exemplo.demo.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
            "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create (User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }
    
    @Transactional
    public User update(User obj) {
        User newObj = this.findById(obj.getId());
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir o usuário, pois há entidades relacionadas!");
        }
    }
}
