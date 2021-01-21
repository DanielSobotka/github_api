package pl.dsobotqa.github_api.user;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.dsobotqa.github_api.GithubApiApplication;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GithubApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserStatisticsRepository userStatisticsRepository;

    @MockBean
    private GitHubClient gitHubClient;

    private final GitHubUserDTO gitHubUserDTO = GitHubUserDTO.builder()
                                                             .id(123)
                                                             .login("daniel")
                                                             .name("DanielS")
                                                             .type("User")
                                                             .avatarUrl("https://avatar_url.com/qwe")
                                                             .createdAt(LocalDateTime.parse("2021-01-03T10:15:30"))
                                                             .publicRepos(1)
                                                             .followers(2)
                                                             .build();

    @Test
    void returnedUserData() throws Exception {
        // given
        when(gitHubClient.getUserDetails(eq("daniel"))).thenReturn(gitHubUserDTO);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/daniel"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.login").value("daniel"))
                .andExpect(jsonPath("$.name").value("DanielS"))
                .andExpect(jsonPath("$.type").value("User"))
                .andExpect(jsonPath("$.avatarUrl").value("https://avatar_url.com/qwe"))
                .andExpect(jsonPath("$.createdAt").value("2021-01-03T10:15:30"))
                .andExpect(jsonPath("$.calculations").value(9));
    }

    @Test
    void userNotFound() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/users/unknown"));

        // then
        resultActions
                .andExpect(status().isNotFound());

        // and
        assertNull(userStatisticsRepository.getRequestCountByLogin("unknown"));
    }

    @Test
    void userCountIncreasedAfterEachRequest() throws Exception {
        // given
        when(gitHubClient.getUserDetails(eq("daniel"))).thenReturn(gitHubUserDTO);

        // when
        mockMvc.perform(get("/users/daniel"));

        // then
        assertEquals(1, (int) userStatisticsRepository.getRequestCountByLogin("daniel"));

        // when
        mockMvc.perform(get("/users/daniel"));

        // then
        assertEquals(2, (int) userStatisticsRepository.getRequestCountByLogin("daniel"));
    }
}
