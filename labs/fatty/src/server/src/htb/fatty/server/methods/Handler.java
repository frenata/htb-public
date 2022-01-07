/*    */ package htb.fatty.server.methods;
/*    */ 
/*    */ import htb.fatty.shared.logging.FattyLogger;
/*    */ import htb.fatty.shared.message.ActionMessage;
/*    */ import htb.fatty.shared.message.Message;
/*    */ import htb.fatty.shared.message.MessageBuildException;
/*    */ import htb.fatty.shared.message.MessageLogoffException;
/*    */ import htb.fatty.shared.message.MessageParseException;
/*    */ import htb.fatty.shared.message.ResponseMessage;
/*    */ import htb.fatty.shared.resources.User;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class Handler
/*    */ {
/*    */   private InputStream clientIn;
/*    */   private OutputStream clientOut;
/*    */   private byte[] sessionID;
/*    */   private User user;
/*    */   private Message incomingMessage;
/*    */   private ActionMessage actionMessage;
/*    */   private ResponseMessage responseMessage;
/* 25 */   private FattyLogger logger = new FattyLogger();
/*    */   
/*    */   public Handler(InputStream clientIn, OutputStream clientOut, byte[] sessionID, User user) {
/* 28 */     this.clientIn = clientIn;
/* 29 */     this.clientOut = clientOut;
/* 30 */     this.sessionID = sessionID;
/* 31 */     this.user = user;
/*    */   }
/*    */   
/*    */   public void startLooping() {
/*    */     do {
/*    */       try {
/* 37 */         recvActionMessage();
/* 38 */       } catch (MessageLogoffException e1) {
/* 39 */         this.logger.logInfo("[+] Logoff message received.");
/*    */         break;
/* 41 */       } catch (Exception e) {
/* 42 */         this.logger.logError("[-] Failure while parsing the ActionMessage.");
/* 43 */         this.logger.logError("[-] Exception was: '" + e.getMessage() + "'.");
/* 44 */         this.responseMessage = new ResponseMessage(this.sessionID, true, "Failed to parse action Message");
/*    */         
/* 46 */         if (sendResponse()) {
/*    */           break;
/*    */         }
/*    */       } 
/* 50 */       byte[] responseString = executeAction();
/* 51 */       this.responseMessage = new ResponseMessage(this.sessionID, false, responseString);
/* 52 */     } while (!sendResponse());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void recvActionMessage() throws MessageParseException, MessageBuildException, MessageLogoffException {
/* 59 */     this.incomingMessage = Message.recv(this.clientIn);
/* 60 */     this.actionMessage = new ActionMessage(this.incomingMessage);
/*    */   }
/*    */   
/*    */   public byte[] executeAction() {
/* 64 */     ArrayList<String> args = this.actionMessage.getArguments();
/* 65 */     String actionName = this.actionMessage.getCommand();
/* 66 */     byte[] response = "Method not found!".getBytes();
/* 67 */     switch (actionName) { case "ping":
/* 68 */         response = Commands.ping(args, this.user).getBytes(); break;
/* 69 */       case "whoami": response = Commands.whoami(args, this.user).getBytes(); break;
/* 70 */       case "files": response = Commands.files(args, this.user).getBytes(); break;
/* 71 */       case "open": response = Commands.open(args, this.user); break;
/* 72 */       case "uname": response = Commands.uname(args, this.user).getBytes(); break;
/* 73 */       case "ipconfig": response = Commands.ipconfig(args, this.user).getBytes(); break;
/* 74 */       case "netstat": response = Commands.netstat(args, this.user).getBytes(); break;
/* 75 */       case "users": response = Commands.users(args, this.user).getBytes(); break;
/* 76 */       case "changePW": response = Commands.changePW(args, this.user).getBytes(); break; }
/*    */     
/* 78 */     return response;
/*    */   }
/*    */   
/*    */   public boolean sendResponse() {
/*    */     try {
/* 83 */       this.responseMessage.send(this.clientOut);
/* 84 */     } catch (MessageBuildException e) {
/* 85 */       this.logger.logError("[-] Failure while generating the response message.");
/* 86 */       this.logger.logError("[-] Exception was: '" + e.getMessage() + "'.");
/* 87 */       return true;
/*    */     } 
/* 89 */     return false;
/*    */   }
/*    */ }


/* Location:              /home/user/htb/labs/main/fatty/src/fatty-server.jar!/htb/fatty/server/methods/Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */