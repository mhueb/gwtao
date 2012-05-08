#!/bin/bash
PROJECT="GWTAO - GWT Add-Ons to build RIAs"
COPYRIGHT="Copyright (C) 2012 Matthias Huebner"
LICENSE="LGPL"

for dir in "../emul" "../gwt2h" "../gwtaf"; do
	cd $dir
	perl -- ../etc/genheader.pl -R -C "$COPYRIGHT" -P "$PROJECT" -L "$LICENSE"
	find . -name "*.__bak" -exec rm {} ";"
	find . -name "*.__new" -exec rm {} ";"
done
