package bowling.scorecard

import grails.testing.mixin.integration.Integration
import grails.testing.web.controllers.ControllerUnitTest
import grails.plugins.rest.client.RestBuilder
import spock.lang.Specification

@Integration
class MainControllerSpec extends Specification implements ControllerUnitTest<MainController> {

	void "test basic function"() {
		given:
		ScoreCard card = new ScoreCard(name: "TST")
		RestBuilder rest = new RestBuilder()

		when:"fetch index"
		def resp = rest.get("http://localhost:${serverPort}/main/index")

		then:
		resp.status == 200
	}
}
