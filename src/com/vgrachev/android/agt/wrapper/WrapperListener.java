package com.vgrachev.android.agt.wrapper;

/**
 * Created by vgrachev on 09/06/14.
 */
public interface WrapperListener {

    void onProgress(String chunk);

    void onError();

    void onDone();
}
