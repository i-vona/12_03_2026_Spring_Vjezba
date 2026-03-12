package hr.java.spring.boot.Example.service;

import hr.java.spring.boot.Example.domain.CustomUserDetails;
import hr.java.spring.boot.Example.domain.UserInfo;
import hr.java.spring.boot.Example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUsername(username);

        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new CustomUserDetails(userInfo);
    }
}