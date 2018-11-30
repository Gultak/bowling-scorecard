package bowling.scorecard

/**
 * Model class for holding the data of one frame.
 */
class Frame {

	/** The score of the first (and possibly only) bowl. */
	Integer firstScore

	/** The score of the optional second bowl. */
	Integer secondScore

	/** The overflow score from consecutive frames. */
	Integer overflowScore

	/** The total score of the frame. */
	final Integer score

	/** getter for calculation of the score. */
	public Integer getScore() {
		// if first score is null, then empty, else first score + second score + overflow
		return firstScore == null ? null : (firstScore + (secondScore ?: 0) + (overflowScore ?: 0))
	}

	static constraints = {
		firstScore nullable: true, min: 0, max: 10
		secondScore nullable: true, min: 0, max: 10
		overflowScore nullable: true, min: 0, max: 20
		score nullable: true, min: 0, max: 30
	}
	
	String toString() {
		// first score (either empty or single digit number)
		def firstString = (firstScore == null || firstScore == 10 ? " " : firstScore)
		// second score (either "X" or empty or "/" or single digit number)
		def secondString = (firstScore == 10 ? "X" : (secondScore == null ? " " : secondScore == 10 ? "/" : secondScore))
		// total frame score (either empty or number)
		def totalString = (score == null ? " " : score)
		"[$firstString][$secondString]:[$totalString]"
	}
}
