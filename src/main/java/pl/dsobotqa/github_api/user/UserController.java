package pl.dsobotqa.github_api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @GetMapping(value = "/users/{login}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUsers(@PathVariable String login) {
        return ResponseEntity.of(
                userService.getuserDetails(login)
        );
    }
}
