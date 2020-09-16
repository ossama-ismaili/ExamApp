package Security;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {
	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md=MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] b=md.digest();
		StringBuffer sb=new StringBuffer();
		for(byte bl : b) {
			sb.append(Integer.toHexString(bl & 0xff).toString());
		}
		return sb.toString();
	}
}
