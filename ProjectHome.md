This is a web service for [Spejd](http://nlp.ipipan.waw.pl/Spejd/) -- a tool for partial parsing and rule-based morphosyntactic disambiguation of Polish. It also may also use [TaKIPI WS](http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/) for tokenization, segmentation, lemmatization and morphologic analysis (using [Morfeusz](http://nlp.ipipan.waw.pl/~wolinski/morfeusz/)), if requested.


See [the article](http://spejdws.googlecode.com/svn/trunk/war/static/spejdws2009a.pdf) for more information.


## Web Service Access ##

To access web service using XMLRPC, use
http://chopin.ipipan.waw.pl:8081/spejdws/xmlrpc.

For SOAP, the WSDL file can be obtained from
http://chopin.ipipan.waw.pl:8081/spejdws/services/SpejdService?wsdl

Functional GWT-based web interface to the Spejd Web Service is deployed at http://spejdws.appspot.com.