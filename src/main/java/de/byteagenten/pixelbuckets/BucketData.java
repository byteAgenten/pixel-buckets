package de.byteagenten.pixelbuckets;

/**
 * Created by knooma2e on 25.04.2017.
 */
public class BucketData {

    private BucketItemComparator comparator = new BucketItemComparator();

    private String key;

    public BucketData(String key) {
        this.key = key;
    }

    private BucketItem entryItem;

    private BucketItem maxItem;

    private BucketItem minItem;

    private BucketItem exitItem;

    private int itemCount = 0;

    public void add(BucketItem bucketItem) {

        this.itemCount++;

        if (entryItem == null) entryItem = bucketItem;

        if(BucketBuilderConfig.current().isDetectMinMaxItems()) {
            if (minItem == null || comparator.compare(minItem, bucketItem) > 0) minItem = bucketItem;
            if (maxItem == null || comparator.compare(maxItem, bucketItem) < 0) maxItem = bucketItem;
        }

        exitItem = bucketItem;
    }

    public String getKey() {
        return key;
    }

    public BucketItem getEntryItem() {
        return entryItem;
    }

    public BucketItem getMaxItem() {
        return maxItem;
    }

    public BucketItem getMinItem() {
        return minItem;
    }

    public BucketItem getExitItem() {
        return exitItem;
    }

    public int getItemCount() {
        return itemCount;
    }

    public boolean isEmpty() {
        return this.itemCount == 0;
    }

    public boolean hasValues() {
        return !this.isEmpty();
    }
}
