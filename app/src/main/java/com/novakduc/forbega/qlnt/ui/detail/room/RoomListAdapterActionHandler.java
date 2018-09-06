package com.novakduc.forbega.qlnt.ui.detail.room;

import com.novakduc.forbega.qlnt.utilities.ItemListAdapterActionHandler;

public interface RoomListAdapterActionHandler extends ItemListAdapterActionHandler {
    void onCheckIn(long id);

    void onCheckOut(long id);

    void onBill(long id);

    void onCallGuest(long id);

    void onSendMessageToGuest(long id);
}
