package bowling.scorecard

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ScoreCardSpec extends Specification implements DomainUnitTest<ScoreCard> {

	void "test score card without name"() {
		when: 'an empty score card without a name is instantiated'
		def card = new ScoreCard()

		then: 'score card is not valid'
		!card.validate()

		and: 'score is null'
		!card.score
	}

	void "test score card with name"() {
		when: 'an empty score card with a name is instantiated'
		def card = new ScoreCard(name:"X")

		then: 'score card is valid'
		card.validate()

		and: 'score is null'
		!card.score
	}
}
