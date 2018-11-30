package bowling.scorecard

class MainController {

	ScorerService scorerService

	def index() {
		ScoreCard card = ScoreCard.list().first()
		respond result: card, maxScore: scorerService.getCurrentMaxScore(card)
	}

	def score() {
		log.info "score called. Params: ${params}"
		ScoreCard card = ScoreCard.list().first()
		// Check score
		if(!params.score)
			flash.message = "no score provided!"
		else if((params.score as Integer) > scorerService.getCurrentMaxScore(card))
			flash.message = "too high score!"
		else
			flash.message = scorerService.score(card, params.score as Integer)
		// Redirect to index
		redirect action: 'index', method: 'GET'
	}

	def reset() {
		log.info "reset called. Params: ${params}"
		ScoreCard card = ScoreCard.list().first()
		// Reset card
		flash.message = scorerService.reset(card)
		// Redirect to index
		redirect action: 'index', method: 'GET'
	}

	def calc() {
		log.info "calc called. Params: ${params}"
		ScoreCard card = ScoreCard.list().first()
		// recaclulate overflow
		flash.message = scorerService.calculateOverflow(card)
		// Redirect to index
		redirect action: 'index', method: 'GET'
	}
}
