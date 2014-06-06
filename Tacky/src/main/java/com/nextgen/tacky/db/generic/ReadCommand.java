package com.nextgen.tacky.db.generic;

import android.database.Cursor;

/**
 * Created by maes on 22/05/14.
 */
public interface ReadCommand<T, I> {

    public T readItem(I input);

}
