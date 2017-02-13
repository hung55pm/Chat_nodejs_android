package ngochung.app.Connect;

import com.android.volley.VolleyError;


public interface StringRequestListener {
    public void onSuccess(String response);

    public void onError(VolleyError error);
}
