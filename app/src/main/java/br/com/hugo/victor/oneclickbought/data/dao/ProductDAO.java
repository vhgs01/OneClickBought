package br.com.hugo.victor.oneclickbought.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.hugo.victor.oneclickbought.data.model.ProductDB;

@Dao
public interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProduct(ProductDB productDB);

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    void updateProduct(ProductDB productDB);

    @Query("DELETE FROM product WHERE productName = :name")
    int deleteProductCart(String name);

    @Query("SELECT * FROM product")
    ProductDB[] showProducts();

    @Query("SELECT * from product WHERE productName = :name")
    ProductDB showProductByName(String name);
}
