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
    private int loginId;
    private int configId;
    private String name;

    public Planner(int plannerId, String username, String password, boolean enabled, String name, int loginId, int configId) {
        this.plannerId = plannerId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.loginId = loginId;
        this.configId = configId;
        this.name = name;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // NOT USED
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

    public int getPlannerId() {
        return plannerId;
    }

    public void setPlannerId(int plannerId) {
        this.plannerId = plannerId;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }
}
