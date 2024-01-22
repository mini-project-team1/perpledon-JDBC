package com.ohgiraffers.hamburgerManager;

import com.ohgiraffers.hamburgerManage.Dto.memberDTO;
import com.ohgiraffers.hamburgerManage.View.burgerView;
import com.ohgiraffers.hamburgerManage.View.setView;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Member {
    public static String name ;

    burgerView burgerView = new burgerView();
    setView setView = new setView();
    Scanner sc = new Scanner(System.in);
    List<memberDTO> memberList = new ArrayList<>();

    ResultSet rset = null;

    public void chooseListSingle() {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        Properties prop = new Properties();
        ResultSet rset = null;
        int result =0;
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/purpledon_query.xml"));
            // 조회문 select count(*) from member where membername ='홍길동' -->  0 이면 없어 1 있어
            String query = prop.getProperty("selectOrderMember");
            pstmt = con.prepareStatement(query);
            System.out.println("회원 검증을 시작합니다.");
            System.out.print("이름을 입력해주세요 : ");
            String mName = sc.nextLine();
            System.out.print("핸드폰 번호를 입력해주세요 : ");
            String mPhone = sc.nextLine();
            pstmt.setString(1,mName);
            pstmt.setString(2,mPhone);
            rset = pstmt.executeQuery();


            if(rset.next()) {
                System.out.println("회원인증에 성공하였습니다.");
                memberDTO member = new memberDTO(rset.getString("member_name"),rset.getString("phone"));
                memberList.add(member);
                this.name = rset.getString("member_name");
                burgerView.burgurview();
            }else{
                System.out.println("---------------------------");;
                System.out.println("❗ 일치하는 멤버가 없습니다. ❗");
                System.out.println("회원 가입을 시작합니다. ");
                System.out.print("이름을 입력해주세요 : ");
                mName = sc.nextLine();
                System.out.print("핸드폰 번호를 입력해주세요 : ");
                mPhone = sc.nextLine();
                query=prop.getProperty("insertOrderMember");
                pstmt = con.prepareStatement(query);
                pstmt.setString(1,mName);
                pstmt.setString(2,mPhone);
                result = pstmt.executeUpdate();
                if(result>0){
                    System.out.println("맴버 db 등록에 성공하였습니다.");
                    this.name = mName;
                    burgerView.burgurview();
                }else{
                    System.out.println("맴버 db 등록에 실패하였습니다.");
                    chooseListSet();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(rset);
            close(con);
        }

    }


    public void chooseListSet() {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        Properties prop = new Properties();
        ResultSet rset = null;
        int result =0;
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/purpledon_query.xml"));

            String query = prop.getProperty("selectOrderMember");
            pstmt = con.prepareStatement(query);
            System.out.println("회원 검증을 시작합니다.");
            System.out.print("이름을 입력해주세요 : ");
            String mName = sc.nextLine();
            System.out.print("핸드폰 번호를 입력해주세요 : ");
            String mPhone = sc.nextLine();
            pstmt.setString(1,mName);
            pstmt.setString(2,mPhone);
            rset = pstmt.executeQuery();


            if(rset.next()) {
                System.out.println("회원인증에 성공하였습니다.");
                memberDTO member = new memberDTO(rset.getString("member_name"),rset.getString("phone"));
                memberList.add(member);
                this.name = rset.getString("member_name");

            }else{
                System.out.println("---------------------------");;
                System.out.println("❗ 일치하는 멤버가 없습니다. ❗");
                System.out.println("회원 가입을 시작합니다. ");
                System.out.print("이름을 입력해주세요 : ");
                mName = sc.nextLine();
                this.name = mName;
                System.out.print("핸드폰 번호를 입력해주세요 : ");
                mPhone = sc.nextLine();
                query=prop.getProperty("insertOrderMember");
                pstmt = con.prepareStatement(query);
                pstmt.setString(1,mName);
                pstmt.setString(2,mPhone);
                result = pstmt.executeUpdate();
                if(result>0){
                    System.out.println("맴버 db 등록에 성공하였습니다.");
                    this.name = mName;

                }else{
                    System.out.println("맴버 db 등록에 실패하였습니다.");
                    chooseListSet();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close(pstmt);
            close(rset);
            close(con);
        }

    }

//    public void regist() {
//        Connection con = getConnection();
//        PreparedStatement pstmt = null;
//        Properties prop = new Properties();
//        int result =0;
//
//        try {
//            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/purpledon_query.xml"));
//            System.out.println("-------------------------");
//            System.out.println("-- 회원 가입을 진행합니다. --");
//            System.out.println("-------------------------");
//            System.out.print(" 성함을 입력하세요 : ");
//            String newName = sc.nextLine();
//
//            System.out.print(" 휴대폰 번호를 입력하세요 : ");
//            String newPhone = sc.nextLine();
////            memberList[index] = new memberDTO(newName, newPhone);
////            System.out.println(memberList[index].memberInformation());
//            System.out.println("---------------------------------------");
//            System.out.println(newName + "님, 회원 가입이 완료되었습니다. 😎");
//            System.out.println("---------------------------------------");
//            String query = prop.getProperty("insertOrderMember");
//            pstmt=con.prepareStatement(query);
//            pstmt.setString(1,newName);
//            pstmt.setString(2,newPhone);
//            result = pstmt.executeUpdate();
//            this.name = newName;
////            query =prop.getProperty("insertOrderList");
////            pstmt=con.prepareStatement(query);
////            pstmt.setString(3,newName);
////            index++;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }finally {
//            close(pstmt);
//            close(con);
//        }
//
//        if(result>0){
//            System.out.println("DB에 맴버추가 완료");
//
//        }else{
//            System.out.println("DB에 맴버추가 실패");
//        }
//
//
//    }

}