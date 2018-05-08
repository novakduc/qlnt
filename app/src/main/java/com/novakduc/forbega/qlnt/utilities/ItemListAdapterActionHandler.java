package com.novakduc.forbega.qlnt.utilities;

//Handler interface to process actions applied on project
public interface ItemListAdapterActionHandler {
    void onDeleteAction(long id);

    void onCopyAction(long id);

    void onEditAction(long id);

    void onItemClick(long id);
}
