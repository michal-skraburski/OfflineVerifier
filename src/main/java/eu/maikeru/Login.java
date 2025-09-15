package eu.maikeru;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Login implements Serializable {
    private final String name;
    private final String hashPass;
    private final ArrayList<String> trustedIps;
    
    public Login(String name, String password) {
        this.name = name;
        this.trustedIps = new ArrayList<>(); 
        this.hashPass = Login.hash(password);
    }

    public static String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return bytesToHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            // Do nothing.
            return null;
        }
    }

    private static String bytesToHex(byte[] hash) {
      StringBuilder hexString = new StringBuilder(2 * hash.length);
      for (int i = 0; i < hash.length; i++) {
          String hex = Integer.toHexString(0xff & hash[i]);
          if(hex.length() == 1) {
              hexString.append('0');
          }
          hexString.append(hex);
      }
      return hexString.toString();
    }
    public boolean hasIp(String ip) {
       return trustedIps.stream().filter(p -> p.equals(ip)).findFirst().isPresent(); 
    }

    public void trustIp(String ip) {
        this.trustedIps.add(ip);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<String> getIpsCopy(){
        return (ArrayList<String>) trustedIps.clone();         
    }

    public boolean distrustIp(String ip) {
        boolean pred = 
        this.trustedIps.removeIf(lip -> lip.equals(ip));

        return pred;
    }

    public String getName() {
        return name;
    }

    public String getHashPass() {
        return hashPass;
    }
}