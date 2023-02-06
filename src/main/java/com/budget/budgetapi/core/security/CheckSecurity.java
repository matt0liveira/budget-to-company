package com.budget.budgetapi.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

    public @interface UsersProfilesPermissions {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityUtils.canConsultUsersProfilesPermissions()")
        public @interface CanConsult {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityUtils.canConsultUser(#userId)")
        public @interface CanConsultUser {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityUtils.canChangeUsersProfilesPermissions()")
        public @interface CanChange {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityUtils.canChangeUser(#userId)")
        public @interface CanChangeUser {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityUtils.canChangePassword(#userId)")
        public @interface CanChangePassword {
        }
    }

    public @interface Categories {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityUtils.canConsultCategories(#filter.userId)")
        public @interface CanConsult {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @PostAuthorize("@securityUtils.canConsultCategories(returnObject.user.id)")
        public @interface CanFind {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') || isAuthenticated()")
        public @interface CanAdd {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        @PostAuthorize("@securityUtils.canChangeCategories(returnObject.user.id)")
        public @interface CanUpdate {
        }

    }

    public @interface Transactions {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityUtils.canSearchTransactions(#filter.userId)")
        public @interface CanSearch {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @PostAuthorize("@securityUtils.canSearchTransactions(returnObject.user.id)")
        public @interface CanFind {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        public @interface CanAdd {
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') && isAuthenticated()")
        public @interface CanUpdate {
        }
    }

    public @interface Analytics {

        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        @PreAuthorize("@securityUtils.canConsultReports(#filter.userId)")
        public @interface CanConsult {
        }
    }
}
