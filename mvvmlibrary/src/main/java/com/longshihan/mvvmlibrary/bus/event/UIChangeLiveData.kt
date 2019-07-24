/*
package com.longshihan.mvvmlibrary.bus.event

*/
/**
 * Created by LONGHE001.
 * @time 2019/7/24 0024
 * @des
 * @function
 *//*

class UIChangeLiveData : SingleLiveEvent() {
    private var showDialogEvent: SingleLiveEvent<String>? = null
    private var dismissDialogEvent: SingleLiveEvent<Void>? = null
    private var startActivityEvent: SingleLiveEvent<Map<String, Any>>? = null
    private var startContainerActivityEvent: SingleLiveEvent<Map<String, Any>>? = null
    private var finishEvent: SingleLiveEvent<Void>? = null
    private var onBackPressedEvent: SingleLiveEvent<Void>? = null

    fun getShowDialogEvent(): SingleLiveEvent<String> {
        showDialogEvent = createLiveData(showDialogEvent)
        return showDialogEvent!!
    }

    fun getDismissDialogEvent(): SingleLiveEvent<Void> {
        dismissDialogEvent = createLiveData(dismissDialogEvent)
        return dismissDialogEvent!!
    }

    fun getStartActivityEvent(): SingleLiveEvent<Map<String, Any>> {
        startActivityEvent = createLiveData(startActivityEvent)
        return startActivityEvent!!
    }

    fun getStartContainerActivityEvent(): SingleLiveEvent<Map<String, Any>> {
        startContainerActivityEvent = createLiveData(startContainerActivityEvent)
        return startContainerActivityEvent!!
    }

    fun getFinishEvent(): SingleLiveEvent<Void> {
        finishEvent = createLiveData(finishEvent)
        return finishEvent!!
    }

    fun getOnBackPressedEvent(): SingleLiveEvent<Void> {
        onBackPressedEvent = createLiveData(onBackPressedEvent)
        return onBackPressedEvent!!
    }

    private fun createLiveData(liveData: SingleLiveEvent?): SingleLiveEvent {
        var liveData = liveData
        if (liveData == null) {
            liveData = SingleLiveEvent()
        }
        return liveData
    }


}*/
