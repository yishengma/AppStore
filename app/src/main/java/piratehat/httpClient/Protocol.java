package piratehat.httpClient;



/**
 *
 * Created by PirateHat on 2019/2/18.
 */

public enum  Protocol {
    HTTP("http"),
    HTTPS("https");

    private final String protocol;

    Protocol(String protocol) {
        this.protocol = protocol;
    }


    @Override
    public String toString() {
        return protocol;
    }
}
