package ez;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class JavaRunner {
  private final StringBuilder cmdBuilder = new StringBuilder("java");

  private void append(String part) {
    if (part != null && !part.isEmpty()) cmdBuilder.append(' ').append(part);
  }

  private String readCP(String libDir) {
    File dir = new File(libDir);
    if (dir.isDirectory()) {
      LinkedList<String> resultList = new LinkedList<>();
      LinkedList<File> stack = new LinkedList<>(Arrays.asList(Objects.requireNonNull(dir.listFiles())));
      while (!stack.isEmpty()) {
        File file = stack.pollLast();
        if (file.isFile()) {
          if (file.getName().endsWith(".jar")) resultList.addFirst(file.getPath());
          // else not a jar, ignore
        } else {
          resultList.addFirst(file.getPath());
          stack.addAll(Arrays.asList(Objects.requireNonNull(file.listFiles())));
        }
      }
      return resultList.peek() == null ? "" : "-cp " + String.join(File.pathSeparator, resultList);
    } else return "";
  }

  private String readOptionsFromFile(String filePath) throws IOException {
    File f = new File(filePath);
    if (f.isFile()) {
      return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8).stream()
        .map(it -> {
          int pos = it.indexOf('#');
          return pos < 0 ? it : it.substring(0, pos);
        })
        .map(String::trim)
        .filter(it -> !it.isEmpty())
        .collect(Collectors.joining(" "));
    } else return "";
  }

  public void run(String target, String libDir) throws Exception {
    String CP = readCP(libDir);
    append(CP);
    String options = readOptionsFromFile(target + ".options");
    append(options);
    boolean isJar = target.endsWith(".jar");
    append(isJar ? "-jar " + target : target);
    System.out.println(cmdBuilder.toString());
  }

  public static void main(String[] args) throws Exception {
    if (args.length > 0) {
      String target = args[0];
      String libDir = args.length > 1 ? args[1] : "lib";
      new JavaRunner().run(target, libDir);
    } else throw new Exception("usage: java -jar javaRunner-*.jar <target> [libDir=lib] > <script>");
  }
}
