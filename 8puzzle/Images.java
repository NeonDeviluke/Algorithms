import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Images {
    public static void main(String[] args) {
        try {
            Class.forName("com.sql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/neondeviluke",
                                                         "root", "#Narutoshipp98");
            String q = "insert into images(pic) values(?)";

            PreparedStatement pst = con.prepareStatement(q);

            FileInputStream fi = new FileInputStream("D:\59110.png");

            pst.setBinaryStream(1, fi, fi.available());
            pst.executeUpdate();
            System.out.println("done.....");

        }
        catch (Exception e) {
            System.out.println("error.....");
        }
    }
}