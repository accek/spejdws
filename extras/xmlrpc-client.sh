#!/bin/bash

echo "After getting python prompt, use 'service' variable for"
echo "accessing the web service. Example:"
echo
echo "service.getVersion()"
echo

python -i -c "import xmlrpclib;
service = xmlrpclib.ServerProxy('http://spejdws.appspot.com/xmlrpc').SpejdService"
