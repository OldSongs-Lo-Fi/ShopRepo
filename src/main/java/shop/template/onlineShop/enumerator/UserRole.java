package shop.template.onlineShop.enumerator;

import org.springframework.security.core.GrantedAuthority;
public enum UserRole implements GrantedAuthority {
    ADMIN, MODERATOR, CUSTOMER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
