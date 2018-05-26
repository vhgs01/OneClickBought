package br.com.hugo.victor.oneclickbought.data.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.hugo.victor.oneclickbought.data.dao.ProductDAO;

@Database(entities = {ProductDB.class}, version = 1)
public abstract class DatabaseOneClickBought extends RoomDatabase {

    private static final String DB_NAME = "oneclickboughtDB";
    private static DatabaseOneClickBought INSTANCE;

    public abstract ProductDAO productDAO();

    public static DatabaseOneClickBought getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseOneClickBought.class, DB_NAME).build();
        }

        return INSTANCE;
    }
}