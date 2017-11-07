// IAppServiceRemoteBinder.aidl
package com.jiekexueyuan.server;

// Declare any non-default types here with import statements

import com.jiekexueyuan.server.TimerServiceCallback;

interface IAppServiceRemoteBinder {

    void setData(String data);
    void registCallback(TimerServiceCallback callback);
    void unRegistCallback(TimerServiceCallback callback);
}
