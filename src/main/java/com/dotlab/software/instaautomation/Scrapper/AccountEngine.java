/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Scrapper;

import com.dotlab.software.instaautomation.Scrapper.Entities.Post;
import com.dotlab.software.instaautomation.Scrapper.Parser.AccountParser;
import com.dotlab.software.instaautomation.Automator.Automation;
import com.dotlab.software.instaautomation.Reporter.CSVReport;
import com.dotlab.software.instaautomation.Reporter.FollowByAccountReport;
import com.dotlab.software.instaautomation.Scrapper.Entities.User;
import com.dotlab.software.instaautomation.UI.AutoStatic;
import com.dotlab.software.instaautomation.filters.CleanerFilter;
import com.dotlab.software.instaautomation.filters.FollowFilter;
import com.dotlab.software.instaautomation.runnable.LoggerInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author omandotkom
 */
public class AccountEngine extends Engine {

    //private String account;
    private boolean isFirstPage = true;
    private FollowFilter followEngineSetting;
    private ArrayList<User> userList;
    private CleanerFilter cleanerEngineSetting;
    //this is need optimization, just use one arraylist.
    //to make a csv report
    private List<User> userReport = new ArrayList<User>();

    //private boolean includePrivate;
    //private LoggerInterface logger;
    //private int maxFollower=-1;
    /*public void setMaxFollower(int num){
    this.maxFollower = num;}*/
 /*public void setIncludePrivate(boolean include){
    this.includePrivate=include;}*/
    public AccountEngine(FollowFilter engineSetting) {
        this.followEngineSetting = engineSetting;
        //this.account = accountName;
        postList = new ArrayList<>();
        userList = new ArrayList<User>();
        //this.logger = log;
    }

    public AccountEngine(CleanerFilter engineSetting) {
        this.cleanerEngineSetting = engineSetting;
        //this.account = accountName;
        postList = new ArrayList<>();
        userList = new ArrayList<User>();
        //this.logger = log;
    }

    public void analyzeAccount(User usertoAnalyze) throws Exception {
        String response2 = newexec(URLGenerator.generateAccountA1(usertoAnalyze.getUsername()));
        if (Engine.isJSONValid(response2)) {
            AccountParser newParser = new AccountParser(response2);
            User analyzedUser = newParser.analyzeUser();
            followEngineSetting.getCustomRunnerInterface().onUserAnalyzed(analyzedUser);
        }
    }

    public ArrayList<User> getCleanFollowerList() throws Exception {
        boolean firstpage = true;
        int currentAccount = 1;
        String response = newexec(URLGenerator.generateAccountA1(cleanerEngineSetting.getUser().getUsername()));

        AccountParser parser = new AccountParser(response);

        String id = parser.getAccountId();
        while (parser.isHas_next_page()) {
            cleanerEngineSetting.getCustomRunnerInterface().logMessage("Gathering data...");
            response = newexec(URLGenerator.generateURLforFollowing(id, firstpage, parser.getEnd_cursor()));

            parser = new AccountParser(response);
            parser.parseFollowing();
            Iterator<User> userl = parser.getFollowerList().iterator();
            while (userl.hasNext()) {
                try {
                    User user = userl.next();
                    String response2 = newexec(URLGenerator.generateAccountA1(user.getUsername()));
                    if (Engine.isJSONValid(response2)) {
                        AccountParser newParser = new AccountParser(response2);
                        User analyzedUser = newParser.analyzeUser();
                        cleanerEngineSetting.getCustomRunnerInterface().logMessage("Checking " + user.getUsername() + "(" + analyzedUser.getFollowerCount() + ") | " + currentAccount);

                        if (!analyzedUser.isIsFollowingBack()) {
                            userList.add(user);
                            cleanerEngineSetting.getCustomRunnerInterface().onUserSuccessfullyFiltered(user);
                            cleanerEngineSetting.getCustomRunnerInterface().logMessage(analyzedUser.getUsername() + " is not following you back");

                        }

                    }

                    firstpage = false;

                } catch (Exception e) {
                    System.out.println("Unresolved error when checking " + userl.next().getUsername());
                    cleanerEngineSetting.getCustomRunnerInterface().logMessage("error when checking " + userl.next().getUsername());
                }
                currentAccount++;
            }

        }
        cleanerEngineSetting.getCustomRunnerInterface().onUserListAdded(userList);
        cleanerEngineSetting.getCustomRunnerInterface().logMessage("Total pengikut setelah difilter :" + userList.size());
        System.out.println("Total parsed follower : " + userList.size());
        return userList;
    }

