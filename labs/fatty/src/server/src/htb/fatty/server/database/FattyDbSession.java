/*    */ package htb.fatty.server.database;
/*    */ 
/*    */ import htb.fatty.shared.logging.FattyLogger;
/*    */ import htb.fatty.shared.resources.Role;
/*    */ import htb.fatty.shared.resources.User;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ 
/*    */ 
/*    */ public class FattyDbSession
/*    */ {
/* 15 */   private static String url = "jdbc:mysql://localhost:3306/Fatty?serverTimezone=UTC";
/*    */   private Connection conn;
/* 17 */   private FattyLogger logger = new FattyLogger();
/*    */
/*    */   
/*    */   public FattyDbSession() throws SQLException {
/* 21 */     Connection conn = null;
/* 22 */     conn = DriverManager.getConnection(url, "user", "resu");
/* 23 */     this.conn = conn;
/*    */   }
/*    */   
/*    */   public void close() {
/*    */     try {
/* 28 */       this.conn.close();
/* 29 */     } catch (SQLException e) {
/* 30 */       this.logger.logError("[-] Failure while closing database connection.");
/* 31 */       this.logger.logError("[-] Exception was: '" + e.getMessage() + "'");
/*    */     } 
/*    */   }
/*    */   
/*    */   public User checkLogin(User user) throws LoginException {
/* 36 */     Statement stmt = null;
/* 37 */     ResultSet rs = null;
/* 38 */     User newUser = null;
/*    */     
/*    */     try {
/* 41 */       stmt = this.conn.createStatement();
/* 42 */       rs = stmt.executeQuery("SELECT id,username,email,password,role FROM users WHERE username='" + user.getUsername() + "'");
/*    */ 
/*    */       
/*    */       try {
/* 46 */         Thread.sleep(3000L);
/* 47 */       } catch (InterruptedException e) {
/* 48 */         return null;
/*    */       } 
/*    */       
/* 51 */       if (rs.next()) {
/*    */         
/* 53 */         int id = rs.getInt("id");
/* 54 */         String username = rs.getString("username");
/* 55 */         String email = rs.getString("email");
/* 56 */         String password = rs.getString("password");
/* 57 */         String role = rs.getString("role");
/* 58 */         newUser = new User(id, username, password, email, Role.getRoleByName(role), false);
/*    */         
/* 60 */         if (newUser.getPassword().equalsIgnoreCase(user.getPassword())) {
/* 61 */           return newUser;
/*    */         }
/* 63 */         throw new LoginException("Wrong Password!");
/*    */       } 
/*    */       
/* 66 */       throw new LoginException("Wrong Username!");
/*    */     }
/* 68 */     catch (SQLException e) {
/* 69 */       this.logger.logError("[-] Failure with SQL query: ==> SELECT id,username,email,password,role FROM users WHERE username='" + user.getUsername() + "' <==");
/* 70 */       this.logger.logError("[-] Exception was: '" + e.getMessage() + "'");
/*    */       
/* 72 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public class LoginException
/*    */     extends Exception {
/*    */     public LoginException(String s) {
/* 79 */       super(s);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/user/htb/labs/main/fatty/src/fatty-server.jar!/htb/fatty/server/database/FattyDbSession.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */