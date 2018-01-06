package com.novakduc.forbega.qlnt.data.database;

/**
 * Created by Novak on 1/6/2018.
 */

public interface RoomServiceInterface extends ItemWithId {
    long charge();

    long getOldIndex();

    long getNewIndex();

    void setNewIndex(long newIndex);
}
