package com.qiniu.qndroidimsdk.pubchat

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qiniu.qndroidimsdk.R
import kotlinx.android.synthetic.main.view_bzui_pubchat.view.*

/**
 * 公屏
 */
class PubChatView : FrameLayout, LifecycleObserver {

    private var adapter: BaseQuickAdapter<IChatMsg, BaseViewHolder> = PubChatAdapter()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val view = LayoutInflater.from(context).inflate(R.layout.view_bzui_pubchat, this, false)
        addView(view)
        chatRecy.layoutManager = LinearLayoutManager(context)
    }


    fun setAdapter(chatAdapter: BaseQuickAdapter<IChatMsg, BaseViewHolder> = PubChatAdapter()) {
        adapter = chatAdapter
        chatRecy.adapter = adapter
    }

    fun onRoomLeaving() {
        adapter.data.clear()
        adapter.notifyDataSetChanged()
    }

    private var mIChatMsgCall = PubChatMsgManager.IChatMsgCall {
        adapter.addData(it)
        chatRecy.smoothScrollToPosition(adapter.data.size - 1)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        //   RtmChannelKit.removeRtmChannelListener(channelListener)
        // RoomManager.removeRoomLifecycleMonitor(roomMonitor)
        PubChatMsgManager.iChatMsgCall = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        //    RtmChannelKit.addRtmChannelListener(channelListener)
        // RoomManager.addRoomLifecycleMonitor(roomMonitor)
        PubChatMsgManager.iChatMsgCall = mIChatMsgCall
    }


}