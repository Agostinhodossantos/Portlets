package mail.newsletter.com.utils;

import java.io.*;

public class ContentFile {

    public static Boolean writeContent(String content, String fileName) {
        String fileContent = fileName+".txt";
        File file = new File(fileContent);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }

        try (
                FileWriter f = new FileWriter(file, true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);
        ) {
            p.println(content);
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }
        return true;
    }
}
