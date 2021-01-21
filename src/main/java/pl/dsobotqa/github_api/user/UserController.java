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
    private final GitHubClient gitHubClient;
    private final UserCalculator userCalculator = new UserCalculator();

    @GetMapping(value = "/users/{login}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUsers(@PathVariable String login) {
        GitHubUserDTO user = gitHubClient.getUserDetails(login);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        int calculation = userCalculator.calculate(user.getFollowers(), user.getPublicRepos());

        return ResponseEntity.ok(
                UserDTO.builder()
                       .id(user.getId())
                       .login(user.getLogin())
                       .name(user.getName())
                       .type(user.getType())
                       .avatarUrl(user.getAvatarUrl())
                       .createdAt(user.getCreatedAt())
                       .calculations(calculation)
                       .build()
        );
    }
}
