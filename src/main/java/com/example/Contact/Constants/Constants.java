package com.example.Contact.Constants;

public class Constants {
    public static final String Stock_Symbol_Not_Blank ="Stock symbol must not be blank";
    public static final String Order_Type ="Order type must be either BUY or SELL (case-insensitive)";
    public static final String Price_Constraint ="Price must be a double greater than 0.0";
    public static final String Password_Constraint="Password doesn't match the expectations";
    public static final String Email_Constraint="Email should not be blank";
    public static final String Username_Constraint="Username should not be blank";
    public static final String Add_Symbols= "/watchlist/add-symbols";
    public static final String Get_Symbols= "/watchlist/get-symbols";
    public static final String Pause_Order_Url = "/pause-order";
    public static final String Password_Not_Blank="Password cannot be blank";
    public static final String Price_Positive="Price must be greater than zero";
    public static final String Quantity_Constraint = "Quantity must be an integer";
    public static final String Stock_Symbol_Length ="Stock symbol length must be between 10 and 25 characters";
    public static final String Dashboard = "/dashboard";
    public static final String Add_Order = "/add-order";
    public static final String Cancel_Order = "/cancel-order";
    public static final String Get_Portfolio = "/get-portfolio";
    public static final String Get_Trade_History = "/get-trade-history";
    public static final String Add_Groups="watchlist/add-groups";
    public static final String Get_Watchlist_Symbols ="/watchlist/get-symbols";
    public static final String Get_Watchlist_Groups="/watchlist/get-groups";
    public static final String Resume_Order_Url = "/resume-order";

    public static final String Quote ="/quote";
    public static final String Get_Symbol ="/get-symbol";
    public static final String Get_All_Symbols ="/get-symbol/all";
    public static final String Trading = "/trading";
    public static final String Trading_Registration ="/register/user-registration";
    public static final String Wrapper_Error ="Error initializing input stream wrapper";
    public static  final String Session = "/session";
    public static final String Login ="/user-login";
    public static final String Time_Stamp_Pattern = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String Logout ="/user-logout";
    public static  final String User_Session="/session/user-session";
    public static final String Success = "Successful";
    public static final String Duplicate_UserName = "Username Already present";
    public static final String User_Not_Found = "User Not found, try with different credentials";
    public static final String User_Not_Log_In = "User not logged in";
    public static final String Order_Not_Found = "Order Not found";
    public static final String Pending = "PENDING";

    public static final String Executed = "EXECUTED";

    public static final String Cancelled = "CANCELLED";
    public static final String Watchlist_Group ="Watchlist Group added Successfully with Group Id: ";
    public static final String Buy = "BUY";
    public static final String Sell = "SELL";
    public static  final String Log_Out_In ="Session Expired, Logout user and Login again";
    public static  final String JWT_Expired =" jwtTokenError";
    public static final String No_Privilege = "You can't modify others order, Enter your orderId ";
    public static final String Order_Integer = "Enter an integer value for orderId";
    public static final String Sym_Not_Found ="Symbol not found";
    public static final String Duplicate_Email = "Email Id Already present";
    public static final String Order_Executed = "Order executed Already";
    public static final String Logged_In = "User already logged in";
    public static final String Logged_Out = "User logged out successfully";
    public static final String Grp_Id_Null = "groupId cannot be null";
    public static final String Grp_Id_Not_Found_User ="Watchlist group ID not found for current user";
    public static final String Symbol_Value = "symbolCache";
    public static final String Symbol_Key = "'allSymbols'";
    public static final String Cache_Manger ="cacheManager";
    public static final String Unless ="#result == null";

    public static final String Paused ="PAUSED";
    public static final String Pause_Order ="Order paused successfully";
    public static final String Resume_Order ="Order resumed successfully";
    public static final String Order_Not_Paused ="Order is not paused successfully";
}
