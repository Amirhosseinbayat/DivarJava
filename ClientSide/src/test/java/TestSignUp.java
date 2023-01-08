import org.finalproject.DataObject.DataObject;
import org.finalproject.DataObject.User;
import org.finalproject.client.Http.HttpRequestManager;
import org.finalproject.client.Http.Request;
import org.finalproject.client.Http.RequestException;
import org.finalproject.client.Http.Response;

public class TestSignUp {

    public static void main(String[] args) throws RequestException {
        testSignUpRequest();
    }

    public static void testSignUpRequest() throws RequestException {
        Request request = new Request("POST", "signup");
        request.setBody(new User("name", "password"));
        byte[] bytes = request.getBodyBytes();
        User user = DataObject.createFromByteArray(bytes);
        System.out.println(user.getUsername());
        Response response = new HttpRequestManager().sendRequest(request);
        System.out.println((String) response.getResponseBody());
    }
}
