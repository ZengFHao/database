package dao;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String passwd;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    static {
        ResourceBundle rb=ResourceBundle.getBundle("database");
        driver=rb.getString("driver");
        url=rb.getString("url");
        username=rb.getString("user");
        passwd=rb.getString("passwd");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 获取连接
     * @return
     */
    public Connection get_connection(){
        try {
            conn= DriverManager.getConnection(url,username,passwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * 关闭连接
     */
    public void closeAll(){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
                //System.out.println("关闭成功");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean doPs(String sql, List<Objects> params){
        this.get_connection();
        boolean flag=true;
        try {
            ps=conn.prepareStatement(sql);
            if(params!=null){
                for(int i=0;i<params.size();i++){
                    ps.setObject(i+1,params.get(i));
                }
            }
            /**
             * 在JDBC中长用的增删改查的方法有execute(),executeQuery(),executeUpdate()
             * execute()能实现增删改查
             * 为什么采用execute(),而不是采用其他的两个呢
             * 是因为发现在封装查询功能的时候发现，其实也需要动态绑定和进行数据库的连接
             * 所以使用execute()，可以在查询中直接使用
             *
             */
            flag=ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    public ResultSet query(String sql,List<Objects> params){

        this.doPs(sql,params);
        try {
            rs=ps.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }
}
