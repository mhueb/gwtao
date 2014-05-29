rem @ECHO OFF
set COPYRIGHT="Copyright 2012 GWTAO"
set LICENSE="APACHE"

set ROOT=%~dp0..
set GEN="%~dp0\genheader.pl"

FOR /f %%a in ('dir /B /A:D %ROOT%\gwtao-*') do %GEN% -M %ROOT%\%%a\src -R -C %COPYRIGHT% -L %LICENSE% 

set ROOT=
set GEN=
set COPYRIGHT=
set LICENSE=

pause