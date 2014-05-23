package com.nextgen.tacky.db.localDB;

import android.database.Cursor;

/**
 * Created by maes on 22/05/14.
 */
public interface ReadCommand<T> {

    public T readItem(Cursor cursor);

}
