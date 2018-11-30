package bowling.scorecard

class BootStrap {

    def init = { servletContext ->
		new ScoreCard(name: "AAA").save() 
    }
    def destroy = {
    }
}
