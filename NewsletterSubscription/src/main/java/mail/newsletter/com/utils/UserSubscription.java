package mail.newsletter.com.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//TODO: Replace with database
public class UserSubscription {

    static String emailFile = "emails.txt";

    public static List<String> getUsers() {
        List<String> emailList = new ArrayList<String>();
        try {
            File myObj = new File(emailFile);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String email = myReader.nextLine().trim();

                if (!email.isEmpty()) {
                    String list[] = email.trim()
                            .replace("\n","")
                            .split(",");

                    emailList.addAll(Arrays.asList(list));
                }

            }
            myReader.close();
            return emailList;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
           // e.printStackTrace();
            return emailList;
        }
    }

    public static Boolean subscribeUser(String email) {
        try (
             FileWriter f = new FileWriter(emailFile, true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b);
        ) {
            p.println(email+",");
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getHostLink(Boolean isLocal) {
        if (isLocal) {
            return "http://localhost:8080";
        } else {
            return "http://154.12.227.44:8080";
        }
    }

    public static void setUserCategories(String email, String category) {
        File file = new File(getCategoryPath(email));

        try (
                FileWriter f = new FileWriter(file, true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);
        ) {
            p.println(category+",");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public List<String> getUserCategories(String userEmail) {
        List<String> categoryList = new ArrayList<>();
        try {
            File myObj = new File(getCategoryPath(userEmail));
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String category = myReader.nextLine().trim();

                if (!category.isEmpty()) {
                    String list[] = category.trim()
                            .replace("\n","")
                            .split(",");

                    categoryList.addAll(Arrays.asList(list));
                }

            }
            myReader.close();
            return categoryList;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            // e.printStackTrace();
            return categoryList;
        }
    }


    private static String getCategoryPath(String email) {
        String path = "categories\\"+email+".txt";

        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        return path;
    }

    public void unsubscribeUser(String email) throws IOException {
        try {
            File file = new File(emailFile);
            File temp = File.createTempFile("file1", ".txt", file.getParentFile());
            System.out.println(file.getParentFile());
            String charset = "UTF-8";
            String delete = email+",";
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);

                line = line.replace(delete, "");
                writer.println(line);
            }
            System.out.println(temp.getAbsolutePath());
            reader.close();
            writer.close();
            file.delete();
            temp.renameTo(file);

        }
        catch (Exception e) {
            System.out.println("Something went Wrong");
        }
    }

}