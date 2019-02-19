package piratehat.HttpClient;

/**
 * Created by PirateHat on 2019/2/18.
 */

public class Response {
    private String mResultString;

    public Response(String resultString) {
        mResultString = resultString;
    }

    @Override
    public String toString() {
        return "Response{" +
                "mResultString='" + mResultString + '\'' +
                '}';
    }
}
