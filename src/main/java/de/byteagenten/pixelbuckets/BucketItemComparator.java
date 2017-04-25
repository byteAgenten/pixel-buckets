package de.byteagenten.pixelbuckets;

import java.util.Comparator;

/**
 * Created by knooma2e on 25.04.2017.
 */
public class BucketItemComparator implements Comparator<BucketItem> {

    @Override
    public int compare(BucketItem o1, BucketItem o2) {

        if ((o1 == null || o1.getValue() == null) && (o2 == null || o2.getValue() == null)) return 0;

        if (o1 == null || o1.getValue() == null) return -1;
        if (o2 == null || o2.getValue() == null) return 1;

        Double o1NumberValue = null;
        Double o2NumberValue = null;
        try {
            o1NumberValue = Double.parseDouble(o1.getValue().toString());
        } catch (NumberFormatException e) {
            //keep o1NumberValue = null;
        }
        try {
            o2NumberValue = Double.parseDouble(o2.getValue().toString());
        } catch (NumberFormatException e) {
            //keep o2NumberValue = null;
        }

        if( o1NumberValue != null && o2NumberValue != null) {
            return o1NumberValue.compareTo(o2NumberValue);
        } else {
            return o1.getValue().toString().compareTo(o2.getValue().toString());
        }
    }
}
