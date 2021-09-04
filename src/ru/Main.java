package ru;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalTime;

public class Main {

    static final String ENTER  = "\n";
    static StringBuilder report = new StringBuilder("Отчет " + LocalTime.now() + ";" + ENTER);
    public static void main(String[] args) throws SQLException {
//        java.sql.DriverManager.drivers().forEach(System.out::println);
        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://192.168.56.200:5432/test","scott","tiger")) {
//            System.out.println(connection.getMetaData().getDatabaseProductName());

            try(Statement sD = connection.createStatement();
                PreparedStatement sE = connection.prepareStatement("select * from scott.emp where deptno = ?")

            ){

                try(ResultSet rD = sD.executeQuery("select dname, deptno from scott.dept ORDER BY dname")){

                    while (rD.next()){
                        report.append(rD.getInt("deptno") + " ; " + rD.getString(  "dname") + ENTER);
                        sE.setInt(1, rD.getInt("deptno"));
                        try( ResultSet rE = sE.executeQuery() ){
                            while (rE.next()){
//                                System.out.println("");
                            for (int i = 1; i <= rE.getMetaData().getColumnCount() ; i++) {
                                report.append(rE.getObject(rE.getMetaData().getColumnName(i)) + " ; ");
                                }
                                report.append(ENTER);
                            }
//                            System.out.println(rE);

                        }catch (Exception e){
                            throw e;
                        }
                    }

                }catch (Exception e){
                    throw e;
                }


            }catch (Exception e){
                throw e;
            }
            System.out.println(report.toString());
            Files.newOutputStream(Paths.get("c://1/report.csv")).write(report.toString().getBytes("UTF-8"));
        }catch (Exception ex){
            System.err.println(ex);
        }
    }
}
