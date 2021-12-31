package recipes.presentation.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import recipes.business.service.UserService;
import recipes.presentation.dto.UserDto;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/register")
public class RegisterController {

    private final UserService userService;

    @PostMapping
    @ResponseBody
    void newUser(@Valid @RequestBody UserDto newUser) {
        userService.register(newUser);
    }
}
