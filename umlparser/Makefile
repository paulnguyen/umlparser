all: clean reset

clean: 
	find . -name "*.class" -exec rm -rf {} \;
	find . -name .DS_Store -exec rm -rf {} \;
	rm -rf build
	rm -f *.log
	rm -rf .gradle


# Gradle: 		https://guides.gradle.org/creating-new-gradle-builds/
# CircleCI:		https://circleci.com/docs/2.0/language-java/

init:
	gradle init

compile:
	gradle build 

test:
	gradle test

build: compile
	gradle shadowJar

spotbugs:
	gradle spotbugsMain

codesmells:
	gradle smartsmells

reset:
	rm -rf output/test1/*
	rm -rf output/test2/*
	rm -rf output/test3/*
	rm -rf output/test4/*
	rm -rf output/test5/*
	rm -rf output/sequence/*

test1: build
	@echo $(PWD)
	java -cp build/libs/umlparser-all.jar umlparser.Main $(PWD) test1

test2: build
	@echo $(PWD)
	java -cp build/libs/umlparser-all.jar umlparser.Main $(PWD) test2

test3: build
	@echo $(PWD)
	java -cp build/libs/umlparser-all.jar umlparser.Main $(PWD) test3

test4: build
	@echo $(PWD)
	java -cp build/libs/umlparser-all.jar umlparser.Main $(PWD) test4

test5: build
	@echo $(PWD)
	java -cp build/libs/umlparser-all.jar umlparser.Main $(PWD) test5

sequence: build
	@echo $(PWD)
	java -cp build/libs/umlparser-all.jar umlparser.Main $(PWD) sequence

