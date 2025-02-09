package org.thosp.yourlocalweather.utils;

import org.thosp.charting.components.AxisBase;
import org.thosp.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class XAxisValueFormatter implements IAxisValueFormatter {

    private Map<Integer, Long> hourIndexes;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat format;
    private Integer lastDayUsed;

    public XAxisValueFormatter(Map<Integer, Long> hourIndexes, Locale locale) {
        this.hourIndexes = hourIndexes;
        this.format = new SimpleDateFormat("EEE", locale);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int valuesIndex = (int) value;
        Long dataTime = hourIndexes.get(valuesIndex);
        if (dataTime == null) {
            return "";
        }
        calendar.setTimeInMillis(dataTime * 1000);
        int currentHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (((lastDayUsed == null) || (lastDayUsed != calendar.get(Calendar.DAY_OF_YEAR))) &&
                (currentHourOfDay >= 10) && (currentHourOfDay <= 14)) {
            lastDayUsed = calendar.get(Calendar.DAY_OF_YEAR);
            return format.format(calendar.getTime());
        } else {
            return "";
        }
    }
}
