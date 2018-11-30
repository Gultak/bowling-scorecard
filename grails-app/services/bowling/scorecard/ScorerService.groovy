package bowling.scorecard

import grails.gorm.transactions.Transactional

class ScorerService {

	/**
	 * Gets the current maximum score to achieve (i.e. number of pins still standing)
	 * @param card the ScoreCard to evaluate
	 * @return the current maximum score
	 */
	@Transactional(readOnly=true)
	int getCurrentMaxScore(ScoreCard card) {
		log.info "getting current max. possible score of ScoreCard ${card}"
		// Find the first non-completed frame
		Frame frame = getCurrentFrame(card)
		// return minimum score for that frame
		int result = getMaxScore(frame)
		log.info "...found: ${result}"
		result
	}

	/**
	 * Gets the current frame (i.e. the frame where the next bowl is counting)
	 * @param card the ScoreCard to evaluate
	 * @return the current frame
	 */
	Frame getCurrentFrame(ScoreCard card) {
		// Find first frame with no second score and no strike
		Frame result = card.frames.find({
			it.secondScore == null && (it.firstScore == null || it.firstScore < 10)
		})
		// If no valid frame found, check last frame
		result ?: card.frames.last()
	}

	/**
	 * Gets the minimum score to achieve for a given frame (i.e. number of pins already cleared)
	 * @param frame the Frame to evaluate
	 * @return the minimum score for the frame
	 */
	int getMaxScore(Frame frame) {
		// No frame ==> 0
		if(!frame)
			return 0
		// No first score ==> 10
		if(frame.firstScore == null)
			return 10
		// first score < 10 ==> 10 - first score
		if(frame.firstScore < 10)
			return 10 - frame.firstScore
		// No second score ==> 10
		if(frame.secondScore == null)
			return 10
		// second score < 10 ==> 1ß - second score
		if(frame.secondScore < 10)
			return 10 - frame.secondScore
		// Fallback ==> 10
		return 10
	}

	/**
	 * Realizes a score
	 * @param card the ScoreCard to realize the score into
	 * @param score the (within a frame cumulative) number of pins 
	 * @return an optional error message
	 */
	@Transactional
	def score(ScoreCard card, int score) {
		log.info "score called. card: ${card}, score: ${score}"
		// Find the first non-completed frame
		Frame frame = getCurrentFrame(card)
		// Validate
		if(card.finished)
			"Game already finished!"
		if(!frame)
			"No valid frame found!"
		int maxScore = getMaxScore(frame)
		if(score > maxScore)
			"Too high Score ($score --> $maxScore)!"
		// Count score
		if(frame.firstScore == null)
			frame.firstScore = score
		else if(frame.secondScore == null)
			frame.secondScore = score
		else
			frame.overflowScore = score
		// Calculate overflow
		calculateOverflow(card)
	}

	/**
	 * Calculates the overflow score for the score card
	 * @param card the ScoreCard to calculate
	 */
	@Transactional
	void calculateOverflow(ScoreCard card) {
		Frame last = card.frames.last()
		// Remember the "next" two scores
		int nextScore1 = card.frames.last().firstScore ?: 0
		int nextScore2 = card.frames.last().secondScore ?: 0
		// Iterate over the frames backwards
		card.frames.reverse().each({
			if(it == last) {
				if(it.firstScore != null && it.firstScore != 10 && it.secondScore != null && it.secondScore != 10)
					it.overflowScore = 0
			}
			else {
				// Calculate overflow
				if(it.firstScore == 10)
					it.overflowScore = nextScore1 + nextScore2
				if(it.secondScore != null && (it.secondScore + it.firstScore) == 10)
					it.overflowScore = nextScore1
				// Revolve next scores
				if(it.secondScore != null) {
					nextScore2 = nextScore1
					nextScore1 = it.secondScore
				}
				nextScore2 = nextScore1
				nextScore1 = it.firstScore ?: 0
			}
		})
	}

	/**
	 * Resets the game.
	 * @param card the ScoreCard to reset
	 */
	@Transactional
	void reset(ScoreCard card) {
		log.info "reset called. card: ${card}, score: ${score}"
		card.frames.each({
			it.firstScore = null
			it.secondScore = null
			it.overflowScore = null
		})
	}
}
