package pl.dsobotqa.github_api.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface UserStatisticsRepository extends Repository<UserStatistics, String> {
    @Modifying
    @Query(value = "INSERT INTO user_statistics(login, request_count) " +
                   "VALUES (:login, 1) " +
                   "ON CONFLICT (login) DO " +
                   "UPDATE SET request_count = (user_statistics.request_count + 1)",
           nativeQuery = true)
    void increaseRequestCount(@Param("login") String login);

    @Query(value = "SELECT request_count FROM user_statistics WHERE login = :login",
           nativeQuery = true)
    Integer getRequestCountByLogin(@Param("login") String login);
}
