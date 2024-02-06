package shop.template.onlineShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.template.onlineShop.enumerator.Country;
import shop.template.onlineShop.enumerator.UserRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String username;
    String password;

    @OneToMany(mappedBy = "author",
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    Set<Comment> comments;

    @ManyToMany(mappedBy = "lovers", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    Set<Product> favorites;

    @OneToMany(mappedBy = "user")
    Set<Order> orders;

    Country country;
    String address;
    String phoneNumber;

    boolean isEnabled = true;
    boolean isNonLocked = true;
    List<UserRole> userRoles;

    public User(String email, String username, String password, Set<Comment> comments, Set<Product> favorites, Set<Order> orders, Country country, String address, String phoneNumber) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.comments = comments;
        this.favorites = favorites;
        this.orders = orders;
        this.country = country;
        this.address = address;
        this.phoneNumber = phoneNumber;
        userRoles = new ArrayList<>();
        userRoles.add(UserRole.CUSTOMER);
    }

    public User(String email, String username, String password, Country country, String address, String phoneNumber) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.country = country;
        this.address = address;
        this.phoneNumber = phoneNumber;
        userRoles = new ArrayList<>();
        userRoles.add(UserRole.CUSTOMER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
