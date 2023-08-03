package CarServiceSystemApp.services;

import CarServiceSystemApp.DTO.UserDTO;
import CarServiceSystemApp.Repo.UserRepository;
import CarServiceSystemApp.entities.User;

import CarServiceSystemApp.exceptions.UserLoginException;
import CarServiceSystemApp.exceptions.UserNotFoundException;
import CarServiceSystemApp.exceptions.UserRegistrationException;
import CarServiceSystemApp.mappers.UserMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO registerUser(UserDTO userDTO) throws UserRegistrationException {

        List<User> existingUsers = userRepository.findByEmail(userDTO.getEmail());
        if(!existingUsers.isEmpty()) {
            throw new UserRegistrationException("User with this email already exists.");
        }
       User user = UserMapper.convertToUser(userDTO);

        User savedUser = userRepository.save(user);

        return UserMapper.convertToUserDTO(savedUser);
    }
    public UserDTO loginUser(String email, String password) throws UserLoginException {
        List<User> users = userRepository.findByEmail(email);

        if (users.isEmpty()) {
            throw new UserLoginException("Invalid email or password.");
        }
        User user = users.get(0);
        if (passwordMatches(password, user.getPassword())) {

            UserDTO loggedInUserDTO = new UserDTO();
            loggedInUserDTO.setId(user.getId());
            loggedInUserDTO.setName(user.getName());
            loggedInUserDTO.setEmail(user.getEmail());

                return loggedInUserDTO;

        }

        throw new UserLoginException("Invalid email or password.");
    }


    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
    public UserDTO getUserDTOById(Long userId) throws UserNotFoundException {

        User user = getUserById(userId);

        return UserMapper.convertToUserDTO(user);
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found."));
    }

}