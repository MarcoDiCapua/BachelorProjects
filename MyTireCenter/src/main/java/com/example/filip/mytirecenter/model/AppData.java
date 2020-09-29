package com.example.filip.mytirecenter.model;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This class provides methods to create the data to populate the application database.
 *
 * @author Marco Di Capua
 * @author Filippo Pelosi
 * @version 1.0
 */
public class AppData {

    /**
     * Create a list of {@link TireCenter} objects
     *
     * @return the list
     */
    public static List<TireCenter> createTireCentersData() {
        TireCenter tireCenter0 = TireCenter.Builder.create(0, "Autosalone Broggi Snc", "VA")
                .withAddress("Via Leopoldo Gasparotto 113, Varese")
                .withTelephoneNumber("0332 831567")
                .withLocation(45.799607f, 8.833922f)
                .build();
        TireCenter tireCenter1 = TireCenter.Builder.create(1, "KIA Clerici Auto", "VA")
                .withAddress("Viale Belforte 240 Varese")
                .withTelephoneNumber("0332 331896")
                .withLocation(45.806550f, 8.860045f)
                .build();
        TireCenter tireCenter2 = TireCenter.Builder.create(2, "Carglass", "VA")
                .withAddress("Viale Luigi Borri 156 Varese")
                .withTelephoneNumber("02 40952824")
                .withLocation(45.798425f, 8.843456f)
                .build();
        TireCenter tireCenter3 = TireCenter.Builder.create(3, "Citroen Citroën Terrenielsauto", "VA")
                .withAddress("Viale Belforte 244, Varese")
                .withTelephoneNumber("332 336318")
                .withLocation(45.806058f, 8.861002f)
                .build();
        TireCenter tireCenter4 = TireCenter.Builder.create(4, "Marcolli Claudio & C.S.N.C.", "VA")
                .withAddress("Via Francesco Guicciardini 108, Varese")
                .withTelephoneNumber("0332 811041")
                .withLocation(45.805960f, 8.845446f)
                .build();
        TireCenter tireCenter5 = TireCenter.Builder.create(5, "Asso Service #", "VA")
                .withAddress("Via Santa Maria Maddalena 8, Varese")
                .withTelephoneNumber("332 262209")
                .withLocation(45.796421f, 8.843053f)
                .build();
        TireCenter tireCenter6 = TireCenter.Builder.create(6, "Autovarese Garage Lombardo", "VA")
                .withAddress("Viale Luigi Borri 232, 21100 Varese")
                .withTelephoneNumber("0332 232181")
                .withLocation(45.794100f, 8.846759f)
                .build();
        TireCenter tireCenter7 = TireCenter.Builder.create(7, "Carrozzeria Fasola s.a.s.", "VA")
                .withAddress("Via Monte Generoso 48, Varese")
                .withTelephoneNumber("0332 262173")
                .withLocation(45.797000f, 8.855658f)
                .build();
        TireCenter tireCenter8 = TireCenter.Builder.create(8, "Carrozzeria Di Moschella", "VA")
                .withAddress("Via Nabresina 21, 21100 Varese")
                .withTelephoneNumber("0332 812132")
                .withLocation(45.794848f, 8.854404f)
                .build();
        TireCenter tireCenter9 = TireCenter.Builder.create(9, "Car Stereo Vogue Sas Di Ponti & C.", "VA")
                .withAddress("Via Fratelli de Grandi 6, 21100 Varese")
                .withTelephoneNumber("0332 261437")
                .withLocation(45.795471f, 8.843044f)
                .build();
        TireCenter tireCenter10 = TireCenter.Builder.create(10, "Carrozzeria Zanzi F.Lli", "VA")
                .withAddress("Via Portorose 32, Varese")
                .withTelephoneNumber("0332 261296")
                .withLocation(45.790115f, 8.846740f)
                .build();
        TireCenter tireCenter11 = TireCenter.Builder.create(11, "Officina Vedani Grandelli Malavasi", "VA")
                .withAddress("Via Santa Maria Maddalena 87, Varese")
                .withTelephoneNumber("0332 262209")
                .withLocation(45.798238f, 8.839213f)
                .build();
        TireCenter tireCenter12 = TireCenter.Builder.create(12, "Car Emme S.R.L.", "VA")
                .withAddress("Viale Luigi Borri 244, Varese")
                .withTelephoneNumber("0332 261222")
                .withLocation(45.793773f, 8.847375f)
                .build();
        TireCenter tireCenter13 = TireCenter.Builder.create(13, "Autoviemme Varese", "VA")
                .withAddress("Viale Luigi Borri, 180, 21100 Varese")
                .withTelephoneNumber("0332 810983")
                .withLocation(45.796227f, 8.844212f)
                .build();
        TireCenter tireCenter14 = TireCenter.Builder.create(14, "Marelli & Pozzi Spa", "VA")
                .withAddress("Viale Luigi Borri 211, Varese")
                .withTelephoneNumber("0332 1520188")
                .withLocation(45.794948f, 8.845585f)
                .build();
        TireCenter tireCenter15 = TireCenter.Builder.create(15, "C.T.Motors Sas Di Musciatelli", "VA")
                .withAddress("Viale Luigi Borri 200, Varese")
                .withTelephoneNumber("0332 224454")
                .withLocation(45.794680f, 8.845168f)
                .build();

        LinkedList<TireCenter> list = new LinkedList<>();
        list.add(tireCenter0);
        list.add(tireCenter1);
        list.add(tireCenter2);
        list.add(tireCenter3);
        list.add(tireCenter4);
        list.add(tireCenter5);
        list.add(tireCenter6);
        list.add(tireCenter7);
        list.add(tireCenter8);
        list.add(tireCenter9);
        list.add(tireCenter10);
        list.add(tireCenter11);
        list.add(tireCenter12);
        list.add(tireCenter13);
        list.add(tireCenter14);
        list.add(tireCenter15);

        return list;
    }

