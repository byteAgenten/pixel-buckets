package de.byteagenten.pixelbuckets;

/**
 * Created by knooma2e on 25.04.2017.
 */
public class BucketsSummaryData {

    private BucketItemComparator comparator = new BucketItemComparator();

    private String key;

    public BucketsSummaryData(String key) {
        this.key = key;
    }

    private BucketItem maxItem;

    private BucketItem minItem;

    private long itemCount;

    private int bucketCount;

    private Double sampleRatio = null;

    public void add(Bucket bucket) {

        BucketData bucketData = bucket.getBucketData(this.key);
        if (bucketData.isEmpty()) return;

        this.bucketCount++;
        this.itemCount += bucketData.getItemCount();

        if (BucketBuilderConfig.current().isDetectMinMaxItems()) {
            if (this.minItem == null || comparator.compare(minItem, bucketData.getMinItem()) > 0)
                this.minItem = bucketData.getMinItem();
            if (this.maxItem == null || comparator.compare(maxItem, bucketData.getMaxItem()) < 0)
                this.maxItem = bucketData.getMaxItem();
        }
    }

    public String getKey() {
        return key;
    }

    public BucketItem getMaxItem() {
        return maxItem;
    }

    public BucketItem getMinItem() {
        return minItem;
    }

    public long getItemCount() {
        return itemCount;
    }

    public int getBucketCount() {
        return bucketCount;
    }

    public boolean isEmpty() {
        return this.itemCount == 0;
    }

    public boolean hasValue() {
        return !isEmpty();
    }

    public Double getSampleRatio() {
        return sampleRatio;
    }

    public void finish() {

        if (this.isEmpty()) return;
        this.sampleRatio = (double) bucketCount / (double) this.itemCount;
    }

}
