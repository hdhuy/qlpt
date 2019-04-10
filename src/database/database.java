package database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import Doituong.*;

public class database {

    String ten = "";
    String mk = "";
    String host = "";
    //String databasename;

    //Connection con=null;
    public Connection getKetnoi() {
        Connection newcon = null;
        laydulieuhardcode();
        try {
            String url = "jdbc:sqlserver://" + host + ":1433;databaseName=quanliphongtro";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            newcon = DriverManager.getConnection(url, ten, mk);
            System.out.println("ket noi thanh cong, database.database.getKetnoi()" + newcon.getMetaData());
        } catch (Exception e) {
            System.out.println("ket noi that bai, database.database.getKetnoi()");
        }
        return newcon;
    }

    public void laydulieuhardcode() {
        this.host = "localHost";
        this.ten = "admin";
        this.mk = "admin";
        //this.databasename = "quanliquanan";
    }

    public void laydulieuconfig() {
        try {
            Properties prop = new Properties();
            InputStream is = null;
            is = new FileInputStream("config.properties");
            prop.load(is);
            this.ten = prop.getProperty("remote.user");
            this.mk = prop.getProperty("remote.password");
            this.host = prop.getProperty("remote.host");
            //this.databasename = prop.getProperty("remote.databasename");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("không lấy được dữ liệu đăng nhập từ file text(database)");
        }
    }

    public ArrayList SelectAll(String tenbang) {
        ArrayList data = new ArrayList<>();
        try {
            Connection con = new database().getKetnoi();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM " + tenbang + ";");
            if (tenbang.equalsIgnoreCase("toanha")) {
                while (rs.next()) {
                    data.add(new Toanha(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            }
            if (tenbang.equalsIgnoreCase("phong")) {
                while (rs.next()) {
                    data.add(new Phong(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
                            rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
                            rs.getInt(11), rs.getDate(12), rs.getInt(13)));
                }
            }
            if (tenbang.equalsIgnoreCase("dichvu")) {
                while (rs.next()) {
                    data.add(new Dichvu(rs.getInt(1), rs.getInt(2),rs.getInt(3)));
                }
            }
            if (tenbang.equalsIgnoreCase("doanhthu")) {
                while (rs.next()) {
                    data.add(new Doanhthu(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5),
                            rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11),
                            rs.getInt(12), rs.getInt(13)));
                }
            }
            if (tenbang.equalsIgnoreCase("hoadon")) {
                while (rs.next()) {
                    data.add(new Hoadon(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                            rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),
                            rs.getInt(10), rs.getInt(11)));
                }
            }
            if (tenbang.equalsIgnoreCase("dangnhap")) {
                while (rs.next()) {
                    data.add(new Dangnhap(rs.getString(1), rs.getString(2)));
                }
            }
            if (tenbang.equalsIgnoreCase("nguoithue")) {
                while (rs.next()) {
                    data.add(new Nguoithue(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                            rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
                            rs.getString(9), rs.getFloat(10), rs.getString(11)));
                }
            }
        } catch (Exception e) {
            System.out.println("select sai" + e);
        }
        return data;
    }

    public void Insert() {
        
        
    }

}
