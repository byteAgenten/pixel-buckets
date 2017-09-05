package de.byteagenten.pixelbuckets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by knooma2e on 25.04.2017.
 */
public class BucketsSummary {

    private Map<String, BucketsSummaryData> summaries = new HashMap<>();

    private long startTimestamp;

    private long buildDuration;

    private int minBucketItemCount = 0;

    private int maxBucketItemCount = 0;

    public BucketsSummary() {
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public long getBuildDuration() {
        return buildDuration;
    }

    public void setBuildDuration(long buildDuration) {
        this.buildDuration = buildDuration;
    }

    public int getMinBucketItemCount() {
        return minBucketItemCount;
    }

    public int getMaxBucketItemCount() {
        return maxBucketItemCount;
    }

    public void include(Bucket bucket) {

        if( minBucketItemCount == 0 || minBucketItemCount > bucket.getTotals().getItemCount()) minBucketItemCount = bucket.getTotals().getItemCount();
        if( maxBucketItemCount == 0 || maxBucketItemCount < bucket.getTotals().getItemCount()) maxBucketItemCount = bucket.getTotals().getItemCount();

        for (String groupKey : bucket.getGroupKeys()) {

            if (!this.summaries.containsKey(groupKey)) {
                this.summaries.put(groupKey, new BucketsSummaryData(groupKey));
            }
            this.summaries.get(groupKey).add(bucket);
        }
    }

    public BucketsSummaryData getTotalSummary() {
        return this.summaries.get(Bucket.TOTALS_BUCKET_GROUP_KEY);
    }

    public BucketsSummaryData getSummary(String key) {

        if( !this.summaries.containsKey(key)) return new BucketsSummaryData(key);
        return this.summaries.get(key);
    }

    public void finish() {
        this.summaries.values().forEach(BucketsSummaryData::finish);
        this.buildDuration = getTotalSummary().hasValue() ? System.currentTimeMillis() - this.startTimestamp : -1;
    }
}
