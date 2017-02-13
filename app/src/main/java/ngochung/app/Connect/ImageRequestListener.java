package ngochung.app.Connect;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;


public interface ImageRequestListener {
    public void onSuccess(ImageContainer paramImageContainer, boolean paramBoolean);

    public void onError(VolleyError error);
}
