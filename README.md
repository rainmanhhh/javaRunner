# javaRunner
a simple tool to create startup script for java app

## runtime
java8+

## install
download jar file from release page

## usage
- `java -jar javaRunner-*.jar <target> [libDir=lib] > <script>`  
  args:
  - target: the main class or jar file of your app
  - libDir: the directory includes jars and classes you wanna add to classpath
  - script: the output startup script file of your app
  
- use the output script file to start your java app

examples:
- ```shell script
  java -jar javaRunner-*.jar my-app.jar > start.sh
  ```
- ```shell script
  java -jar javaRunner-*.jar MyApp.class my-app-libs > start.bat
  ```
  
## notice
- all classes and sub directories in `<libDir>` will be added to classpath
- the tool will try to read java options from file `<target>.options`  
  options file encoding should be utf-8. example: 
  ```
  # max heap size
  -Xmx256m
  # min heap size
  -Xmx128m
  ```
