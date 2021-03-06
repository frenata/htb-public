/*    */ package htb.fatty.client.methods;
/*    */ 
/*    */ import htb.fatty.shared.logging.FattyLogger;
/*    */ import htb.fatty.shared.resources.User;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccessCheck
/*    */ {
/*    */   private static Map<String, Integer> functionMap;
/*    */   
/*    */   static {
/* 24 */     functionMap = (Map<String, Integer>)Stream.<Object[]>of(new Object[][] { { "ping", Integer.valueOf(1) }, { "whoami", Integer.valueOf(2) }, { "showFiles", Integer.valueOf(3) }, { "about", Integer.valueOf(4) }, { "contact", Integer.valueOf(5) }, { "open", Integer.valueOf(6) }, { "changePW", Integer.valueOf(7) }, { "uname", Integer.valueOf(8) }, { "users", Integer.valueOf(9) }, { "netstat", Integer.valueOf(10) }, { "ipconfig", Integer.valueOf(11) } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Integer)data[1]));
/* 25 */   } private static FattyLogger logger = new FattyLogger();
/*    */ 
/*    */   
/*    */   public static boolean checkAccess(String methodName, User user) {
/* 29 */     Integer methodID = functionMap.get(methodName);
/*
    if (methodID == null) {
       logger.logError("[-] Acces denied. User '" + user.getUsername() + "'with role '" + user.getRoleName() + "' called an unkown method with name '" + methodName + "'.");
       return true;
     }
     if (!user.getRole().isAllowed(methodID.intValue())) {
      logger.logError("[-] Acces denied. Method '" + methodName + "' was called by user '" + user.getUsername() + "'with role '" + user.getRoleName() + "'.");
      return true;
     } */
     return false;
  }
 }


/* Location:              /home/user/htb/labs/main/fatty/exfil/ftp/fatty-client.jar!/htb/fatty/client/methods/AccessCheck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */