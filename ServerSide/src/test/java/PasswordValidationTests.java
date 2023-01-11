import org.finalproject.server.Logic.PasswordValidator;

public class PasswordValidationTests {

    static PasswordValidator validator = new PasswordValidator();

    public static void main(String[] args) {
        crashIfAccepted("pass921"); //not 8 chars.
        crashIfAccepted("pass word"); //space
        crashIfAccepted("passWORD"); //uppercase
        crashIfAccepted("passworda"); //does not contain numbers.
        crashIfAccepted("16777216"); //does not contain letters. (16777216 is a power of two)
        crashIfAccepted("password1987"); //neither has two 'a' chars, nor has a power of two.

        crashIfRejected("passaword1901"); //two a
        crashIfRejected("password1024"); //a number in power of two.
        crashIfRejected("password10v1024"); //a number in power of two.
    }


    public static void crashIfAccepted(String password) {
        String result = validator.validatePassword(password);
        assert result != null;
        System.out.println("rejected "+password+" : "+result);
    }

    public static void crashIfRejected(String password) {
        String result = validator.validatePassword(password);
        assert result == null;
        System.out.println("password '"+password+"' is accepted.");
    }
}
