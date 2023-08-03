package CarServiceSystemApp.mappers;


import CarServiceSystemApp.DTO.UserDTO;
import CarServiceSystemApp.entities.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class UserMapper {


    public static UserDTO convertToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }
    public static User convertToUser(UserDTO userDTO){
        User user = new User();
        user.setId(user.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);


        return user;
    }

}
