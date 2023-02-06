package com.budget.budgetapi.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.budget.budgetapi.domain.exception.DomainException;
import com.budget.budgetapi.domain.exception.EntityInUseException;
import com.budget.budgetapi.domain.exception.UserNotFoundException;
import com.budget.budgetapi.domain.model.Profile;
import com.budget.budgetapi.domain.model.User;
import com.budget.budgetapi.domain.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User newUser) {
        Optional<User> userExists = userRepository.findByEmail(newUser.getEmail());

        if (userExists.isPresent() && !userExists.get().equals(newUser)) {
            throw new DomainException(
                    String.format("Já existe um usuário com o e-mail %s cadastrado", newUser.getEmail()));
        }

        if (newUser.isNew()) {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        return userRepository.save(newUser);
    }

    @Transactional
    public void remove(Long userId) {
        try {
            User user = findOrFail(userId);
    
            userRepository.delete(user);
            userRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format("User code %d cannot be removed, because it's in use", userId));
        }
    }

    @Transactional
    public void toChangePassword(Long userId, String passwordCurrent, String newPassword) {
        User user = findOrFail(userId);

        if (!passwordEncoder.matches(passwordCurrent, user.getPassword())) {
            throw new DomainException("Senha atual incorreta. Insira a senha atual correta e tente novamente.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void connectProfile(Long userId, Long profileId) {
        User user = findOrFail(userId);
        Profile profile = profileService.findOrfail(profileId);

        user.connectProfile(profile);
    }

    @Transactional
    public void disassociateProfile(Long userId, Long profileId) {
        User user = findOrFail(userId);
        Profile profile = profileService.findOrfail(profileId);

        user.disassociateProfile(profile);
    }

    public User findOrFail(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
