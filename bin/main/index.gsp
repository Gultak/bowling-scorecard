<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
    <content tag="nav">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Application Status <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">Environment: ${grails.util.Environment.current.name}</a></li>
                <li><a href="#">App profile: ${grailsApplication.config.grails?.profile}</a></li>
                <li><a href="#">App version:
                    <g:meta name="info.app.version"/></a>
                </li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Grails version:
                    <g:meta name="info.app.grailsVersion"/></a>
                </li>
                <li><a href="#">Groovy version: ${GroovySystem.getVersion()}</a></li>
                <li><a href="#">JVM version: ${System.getProperty('java.version')}</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Artefacts <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#">Controllers: ${grailsApplication.controllerClasses.size()}</a></li>
                <li><a href="#">Domains: ${grailsApplication.domainClasses.size()}</a></li>
                <li><a href="#">Services: ${grailsApplication.serviceClasses.size()}</a></li>
                <li><a href="#">Tag Libraries: ${grailsApplication.tagLibClasses.size()}</a></li>
            </ul>
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Installed Plugins <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
                    <li><a href="#">${plugin.name} - ${plugin.version}</a></li>
                </g:each>
            </ul>
        </li>
    </content>

    <div id="content" role="main">
        <section class="row colset-2-its">
            <h1>Bowling Scorecard</h1>

            <h4>${flash.message}</h4>
            
              <table class="score-table">
              <colgroup>
                <col width=100/>
                <g:each var="index" in="${(1..21)}">
                  <col width=50/>
                </g:each>
                <col />
              </colgroup>
              <tr>
                <td />
                <g:each var="frame" in="${result.frames}" status="index">
                  <td colspan=${index == 9 ? 3 : 2} class="score-header">${index + 1}</td>
                </g:each>
                <td />
              </tr>
              <tr>
                <td />
                <g:each var="frame" in="${result.frames}" status="index">
                  <g:if test="${index == 9}">
                    <td class="second-score">${frame.firstScore == 10 ? 'X' : frame.firstScore}</td>
                    <td class="second-score">${frame.secondScore == 10 ? (frame.firstScore == 10 ? 'X' : '/') : frame.secondScore}</td>
                    <td class="second-score">${frame.overflowScore == 10 ? (frame.secondScore == 10 ? 'X' : '/') : (frame.overflowScore == 0 ? null : frame.overflowScore)}</td>
                  </g:if>
                  <g:else>
                    <td class="first-score">${frame.firstScore == 10 ? null : (frame.firstScore == 0 ? '-' : frame.firstScore)}</td>
                    <td class="second-score">${frame.firstScore == 10 ? 'X' : (frame.secondScore == null ? null : 
                      (frame.secondScore == 0 ? '-' : ((frame.firstScore + frame.secondScore) == 10 ? '/' : (frame.firstScore + frame.secondScore))))}</td>
                  </g:else>
                </g:each>
                <td />
              </tr>
              <tr>
                <td class="score-name" rowspan=2>${result.name}</td>
                <g:set var="total" value="0" />
                <g:each var="frame" in="${result.frames}" status="index">
                  <g:set var="total" value="${(total as Integer) + (frame.score?:0)}" />
                  <td colspan=${index == 9 ? 3 : 2} class="frame-score">${frame.score == null ? null : total}</td>
                </g:each>
                <td />
              </tr>
            </table>

            <form action="/main/score" method="post">
              <table class="score-table">
                <colgroup>
                  <col width=100/>
                  <g:each var="index" in="${(0..10)}">
                    <col width=50/>
                  </g:each>
                  <col />
                </colgroup>
                <tr>
                  <td class="score-label">Score:</td>
                  <g:each var="index" in="${(0..10)}">
                    <td class="score-item"><input type="radio" name="score" value="${index}" id="${'score'+index}"
                      <g:if test="${result.finished || index > maxScore}"> disabled</g:if>
                      ><label class="score-pins" for="${'score'+index}">${index}</label></td>
                  </g:each>
                  <td class="score-submit"><button type="submit" class="score"
                    <g:if test="${result.finished}"> disabled</g:if>
                    >Bowl!</button>
                    <g:if test="${result.finished}"> Game finished!</g:if>
                  </td>
                  <td />
                </tr>
              </table>
            </form>
            
            <div class="score-reset">
              <form action="/main/reset" method="post"><button type="submit" class="score" value="reset">Reset</button></form>
              <!-- form action="/main/calc" method="post"><button type="submit" class="score" value="reset">Calculate</button></form> -->
            </div>
            
        </section>
    </div>
    
</body>
</html>
