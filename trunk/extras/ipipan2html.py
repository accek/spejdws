#!/usr/bin/env python
# coding: utf-8

from optparse import OptionParser
import xmlrpclib
import os.path
import sys

def ipipan2html(ifile, ofile):
    service = \
        xmlrpclib.ServerProxy("http://spejdws.appspot.com/xmlrpc").SpejdService
    body = service.formatXmlAsHtml(ifile.read())
    ofile.write(
"""
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
body, table td, select {
  font-family: Arial Unicode MS, Arial, sans-serif;
  font-size: small;
}
body {
  color: black;
  margin: 0px;
  border: 0px;
  padding: 0px;
  background: #fff;
  direction: ltr;
}
a, a:visited, a:hover {
  color: #0000AA;
}
h1 {
  font-size: 2em;
  font-weight: bold;
  color: #777777;
  margin: 16px 16px 20px;
  padding: 0px;
}
.content {
  margin: 0px 16px 0px 16px;
}
.separator {
  width: 100%;
  height: 7px;
  margin: 16px 0 16px 0;
  background-color: rgb(145, 192, 239);
}
.footer {
  text-align: center;
}
</style>
<link rel="stylesheet" href="http://spejdws.appspot.com/css/ipipanxces.css">
</head><body>
<h1>Ładnie sformatowany tekst ;)</h1>
<div class="separator"></div>
<div class="content">
""")
    ofile.write(body.encode('utf-8'))
    ofile.write(
"""
</div><div class="separator"></div>
<div class="footer">
Formatter © <a href="http://www.mimuw.edu.pl/~accek/">Szymon Acedański</a>, 2009
</div>
</html>
""")

def main():
    usage = "usage: %prog [options] input.xml output.html"
    parser = OptionParser(usage=usage)
    parser.add_option("-n", "--no-browser",
            action="store_false", dest="browser", default=True,
            help="Do not open web browser when done")
    parser.add_option("-f", "--force",
            action="store_true", dest="force",
            help="Overwrite existsing output file")
    options, args = parser.parse_args()
    if len(args) != 2:
        parser.error("expected input and output file names")

    if args[0] == "-":
        ifile = sys.stdin
    else:
        ifile = open(args[0], "r")

    if os.path.exists(args[1]) and not options.force:
        print >>sys.stderr, "Error: output file exists and -f not specified"
        sys.exit(1)

    ofile = open(args[1], "w")
    ipipan2html(ifile, ofile)
    if options.browser:
        import webbrowser
        webbrowser.open('file://' + os.path.abspath(args[1]))

if __name__ == '__main__':
    main()
