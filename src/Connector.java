import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class Connector {
    Connection connection;
    Statement st;
    public Connector(){
        try{
            Properties prop = new Properties();
            InputStream str = getClass().getClassLoader().getResourceAsStream("OOPS.properties");
            prop.load(str);
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            connection = DriverManager.getConnection(url,user,password);
            st = connection.createStatement();
        }catch(Exception exc){
            exc.printStackTrace();
        }
    } 
}
