package de.byteagenten.pixelbuckets;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by knooma2e on 25.04.2017.
 */
public class TimeRange {

    private double from;

    private double to;

    public TimeRange(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double duration() {
        return this.to - this.from;
    }

    public boolean contains(long timestamp) {
        return this.from <= timestamp && timestamp < this.to;
    }

    @Override
    public String toString() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("%s - %s", df.format(new Date((long) this.from)), df.format(new Date((long) this.to)));
    }
}
