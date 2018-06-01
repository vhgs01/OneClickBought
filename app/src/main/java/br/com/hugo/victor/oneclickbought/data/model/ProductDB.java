package br.com.hugo.victor.oneclickbought.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "product")
public class ProductDB implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "productName")
    private String productName;

    @ColumnInfo(name = "userId")
    private String userId;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] productPhoto;

    @ColumnInfo(name = "productDescription")
    private String productDescription;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    public ProductDB() {}

    public ProductDB(@NonNull String productName, String userId, byte[] productPhoto,
                     String productDescription, String latitude, String longitude) {
        this.productName = productName;
        this.userId = userId;
        this.productPhoto = productPhoto;
        this.productDescription = productDescription;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte[] getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(byte[] productPhoto) {
        this.productPhoto = productPhoto;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productName);
        dest.writeString(this.userId);
        dest.writeByteArray(this.productPhoto);
        dest.writeString(this.productDescription);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    protected ProductDB(Parcel in) {
        this.productName = in.readString();
        this.userId = in.readString();
        this.productPhoto = in.createByteArray();
        this.productDescription = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Creator<ProductDB> CREATOR = new Creator<ProductDB>() {
        @Override
        public ProductDB createFromParcel(Parcel source) {
            return new ProductDB(source);
        }

        @Override
        public ProductDB[] newArray(int size) {
            return new ProductDB[size];
        }
    };
}
