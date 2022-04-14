/*     */ import java.io.Serializable;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import javax.xml.bind.DatatypeConverter;
/*     */ 
/*     */ 
/*     */ public class User
/*     */   implements Serializable
/*     */ {
/*     */   int uid;
/*     */   String username;
/*     */   String password;
/*     */   String email;
/*     */   
/*     */   public User(int uid, String username, String password, String email) {
/*  20 */     this.uid = uid;
/*  21 */     this.username = username;
/*     */     
/*  23 */     String hashString = this.username + password + "clarabibimakeseverythingsecure";
/*  24 */     MessageDigest digest = null;
/*     */     try {
/*  26 */       digest = MessageDigest.getInstance("SHA-256");
/*  27 */     } catch (NoSuchAlgorithmException e) {
/*  28 */       e.printStackTrace();
/*     */     } 
/*  30 */     byte[] hash = digest.digest(hashString.getBytes(StandardCharsets.UTF_8));
/*     */     
/*  32 */     this.password = DatatypeConverter.printHexBinary(hash);
System.out.println(this.password);
/*  33 */     this.email = email;
/*     */   }
/*     */ 

public static void main(String[] argv) {
System.out.println("test");
User user = new User(1, "qtc", "clarabibi", "foo@bar.com");
}

/*     */ }


/* Location:              /home/user/htb/labs/main/fatty/src/fatty-server.jar!/htb/fatty/shared/resources/User.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
