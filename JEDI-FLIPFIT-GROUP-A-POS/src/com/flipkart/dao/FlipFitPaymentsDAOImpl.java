package com.flipkart.dao;

import com.flipkart.bean.FlipFitPayments;
import com.flipkart.constant.DBConstants;
import com.flipkart.dao.interfaces.IFlipFitPaymentsDAO;

import java.sql.SQLException;
import java.util.Random;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class FlipFitPaymentsDAOImpl implements IFlipFitPaymentsDAO {
    Random rand = new Random();

    /**
     * setPaymentInfo
     *
     * @param FFP
     */
    @Override
    public void setPaymentInfo(FlipFitPayments FFP) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    DBConstants.DB_URL,DBConstants.USER,DBConstants.PASSWORD);

            PreparedStatement stmt = con.prepareStatement("REPLACE INTO Payments (userID, paymentType, paymentInfo) VALUES (?, ?, ?)");


            // Generate random integers in range 0 to 999
            stmt.setInt(1, FFP.getUserID());
            stmt.setInt(2, FFP.getPaymentType());
            stmt.setString(3, FFP.getPaymentInfo());

            int i = stmt.executeUpdate();
            if(i==0){
                throw new SQLException("Creating payment failed. No rows affected");
            }

            con.close();
        } catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * deletePaymentInfo
     *
     * @param FFP
     */
    @Override
    public void deletePaymentInfo(FlipFitPayments FFP) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    DBConstants.DB_URL,DBConstants.USER,DBConstants.PASSWORD);

            PreparedStatement stmt = con.prepareStatement("DELETE FROM Payments WHERE userID=(?)");

            stmt.setInt(1, FFP.getUserID());

            int i = stmt.executeUpdate();
            System.out.println( i + " payment info deleted");

            con.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }

}
