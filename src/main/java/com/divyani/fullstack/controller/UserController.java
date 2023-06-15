package com.divyani.fullstack.controller;


import com.divyani.fullstack.exception.UserNotFoundException;
import com.divyani.fullstack.model.Users;
import com.divyani.fullstack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private UserRepository userRepository ;

    @PostMapping("/users")
    public Users newUser(@RequestBody Users newUsers){
        return userRepository.save(newUsers) ;
    }

    @GetMapping("/userss")
    List<Users> getAllUsers(){
        return userRepository.findAll() ;
    }

    @GetMapping("/users/{id}")
    Users getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow( ()-> new UserNotFoundException(id));
    }

    @PutMapping("/users/{id}")
    Users updateUser(@RequestBody Users newUser ,@PathVariable Long id ) {
        return userRepository.findById(id)
                .map(users -> {
                    users.setUsername(newUser.getUsername());
                    users.setName(newUser.getName());
                    users.setEmail(newUser.getEmail());
                    return userRepository.save(users);
                }).orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    String deleteUser(@PathVariable Long id ){
        if (!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "user with id "+id+" has been deleted successfully. ";
    }
}
