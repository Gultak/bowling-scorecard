package bowling.scorecard

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ScorerServiceSpec extends Specification implements ServiceUnitTest<ScorerService>, DataTest{

	void setupSpec() {
		mockDomain Frame
		mockDomain ScoreCard
	}
	
	void "test demo game"() {
		given:
		ScoreCard card = new ScoreCard(name: "TST")
		ScorerService scorerService = new ScorerService()

		when: "play demo game"
		scorerService.score(card, 10)
		scorerService.score(card, 7)
		scorerService.score(card, 3)
		scorerService.score(card, 9)
		scorerService.score(card, 0)
		scorerService.score(card, 10)
		scorerService.score(card, 0)
		scorerService.score(card, 8)
		scorerService.score(card, 8)
		scorerService.score(card, 2)
		scorerService.score(card, 0)
		scorerService.score(card, 6)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 8)
		scorerService.score(card, 1)

		then: "game is finished"
		card.finished

		and: "score is 167"
		card.score == 167
	}

	void "test perfect game"() {
		given:
		ScoreCard card = new ScoreCard(name: "TST")
		ScorerService scorerService = new ScorerService()

		when: "play perfect game"
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)
		scorerService.score(card, 10)

		then: "game is finished"
		card.finished

		and: "score is 300"
		card.score == 300
	}
}
