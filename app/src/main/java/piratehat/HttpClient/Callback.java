package piratehat.HttpClient;

/**
 *
 * Created by PirateHat on 2019/2/18.
 */

public interface Callback {

    void onFailure(Call call,Exception e);

    void onResponse(Call call,Response response);
}
