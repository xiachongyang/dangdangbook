import com.oraclewdp.ddbookmarket.util.MD5Util;

public class dd {
    public  static  void main(String[] args)throws  Exception{
        String ss = "admin";
        System.out.println(MD5Util.getEncryptedPwd(ss));
    }
}
