package de.byteagenten.pixelbuckets;

/**
 * Created by knooma2e on 25.04.2017.
 */
public class BucketBuilderConfig {

    public static final ThreadLocal<BucketBuilderConfig> configThreadLocal = ThreadLocal.withInitial(()-> null);

    private TimeRange timeRange;

    private int resolution;

    private boolean detectMinMaxItems;

    public BucketBuilderConfig() {
    }

    public static BucketBuilderConfig current() {
        return configThreadLocal.get();
    }

    public static void current(BucketBuilderConfig config) {
        configThreadLocal.set(config);
    }

    public BucketBuilderConfig(TimeRange timeRange, int resolution, boolean detectMinMaxItems) {
        this.timeRange = timeRange;
        this.resolution = resolution;
        this.detectMinMaxItems = detectMinMaxItems;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public boolean isDetectMinMaxItems() {
        return detectMinMaxItems;
    }

    public void setDetectMinMaxItems(boolean detectMinMaxItems) {
        this.detectMinMaxItems = detectMinMaxItems;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }
}
