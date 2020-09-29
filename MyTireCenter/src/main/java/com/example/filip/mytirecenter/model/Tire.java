package com.example.filip.mytirecenter.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An object of the Tire class contains tire information
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class Tire implements Parcelable {

    /**
     * Implementation of a CREATOR for the creation of the instance
     */
    public static final Parcelable.Creator<Tire> CREATOR = new Parcelable.Creator<Tire>() {
        public Tire createFromParcel(Parcel in) {
            return new Tire(in);
        }

        public Tire[] newArray(int size) {
            return new Tire[size];
        }
    };

    private final int id;

    private final String brand;

    private final String model;

    private final String type;

    private final int sectionWidth;

    private final int aspectRatio;

    private final int rimDiameter;

    private final float price;

    /**
     * Create a new Tire object with the given data.
     *
     * @param id           states the identification number of the tire
     * @param brand        states the brand of the tire
     * @param model        states the model of the tire
     * @param type         states the type of the tire
     * @param sectionWidth states the section width of the tire
     * @param aspectRatio  states the aspect ratio of the tire
     * @param rimDiameter  states the rim diameter of the tire
     * @param price        states the price of the tire
     */
    private Tire(final int id, final String brand, final String model, final String type, final int sectionWidth,
                 final int aspectRatio, final int rimDiameter, final float price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.sectionWidth = sectionWidth;
        this.aspectRatio = aspectRatio;
        this.rimDiameter = rimDiameter;
        this.price = price;
    }

    /**
     * Tire method that generates the Tire object
     *
     * @param in reads the input data
     */
    private Tire(Parcel in) {
        id = in.readInt();
        brand = in.readString();
        model = in.readString();
        type = in.readString();
        sectionWidth = in.readInt();
        aspectRatio = in.readInt();
        rimDiameter = in.readInt();
        price = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(type);
        dest.writeInt(sectionWidth);
        dest.writeInt(aspectRatio);
        dest.writeInt(rimDiameter);
        dest.writeFloat(price);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tire) {
            Tire tire = (Tire) obj;
            return this.id == tire.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Tire{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", section_width=" + sectionWidth +
                ", aspect_ratio=" + aspectRatio +
                ", rim_diameter=" + rimDiameter +
                ", price=" + price +
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
     * Getter for the brand
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Getter for the model
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Getter for the type
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for the section width
     *
     * @return the section width
     */
    public int getSectionWidth() {
        return sectionWidth;
    }

    /**
     * Getter for the aspect ratio
     *
     * @return the aspect ratio
     */
    public int getAspectRatio() {
        return aspectRatio;
    }

    /**
     * Getter for the rim diameter
     *
     * @return the rim diameter
     */
    public int getRimDiameter() {
        return rimDiameter;
    }

    /**
     * Getter for the price
     *
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Keys for the properties
     */
    public interface Keys {
        String ID = "id";
        String BRAND = "brand";
        String MODEL = "model";
        String TYPE = "type";
        String SECTION_WIDTH = "section_width";
        String ASPECT_RATIO = "aspect_ratio";
        String RIM_DIAMETER = "rim_diameter";
        String PRICE = "price";
    }

    /**
     * Builder Pattern implementation for the Tire class
     */
    public static class Builder {

        private final String mBrand;
        private final String mModel;
        private final String mType;
        private final int mSectionWidth;
        private final int mAspectRatio;
        private final int mRimDiameter;
        private final float mPrice;
        private int mId;

        /**
         * Creates a Builder for the Tire class
         *
         * @param id           The id of the Tire
         * @param brand        The brand of the Tire
         * @param model        The model of the Tire
         * @param type         The type of the Tire
         * @param sectionWidth The section width of the Tire
         * @param aspectRatio  The aspect ratio of the Tire
         * @param rimDiameter  The rim diameter of the Tire
         * @param price        The price of the Tire
         */
        private Builder(final int id, final String brand, final String model, final String type, final int sectionWidth,
                        final int aspectRatio, final int rimDiameter, final float price) {
            this.mId = id;
            this.mBrand = brand;
            this.mModel = model;
            this.mType = type;
            this.mSectionWidth = sectionWidth;
            this.mAspectRatio = aspectRatio;
            this.mRimDiameter = rimDiameter;
            this.mPrice = price;
        }

        /**
         * Static Factory method for the Builder of the Tire object
         *
         * @param id           The id of the Tire
         * @param brand        The brand of the Tire
         * @param model        The model of the Tire
         * @param type         The type of the Tire
         * @param sectionWidth The section width of the Tire
         * @param aspectRatio  The aspect ratio of the Tire
         * @param rimDiameter  The rim diameter of the Tire
         * @param price        The price of the Tire
         * @return The Builder for the Tire class
         */
        public static Tire.Builder create(final int id, final String brand, final String model, final String type, final int sectionWidth,
                                          final int aspectRatio, final int rimDiameter, final float price) {
            return new Tire.Builder(id, brand, model, type, sectionWidth, aspectRatio, rimDiameter, price);
        }


        /**
         * @return The Tire with the given data
         */
        public Tire build() {
            return new Tire(mId, mBrand, mModel, mType, mSectionWidth, mAspectRatio, mRimDiameter, mPrice);
        }

    }
}