    private FilterResult filterUser(User user) {
        FilterResult filterResult = new FilterResult();
        StringBuilder build = new StringBuilder("--Begin Analyzing\n");
        //cek jika user sudah di follow
        if (user.isIsFollowed()) {
            followEngineSetting.getCustomRunnerInterface().logMessage("Removing because user already followed");
            System.out.println("Removing because user already followed");
            filterResult.setRes(false);
            filterResult.setUserStatus("Removed because already followed");
            return filterResult;
        }

        //jika tidak include private account
        if (!followEngineSetting.isIncludePrivateAccount()) {
            if (user.getIsPrivate()) {
                //jika tidak private
                followEngineSetting.getCustomRunnerInterface().logMessage("Removing " + user.getUsername() + " because private");
                System.out.println("Removing " + user.getUsername() + " because private");
                //  userl.remove();
                //     return false;
                filterResult.setRes(false);
                filterResult.setUserStatus("Removed because private");
                return filterResult;
            }
        }

        //jika jumlah follower lebih banyak dari pada jumlah yang ditentukan
        if (followEngineSetting.getMaxFollowerofAnAccount() != 0 && user.getFollowerCount() > followEngineSetting.getMaxFollowerofAnAccount()) {
            followEngineSetting.getCustomRunnerInterface().logMessage("removing " + user.getUsername() + " because " + user.getFollowerCount() + " > " + followEngineSetting.getMaxFollowerofAnAccount());
            //return false;
            filterResult.setRes(false);
            filterResult.setUserStatus("Removed because follower more than maximum follower");
            return filterResult;
        }

        //detect if this account is a bot
        //needs a fixing
        if (user.getFollowingCount() > user.getFollowerCount()) {
        build.append("Username : " + user.getUsername());
        build.append("\nFollower : " + user.getFollowerCount());
        build.append("\nFollowing :" + user.getFollowingCount());
        build.append("\nPost : " + user.getPostCount());
        followEngineSetting.getCustomRunnerInterface().logMessage(build.toString());
        
            //jika following > follower
            if (user.getPostCount() < 4) {
                filterResult.setRes(false);
                filterResult.setUserStatus("Removed because considered as bot");
                followEngineSetting.getCustomRunnerInterface().logMessage("Removing " + user.getUsername() + " because considered as a bot");
               
                return filterResult;
            }
            
        }

        //default value 
        filterResult.setRes(true);
        filterResult.setUserStatus("Added to follower list.");
        return filterResult;
    }

    private class FilterResult {

        private boolean res = true;
        private String userStatus;

        public boolean isRes() {
            return res;
        }

        public void setRes(boolean res) {
            this.res = res;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

    }