    /**
     * Create a list of {@link Tire} objects
     *
     * @return the list
     */
    public static List<Tire> createTiresData() {
        Tire tire0 = Tire.Builder.create(0, "Pirelli", "Cinturato P1", "Summer", 195, 55, 15, 89.88f)
                .build();
        Tire tire1 = Tire.Builder.create(1, "Pirelli", "P Zero", "Summer", 205, 45, 17, 121.48f)
                .build();
        Tire tire2 = Tire.Builder.create(2, "Pirelli", "Cinturato All", "All", 205, 55, 16, 72.68f)
                .build();
        Tire tire3 = Tire.Builder.create(3, "Pirelli", "Scorpion Winter", "Winter", 215, 65, 16, 94.43f)
                .build();
        Tire tire4 = Tire.Builder.create(4, "Pirelli", "Scorpione Verde", "All", 235, 65, 17, 109.06f)
                .build();
        Tire tire5 = Tire.Builder.create(5, "Michelin", "Alpin 6", "Winter", 205, 55, 16, 89.32f)
                .build();
        Tire tire6 = Tire.Builder.create(6, "Michelin", "CrossClimate Plus", "All", 205, 55, 15, 91.98f)
                .build();
        Tire tire7 = Tire.Builder.create(7, "Michelin", "CrossClimate Plus", "All", 205, 55, 16, 82.64f)
                .build();
        Tire tire8 = Tire.Builder.create(8, "Michelin", "CrossClimate Plus", "All", 225, 45, 17, 109.62f)
                .build();
        Tire tire9 = Tire.Builder.create(9, "GoodYear", "Ultragrip 9", "Winter", 185, 65, 15, 57.12f)
                .build();
        Tire tire10 = Tire.Builder.create(10, "GoodYear", "Vector Gen 2", "All", 155, 70, 13, 46.99f)
                .build();
        Tire tire11 = Tire.Builder.create(11, "GoodYear", "Vector Gen 2", "All", 165, 70, 13, 55.28f)
                .build();
        Tire tire12 = Tire.Builder.create(12, "Toyo", "Snowprox S954", "Winter", 225, 45, 19, 157.78f)
                .build();
        Tire tire13 = Tire.Builder.create(13, "Toyo", "Snowprox S954", "Winter", 205, 50, 16, 86.68f)
                .build();
        Tire tire14 = Tire.Builder.create(14, "Toyo", "Neva", "Summer", 215, 65, 16, 102.69f)
                .build();
        Tire tire15 = Tire.Builder.create(15, "Dunlop", "Gt Touring AS", "All", 255, 60, 17, 152.12f)
                .build();
        Tire tire16 = Tire.Builder.create(16, "Dunlop", "Winter Sport 5", "Winter", 195, 55, 15, 91.21f)
                .build();
        Tire tire17 = Tire.Builder.create(17, "Dunlop", "Sport BluResponse", "Summer", 205, 50, 17, 96.10f)
                .build();
        Tire tire18 = Tire.Builder.create(18, "Bridgestone", "Turanza T005", "Summer", 205, 55, 16, 59.37f)
                .build();
        Tire tire19 = Tire.Builder.create(19, "Bridgestone", "Weather Control A005", "All", 205, 55, 16, 97.33f)
                .build();

        LinkedList<Tire> list = new LinkedList<>();
        list.add(tire0);
        list.add(tire1);
        list.add(tire2);
        list.add(tire3);
        list.add(tire4);
        list.add(tire5);
        list.add(tire6);
        list.add(tire7);
        list.add(tire8);
        list.add(tire9);
        list.add(tire10);
        list.add(tire11);
        list.add(tire12);
        list.add(tire13);
        list.add(tire14);
        list.add(tire15);
        list.add(tire16);
        list.add(tire17);
        list.add(tire18);
        list.add(tire19);

        return list;
    }

    /**
     * Create a list of {@link ContentValues} to populate the warehouse table
     *
     * @return the list
     */
    public static List<ContentValues> createWarehouseData() {
        Random random = new Random();
        List<ContentValues> valuesList = new LinkedList<>();

        for (int i = 0; i < 16; i++) {
            int numTires = random.nextInt(7) + 4;
            List<Integer> idTires = new ArrayList<>();
            for (int j = 0; j < numTires; j++) {
                ContentValues values = new ContentValues();
                values.put("id_tire_center", i);
                int idTire = random.nextInt(20);
                while (idTires.contains(idTire)) {
                    idTire = random.nextInt(20);
                }
                values.put("id_tire", idTire);
                float price = random.nextInt(100) + 50.99f;
                values.put(Tire.Keys.PRICE, round(price));
                idTires.add(idTire);
                valuesList.add(values);
            }
        }

        return valuesList;
    }

    private static double round(float x) {
        return Math.floor(x * 100) / 100;
    }
}
