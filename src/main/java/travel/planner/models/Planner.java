package travel.planner.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Planner implements UserDetails {
    private int plannerId;
    private final String username;
    private final String password;
    private boolean enabled;

    public Planner(int plannerId, String username, String password, boolean enabled) {
        this.plannerId = plannerId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    // TODO: Implement the methods of the UserDetails interface.
    // The methods should return the appropriate values for the Planner class.
    // may not need this method
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // TODO: verify these should return the enabled/disabled flag for the account
    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPlannerId(int plannerId) {
        this.plannerId = plannerId;
    }

    public int getPlannerId() {
        return plannerId;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
