/*    */ package htb.fatty.server.connection;
/*    */ 
/*    */ import htb.fatty.shared.connection.ConnectionContext;
/*    */ import htb.fatty.shared.connection.SecretHolder;
/*    */ import htb.fatty.shared.connection.TrustedFatty;
/*    */ import java.io.IOException;
/*    */ import java.security.UnrecoverableKeyException;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.SSLServerSocket;
/*    */ import javax.net.ssl.SSLServerSocketFactory;
/*    */ import javax.net.ssl.SSLSocket;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Connection
/*    */ {
/*    */   protected ConnectionContext connectionContext;
/*    */   protected TrustedFatty sslContext;
/*    */   private SecretHolder secretHolder;
/*    */   
/*    */   public Connection(ConnectionContext connectionContext, TrustedFatty sslContext, SecretHolder secretHolder) {
/* 26 */     this.connectionContext = connectionContext;
/* 27 */     this.sslContext = sslContext;
/* 28 */     this.secretHolder = secretHolder;
/* 29 */     if (!this.secretHolder.getSecret().contentEquals("clarabibiclarabibiclarabibi")) {
/* 30 */       System.out.println("What?");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void startListening() {
/* 36 */     SSLContext realContext = null;
/*    */     try {
/* 38 */       realContext = this.sslContext.getSSLContext();
/* 39 */     } catch (UnrecoverableKeyException|java.security.KeyManagementException|java.security.KeyStoreException|java.security.NoSuchAlgorithmException|java.security.cert.CertificateException|IOException e) {
/*    */       
/* 41 */       e.printStackTrace();
/*    */     } 
/* 43 */     SSLServerSocketFactory ssf = realContext.getServerSocketFactory();
/* 44 */     SSLServerSocket serverSocket = null;
/*    */     try {
/* 46 */       serverSocket = (SSLServerSocket)ssf.createServerSocket(this.connectionContext.port);
/* 47 */     } catch (IOException e) {
/* 48 */       e.printStackTrace();
/*    */     }  while (true) {
/*    */       try {
/*    */         while (true)
/* 52 */         { SSLSocket clientSocket = (SSLSocket)serverSocket.accept();
/* 53 */           (new Thread(new ClientConnection(clientSocket))).start(); }  //break;
/* 54 */       } catch (IOException e1) {
/* 55 */         e1.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHostname() {
/* 61 */     return this.connectionContext.hostname;
/*    */   }
/*    */   
/*    */   public String getPort() {
/* 65 */     return Integer.toString(this.connectionContext.port);
/*    */   }
/*    */ }


/* Location:              /home/user/htb/labs/main/fatty/src/fatty-server.jar!/htb/fatty/server/connection/Connection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */