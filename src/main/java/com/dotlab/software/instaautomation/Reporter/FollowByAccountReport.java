/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Reporter;

import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omandotkom
 */
public class FollowByAccountReport extends CSVReport {

    private String targetAccount;

    public FollowByAccountReport(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    @Override
    public void print(List<User> data, int reportType) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        try {
            //HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();

            String fn = generateFileName(reportType, targetAccount);
            /* ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
            String[] str = {"username", "fullname", "followerCount", "followingCount", "postCount", "isPrivate", "isFollowed", "userID"};
            strategy.setType(User.class);
            strategy.setColumnMapping(str);
             */
            Writer writer = new FileWriter(fn);
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .build();
            beanToCsv.write(data);
            writer.close();
            System.out.println("Report successfully created " + fn);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (CsvDataTypeMismatchException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(FollowByAccountReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(FollowByAccountReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(FollowByAccountReport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
