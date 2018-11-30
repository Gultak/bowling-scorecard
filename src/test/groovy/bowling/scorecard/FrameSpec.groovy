package bowling.scorecard

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class FrameSpec extends Specification implements DomainUnitTest<Frame> {

    void "test empty frame"() {
		when: 'an empty frame is instantiated'
			def frame = new Frame()
			
		then: 'frame is valid'
			frame.validate()
			
		and: 'score is null'
		    !frame.score
    }
	
	void "test frame with strike"() {
		when: 'a frame with a strike is instantiated'
			def frame = new Frame( firstScore: 10 )
			
		then: 'frame is valid'
			frame.validate()
			
		and: 'score is 10'
			frame.score == 10
			
		when: 'overflow of 10 is added'
			frame.overflowScore = 10
			
		then: 'score is 20'
			frame.score == 20
	}

	void "test frame with spare"() {
		when: 'a frame with a strike is instantiated'
			def frame = new Frame( firstScore: 3, secondScore: 7 )
			
		then: 'frame is valid'
			frame.validate()
			
		and: 'score is 10'
			frame.score == 10
			
		when: 'overflow of 10 is added'
			frame.overflowScore = 10
			
		then: 'score is 20'
			frame.score == 20
	}

}
