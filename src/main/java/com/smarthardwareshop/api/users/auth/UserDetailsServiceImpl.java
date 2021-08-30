package com.smarthardwareshop.api.users.auth;

import com.smarthardwareshop.api.users.entities.User;
import com.smarthardwareshop.api.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

/**
 * Loads the user from the database by using its username.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UserDetailsImpl(user);
    }
}
