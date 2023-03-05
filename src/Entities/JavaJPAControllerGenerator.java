package Entities;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaJPAControllerGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter controller name:");
        String controllerName = scanner.nextLine();
        System.out.println("Enter package name:");
        String packageName = scanner.nextLine();
        System.out.println("Enter output directory path (relative or absolute):");
        String outputDir = scanner.nextLine();
        System.out.println("Enter number of methods:");
        int numMethods = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        List<Method> methods = new ArrayList<>();
        for (int i = 1; i <= numMethods; i++) {
            System.out.println("Enter method " + i + " name:");
            String methodName = scanner.nextLine();
            System.out.println("Enter method " + i + " request mapping:");
            String requestMapping = scanner.nextLine();
            System.out.println("Enter method " + i + " return type:");
            String returnType = scanner.nextLine();
            System.out.println("Enter method " + i + " parameters (comma-separated):");
            String[] parameters = scanner.nextLine().split(",");
            List<String> parameterList = new ArrayList<>();
            for (String parameter : parameters) {
                parameterList.add(parameter.trim());
            }
            methods.add(new Method(methodName, requestMapping, returnType, parameterList));
        }

        scanner.close();

        String className = controllerName.substring(0, 1).toUpperCase() + controllerName.substring(1) + "Controller";
        String fileName = className + ".java";
        String packageDir = packageName.replace(".", "/");
        String filePath = outputDir + "/" + packageDir + "/" + fileName;

        try {
            Path packagePath = Paths.get(outputDir, packageDir);
            if (!Files.exists(packagePath)) {
                Files.createDirectories(packagePath);
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("package " + packageName + ";\n\n");
            writer.write("import org.springframework.stereotype.Controller;\n");
            writer.write("import org.springframework.web.bind.annotation.*;\n\n");
            writer.write("@Controller\n");
            writer.write("@RequestMapping(\"/" + controllerName.toLowerCase() + "\")\n");
            writer.write("public class " + className + " {\n\n");
            for (Method method : methods) {
                writer.write("    @RequestMapping(\"" + method.getRequestMapping() + "\")\n");
                writer.write("    public " + method.getReturnType() + " " + method.getName() + "(");
                List<String> parameters = method.getParameters();
                for (int i = 0; i < parameters.size(); i++) {
                    String parameter = parameters.get(i);
                    writer.write(parameter + " " + "param" + i);
                    if (i != parameters.size() - 1) {
                        writer.write(", ");
                    }
                }
                writer.write(") {\n");
                writer.write("        // TODO: Add implementation\n");
                writer.write("    }\n\n");
            }
            writer.write("}\n");
            writer.close();
            System.out.println("Controller " + className + " created successfully at " + filePath);
        } catch (IOException e) {
            System.err.println("Error creating controller: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
    } 
    
    private static class Method {
        private String name;
        private String requestMapping;
        private String returnType;
        private List<String> parameters;

        public Method(String name, String requestMapping, String returnType, List<String> parameters) {
            this.name = name;
            this.requestMapping = requestMapping;
            this.returnType = returnType;
            this.parameters = parameters;
        }

        public String getName() {
            return name;
        }

        public String getRequestMapping() {
            return requestMapping;
        }

        public String getReturnType() {
            return returnType;
        }

        public List<String> getParameters() {
            return parameters;
        }
    }
}
        