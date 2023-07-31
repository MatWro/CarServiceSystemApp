package CarServiceSystemApp.services;

import CarServiceSystemApp.DTO.UserDTO;
import CarServiceSystemApp.Repo.UserRepository;
import CarServiceSystemApp.entities.User;

import CarServiceSystemApp.exceptions.UserLoginException;
import CarServiceSystemApp.exceptions.UserNotFoundException;
import CarServiceSystemApp.exceptions.UserRegistrationException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
        User newUser = new User();
        newUser.setName(userDTO.getName());
        newUser.setEmail(userDTO.getEmail());

        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashedPassword);

        User savedUser = userRepository.save(newUser);

        UserDTO registeredUserDTO = new UserDTO();
        registeredUserDTO.setId(savedUser.getId());
        registeredUserDTO.setName(savedUser.getName());
        registeredUserDTO.setEmail(savedUser.getEmail());
        registeredUserDTO.setPassword(savedUser.getPassword());

        return registeredUserDTO;
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
    public UserDTO getUserById(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        User user = userOptional.get();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

}