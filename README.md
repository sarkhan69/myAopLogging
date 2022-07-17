# myAopLogging
in front of you is a logging library that logs the execution of all methods inside:
- @RestController
- @Service
- @Repository

if you want to exclude some method from logging, then:
- just mark it with the annotation @NoLogging
- or use it for classes @Component 

in the directory src/test is an example of a compiled file for integration into projects on the 15th JAVA version

P.S. and don't forget about @ToString in your models, dto, entity etc...
