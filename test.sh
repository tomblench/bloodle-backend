curl -XPUT 'http://localhost:8080/demo/event?name=Curry' 
curl -XPUT 'http://localhost:8080/demo/choice?title=Mr%20Jonnys&eventId=1'
curl -XPUT 'http://localhost:8080/demo/choice?title=Pingus%20Waffle%20Hut&eventId=1'
curl -XPUT 'http://localhost:8080/demo/event?name=Drinks'
curl -XPUT 'http://localhost:8080/demo/choice?title=Kings%20Head&eventId=4'
curl -XPUT 'http://localhost:8080/demo/choice?title=Shakespeare&eventId=4'
curl -XPUT 'http://localhost:8080/demo/user?name=Tom%20Blench&email=tom@blench.org'
curl -XPUT 'http://localhost:8080/demo/user?name=Psyduck&email=psy@blench.org'
curl -XPUT 'http://localhost:8080/demo/user?name=Pingu&email=pingu@blench.org'
curl -XPUT 'http://localhost:8080/demo/vote?choiceId=2&userId=7&comments=Great'
curl -XPUT 'http://localhost:8080/demo/vote?choiceId=3&userId=8&comments=OK'
curl -XPUT 'http://localhost:8080/demo/vote?choiceId=3&userId=9&comments=Mwap'
curl -XPUT 'http://localhost:8080/demo/vote?choiceId=5&userId=9&comments=Mwap'

echo

curl http://localhost:8080/demo/list
