package bowling.scorecard

import javax.security.auth.Subject.SecureSet

/**
 * Model class for holding all score data.
 */
class ScoreCard {

	/** The name of the scorer. */
	String name

	/** The list of frame results (always 10). */
	List<Frame> frames = [
		new Frame(),
		new Frame(),
		new Frame(),
		new Frame(),
		new Frame(),
		new Frame(),
		new Frame(),
		new Frame(),
		new Frame(),
		new Frame()
	]

	/** Flag, if the game if finished. */
	final Boolean finished

	/** Getter for calculation of the finished-Flag. */
	public Boolean getFinished() {
		frames.last().overflowScore != null
	}

	/** The total score. */
	final Integer score

	/** Getter for calculation of the score. */
	public Integer getScore() {
		frames.inject(null) { Integer result, item -> item.score ? ((result ?: 0) + item.score) : result }
	}

	static constraints = {
		name nullable: false, size: 1..3
		frames size: 10..10
		score nullable: true, min: 0, max: 300
	}

	String toString() {
		def collected = frames.collect { it.toString() }
		"name: {$name}, frames: ${collected}:[${score ?: ' '}]"
	}
}
