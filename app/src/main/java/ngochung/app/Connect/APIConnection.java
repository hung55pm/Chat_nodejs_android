package ngochung.app.Connect;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import ngochung.app.Constants.Constants;

/**
 * Created by HoangTien on 3/26/16.
 */
public class APIConnection {

    public static void login(Context context, final JSONObjectRequestListener callback) throws JSONException {

        final JSONObject jsonBody = new JSONObject();
        jsonBody.put("employee_code","hungdn@vinnet.vn");
        jsonBody.put("password","123456");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_LOGIN, jsonBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("charset", "utf-8");
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }


}
