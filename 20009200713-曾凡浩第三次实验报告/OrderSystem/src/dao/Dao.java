package dao;

import model.*;

import java.util.List;
import java.util.Scanner;

public interface Dao {
    Scanner input=new Scanner(System.in);
    //修改数据的接口
    public void update(String sql);

    public Flights find_flight(String sql);

    public Hotels find_hotel(String sql);

    public Bus find_bus(String sql);
    public Customers find_customer(String sql);
    public Reservations find_reservation(String sql);
    public String orderType();

    public void showall(String s);


    public String update_flight();
    public String update_bus();

    public String update_hotel();

    public String update_customer();

    public void query();

    public boolean find_name(String sql);


    public void search_route();

    public void update_insert();

    public void cheek_route();

    public List<String> cheek_route_search(List<String>list, String place,int time);

}
