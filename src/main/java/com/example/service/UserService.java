package com.example.service;

import com.example.domain.entity.User;
import com.example.domain.request.UserRequest;
import com.example.domain.response.UserResponse;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(UserRequest userRequest) {
        User user = new User(
                userRequest.getUsername(),
                passwordEncoder.encode(userRequest.getPassword()),
                userRequest.getRole()
        );
        userRepository.save(user);
    }

/*   private void validateDuplicateUser(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }*/

    /**
     * 로그인용 메소드
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return UserResponse.of(user);
    }
}