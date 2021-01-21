package pl.dsobotqa.github_api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserService {
    private final GitHubClient gitHubClient;
    private final UserStatisticsRepository userStatisticsRepository;
    private final UserCalculator userCalculator = new UserCalculator();

    @Transactional
    public Optional<UserDTO> getuserDetails(String login) {
        GitHubUserDTO user = gitHubClient.getUserDetails(login);
        if (user == null) {
            return Optional.empty();
        }

        userStatisticsRepository.increaseRequestCount(login);
        int calculation = userCalculator.calculate(user.getFollowers(), user.getPublicRepos());

        return Optional.of(
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
