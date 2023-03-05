package Entities;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.File;


public class ProjectGenerator {

    public static void main(String[] args) throws IOException {
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
			System.out.print("Enter project name: ");
			String projectName = scanner.nextLine();

			String rootPath = "./src/main/java/" + projectName;

			String modelPath = rootPath + "/Model";
			String viewPath = rootPath + "/View";
			String controllerPath = rootPath + "/Controller";
			String databasePath = rootPath + "/Database";


   try {
			Path root = Paths.get(rootPath);
			if (!Files.exists(root)) {
			    Files.createDirectories(root);
			}

			Path project = Paths.get(rootPath);
			if (!Files.exists(project)) {
			    Files.createDirectories(project);
			}

			Path model = Paths.get(modelPath);
			if (!Files.exists(model)) {
			    Files.createDirectories(model);
			}

			Path view = Paths.get(viewPath);
			if (!Files.exists(view)) {
			    Files.createDirectories(view);
			}

			Path controller = Paths.get(controllerPath);
			if (!Files.exists(controller)) {
			    Files.createDirectories(controller);
			}

			Path database = Paths.get(databasePath);
			if (!Files.exists(database)) {
			    Files.createDirectories(database);
			}
    
			        

			} catch (IOException e) {
			    System.err.println("Error creating project: " + e.getMessage());
			}
			String packageName = projectName;

			System.out.print("Enter driver name (mysql/postgresql): ");
			String driverName = scanner.nextLine().toLowerCase();

			String content = "";
			if (driverName.contains("mysql")) {
			    // Download and install MySQL driver
			    String driverUrl = "https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.27.zip";
			    String driverFileName = "mysql-connector-java-8.0.27.jar";
			    String driverPath = "./lib/" + driverFileName;

			    try {
			        URL website = new URL(driverUrl);
			        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			        FileOutputStream fos = new FileOutputStream(driverPath);
			        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			        System.out.println(driverFileName + " downloaded successfully.");
			    } catch (IOException e) {
			        e.printStackTrace();
			    }

			    // Generate content for database.java
			    String databaseFile = databasePath + "/Database.java";
			    BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile));
			    content += "package " + packageName +"Database"+ ".Database;\n\n" +
			            "import java.sql.Connection;\n" +
			            "import java.sql.DriverManager;\n\n" +
			            "public class Database {\n\n" +
			            "\tpublic static Connection getConnection() {\n" +
			            "\t\ttry {\n" +
			            "\t\t\tClass.forName(\"com.mysql.cj.jdbc.Driver\");\n" +
			            "\t\t\tConnection connection = DriverManager.getConnection(\"jdbc:mysql://localhost:3306/mydatabase\", \"root\", \"\");\n" +
			            "\t\t\treturn connection;\n" +
			            "\t\t} catch (Exception e) {\n" +
			            "\t\t\te.printStackTrace();\n" +
			            "\t\t\treturn null;\n" +
			            "\t\t}\n" +
			            "\t}\n" +
			            "}";
			    writer.write(content);
			    writer.close();
			    System.out.println("Project " + projectName + " created successfully at " + rootPath);
			} else if (driverName.contains("postgresql")) {
			    // Download and install PostgreSQL driver
			    String driverUrl = "https://jdbc.postgresql.org/download/postgresql-42.3.1.jar";
			    String driverFileName = "postgresql-42.3.1.jar";
			    String driverPath = "./lib/" + driverFileName;

			    try {
			        URL website = new URL(driverUrl);
			        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			        try (FileOutputStream fos = new FileOutputStream(driverPath)) {
						fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
					}
			        System.out.println(driverFileName + " downloaded successfully.");
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			    
			    
			    // Add driver to classpath
	            String projectPath = rootPath;
	            String driverPath2 = driverPath;

	            File projectDirectory = new File(projectPath);
	            File driverFile = new File(driverPath2);

	            if (projectDirectory.exists() && driverFile.exists()) {
	                // Add driver to classpath
	                String classpath = System.getProperty("java.class.path");
	                classpath += File.pathSeparator + driverFile.getAbsolutePath();
	                System.setProperty("java.class.path", classpath);

	                System.out.println("Le driver a été ajouté au classpath.");
	            } else {
	                System.out.println("Le répertoire du projet ou le fichier driver n'existe pas.");
	            }

			    // Generate content for database.java
			    String databaseFile = databasePath + "/Database.java";
			    BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile));
			    content += "package " + packageName + ".Database;\n\n" +
			            "import java.sql.Connection;\n" +
			            "import java.sql.DriverManager;\n\n" +
			            "public class Database {\n\n" +
			            "\tpublic static Connection getConnection() {\n" +
			            "\t\ttry {\n" +
			            "\t\t\tClass.forName(\"org.postgresql.Driver\");\n" +
			            "\t\t\tConnection connection = DriverManager.getConnection(\"jdbc:postgresql://localhost:5432/mydatabase\", \"postgres\", \"\");\n" +
			            "\t\t\treturn connection;\n" +
			            "\t\t} catch (Exception e) {\n" +
			            "\t\t\te.printStackTrace();\n" +
			            "\t\t\treturn null;\n" +
			            "\t\t}\n" +
			            "\t}\n" +
			            "}";
			    writer.write(content);
			    writer.close();
			    System.out.println("Project " + projectName + " created successfully at " + rootPath);
			}
		}
        
    }
    
}