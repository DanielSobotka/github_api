package pl.dsobotqa.github_api.user

import spock.lang.Specification
import spock.lang.Subject

class UserCalculatorSpec extends Specification {
    @Subject
    UserCalculator userCalculator = new UserCalculator()

    def 'test calculation'() {
        expect:
        userCalculator.calculate(followers, publicRepos) == expectedValue

        where:
        followers | publicRepos || expectedValue
        0         | 1           || 0
        1         | 1           || 18
        7         | 3           || 0
        4         | 12          || 14
    }
}
