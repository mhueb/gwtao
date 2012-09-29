@ECHO OFF
set COPYRIGHT="Copyright 2012 Matthias Huebner"
set LICENSE="APACHE"

set ROOT=%~dp0\..\
set GEN="%~dp0\genheader.pl"

set modules=gwtao-emul;gwtao-common;gwtao-ui;gwtao-digist;gwtao-showcase

FOR %%a in (%modules%) do %GEN% -M %ROOT%\%%a\src -R -C %COPYRIGHT% -L %LICENSE% 

set ROOT=
set GEN=
set COPYRIGHT=
set LICENSE=

pause