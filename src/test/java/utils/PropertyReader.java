package utils;

import java.io.FileReader;
import java.util.Properties;

public class PropertyReader {

    static Properties property = new Properties();

    public static void initProperty(){
        try {
            FileReader fileReader = new FileReader("src/test/resources/configs/config.properties");
            property.load(fileReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        return property.getProperty(key);
    }
}
