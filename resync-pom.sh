#!/usr/bin/bash

# Só atualiza o pom.yml e reescreve ele em XML.
# Como o VSCode só olha para o xml para resolver imports, isso previne erros do linter,
# como por exemplo "não é possível resolver o import xyz".
#
# O Maven só lê o pom.yml!
mvn io.takari.polyglot:polyglot-translate-plugin:translate -Dinput=pom.yml -Doutput=pom.xml
sed -i -r 's/\$[0-9]+>/>/g' ./pom.xml