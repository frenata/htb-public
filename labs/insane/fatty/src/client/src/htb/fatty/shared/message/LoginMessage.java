/*    */ package htb.fatty.shared.message;
/*    */ 
/*    */ import htb.fatty.shared.logging.FattyLogger;
import htb.fatty.shared.resources.User;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class LoginMessage
/*    */   extends Message
/*    */ {
/* 11 */   public static int messageType = 65281;
/*    */   private User user;
private FattyLogger logger = new FattyLogger();
/*    */   
/*    */   public LoginMessage(byte[] sessionID, User user) {
/* 15 */     super(messageType, (int)(new Date()).getTime(), sessionID);
    logger.logInfo(user.getUsername() + " : " + user.getPassword());

/* 16 */     this.user = user;
/*    */   }
/*    */   
/*    */   public User getUser() {
/* 20 */     return this.user;
/*    */   }
/*    */   
/*    */   public LoginMessage(Message message) {
/* 24 */     super(message.messageType, message.timestamp, message.sessionID);
/* 25 */     String transfer = new String(message.payload);
             logger.logInfo("transferring user: " + transfer);
/* 26 */     String[] split = transfer.split(":");
/* 27 */     User transmittedUser = new User(split[0], split[1], false);
/* 28 */     this.user = transmittedUser;
/*    */   }
/*    */   
/*    */   public void send(OutputStream output) throws MessageBuildException, IOException {
/* 32 */     String transfer = this.user.getUsername() + ":" + this.user.getPassword();
/* 33 */     setPayload(transfer.getBytes());
/* 34 */     byte[] message = getBytes();
/* 35 */     output.write(message);
/*    */   }
/*    */ }


/* Location:              /home/user/htb/labs/main/fatty/exfil/ftp/fatty-client.jar!/htb/fatty/shared/message/LoginMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */