# javaRunner
a simple tool to create startup script for java app

## usage
`java -jar javaRunner-*.jar <target> [libDir=lib] > <output script file>`
then use the output script file to start your java app

examples:
- `java -jar javaRunner-*.jar my-app.jar > start.sh`
- `java -jar javaRunner-*.jar MyApp.class my-app-libs > start.bat`
  
## notice
- all classes and sub directories in `<libDir>` will be add to classpath
- the tool will try to read java options from file `<target>.options`
  options file encoding should be utf-8. example: 
  ```
  # max heap size
  -Xmx256m
  # min heap size
  -Xmx128m
  ```
