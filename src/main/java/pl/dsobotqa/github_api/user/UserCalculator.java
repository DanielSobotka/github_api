package pl.dsobotqa.github_api.user;

class UserCalculator {
    int calculate(int followers, int publicRepos) {
        if (followers == 0) {
            return 0;
        }

        return (6 / followers) * (2 + publicRepos);
    }
}
