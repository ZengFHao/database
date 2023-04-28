package Service;

import dao.BaseDao;
import dao.Dao;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static rescources.demo.*;

public class JDBCimpl extends BaseDao implements Dao {

    //修改操作接口的实现
    public void update(String sql){
        doPs(sql,null);
    }

    public Flights find_flight(String sql){
        Flights flights = new Flights();
        ResultSet rs = query(sql,null);
        try {
            while (rs.next()){
                flights.setFlightNum(rs.getString(1));
                flights.setPrice(rs.getInt(2));
                flights.setNumSeats(rs.getInt(3));
                flights.setNumAvail(rs.getInt(4));
                flights.setFromCity(rs.getString(5));
                flights.setArviCity(rs.getString(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll();
        }
        return flights;
    }

    public Hotels find_hotel(String sql){
        Hotels hotels = new Hotels();
        ResultSet rs = query(sql,null);
        try {
            hotels.setLocaltion(rs.getString(1));
            hotels.setPrice(rs.getInt(2));
            hotels.setNumRooms(rs.getInt(3));
            hotels.setNumAvail(rs.getInt(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll();
        }
        return hotels;
    }

    public Bus find_bus(String sql){
        Bus bus=new Bus();
        ResultSet rs=query(sql,null);
        try {
            bus.setLocation(rs.getString(1));
            bus.setPrice(rs.getInt(2));
            bus.setNumBus(rs.getInt(3));
            bus.setNumAvail(rs.getInt(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll();
        }
        return bus;
    }

    public Customers find_customer(String sql){
        Customers customer=new Customers();
        ResultSet rs= query(sql,null);
        try {
            customer.setCustName(rs.getString(1));
            customer.setCustID(rs.getInt(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll();
        }
        return customer;
    }

    public Reservations find_reservation(String sql){
        Reservations res=new Reservations();
        ResultSet rs=query(sql,null);
        try {
            res.setCustname(rs.getString(1));
            res.setResvType(rs.getInt(2));
            res.setResvKey(rs.getInt(3));
            res.setDetail(rs.getString(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll();
        }
        return res;
    }
    public String orderType(){
        System.out.println("请输入客户名称：");
        String name = input.next();
        String res=null;
        ResultSet rs=null;
        String sql;
        if(!find_name(name)){
            System.out.println("System no this customerName!!!");
            res="Order Unsuccess!!!";
        }else {
            order_nemu();
            int resvKey=-1;
            int choise=input.nextInt();
            if(choise==1){
                showall("flight");
                System.out.println("flight choise:");
                String category=input.next();
                sql="select numAvail from flights where flightNum='"+category+"'";
                rs=query(sql,null);
                try {
                    //rs=query(sql,null);
                    while(rs.next()){
                        if(rs.getInt(1)>0){
                            sql="update flights set numAvail="+(rs.getInt(1)-1)+" where flightNum='"+category+"'";
                            update(sql);
                            sql="select count(*) from reservations";
                            rs=query(sql,null);
                            while(rs.next()){
                                resvKey=rs.getInt(1);
                            }
                            if(resvKey!=-1){
                                sql="insert into reservations (resvKey,resvType,custName,order_detail )values" +
                                        "("+(resvKey+1)+","+choise+",'"+name+"','"+category+"')";
                                update(sql);
                                res="Order flight success!!!";
                            }

                        }else {
                            res ="Order flight Unsuccess!!! NO numAvail!!!";
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }finally {
                    closeAll();
                }

            } else if (choise==2) {
                showall("bus");
                System.out.println("bus location choise:");
                String location = input.next();
                sql="select numAvail from bus where location ='"+location+"'";
                rs=query(sql,null);
                try {
                    if(rs.next()){
                        if(rs.getInt(1)>0){
                            sql="update bus set numAvail="+(rs.getInt(1)-1)+" where location='"+location+"'";
                            update(sql);
                            sql="select count(*) from reservations";
                            rs=query(sql,null);
                            if(rs.next()){
                                resvKey=rs.getInt(1);
                                sql="insert into reservations (resvKey,resvType,custName,order_detail )values" +
                                        "("+(resvKey+1)+","+choise+",'"+name+"','"+location+"')";
                                update(sql);
                                res="Order bus success!!!";
                            }

                        }else{
                            res ="Order bus Unsuccess!!! NO numAvail!!!";
                        }
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }finally {
                    closeAll();
                }

            } else if (choise==3) {
                showall("hotel");
                System.out.println("hotel location choise:");
                String location=input.next();
                sql="select numAvail from hotels where location ='"+location+"'";
                rs=query(sql,null);
                try {
                    if(rs.next()){
                        if(rs.getInt(1)>0){
                            sql="update hotels set numAvail="+(rs.getInt(1)-1)+" where location='"+location+"'";
                            update(sql);
                            sql="select count(*) from reservations";
                            rs=query(sql,null);
                            if(rs.next()){
                                resvKey=rs.getInt(1);
                                sql="insert into reservations (resvKey,resvType,custName,order_detail )values" +
                                        "("+(resvKey+1)+","+choise+",'"+name+"','"+location+"')";
                                update(sql);
                                res="Order bus success!!!";
                            }
                        }else{
                            res ="Order hotel Unsuccess!!! NO numAvail!!!";
                        }
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }finally {
                    closeAll();
                }
            }
        }


//        try {
//            if(!rs.next()){
//                System.out.println("System no this customerName!!!");
//                res="Order Unsuccess!!!";
//            }
//            else{
//                closeAll();
//                order_nemu();
//                int resvKey=-1;
//                int choise=input.nextInt();
//                if(choise==1){
//                    showall("flight");
//                    System.out.println("flight choise:");
//                    String category=input.next();
//                    sql="select numAvail from flights where flightNum='"+category+"'";
//                    ResultSet rs_1=query(sql,null);
//                    try {
//                        while(rs_1.next()){
//                            if(rs_1.getInt(1)>0){
//                                sql="update flights set numAvail="+(rs_1.getInt(1)-1)+" where flightNum='"+category+"'";
//                                update(sql);
//                                sql="select count(*) from reservations";
//                                rs_1=query(sql,null);
//                                while(rs_1.next()){
//                                    resvKey=rs_1.getInt(1);
//                                }
//                                if(resvKey!=-1){
//                                    sql="update reservations set resvKey="+(resvKey+1)+",resvType="+choise+",custName='"+name+"'+order_detail='"+category+"'";
//                                    update(sql);
//                                    res="Order flight success!!!";
//                                }
//
//                            }else {
//                                res ="Order flight Unsuccess!!! NO numAvail!!!";
//                            }
//                        }
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }finally {
//                        closeAll();
//                    }
//                } else if (choise==2) {
//                    showall("bus");
//                    System.out.println("bus location choise:");
//                    String location = input.next();
//                    sql="select numAvail from bus where location ='"+location+"'";
//                    rs=query(sql,null);
//                    try {
//                        if(rs.next()){
//                            if(rs.getInt(1)>0){
//                                sql="update bus set numAvail="+(rs.getInt(1)-1)+" where location='"+location+"'";
//                                update(sql);
//                                res="Order bus success!!!";
//                            }else{
//                                res ="Order bus Unsuccess!!! NO numAvail!!!";
//                            }
//                        }
//
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                } else if (choise==3) {
//                    showall("hotel");
//                    System.out.println("hotel location choise:");
//                    String location=input.next();
//                    sql="select numAvail from hotels where location ='"+location+"'";
//                    rs=query(sql,null);
//                    try {
//                        if(rs.next()){
//                            if(rs.getInt(1)>0){
//                                sql="update hotels set numAvail="+(rs.getInt(1)-1)+" where location='"+location+"'";
//                                update(sql);
//                                res="Order hotel success!!!";
//                            }else{
//                                res ="Order hotel Unsuccess!!! NO numAvail!!!";
//                            }
//                        }
//
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        return res;
    }

    public void showall(String s) {
        String sql;
        ResultSet rs;
        if(s.equals("flight")){
            sql="select * from flights";
            rs=query(sql,null);
            try {
                while (rs.next()){
                    System.out.print("(flightNum):"+rs.getString(1)+"     ");
                    System.out.print("(price):"+rs.getInt(2)+"     ");
                    System.out.print("(numSeats):"+rs.getInt(3)+"     ");
                    System.out.print("(numAvail):"+rs.getInt(4)+"     ");
                    System.out.print("(FromCity):"+rs.getString(5)+"     ");
                    System.out.println("(ArivCity):"+rs.getString(6));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (s.equals("bus")) {
            sql="select * from bus";
            rs=query(sql,null);

            try {
                while (rs.next()){
                    System.out.print("(location):"+rs.getString(1)+"     ");
                    System.out.print("(price):"+rs.getInt(2)+"      ");
                    System.out.print("(numBus):"+rs.getInt(3)+"     ");
                    System.out.println("numAvail):"+rs.getInt(4));
                }
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }else if (s.equals("hotel")) {
            sql = "select * from hotels";
            rs = query(sql, null);
            try {
                while (rs.next()) {
                    System.out.print("(location):"+rs.getString(1)+"     ");
                    System.out.print("(price):"+rs.getInt(2)+"      ");
                    System.out.print("(numBooms):"+rs.getInt(3)+"     ");
                    System.out.println("numAvail):"+rs.getInt(4));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (s.equals("customers")) {
            sql="select * from customers";
            rs=query(sql,null);
            while (true) {
                try {
                    if (!rs.next()) break;
                    else {
                        System.out.print("(custName):"+rs.getString(1)+"     ");
                        System.out.println("(custID):"+rs.getInt(2));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (s.equals("reservations")) {
            sql="select * from reservations";
            rs=query(sql,null);
            while (true) {
                try {
                    if (!rs.next()) break;
                    else{
                        System.out.print("(resvKey):"+rs.getString(1)+"     ");
                        System.out.print("(custName):"+rs.getString(2)+"     ");
                        System.out.println("(resvType):"+rs.getInt(3));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        closeAll();

    }

    public String update_flight(){
        String res=null;
        System.out.println("请选择功能：1：数据入库  2：更新数据");
        int choise=input.nextInt();
        if(choise!=1&&choise!=2){
            res ="Unsuccess!!! Update error";
        }else{
            Flights flights=new Flights();
            System.out.println("请输入：");
            System.out.println("(String)flight_number:");
            //String flight_number=input.next();
            flights.setFlightNum(input.next());
            System.out.println("(int)price:");
            //int price=input.nextInt();
            flights.setPrice(input.nextInt());
            System.out.println("(int)numSeats:");
            //int numSeats=input.nextInt();
            flights.setNumSeats(input.nextInt());
            System.out.println("(String)FromCity:");
            //String FromCity=input.next();
            flights.setFromCity(input.next());
            System.out.println("(String)ArivCity:");
            //String ArivCity=input.next();
             flights.setArviCity(input.next());
            String sql;
            if(choise==1){
                //sql="select * from flights where flightNum='"+flight_number+"'";
                sql="select * from flights where flightNum='"+flights.getFlightNum()+"'";
                ResultSet rs=query(sql,null);
                try {
                    if(!rs.next()){
                        //sql="insert into flights values('"+flight_number+"',"+price+","+numSeats+",'"+FromCity+"','"+ArivCity+"')";
                        sql="insert into flights values('"+flights.getFlightNum()+"',"+flights.getPrice()+","+ flights.getNumSeats()+","+flights.getNumSeats()+",'"
                                +flights.getFromCity()+"','"+flights.getArviCity()+"')";
                        update(sql);
                        //System.out.println(sql);
                        res= " Insert Success!!!";
                    }
                    else{
                        res= "Insert Unsuccess!!! flightNum is already!!!";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else if (choise==2) {
                sql="select * from flights where flightNum='"+flights.getFlightNum()+"'";
                ResultSet rs=query(sql,null);
                try {
                    if(rs.next()){
                        sql="update flights set flightNum='"+ flights.getFlightNum()+"',price="+ flights.getPrice()+",numSeats="+ flights.getNumSeats()+",FromCity='"
                                + flights.getFromCity()+"',ArivCity='"+ flights.getArviCity()+"' where flightNum='"+ flights.getFlightNum()+"'";
                        update(sql);
                        res= "Update Success!!!";
                    }
                    else{
                        res= "Update Unsuccess!!! No this flghtNum!!!";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return res;
    }

    public String update_bus(){
        String res=null;
        System.out.println("请选择功能：1：数据入库  2：更新数据");
        int choise=input.nextInt();
        String sql;
        if(choise!=1&&choise!=2){
            res ="Unsuccess!!! Update error";
        }else {

            /**
             * 其实后续的操作也应当是构建一个bus hotel customer对象
             * 然后通过get set方法来进行操作而不是去定义这些字符串和integer
             * 但是在此处就只修改flight后续操作完全一样
             */
            System.out.println("请输入：");
            System.out.println("(String)location:");
            String location=input.next();
            System.out.println("(int)price:");
            int price=input.nextInt();
            System.out.println("(int)numBus:");
            int numBus=input.nextInt();
            System.out.println("(int)numAvail:");
            int numAvail=input.nextInt();
            if(choise==1){
                sql="select * from bus where location='"+location+"'";
                ResultSet rs=query(sql,null);
                try {
                    if(!rs.next()){
                        sql="insert into bus values('"+location+"',"+price+","+numBus+","+numAvail+")";
                        update(sql);
                        res= " Insert Success!!!";
                    }else{
                        res= "Update Unsuccess!!! bus location is already!!!";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (choise==2) {
                sql="select * from bus where location='"+location+"'";
                ResultSet rs=query(sql,null);
                try {
                    if(rs.next()){
                        sql="update bus set location='"+location+"',price="+price+",numBus="+numBus+",numAvail="+numAvail+" where location='"+location+"'";
                        System.out.println(sql);
                        update(sql);
                        res="Update success!!!";
                    }else{
                        res="Update Unsuccess!!! No this bus location!!!";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return  res;
    }

    public String update_hotel() {
        String res=null;
        System.out.println("请选择功能：1：数据入库  2：更新数据");
        int choise=input.nextInt();
        String sql;
        if(choise!=1&&choise!=2){
            res ="Unsuccess!!! Update error";
        }else{
            System.out.println("请输入：");
            System.out.println("(String)location:");
            String location=input.next();
            System.out.println("(int)price:");
            int price=input.nextInt();
            System.out.println("(int)numRooms:");
            int numRooms=input.nextInt();
            System.out.println("(int)numAvail:");
            int numAvail=input.nextInt();
            sql="select * from hotels where location='"+location+"'";
            ResultSet rs=query(sql,null);
            if(choise==1)
            try {
                if(!rs.next()){
                    sql="insert into hotels values('"+location+"',"+price+","+numRooms+","+numAvail+")";
                    update(sql);
                    res="Insert Success!!!";
                }else{
                    res="Insert Unsuccess!!! this hotel location is already!!!";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            else if (choise==2) {
                try {
                    if(rs.next()){
                        sql="update hotels set location='"+location+"',price="+price+",numRooms="+numRooms+",numAvail="+numAvail+" where location='"+location+"'";
                        update(sql);
                        res="Update Success!!!";
                    }else{
                        res="Update Unsuccess!!! No this hotel location!!!";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return  res;

    }

    public String update_customer() {
        String res=null;
        System.out.println("请选择功能：1：数据入库  2：更新数据");
        int choise=input.nextInt();
        String sql;
        if(choise!=1&&choise!=2){
            res ="Unsuccess!!! Update error";
        }else{
            System.out.println("请输入：");
            System.out.println("(String)custName:");
            String custName=input.next();
//            System.out.println("(int)custID:");
//            int custId=input.nextInt();
            sql="select * from customers where custName='"+custName+"'";
            ResultSet rs=query(sql,null);
            if(choise==1){
                try {
                    if(!rs.next()){
                        System.out.println("(int)custID:");
                        int custId=input.nextInt();
                        sql="insert into customers values ('"+custName+"',"+custId+")";
                        update(sql);
                        res="Insert Success!!!";
                    }else{
                        res="Insert Unsuccess!!! this custName is already!!!";
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (choise==2) {
                try {
                    if(rs.next()){
                        System.out.println("(int)custID:");
                        int custId=input.nextInt();
                        sql="update customers set custName='"+custName+"',custID="+custId+" where custName='"+custName+"'";
                        update(sql);
                        res="Update Success!!!";
                    }else{
                        res="Update Unsuccess!!! No this custName!!!";
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return res;
    }

    public void query(){
        search();
        String sql;
        ResultSet rs;
        int choise=input.nextInt();
        if(choise==1){
            showall("flight");

        } else if (choise==2) {
            showall("bus");
        } else if (choise==3) {
            showall("hotel");
        } else if (choise==4) {
            showall("customers");
        } else if (choise==5) {
            showall("reservations");
        } else {

        }
    }


    public boolean find_name(String name) {
        String sql="select * from customers where custName='"+name+"'";
        ResultSet rs=query(sql,null);
        try {
            if(!rs.next()){
                return false;
            }
            else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll();
        }
    }

    public void search_route(){
        System.out.println("请输入客户名：");
        String name=input.next();
        String sql;
        //查询database中是否有该用户
        if(!find_name(name)){
            System.out.println("Search unsuccess!!! No this customer!!!");
        }else{

            sql="select distinct flightNum , FromCity, ArivCity from flights,reservations where order_detail=flightNum and custName='"+name+"'";
            List<String> route=new ArrayList<>();
            List<Flights> list=new ArrayList<>();
            ResultSet rs=query(sql,null);

                try {
                        while (rs.next()){
                            Flights flights=new Flights();
                            flights.setFlightNum(rs.getString(1));
                            flights.setFromCity(rs.getString(2));
                            flights.setArviCity(rs.getString(3));
                            list.add(flights);
                        }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

//            sql="select ArivCity  from flights,reservations where custName='"+name+"'";
//            rs=query(sql,null);
//            while (true) {
//                try {
//                    if (!rs.next()) break;
//                    else {
//                        arivcity.add(rs.getString(1));
//                    }
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            String start=null;
//            for(String from:fromcity){
//                if(!arivcity.contains(from)){
//                    start=from;
//                    route.add(start);
//                }
//            }
//            boolean flag=true;
//            while(flag){
//                sql="select arivcity from fights where fromcity='"+start+"'";
//                rs=query(sql,null);
//                try {
//                    if(rs.next()){
//
//                    }
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                if(!fromcity.contains(start)){
//                    flag=false;
//                }
//            }
            boolean flag=true;
            String temp=null;
            for(Flights f:list){
                for(Flights x:list){
                    if(f.getFromCity().equals(x.getArviCity())){
                        flag=false;
                    }
                }
                if(flag){
                    route.add(f.getFromCity());
                    temp=f.getArviCity();
                    route.add(temp);
                }
            }
            for(int i=0;i<list.size()-1;i++){
                for(Flights f:list){
                    if(temp.equals(f.getFromCity())){
                        temp=f.getArviCity();
                        route.add(f.getArviCity());
                    }
                }
            }
            System.out.println(name+" route: ");
            int time=1;//用于控制输出箭头
            for(String l:route){
                System.out.print(l);
                if(time<route.size()){
                    System.out.print("===>");
                    time++;
                }

            }
            System.out.println();
        }
    }

    public void update_insert(){
        update_nemu();
        int n1=input.nextInt();
        if(n1==1){
            System.out.println(update_flight());
        }else if(n1==2){
            System.out.println(update_bus());
        }else if(n1==3){
            System.out.println(update_hotel());
        } else if (n1==4) {
            System.out.println(update_customer());
        } else System.out.println("输入错误！！！请重新输入:");

    }

    public void cheek_route(){
        System.out.println("Start:");
        String start=input.next();
        System.out.println("End:");
        String end=input.next();
        String sql="select distinct FromCity , ArivCity  from Flights";
        List<String> route=new ArrayList<>();
        List<Flights> list=new ArrayList<>();
        ResultSet rs=query(sql,null);
        try {
            while (rs.next()){
                Flights flights =new Flights();
                flights.setFromCity(rs.getString(1));
                flights.setArviCity(rs.getString(2));
                list.add(flights);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll();
        }
        boolean flag_start=false;
        boolean flag_end=false;
        for(Flights f:list){
            if(start.equals(f.getFromCity())){
                flag_start=true;
            }
        }
        for(Flights f:list){
            if(end.equals(f.getArviCity())){
                flag_end=true;
            }
        }

        if(!flag_end||!flag_start){
            System.out.println("Route is not complete!!!");
        }else {
            int time=0;
            route=cheek_route_search(route,start,time);
            System.out.println(route);
            if(route.contains(end)){
                System.out.println("Route is complete!!!");
            }
            else{
                System.out.println("Route is not complete!!!");
            }
        }


    }

    public List<String> cheek_route_search(List<String> route, String place,int time){
        int max=0;
        String sql="select count(*) from flights";
        ResultSet rs=query(sql,null);
        try {
            if(rs.next()){
                max=rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sql="select Fromcity from flights";
        List<String> list_from=new ArrayList<>();
        rs=query(sql,null);
        try {
            while(rs.next()){
                if(!list_from.contains(rs.getString(1))){
                    list_from.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sql="select  Arivcity from flights where Fromcity='"+place+"'";
        String newplace=null;
        rs=query(sql,null);
        try {
            while (rs.next()){
                route.add(rs.getString(1));
                newplace=rs.getString(1);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll();
        }
        //递归添加
        if(time<max){
            time++;
            route=cheek_route_search(route,newplace,time);
        }
        return route;
    }

}
