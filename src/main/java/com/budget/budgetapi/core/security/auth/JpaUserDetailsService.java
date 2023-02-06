package com.budget.budgetapi.core.security.auth;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.budget.budgetapi.domain.model.User;
import com.budget.budgetapi.domain.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                getAuthorities(user));
    }

    public Collection<GrantedAuthority> getAuthorities(User user) {
        return user
                .getProfiles()
                .stream()
                .flatMap(profile -> profile.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName().toUpperCase()))
                .collect(Collectors.toList());
    }

}
