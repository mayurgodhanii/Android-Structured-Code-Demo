package dev.studentapp.model.webservice;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import dev.studentapp.util.PrefData;

/**
 *
 * Created by Nirav Dangi
 *
 * For uploading photo in Multipart way. This class will create a Volley Request.
 *
 */
public class PhotoMultipartRequest extends Request<NetworkResponse> {


private static final String FILE_PART_NAME = "image";

private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
private final Response.Listener<NetworkResponse> mListener;
private final File mImageFile;
protected Map<String, String> headers;

public PhotoMultipartRequest(String url, Response.ErrorListener errorListener, Response.Listener<NetworkResponse> listener, File imageFile){
    super(Method.POST, url, errorListener);

    setRetryPolicy(new DefaultRetryPolicy(
            100000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    mListener = listener;
    mImageFile = imageFile;

    buildMultipartEntity();
}



@Override
public Map<String, String> getHeaders() throws AuthFailureError {
    Map<String, String> headers = super.getHeaders();

    if (headers == null
            || headers.equals(Collections.emptyMap())) {
        headers = new HashMap<String, String>();
    }

    headers.put("Accept", "application/json");

    // This will be fetched from shared pref //
    String userId = PrefData.getUserId()+"";
    String accessToken = PrefData.getAccessToken();

    headers.put("Xapi", WSUtils.XAPI);
    headers.put("Id", userId);
    headers.put("access_token", accessToken);

    return headers;
}

private void buildMultipartEntity(){
    mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile, ContentType.create("image/jpeg"), mImageFile.getName());
    mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
}

@Override
public String getBodyContentType(){
    String contentTypeHeader = mBuilder.build().getContentType().getValue();
    return contentTypeHeader;
}

@Override
public byte[] getBody() throws AuthFailureError{
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    try {
        mBuilder.build().writeTo(bos);
    } catch (IOException e) {
        VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
    }

    return bos.toByteArray();
}

@Override
protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
    return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
}

@Override
protected void deliverResponse(NetworkResponse response) {
    mListener.onResponse(response);
}
}