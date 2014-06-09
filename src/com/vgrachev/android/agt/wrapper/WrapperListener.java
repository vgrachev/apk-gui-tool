package com.vgrachev.android.agt.wrapper;

import com.vgrachev.android.agt.object.Progress;

/**
 * Created by vgrachev on 09/06/14.
 */
public interface WrapperListener {

    void onProgress(Progress progress);

    void onError();

    void onDone();
}
