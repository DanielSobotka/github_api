package pl.dsobotqa.github_api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class GitHubClient {
    private final static String GIT_HUB_URL = "https://api.github.com/users/";
    private final RestTemplate restTemplate;

    public GitHubUserDTO getUserDetails(String login) {
        try {
            return restTemplate.getForObject(GIT_HUB_URL + login, GitHubUserDTO.class);
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("User not found: " + login);
            return null;
        }
    }
}
