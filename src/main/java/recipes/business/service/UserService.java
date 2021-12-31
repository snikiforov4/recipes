package recipes.business.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.persistence.entity.User;
import recipes.persistence.repository.UserRepository;
import recipes.presentation.dto.UserDto;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public void register(UserDto userDto) throws UserAlreadyExistsException {
        String email = userDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException();
        }
        String encodedPassword = encoder.encode(userDto.getPassword().trim());
        userRepository.save(User.fromEmailAndPassword(email, encodedPassword));
    }
}
