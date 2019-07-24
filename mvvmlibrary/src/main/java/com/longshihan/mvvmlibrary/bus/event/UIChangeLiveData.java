package com.longshihan.mvvmlibrary.bus.event;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import java.util.Map;

public class UIChangeLiveData extends SingleLiveEvent {
    private SingleLiveEvent<String> showDialogEvent;
    private SingleLiveEvent<Void> dismissDialogEvent;
    private SingleLiveEvent<Map<String, Object>> startActivityEvent;
    private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
    private SingleLiveEvent<Void> finishEvent;
    private SingleLiveEvent<Void> onBackPressedEvent;

    public SingleLiveEvent<String> getShowDialogEvent() {
        return showDialogEvent = createLiveData(showDialogEvent);
    }

    public SingleLiveEvent<Void> getDismissDialogEvent() {
        return dismissDialogEvent = createLiveData(dismissDialogEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
        return startActivityEvent = createLiveData(startActivityEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
        return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
    }

    public SingleLiveEvent<Void> getFinishEvent() {
        return finishEvent = createLiveData(finishEvent);
    }

    public SingleLiveEvent<Void> getOnBackPressedEvent() {
        return onBackPressedEvent = createLiveData(onBackPressedEvent);
    }

    private SingleLiveEvent createLiveData(SingleLiveEvent liveData) {
        if (liveData == null) {
            liveData = new SingleLiveEvent();
        }
        return liveData;
    }

    @Override
    public void observe(LifecycleOwner owner, Observer observer) {
        super.observe(owner, observer);
    }
}
