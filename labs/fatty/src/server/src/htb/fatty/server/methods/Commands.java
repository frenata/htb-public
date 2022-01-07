/*     */ package htb.fatty.server.methods;
/*     */ 
/*     */ import htb.fatty.shared.logging.FattyLogger;
/*     */ import htb.fatty.shared.resources.User;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Commands
/*     */ {
/*  19 */   public static FattyLogger logger = new FattyLogger();
/*     */ 
/*     */   
/*     */   public static String ping(ArrayList<String> args, User user) {
/*  23 */     logger.logInfo("[+] Method 'ping' was called.");
/*  24 */     int methodID = 1;
/*  25 */     if (!user.getRole().isAllowed(methodID)) {
/*  26 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/*  27 */       return "Error: Method 'ping' is not allowed for this user account";
/*     */     } 
/*  29 */     return "Pong";
/*     */   }
/*     */   
/*     */   public static String whoami(ArrayList<String> args, User user) {
/*  33 */     logger.logInfo("[+] Method 'whoami' was called.");
/*  34 */     int methodID = 2;
/*  35 */     if (!user.getRole().isAllowed(methodID)) {
/*  36 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/*  37 */       return "Error: Method 'whoami' is not allowed for this user account";
/*     */     } 
/*  39 */     return "Username: " + user.getUsername() + "\nRolename: " + user.getRoleName();
/*     */   }
/*     */   
/*     */   public static String files(ArrayList<String> args, User user) {
/*  43 */     logger.logInfo("[+] Method 'files' was called.");
/*  44 */     int methodID = 3;
/*  45 */     if (!user.getRole().isAllowed(methodID)) {
/*  46 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/*  47 */       return "Error: Method 'files' is not allowed for this user account";
/*     */     } 
/*  49 */     String response = "";
/*     */     
/*  51 */     int infiniteLoopPrevention = 0;
/*  52 */     String folder = args.get(0);
/*  53 */     while (folder.contains("/../")) {
/*  54 */       folder = folder.replaceAll("/\\.\\./", "");
/*  55 */       infiniteLoopPrevention++;
/*  56 */       if (infiniteLoopPrevention > 100) {
/*  57 */         return "Error: Infinite Loop detected!";
/*     */       }
/*     */     } 
/*  60 */     String folderName = "/opt/fatty/files/" + folder;
/*  61 */     File folderFile = new File(folderName);
/*  62 */     String[] files = folderFile.list();
/*  63 */     if (files != null) {
/*  64 */       for (String name : files) {
/*  65 */         response = response + name + "\n";
/*     */       }
/*     */     } else {
/*  68 */       response = "Directory with name '" + folderName + "' does not exist.";
/*     */     } 
/*  70 */     return response;
/*     */   }
/*     */   public static byte[] open(ArrayList<String> args, User user) {
/*     */     byte[] fileBytes;
/*  74 */     logger.logInfo("[+] Method 'open' was called.");
/*  75 */     int methodID = 6;
/*  76 */     if (!user.getRole().isAllowed(methodID)) {
/*  77 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/*  78 */       return "Error: Method 'open' is not allowed for this user account".getBytes();
/*     */     } 
/*     */     
/*  81 */     int infiniteLoopPrevention = 0;
/*     */     
/*  83 */     String file = (String)args.get(0) + "/" + (String)args.get(1);
/*  84 */     while (file.contains("/../")) {
/*  85 */       file = file.replaceAll("/\\.\\./", "");
/*  86 */       infiniteLoopPrevention++;
/*  87 */       if (infiniteLoopPrevention > 100) {
/*  88 */         return "Error: Infinite Loop detected!".getBytes();
/*     */       }
/*     */     } 
/*  91 */     String fileName = "/opt/fatty/files/" + file;
/*     */     
/*  93 */     File file2 = new File(fileName);
/*     */     
/*     */     try {
/*  96 */       fileBytes = Files.readAllBytes(file2.toPath());
/*  97 */     } catch (IOException e) {
/*  98 */       logger.logError("[-] Failure while reading contents of file '" + fileName + "'.");
/*  99 */       logger.logError("[-] Exception was: " + e.getMessage());
/* 100 */       return ("[-] Failed to open file '" + fileName + "'.").getBytes();
/*     */     } 
/*     */     
/* 103 */     return fileBytes;
/*     */   }
/*     */   
/*     */   public static String changePW(ArrayList<String> args, User user) {
/* 107 */     logger.logInfo("[+] Method 'changePW' was called.");
/* 108 */     int methodID = 7;
/* 109 */     if (!user.getRole().isAllowed(methodID)) {
/* 110 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/* 111 */       return "Error: Method 'changePW' is not allowed for this user account";
/*     */     } 
/* 113 */     String response = "";
/*     */     
/* 115 */     String b64User = args.get(0);
logger.logInfo(b64User);
/* 116 */     byte[] serializedUser = Base64.getDecoder().decode(b64User.getBytes());
/* 117 */     ByteArrayInputStream bIn = new ByteArrayInputStream(serializedUser);
/*     */     
/*     */     try {
/* 120 */       ObjectInputStream oIn = new ObjectInputStream(bIn);
/*     */       
/* 122 */       User user1 = (User)oIn.readObject();
/* 123 */     } catch (Exception e) {
/* 124 */       e.printStackTrace();
/* 125 */       response = response + "Error: Failure while recovering the User object.";
/* 126 */       return response;
/*     */     } 
/*     */     
/* 129 */     response = response + "Info: Your call was successful, but the method is not fully implemented yet.";
/* 130 */     return response;
/*     */   }
/*     */   
/*     */   public static String uname(ArrayList<String> args, User user) {
/* 134 */     logger.logInfo("[+] Method 'uname' was called.");
/* 135 */     int methodID = 8;
/* 136 */     if (!user.getRole().isAllowed(methodID)) {
/* 137 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/* 138 */       return "Error: Method 'uname' is not allowed for this user account";
/*     */     } 
/* 140 */     String response = "";
/*     */     try {
/* 142 */       Process p = Runtime.getRuntime().exec("uname -a");
/* 143 */       String s = "";
/* 144 */       BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
/* 145 */       BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/*     */       
/* 147 */       while ((s = stdInput.readLine()) != null) {
/* 148 */         response = response + s + "\n";
/*     */       }
/*     */       
/* 151 */       while ((s = stdError.readLine()) != null) {
/* 152 */         response = response + s + "\n";
/*     */       }
/*     */     }
/* 155 */     catch (IOException e) {
/* 156 */       e.printStackTrace();
/* 157 */       return "";
/*     */     } 
/* 159 */     return response;
/*     */   }
/*     */   
/*     */   public static String users(ArrayList<String> args, User user) {
/* 163 */     logger.logInfo("[+] Method 'users' was called.");
/* 164 */     int methodID = 9;
/* 165 */     if (!user.getRole().isAllowed(methodID)) {
/* 166 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/* 167 */       return "Error: Method 'users' is not allowed for this user account";
/*     */     } 
/* 169 */     String response = "";
/*     */     try {
/* 171 */       Process p = Runtime.getRuntime().exec("ls -l /home");
/* 172 */       String s = "";
/* 173 */       BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
/* 174 */       BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/*     */       
/* 176 */       while ((s = stdInput.readLine()) != null) {
/* 177 */         response = response + s + "\n";
/*     */       }
/*     */       
/* 180 */       while ((s = stdError.readLine()) != null) {
/* 181 */         response = response + s + "\n";
/*     */       }
/*     */     }
/* 184 */     catch (IOException e) {
/* 185 */       e.printStackTrace();
/* 186 */       return "";
/*     */     } 
/* 188 */     return response;
/*     */   }
/*     */   
/*     */   public static String netstat(ArrayList<String> args, User user) {
/* 192 */     logger.logInfo("[+] Method 'netstat' was called.");
/* 193 */     int methodID = 10;
/* 194 */     if (!user.getRole().isAllowed(methodID)) {
/* 195 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/* 196 */       return "Error: Method 'netstat' is not allowed for this user account";
/*     */     } 
/* 198 */     String response = "";
/*     */     try {
/* 200 */       Process p = Runtime.getRuntime().exec("netstat -tln");
/* 201 */       String s = "";
/* 202 */       BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
/* 203 */       BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/*     */       
/* 205 */       while ((s = stdInput.readLine()) != null) {
/* 206 */         response = response + s + "\n";
/*     */       }
/*     */       
/* 209 */       while ((s = stdError.readLine()) != null) {
/* 210 */         response = response + s + "\n";
/*     */       }
/*     */     }
/* 213 */     catch (IOException e) {
/* 214 */       e.printStackTrace();
/* 215 */       return "";
/*     */     } 
/* 217 */     return response;
/*     */   }
/*     */   
/*     */   public static String ipconfig(ArrayList<String> args, User user) {
/* 221 */     logger.logInfo("[+] Method 'ipconfig' was called.");
/* 222 */     int methodID = 11;
/* 223 */     if (!user.getRole().isAllowed(methodID)) {
/* 224 */       logger.logError("[+] Access denied. Method with id '" + methodID + "' was called by user '" + user.getUsername() + "' with role '" + user.getRoleName() + "'.");
/* 225 */       return "Error: Method 'ipconfig' is not allowed for this user account";
/*     */     } 
/* 227 */     String response = "";
/*     */     try {
/* 229 */       Process p = Runtime.getRuntime().exec("ifconfig");
/* 230 */       String s = "";
/* 231 */       BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
/* 232 */       BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/*     */       
/* 234 */       while ((s = stdInput.readLine()) != null) {
/* 235 */         response = response + s + "\n";
/*     */       }
/*     */       
/* 238 */       while ((s = stdError.readLine()) != null) {
/* 239 */         response = response + s + "\n";
/*     */       }
/*     */     }
/* 242 */     catch (IOException e) {
/* 243 */       e.printStackTrace();
/* 244 */       return "";
/*     */     } 
/* 246 */     return response;
/*     */   }
/*     */ }


/* Location:              /home/user/htb/labs/main/fatty/src/fatty-server.jar!/htb/fatty/server/methods/Commands.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */