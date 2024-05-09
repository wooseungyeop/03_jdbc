package com.ohgiraffers.section02.preparedstatement;

import com.ohgiraffers.model.dto.EmployeeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import static com.ohgiraffers.common.JDBCTemplate.*;

public class Application03 {

    public static void main(String[] args) {

        /* 설명. Scanner 이용해서 사번을 입력 받고 해당 사번의 사원 정보를 EmployeeDTO 객체에 담아서 출력 */
        Connection con = getConnection();

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        EmployeeDTO selectedEmp = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("조회하실 사번을 입력해주세요 : ");
        String empId = sc.nextLine();

        String query = "SELECT * FROM EMPLOYEE WHERE EMP_ID = ?";

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, empId);

            rset = pstmt.executeQuery();

            if(rset.next()) {
                selectedEmp = new EmployeeDTO();

                selectedEmp.setEMP_ID(rset.getString("EMP_ID"));
                selectedEmp.setEMP_NAME(rset.getString("EMP_NAME"));
                selectedEmp.setEMP_NO(rset.getString("EMP_NO"));
                selectedEmp.setEMAIL(rset.getString("EMAIL"));
                selectedEmp.setPHONE(rset.getString("PHONE"));
                selectedEmp.setDEPT_CODE(rset.getString("DEPT_CODE"));
                selectedEmp.setJOB_CODE(rset.getString("JOB_CODE"));
                selectedEmp.setSAL_LEVEL(rset.getString("SAL_LEVEL"));
                selectedEmp.setSALARY(rset.getInt("SALARY"));
                selectedEmp.setBONUS(rset.getDouble("BONUS"));
                selectedEmp.setMANAGER_ID(rset.getString("MANAGER_ID"));
                selectedEmp.setHIRE_DATE(rset.getDate("HIRE_DATE"));
                selectedEmp.setENT_DATE(rset.getDate("ENT_DATE"));
                selectedEmp.setENT_YN(rset.getString("ENT_YN"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }

        System.out.println("selectedEmp : " + selectedEmp);

    }
}
