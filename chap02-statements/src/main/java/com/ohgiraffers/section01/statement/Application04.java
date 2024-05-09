package com.ohgiraffers.section01.statement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.*;
public class Application04 {
    public static void main(String[] args){
        Connection con = getConnection();

        Statement stmt = null;
        ResultSet rset = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("조회할 사번 입력 : ");
        int empId = sc.nextInt();
        String query = "SELECT * FROM EMPLOYEE WHERE EMP_ID = " + empId;
        EmployeeDTO employeeDTO = null;

        try {
            stmt = con.createStatement();
            rset = stmt.executeQuery(query);

            if(rset.next()){
                employeeDTO = new EmployeeDTO();
                employeeDTO.setEMP_ID(rset.getString("EMP_ID"));
                employeeDTO.setEMP_NAME(rset.getString("EMP_NAME"));
                employeeDTO.setEMP_NO(rset.getString("EMP_NO"));
                employeeDTO.setPHONE(rset.getString("PHONE"));
                employeeDTO.setDEPT_CODE(rset.getString("DEPT_CODE"));
                employeeDTO.setJOB_CODE(rset.getString("JOB_CODE"));
                employeeDTO.setSALARY(rset.getString("SALARY"));
                employeeDTO.setBONUS(rset.getString("BONUS"));
                employeeDTO.setMANAGER_ID(rset.getString("MANAGER_ID"));
                employeeDTO.setHIRE_DATE(rset.getString("HIRE_DATE"));
                employeeDTO.setENT_DATE(rset.getString("ENT_DATE"));
                employeeDTO.setENT_YN(rset.getString("ENT_YN"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(stmt);
            close(con);

        }
        System.out.println("selet emp : " + employeeDTO);

    }
}



