set PROJECT="GWTAO"
set COPYRIGHT="Copyright (C) 2012 Matthias Huebner"
set LICENSE="LGPL"

set ROOT=%~dp0\..\
set GEN="%~dp0\genheader.pl"

pushd

cd "%ROOT%\gwtao-emul\src"
%GEN% -R -C %COPYRIGHT% -P %PROJECT% -L %LICENSE%

cd "%ROOT%\gwtao-common\src"
%GEN% -R -C %COPYRIGHT% -P %PROJECT% -L %LICENSE%

cd "%ROOT%\gwtao-ui\src"
%GEN% -R -C %COPYRIGHT% -P %PROJECT% -L %LICENSE%

cd "%ROOT%\gwtao-digist\src"
%GEN% -R -C %COPYRIGHT% -P %PROJECT% -L %LICENSE%

cd "%ROOT%\gwtao-showcase\src"
%GEN% -R -C %COPYRIGHT% -P %PROJECT% -L %LICENSE%

popd

set ROOT=
set GEN=
set PROJECT=
set COPYRIGHT=
set LICENSE=

pause