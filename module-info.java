import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("path/to/json/file.json");
        MyObject myObject = objectMapper.readValue(file, MyObject.class);
        System.out.println(myObject);
    }
}