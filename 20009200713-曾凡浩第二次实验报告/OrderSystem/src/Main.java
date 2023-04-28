import Service.JDBCimpl;
import dao.Dao;

import java.util.Scanner;

import static rescources.demo.*;

public class Main {
    public static void main(String[] args) {

        String sql;
        Dao dao=new JDBCimpl();
        boolean flag=true;
        do{
            menu();

            Scanner input=new Scanner(System.in);
            int choise=input.nextInt();
            switch (choise){
                case 1:
                    dao.update_insert();
//                    int n1=input.nextInt();
//                    if(n1==1){
//                        System.out.println(dao.update_flight());
//                    }else if(n1==2){
//                        System.out.println(dao.update_bus());
//                    }else if(n1==3){
//                        System.out.println(dao.update_hotel());
//                    } else if (n1==4) {
//                        System.out.println(dao.update_customer());
//                    } else System.out.println("输入错误！！！请重新输入:");
                    break;
                case 2:
                    System.out.println(dao.orderType());
                    break;
                case 3:
                    dao.query();
                    break;
                case 4:
                    dao.search_route();
                    break;
                case 5:
                    dao.cheek_route();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("输入序号错误，请重新输入");
            }
            if(choise==6) {
                flag=false;
                System.out.println("退出成功！！！");
            }

        }while (flag);
    }

}