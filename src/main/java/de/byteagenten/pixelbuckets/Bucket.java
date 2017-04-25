package de.byteagenten.pixelbuckets;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by knooma2e on 25.04.2017.
 */
public class Bucket {

    public static final String TOTALS_BUCKET_GROUP_KEY = "**pixel-bucket-totals**";

    private int index;
    private TimeRange timeRange;

    private Map<String, BucketData> bucketValuesMap = new HashMap<>();

    public Bucket(int index, TimeRange timeRange) {
        this.index = index;
        this.timeRange = timeRange;
        this.bucketValuesMap.put(TOTALS_BUCKET_GROUP_KEY, new BucketData(TOTALS_BUCKET_GROUP_KEY));
    }

    public int getIndex() {
        return index;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void add(BucketItem bucketItem) {

        if( !timeRange.contains(bucketItem.getTimestamp()) ) return;

        this.bucketValuesMap.get(TOTALS_BUCKET_GROUP_KEY).add(bucketItem);

        if( !bucketValuesMap.containsKey(bucketItem.getGroupKey())) {
            bucketValuesMap.put(bucketItem.getGroupKey(), new BucketData(bucketItem.getGroupKey()));
        }
        bucketValuesMap.get(bucketItem.getGroupKey()).add(bucketItem);
    }

    public BucketData getTotals() {
        return this.bucketValuesMap.get(TOTALS_BUCKET_GROUP_KEY);
    }

    public BucketData getBucketData(String key) {

        if( !this.bucketValuesMap.containsKey(key)) return new BucketData(key);

        return this.bucketValuesMap.get(key);
    }

    public Collection<String> getGroupKeys() {
        return this.bucketValuesMap.keySet();
    }

    @Override
    public String toString() {

        BucketData totals = this.getTotals();
        return String.format("[%s] %s -> count: %s, min: %s, max: %s", this.index, this.timeRange.toString(),
                totals.getItemCount(),
                totals.getMinItem() != null ? totals.getMinItem().getValue() : "-",
                totals.getMaxItem() != null? totals.getMaxItem().getValue() : "-");
    }
}