    public ArrayList<User> getFollowerList() throws Exception {
        boolean firstpage = true;
        String response = newexec(URLGenerator.generateAccountA1(followEngineSetting.getTargetAccount()));

        AccountParser parser = new AccountParser(response);

        String id = parser.getAccountId();
        while (userList.size() < (followEngineSetting.getJumlahFollow()) && parser.isHas_next_page()) {
            followEngineSetting.getCustomRunnerInterface().logMessage("Gathering data...");
            response = newexec(URLGenerator.generateURLforFollowers(id, firstpage, parser.getEnd_cursor()));

            parser = new AccountParser(response);
            parser.parseFollower();

            for (User user : parser.getFollowerList()) {
                if (userList.size() >= followEngineSetting.getJumlahFollow()) {
                    break;
                }
                try {

                    String response2 = newexec(URLGenerator.generateAccountA1(user.getUsername()));
                    if (Engine.isJSONValid(response2)) {
                        AccountParser newParser = new AccountParser(response2);
                        User analyzedUser = newParser.analyzeUser();
                        followEngineSetting.getCustomRunnerInterface().logMessage("Checking " + user.getUsername());

                        //remove jika follower adalah user sekarang
                        if (user.getUsername().toLowerCase().equals(AutoStatic.LOGGED_USER.getUsername().toLowerCase())) {
                            //removing this user from list
                            followEngineSetting.getCustomRunnerInterface().logMessage("Removing this user from the list");
                            System.out.println("Removing this user from the list");
                            //   userl.remove();
                        } else {

                            if (userList.size() < followEngineSetting.getJumlahFollow()) {
                                FilterResult fil = filterUser(analyzedUser);
                                analyzedUser.setStatus(fil.getUserStatus());
                                /*StringBuilder build = new StringBuilder("adding to report \n");
                                build.append("username " + user.getUsername() + " followr : " + user.getFollowerCount());
                                System.out.println(build);*/
                                userReport.add(analyzedUser);
                                if (fil.isRes()) {
                                    userList.add(user);

                                    followEngineSetting.getCustomRunnerInterface().onUserSuccessfullyFiltered(user);
                                }

                                /*    if (analyzedUser.isIsFollowed()) {
                                    //jika suda di follow
                                    followEngineSetting.getCustomRunnerInterface().logMessage("Removing " + user.getUsername() + " because already followed");
                                    System.out.println("Removing " + user.getUsername() + " because already followed");

                                    //userl.remove();
                                }*/ //else {
                                //jika tidakk include private, defaultnya include
                                //boolean removed = false;
                                /*if (!followEngineSetting.isIncludePrivateAccount()) {
                                        if (analyzedUser.getIsPrivate()) {
                                            //jika tidak private
                                            followEngineSetting.getCustomRunnerInterface().logMessage("Removing " + user.getUsername() + " because private");
                                            System.out.println("Removing " + user.getUsername() + " because private");
                                            //  userl.remove();
                                            removed = true;
                                        }
                                    }*/
 /*  if (!removed) {
                                        /*if (analyzedUser.getFollowerCount() > followEngineSetting.getMaxFollowerofAnAccount()) {
                                            followEngineSetting.getCustomRunnerInterface().logMessage("removing " + user.getUsername() + " because " + analyzedUser.getFollowerCount() + " > " + followEngineSetting.getMaxFollowerofAnAccount());
                                            removed = true;

                                        } else {
                                            //di sini
                                            if (userList.size() < followEngineSetting.getJumlahFollow()) {
                                                userList.add(user);
                                                followEngineSetting.getCustomRunnerInterface().onUserSuccessfullyFiltered(user);
                                                System.out.println("masuk sini");
                                            } else {
                                                break;
                                            }
                                        }
                                    }*/
                                //}
                            }

                        }
                    }

                    firstpage = false;

                } catch (Exception e) {
                    System.out.println("Unresolved error when checking " + user.getUsername());
                    followEngineSetting.getCustomRunnerInterface().logMessage("error when checking " + user.getUsername());
                }
            }

        }
        followEngineSetting.getCustomRunnerInterface().onUserListAdded(userList);
        followEngineSetting.getCustomRunnerInterface().logMessage("Total pengikut setelah difilter :" + userList.size());
        System.out.println("Total parsed follower : " + userList.size());
        //make a report
        //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        FollowByAccountReport report = new FollowByAccountReport(followEngineSetting.getTargetAccount());
        report.print(userReport, CSVReport.ReportType.FOLLOW_BY_ACCOUNT);

        return userList;
    }

    public static void main(String[] args) {

    }

    @Override
    public void run() {
        String end_cursor = "";
        String accountid = "";
        AccountParser parser = new AccountParser();
        while (parser.isHas_next_page()) {
            String response = "";
            if (isFirstPage) {
                try {
                    response = newexec(followEngineSetting.getTargetAccount());
                    parser = new AccountParser(response);
                    parser.parse();
                    accountid = parser.getAccountId();
                    isFirstPage = false;
                } catch (Exception ex) {
                    Logger.getLogger(AccountEngine.class.getName()).log(Level.SEVERE, null, ex);
                
                }
            } else {

                //response = exec("https://www.instagram.com/graphql/query/?query_hash=472f257a40c653c64c666ce877d59d2b&variables={\"id\":\"" + accountid + "\",\"first\":50,\"after\":\"" + parser.getEnd_cursor() + "\"}", AUTOMATION);
                /*response = newexec(account,AutoStatic.AUTOMATION.getSessionID());
                parser = new AccountParser(response);
                parser.parse();
                 */
                System.out.println("this line is executed");
            }

            if (parser.isHas_next_page()) {
                end_cursor = parser.getEnd_cursor();
            }
            for (int i = 0; i < parser.getPostList().size(); i++) {

                postList.add(parser.getPostList().get(i));

            }
            //this break to stop the looping request because it's no longer available.
            //TODO : find a way to get this.
            //break;
        }

        System.out.println("Successfully gather " + postList.size() + " media from " + followEngineSetting.getTargetAccount());
    }

    @Override
    public void run(int maxPost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
