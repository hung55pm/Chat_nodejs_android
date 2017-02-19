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

import ngochung.app.Applications.MyApplication;
import ngochung.app.Constants.Constants;
import ngochung.app.Models.Acounts;
import ngochung.app.Untils.SharedConfig;

/**
 * Created by HoangTien on 3/26/16.
 */
public class APIConnection {

    public static void login(Context context, String phone, String pass, final JSONObjectRequestListener callback) throws JSONException {

        final JSONObject jsonBody = new JSONObject();
        jsonBody.put(Constants.PHONE, phone);
        jsonBody.put(Constants.PASSWORD, pass);

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

    public static void register(Context context, Acounts acounts, final JSONObjectRequestListener callback) throws JSONException {

        final JSONObject jsonBody = new JSONObject();
        jsonBody.put(Constants.PHONE, acounts.getUser_id());
        jsonBody.put(Constants.PASSWORD, acounts.getPassword());
        jsonBody.put(Constants.NAME, acounts.getName());
        jsonBody.put(Constants.EMAIL, acounts.getEmail());


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_REGISTER, jsonBody, new Response.Listener<JSONObject>() {

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

    public static void searchfriend(Context context, String phone, final String access_token, final JSONObjectRequestListener callback) throws JSONException {

        final JSONObject jsonBody = new JSONObject();
        jsonBody.put(Constants.PHONE, phone);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_SEARCH_FRIENDS, jsonBody, new Response.Listener<JSONObject>() {

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
                params.put("Authorization", "access_token "+access_token);
                params.put("charset", "utf-8");
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public static void addfriend(Context context, String phone,String name, final String access_token, final JSONObjectRequestListener callback) throws JSONException {

        final JSONObject jsonBody = new JSONObject();
        jsonBody.put(Constants.FRIEND_ID, phone);
        jsonBody.put(Constants.NAME, name);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_ADD_FRIENDS, jsonBody, new Response.Listener<JSONObject>() {

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
                params.put("Authorization", "access_token "+access_token);
                params.put("charset", "utf-8");
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }
    public static void getallinvitation(Context context,final String access_token, final JSONObjectRequestListener callback) throws JSONException {

        final JSONObject jsonBody = new JSONObject();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_GET_ALL_INVITATION, jsonBody, new Response.Listener<JSONObject>() {

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
                params.put("Authorization", "access_token "+access_token);
                params.put("charset", "utf-8");
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }
    public static void friendreply(Context context,String frien_id, int status,final String access_token, final JSONObjectRequestListener callback) throws JSONException {

        final JSONObject jsonBody = new JSONObject();
        jsonBody.put(Constants.FRIEND_ID, frien_id);
        jsonBody.put(Constants.STATUS, status);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_FRIEND_REPLY, jsonBody, new Response.Listener<JSONObject>() {

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
                params.put("Authorization", "access_token "+access_token);
                params.put("charset", "utf-8");
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

}
