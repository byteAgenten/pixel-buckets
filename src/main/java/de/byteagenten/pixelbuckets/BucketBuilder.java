package de.byteagenten.pixelbuckets;

/**
 * Created by knooma2e on 25.04.2017.
 */
public class BucketBuilder {

    BucketConsumer consumer;

    private BucketBuilderConfig config;

    private Bucket currentBucket;

    private double bucketPeriod;

    private BucketsSummary summary = new BucketsSummary();

    public BucketBuilder( BucketBuilderConfig config) {

        this.config = config;
        BucketBuilderConfig.current(config);

        this.bucketPeriod = this.config.getTimeRange().duration() / this.config.getResolution();
        this.currentBucket = new Bucket(0,
                new TimeRange(
                        this.config.getTimeRange().getFrom(),
                        this.config.getTimeRange().getFrom() + this.bucketPeriod
                ));
    }

    public void setConsumer(BucketConsumer consumer) {
        this.consumer = consumer;
    }

    public void add(BucketItem item) {

        if (this.summary.getStartTimestamp() == 0) this.summary.setStartTimestamp(System.currentTimeMillis());

        while (this.currentBucket.getTimeRange().getTo() < item.getTimestamp()) {

            this.finalizeBucket(this.currentBucket);
            this.currentBucket = new Bucket(this.currentBucket.getIndex() + 1,
                    new TimeRange(
                            this.currentBucket.getTimeRange().getTo(),
                            this.currentBucket.getTimeRange().getTo() + this.bucketPeriod)
            );
        }
        this.currentBucket.add(item);
    }

    private void finalizeBucket(Bucket bucket) {

        this.summary.include(bucket);
        if (this.consumer != null) this.consumer.bucketBuild(bucket);
    }


    public BucketsSummary finish() {

        do {

            this.finalizeBucket(this.currentBucket);
            this.currentBucket = new Bucket(this.currentBucket.getIndex() + 1, new TimeRange(this.currentBucket.getTimeRange().getTo(), this.currentBucket.getTimeRange().getTo() + this.bucketPeriod));

        } while (this.currentBucket.getTimeRange().getTo() <= this.config.getTimeRange().getTo());

        this.summary.finish();

        return this.summary;
    }


}
