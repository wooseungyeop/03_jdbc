package com.javaeagles.phone.dao;

import com.javaeagles.phone.dto.PhoneDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static com.javaeagles.common.JDBCTemplate.close;
import static com.javaeagles.common.JDBCTemplate.getConnection;

public class PhoneRepository {


    private Properties pros =new Properties();
    private Connection con = null;
    private PreparedStatement pstmt = null;
    private PreparedStatement pstmt2 = null;
    private ResultSet rset = null;

    public PhoneRepository(){
        try {
            this.pros.loadFromXML(new FileInputStream("src/main/java/com/javaeagles/phone/mapper/phone-query.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList phoneViewAll(){
        ArrayList arrayList = new ArrayList();
        String query = pros.getProperty("phoneAll");
        con = getConnection();
        try {
            pstmt = con.prepareStatement(query);
            rset = pstmt.executeQuery();
            while (rset.next()){
                PhoneDTO ph = new PhoneDTO();
                ph.setUserCode(rset.getInt("USER_CODE"));
                ph.setUserName(rset.getString("USER_NAME"));
                ph.setUserEmail(rset.getString("USER_EMAIL"));
                ph.setUserMemo(rset.getString("USER_MEMO"));
                ph.setUserGroup(rset.getString("USER_GROUP"));
                ph.setPhone(rset.getString("PHONE"));
                ph.setPhoneName(rset.getString("PHONE_NAME"));
                arrayList.add(ph);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(pstmt);
            close(con);
        }

        return arrayList;
    }

    public PhoneDTO phoneFindByName(String name) {
        String query = pros.getProperty("phoneFindByName");
        con = getConnection();
        PhoneDTO ph = new PhoneDTO();

        try {
            pstmt = con.prepareStatement(query); // 쿼리 읽기
            pstmt.setString(1, name);
            rset = pstmt.executeQuery(); // 쿼리 실행
            if(rset.next()){
                ph.setUserCode(rset.getInt("USER_CODE"));
                ph.setUserName(rset.getString("USER_NAME"));
                ph.setUserEmail(rset.getString("USER_EMAIL"));
                ph.setUserMemo(rset.getString("USER_MEMO"));
                ph.setUserGroup(rset.getString("USER_GROUP"));
                ph.setPhone(rset.getString("PHONE"));
                ph.setPhoneName(rset.getString("PHONE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(pstmt);
            close(con);
        }

        return ph;
    }

    public PhoneDTO phoneFindById(String index){
        String query = pros.getProperty("phoneFindById");
        con = getConnection();
        PhoneDTO ph = null;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, index);
            rset = pstmt.executeQuery();
            if(rset.next()){
                ph = new PhoneDTO();
                ph.setUserCode(rset.getInt("USER_CODE"));
                ph.setUserName(rset.getString("USER_NAME"));
                ph.setUserEmail(rset.getString("USER_EMAIL"));
                ph.setUserMemo(rset.getString("USER_MEMO"));
                ph.setUserGroup(rset.getString("USER_GROUP"));
                ph.setPhone(rset.getString("PHONE"));
                ph.setPhoneName(rset.getString("PHONE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
            close(rset);
        }

        return ph;
    }
    public int phoneInsert(PhoneDTO ph) {
        String query = pros.getProperty("phoneInsert");
        String query2 = pros.getProperty("phoneInsert2");
        con = getConnection();
//        PreparedStatement pstmt1 = null;
//        PreparedStatement pstmt2 = null;
        int result1 = 0;
        int result2 = 0;

        try {
            con = getConnection();


            pstmt = con.prepareStatement(query);
            pstmt.setString(1, ph.getUserName());
            pstmt.setString(2, ph.getUserEmail());
            pstmt.setString(3, ph.getUserMemo());
            pstmt.setString(4, ph.getUserGroup());
            result1 = pstmt.executeUpdate();

            pstmt2 = con.prepareStatement(query2);
            pstmt2.setString(1, ph.getPhone());
            pstmt2.setString(2, ph.getPhoneName());
            result2 = pstmt2.executeUpdate();


        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            close(pstmt);
            close(pstmt2);
            close(con);
        }

        return result1 + result2;
    }

    public int phoneModify(String name, String index) {
        // 쿼리불러오기
        String query = pros.getProperty("phoneModify");
        con = getConnection(); // 쿼리 실행
        int result = 0;
        try {
            pstmt = con.prepareStatement(query); // 쿼리 읽기
            pstmt.setString(1, name);
            pstmt.setString(2, index);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(con);
            close(pstmt);
        }

        return result;

    }
    public int phoneDelete(String name){
        String query = pros.getProperty("phoneDelete");
        con = getConnection();
        int result = 0;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            close(con);
            close(pstmt);
        }
        return result;
    }
}