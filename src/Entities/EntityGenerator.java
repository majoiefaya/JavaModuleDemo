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

public class EntityGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter entity name:");
        String entityName = scanner.nextLine();
        System.out.println("Enter package name:");
        String packageName = scanner.nextLine();
        System.out.println("Enter output directory path (relative or absolute):");
        String outputDir = scanner.nextLine();
        System.out.println("Enter number of attributes:");
        int numAttributes = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        List<Attribute> attributes = new ArrayList<>();
        for (int i = 1; i <= numAttributes; i++) {
            System.out.println("Enter attribute " + i + " name:");
            String attributeName = scanner.nextLine();
            System.out.println("Enter attribute " + i + " type:");
            String attributeType = scanner.nextLine();
            attributes.add(new Attribute(attributeName, attributeType));
        }

        scanner.close();

        String className = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);
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
            writer.write("public class " + className + " {\n\n");
            for (Attribute attribute : attributes) {
                writer.write("    private " + attribute.getType() + " " + attribute.getName() + ";\n");
            }
            writer.write("\n");
            for (Attribute attribute : attributes) {
                writer.write("    public " + attribute.getType() + " get" + capitalize(attribute.getName()) + "() {\n");
                writer.write("        return " + attribute.getName() + ";\n");
                writer.write("    }\n\n");
                writer.write("    public void set" + capitalize(attribute.getName()) + "(" + attribute.getType() + " " + attribute.getName() + ") {\n");
                writer.write("        this." + attribute.getName() + " = " + attribute.getName() + ";\n");
                writer.write("    }\n\n");
            }
            writer.write("}\n");
            writer.close();
            System.out.println("Entity " + className + " created successfully at " + filePath);
        } catch (IOException e) {
            System.err.println("Error creating entity: " + e.getMessage());
        }
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static class Attribute {
        private String name;
        private String type;

        public Attribute(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }
}
