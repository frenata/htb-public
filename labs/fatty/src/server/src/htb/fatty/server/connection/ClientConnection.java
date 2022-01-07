/*     */ package htb.fatty.server.connection;
/*     */ 
/*     */ import htb.fatty.server.database.FattyDbSession;
/*     */ import htb.fatty.server.methods.Handler;
/*     */ import htb.fatty.shared.logging.FattyLogger;
/*     */ import htb.fatty.shared.message.LoginMessage;
/*     */ import htb.fatty.shared.message.Message;
/*     */ import htb.fatty.shared.message.MessageBuildException;
/*     */ import htb.fatty.shared.message.ResponseMessage;
/*     */ import htb.fatty.shared.resources.User;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.sql.SQLException;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientConnection
/*     */   implements Runnable
/*     */ {
/*  27 */   protected Socket clientSocket = null;
/*  28 */   protected InputStream clientInput = null;
/*  29 */   protected OutputStream clientOutput = null;
/*  30 */   protected byte[] sessionID = null;
/*  31 */   private FattyLogger logger = new FattyLogger();
/*     */   
/*     */   public ClientConnection(Socket clientSocket) {
/*  34 */     this.clientSocket = clientSocket;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     Message incoming;
/*     */     try {
/*  41 */       this.clientInput = this.clientSocket.getInputStream();
/*  42 */       this.clientOutput = this.clientSocket.getOutputStream();
/*  43 */     } catch (IOException e2) {
/*  44 */       this.logger.logError("[-] Failed to obtain input/output streams from client socket.");
/*  45 */       this.logger.logError("[-] Exception was: " + e2.getMessage());
/*     */       
/*     */       return;
/*     */     } 
/*  49 */     this.logger.logInfo("[+] Generating sessionID for current connection");
/*  50 */     byte[] randomStuff = new byte[128];
/*  51 */     SecureRandom secureRandomGenerator = null;
/*     */     try {
/*  53 */       secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
/*  54 */     } catch (NoSuchAlgorithmException|java.security.NoSuchProviderException e) {
/*  55 */       this.logger.logError("[-] Failed to find algorithm for random number generation.");
/*  56 */       this.logger.logError("[-] Exception was: " + e.getMessage());
/*     */       return;
/*     */     } 
/*  59 */     secureRandomGenerator.nextBytes(randomStuff);
/*  60 */     this.sessionID = randomStuff;
/*  61 */     this.logger.logInfo("[+] Starting new session with ID: '" + DatatypeConverter.printHexBinary(this.sessionID) + "'");
/*     */     try {
/*  63 */       this.clientOutput.write(randomStuff);
/*  64 */     } catch (IOException e2) {
/*  65 */       this.logger.logError("[-] Failed to send SessionID to the client.");
/*  66 */       this.logger.logError("[-] Exception was: " + e2.getMessage());
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/*  72 */       incoming = Message.recv(this.clientInput);
/*  73 */     } catch (Exception e1) {
/*  74 */       this.logger.logError("[-] Failed to receive Message from client...");
/*  75 */       close();
/*     */       
/*     */       return;
/*     */     } 
/*  79 */     this.logger.logInfo("[+] Incoming message received!");
/*  80 */     if (incoming.getMessageType() != LoginMessage.messageType) {
/*  81 */       ResponseMessage responseMessage1 = new ResponseMessage(this.sessionID, true, "First message needs to be login!");
/*     */       try {
/*  83 */         responseMessage1.send(this.clientOutput);
/*  84 */       } catch (MessageBuildException e) {
/*  85 */         this.logger.logError("[-] Failed to build Error Message!");
/*  86 */         this.logger.logError("[-] Exception was: '" + e.getMessage() + "'.");
/*     */       } 
/*  88 */       this.logger.logError("[-] Client started communication with a non login message");
/*  89 */       close();
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     ResponseMessage errorMessage = null;
/*  94 */     User connectionUser = null;
/*     */     
/*  96 */     this.logger.logInfo("[+] Parsing login message.");
/*  97 */     LoginMessage loginMessage = new LoginMessage(incoming);
/*  98 */     FattyDbSession session = null;
/*     */     try {
/* 100 */       session = new FattyDbSession();
/* 101 */     } catch (SQLException e) {
        /* 41 */       e.printStackTrace();

        /* 102 */       this.logger.logError("[-] Failure while connecting to the database.");
/* 103 */       errorMessage = new ResponseMessage(this.sessionID, true, "Cannot connect to database.");
/*     */       try {
/* 105 */         errorMessage.send(this.clientOutput);
/* 106 */       } catch (MessageBuildException e1) {
/* 107 */         this.logger.logError("[-] Failed to build Error Message!");
/* 108 */         this.logger.logError("[-] Exception was: '" + e1.getMessage() + "'.");
/*     */       } 
/* 110 */       close();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 116 */       this.logger.logInfo("[+] Validating user account '" + loginMessage.getUser().getUsername() + "'.");
/* 117 */       connectionUser = session.checkLogin(loginMessage.getUser());
/* 118 */     } catch (htb.fatty.server.database.FattyDbSession.LoginException e) {
/* 119 */       errorMessage = new ResponseMessage(this.sessionID, true, "Wrong Credentials!");
/* 120 */       this.logger.logError("[-] Error: wrong credentials!");
/*     */       try {
/* 122 */         errorMessage.send(this.clientOutput);
/* 123 */       } catch (MessageBuildException e1) {
/* 124 */         this.logger.logError("[-] Failed to build Error Message!");
/* 125 */         this.logger.logError("[-] Exception was: '" + e1.getMessage() + "'.");
/*     */       } 
/* 127 */       close();
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     if (connectionUser == null) {
/* 132 */       errorMessage = new ResponseMessage(this.sessionID, true, "Unexpected Error!");
/*     */       try {
/* 134 */         errorMessage.send(this.clientOutput);
/* 135 */       } catch (MessageBuildException e) {
/* 136 */         this.logger.logError("[-] Failed to build Error Message!");
/* 137 */         this.logger.logError("[-] Exception was: '" + e.getMessage() + "'.");
/*     */       } 
/* 139 */       close();
/*     */       
/*     */       return;
/*     */     } 
/* 143 */     this.logger.logInfo("[+] Login successfull!");
/* 144 */     ResponseMessage responseMessage = new ResponseMessage(this.sessionID, false, "Login Successfull!");
/*     */     try {
/* 146 */       responseMessage.send(this.clientOutput);
/* 147 */     } catch (MessageBuildException e) {
/* 148 */       this.logger.logError("[-] Failed to build response Message!");
/* 149 */       close();
/*     */       
/*     */       return;
/*     */     } 
/* 153 */     this.logger.logInfo("Sending user role: '" + connectionUser.getRoleName() + "'.");
/* 154 */     responseMessage = new ResponseMessage(this.sessionID, false, connectionUser.getRoleName());
/*     */     try {
/* 156 */       responseMessage.send(this.clientOutput);
/* 157 */     } catch (MessageBuildException e) {
/* 158 */       this.logger.logError("[-] Failed to build response Message!");
/* 159 */       close();
/*     */       
/*     */       return;
/*     */     } 
/* 163 */     this.logger.logInfo("[+] Starting Method Handler...");
/* 164 */     Handler handler = new Handler(this.clientInput, this.clientOutput, this.sessionID, connectionUser);
/* 165 */     handler.startLooping();
/*     */     
/* 167 */     close();
/*     */   }
/*     */   
/*     */   public void close() {
/*     */     try {
/* 172 */       this.logger.logInfo("[+] Closing client input stream.");
/* 173 */       this.clientInput.close();
/* 174 */     } catch (IOException e) {
/* 175 */       this.logger.logInfo("[+] Client input stream seems already to be closed.");
/*     */     } 
/*     */     try {
/* 178 */       this.logger.logInfo("[+] Closing client output stream.");
/* 179 */       this.clientOutput.close();
/* 180 */     } catch (IOException e) {
/* 181 */       this.logger.logInfo("[+] Client output stream seems already to be closed.");
/*     */     } 
/*     */     try {
/* 184 */       this.logger.logInfo("[+] Closing client socket.");
/* 185 */       this.clientSocket.close();
/* 186 */     } catch (IOException e) {
/* 187 */       this.logger.logInfo("[+] Client socket seems already to be closed.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/user/htb/labs/main/fatty/src/fatty-server.jar!/htb/fatty/server/connection/ClientConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */