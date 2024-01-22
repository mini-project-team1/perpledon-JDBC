package com.ohgiraffers.hamburgerManager;


import com.ohgiraffers.hamburgerManage.View.setView;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;
import static com.ohgiraffers.hamburgerManage.View.setView.*;
import static com.ohgiraffers.hamburgerManager.payView.*;

public class Controller {
    setView SETVIEW= new setView();
    payView payView = new payView();
    Member member = new Member();
    String c;
    Scanner sc = new Scanner(System.in);
    Connection con = getConnection();
    PreparedStatement pstmt = null;
    Properties prop = new Properties();
    Statement stmt = null;

    ResultSet rset = null;

    int burgerResult=0;
    int setResult =0;


    public void mainMenu() {

        String query ="";

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/purpledon_query.xml"));


            do {

                System.out.println("============================================");
                System.out.println("🍔🍟🍔🍟 롯데리아에 오신것을 환영합니다 🍔🍟🍔🍟");
                System.out.println(" ========= 1. 🍔 단품 메뉴 선택 🍔 =========");
                System.out.println(" ========= 2. 🍟 세트 메뉴 선택 🍹 =========");
                System.out.println(" ========= 9. ⛔  프로그램 종료 ⛔ =========");
                System.out.println("============================================");
                System.out.print("원하시는 메뉴 번호를 입력하세요 : ");
                c = sc.nextLine();

                switch (c) {
                    case "1" :
                        System.out.println("단품메뉴 선택하셨습니다. 회원인증을 시작합니다!");
                        member.chooseListSingle();
                        stmt = con.createStatement();
                        query = " select max(order_code) from order_list " ;
                        System.out.println(query);
                        rset = stmt.executeQuery(query);
                        if(rset.next()){
                            int a = rset.getInt("max(order_code)");
                            query = prop.getProperty("insertOrderBurger");
                            pstmt=con.prepareStatement(query);
                            pstmt.setInt(1,a);
                            pstmt.setString(2,burgername);
                            burgerResult = pstmt.executeUpdate();
                            if(burgerResult>0){
                                System.out.println("주문버거db 추가완료");
                            }else{
                                System.out.println("주문버거db 추가 실패 ㅠ");
                            }}
                        break;

                    case "2" :
                        System.out.println("세트메뉴 선택하셨습니다. 회원인증을 시작합니다!");
                        member.chooseListSet();
                        SETVIEW.burgurviewset();
                        SETVIEW.drinkview();
                        SETVIEW.sideview();
                        System.out.println(SETVIEW.setInformation());
                        payView.setMemberPay();
                        stmt = con.createStatement();
                       query = " select max(order_code) from order_list " ;
                        System.out.println(query);
                        rset = stmt.executeQuery(query);

                        if(rset.next()) {

                          int listOrderCode = rset.getInt("max(order_code)");
                            query = prop.getProperty("insertOrderSet");
                            pstmt=con.prepareStatement(query);
                            pstmt.setInt(1,listOrderCode);
                            pstmt.setString(2,drinkname);
                            pstmt.setString(3,sidename);
                            setResult = pstmt.executeUpdate();
                            if(setResult>0){
                                System.out.println("주문세트db 추가완료");
                            }else{
                                System.out.println("주문세트db 추가 실패 ㅠ");
                            }
                        }
                        break;
                    case "9" :
                        System.out.println("⛔ 프로그램 종료 ⛔");
                        break;

                    default:
                        System.out.println("❗ 잘못된 번호를 입력하셨습니다, 처음으로 돌아갑니다 ❗");
                }

            } while (!c.equals("9"));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(stmt);
            close(pstmt);
            close(con);

        }




    }
}
