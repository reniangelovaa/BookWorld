package bg.softuni.bookworld.service;

import bg.softuni.bookworld.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {
    private User user;

    public boolean isLoggedIn(){
        return this.user != null;
    }

    public boolean isAdministrator(){
        return true;
       // return this.user.getRoles().stream().anyMatch(role -> role.getRole().equals("ADMINISTRATOR"));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
