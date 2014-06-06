package com.nextgen.tacky.db.generic;

import android.content.ContentValues;

/**
 * Created by maes on 22/05/14.
 */
public interface StoreCommand<T, R> {

    public R storeItem(T item);
}
