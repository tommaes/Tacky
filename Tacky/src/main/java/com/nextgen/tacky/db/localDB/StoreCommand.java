package com.nextgen.tacky.db.localDB;

import android.content.ContentValues;

/**
 * Created by maes on 22/05/14.
 */
public interface StoreCommand<T> {

    public ContentValues storeItem(T item);
}
