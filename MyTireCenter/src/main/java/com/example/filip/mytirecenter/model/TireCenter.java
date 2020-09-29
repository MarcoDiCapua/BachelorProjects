package com.example.filip.mytirecenter.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An object of the TireCenter class contains tire center information
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class TireCenter implements Parcelable {
    /**
     * Implementation of a CREATOR for the creation of the instance
     */
    public static final Parcelable.Creator<TireCenter> CREATOR = new Parcelable.Creator<TireCenter>() {
        public TireCenter createFromParcel(Parcel in) {
            return new TireCenter(in);
        }

        public TireCenter[] newArray(int size) {
            return new TireCenter[size];
        }
    };

    // Constant that identify an existing data
    private static final byte PRESENT = 1;

    // Constant that identify a non existing data
    private static final byte NOT_PRESENT = 0;

    private final int id;

    private final String name;

    private final String provinceCode;

    private final String address;

    private final String telephoneNumber;

    private final float latitude;

    private final float longitude;

    /**
     * Create a new TireCenter object with the given data.
     *
     * @param id              states the identification number of the tire center
     * @param name            states the name of the tire center
     * @param provinceCode    states the province code of the tire center
     * @param address         states the address of the tire center
     * @param telephoneNumber states the telephone number of the tire center
     * @param latitude        states the latitude of the location of the tire center
     * @param longitude       states the longitude of the location of the tire center
     */
    private TireCenter(final int id, final String name, final String provinceCode, final String address,
                       final String telephoneNumber, final float latitude, final float longitude) {
        this.id = id;
        this.name = name;
        this.provinceCode = provinceCode;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * TireCenter constructor that generates the TireCenter object
     *
     * @param in reads the input data
     */
    private TireCenter(Parcel in) {
        id = in.readInt();
        name = in.readString();
        provinceCode = in.readString();
        boolean present = in.readByte() == PRESENT;
        if (present) {
            address = in.readString();
        } else {
            address = "unknown";
        }
        if (present) {
            telephoneNumber = in.readString();
        } else {
            telephoneNumber = "unknown";
        }
        present = in.readByte() == PRESENT;
        if (present) {
            latitude = in.readFloat();
            longitude = in.readFloat();
        } else {
            latitude = 0.0f;
            longitude = 0.0f;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(provinceCode);

        if (address.equals("unknown")) {
            dest.writeByte(PRESENT);
            dest.writeString(address);
        } else {
            dest.writeByte(NOT_PRESENT);
        }
        if (telephoneNumber.equals("unknown")) {
            dest.writeByte(PRESENT);
            dest.writeString(telephoneNumber);
        } else {
            dest.writeByte(NOT_PRESENT);
        }
        if (latitude != 0.0f) {
            dest.writeByte(PRESENT);
            dest.writeFloat(latitude);
            dest.writeFloat(longitude);
        } else {
            dest.writeByte(NOT_PRESENT);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TireCenter) {
            TireCenter tireCenter = (TireCenter) obj;
            return this.id == tireCenter.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "TireCenter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", address='" + address + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    /**
     * Getter for the id
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the province code
     *
     * @return the name
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * Getter for the address
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for the telephone number
     *
     * @return the telephone number
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * Getter for the latitude
     *
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Getter for the longitude
     *
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Keys for the properties
     */
    public interface Keys {
        String ID = "id";
        String NAME = "name";
        String PROVINCE_CODE = "province_code";
        String ADDRESS = "address";
        String TELEPHONE_NUMBER = "telephone_number";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
    }

    /**
     * Builder Pattern implementation for the TireCenter class
     */
    public static class Builder {

        private int mId;

        private String mName;

        private String mProvinceCode;

        private String mAddress;

        private String mTelephoneNumber;

        private float mLatitude;

        private float mLongitude;


        /**
         * Creates a Builder for the TireCenter class
         *
         * @param id           The id of the TireCenter
         * @param name         The name of the TireCenter
         * @param provinceCode The province code of the tire center
         */
        private Builder(final int id, final String name, final String provinceCode) {
            this.mId = id;
            this.mName = name;
            this.mProvinceCode = provinceCode;
        }

        /**
         * Static Factory method for the Builder of the TireCenter object
         *
         * @param id           The id of the TireCenter
         * @param name         The name of the TireCenter
         * @param provinceCode The province code of the tire center
         * @return The Builder for the TireCenter class
         */
        public static Builder create(final int id, final String name, final String provinceCode) {
            return new Builder(id, name, provinceCode);
        }

        /**
         * Set the optional address for the TireCenter
         *
         * @param address The address for the TireCenter
         * @return The TireCenter itself
         */
        public Builder withAddress(final String address) {
            this.mAddress = address;
            return this;
        }

        /**
         * Set the optional telephone number for the TireCenter
         *
         * @param telephoneNumber The telephone number for the TireCenter
         * @return The TireCenter itself
         */
        public Builder withTelephoneNumber(final String telephoneNumber) {
            this.mTelephoneNumber = telephoneNumber;
            return this;
        }

        /**
         * Set the optional location for the TireCenter
         *
         * @param latitude  The latitude for the TireCenter
         * @param longitude The longitude for the TireCenter
         * @return The TireCenter itself
         */
        public Builder withLocation(final float latitude, final float longitude) {
            this.mLatitude = latitude;
            this.mLongitude = longitude;
            return this;
        }

        /**
         * @return The TireCenter with the given data
         */
        public TireCenter build() {
            return new TireCenter(mId, mName, mProvinceCode, mAddress, mTelephoneNumber, mLatitude,
                    mLongitude);
        }

    }
}