package com.budget.budgetapi.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.domain.repository.UserRepository;

@Component
public class SecurityUtils {

    @Autowired
    private UserRepository userRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserIdAuthenticated() {
        if(getAuthentication().getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("user_id");
        }

        return this.userRepository.findByEmail(getAuthentication().getName()).get().getId();
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    private boolean hasAuthority(String authorityName) {
        return getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    public boolean hasScopeRead() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean hasScopeWrite() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean canConsultUsersProfilesPermissions() {
        return hasScopeRead() && hasAuthority("CONSULT_USERS_PROFILES_PERMISSIONS");
    }

    public boolean canConsultUser(Long userId) {
        return canConsultUsersProfilesPermissions()
                || hasScopeRead() && userAuthenticatedEquals(userId);
    }

    public boolean canChangeUsersProfilesPermissions() {
        return hasScopeWrite() && hasAuthority("CHANGE_USERS_PROFILES_PERMISSIONS");
    }

    public boolean canChangeUser(Long userId) {
        return canChangeUsersProfilesPermissions() || hasScopeWrite() && userAuthenticatedEquals(userId);
    }

    public boolean canChangePassword(Long userId) {
        return hasScopeWrite() && userAuthenticatedEquals(userId);
    }

    public boolean canConsultCategories(Long userId) {
        return hasScopeRead() && (hasAuthority("CONSULT_CATEGORIES")) || userAuthenticatedEquals(userId);
    }

    public boolean canChangeCategories(Long userId) {
        return hasScopeWrite() && (hasAuthority("CHANGE_CATEGORIES")) || userAuthenticatedEquals(userId);
    }

    public boolean canSearchTransactions(Long userId) {
        return hasScopeRead() && (hasAuthority("CONSULT_TRANSACTIONS")) || userAuthenticatedEquals(userId);
    }

    public boolean userAuthenticatedEquals(Long userId) {
        return getUserIdAuthenticated() != null && userId != null && getUserIdAuthenticated().equals(userId);
    }

    public boolean canUpdateTransactions(Long userId) {
        return hasScopeWrite() && (hasAuthority("CHANGE_TRANSACTIONS")) || userAuthenticatedEquals(userId);
    }

    public boolean canConsultReports(Long userId) {
        return hasScopeRead() && (hasAuthority("CONSULT_REPORTS")) || userAuthenticatedEquals(userId);
    }

}
