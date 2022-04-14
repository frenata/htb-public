/*    */ package htb.fatty.server.run;
/*    */ 
/*    */ import htb.fatty.server.connection.Connection;
/*    */ import htb.fatty.shared.logging.FattyLogger;
/*    */ import org.springframework.context.support.ClassPathXmlApplicationContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Starter
/*    */ {
/*    */   public static void main(String[] argv) {
/* 13 */     FattyLogger logger = new FattyLogger();
/* 14 */     logger.logInfo("[+] Fatty starts running. Run Fatty, run!");
/*    */     
/* 16 */     ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
/* 17 */     Connection obj = (Connection)classPathXmlApplicationContext.getBean("connection");
/* 18 */     logger.logInfo("[+] Starting FatClient Server on port '" + obj.getPort() + "'.");
/* 19 */     obj.startListening();
/*    */   }
/*    */ }


/* Location:              /home/user/htb/labs/main/fatty/src/fatty-server.jar!/htb/fatty/server/run/Starter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */