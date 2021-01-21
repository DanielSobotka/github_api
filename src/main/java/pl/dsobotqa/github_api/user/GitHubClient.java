package pl.dsobotqa.github_api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GitHubClient {
    private final static String GIT_HUB_URL = "https://api.github.com/users/";
    private final RestTemplate restTemplate;

    public GitHubUserDTO getUserDetails(String login) {
        return restTemplate.getForEntity(GIT_HUB_URL + login, GitHubUserDTO.class)
                           .getBody();
    }
}
