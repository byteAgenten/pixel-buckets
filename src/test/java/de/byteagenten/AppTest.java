package de.byteagenten;


import de.byteagenten.pixelbuckets.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Before
    public void startup() {

    }

    @After
    public void teardown() {

    }

    @Test
    public void testBucketBuilder() throws ParseException {

        Random r = new Random();

        String[] groupKeys = new String[]{"a", "b", "c"};
        double maxValue = 100.0;
        double meanTimeDistance = 1000;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long from = df.parse("2017-04-25 10:00:00").getTime();

        Item item = null;
        long timestamp = from;

        List<Item> items = new ArrayList<>();

        for (int i = 0; i < 1000000; i++) {

            timestamp += Math.round(r.nextFloat() * meanTimeDistance);

            item = new Item(
                    groupKeys[(int) Math.floor(r.nextInt(groupKeys.length))],
                    timestamp,
                    String.valueOf(r.nextFloat() * maxValue)
            );

            items.add(item);
        }

        BucketBuilder builder = new BucketBuilder(new BucketBuilderConfig(
                new TimeRange(from, items.get(items.size()-1).getTimestamp()+10*3600*1000),
                200,
                false
        ));
        builder.setConsumer(bucket -> {
            System.out.println(bucket);
        });

        items.forEach(builder::add);

        BucketsSummary summary = builder.finish();

        int i=0;

    }

    class Item implements BucketItem {

        private String groupKey;

        private long timestamp;

        private String value;

        public Item(String groupKey, long timestamp, String value) {
            this.groupKey = groupKey;
            this.timestamp = timestamp;
            this.value = value;
        }

        @Override
        public String getGroupKey() {
            return groupKey;
        }

        @Override
        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.format("%s = %s", this.groupKey, this.value);
        }
    }
}
