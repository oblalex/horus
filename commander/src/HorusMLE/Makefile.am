all-local:
	qmake @srcdir@/HorusMLE.pro -o QMakefile; \
	make -f QMakefile;

dist-hook:
	mkdir -p "l10n"; \
	find "@srcdir@/l10n" -name "*.qm" -exec cp --t=l10n {} \;

install-exec-hook:
	cp --t "$(prefix)/@PACKAGE@/" `grep "^TARGET" @srcdir@/HorusMLE.pro | awk '{print $$3;}'`

install-data-hook: dist-hook install-data-my

install-data-my:	
	mkdir -p "$(prefix)/@PACKAGE@/l10n"; \
	cp -r "l10n" "$(prefix)/@PACKAGE@/"

clean-local:
	make clean -f QMakefile