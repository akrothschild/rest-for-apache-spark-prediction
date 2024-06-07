package ki.forecast.rest.service.api;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class DataMapper implements MapFunction<Row, RFData> {

    @Override
    public RFData call(Row row) {
        RFData RFData = new RFData();
        RFData.setIncomingDate(row.getAs("incomingDate"));
        RFData.setIncomingDateDouble(row.getAs("incomingDateDouble"));
        RFData.setDayOfWeek(row.getAs("dayOfWeek"));
        RFData.setDayOfYear(row.getAs("dayOfYear"));
        RFData.setWeekOfYear(row.getAs("weekOfYear"));
        RFData.setMonth(row.getAs("month"));
        RFData.setYear(row.getAs("year"));
        RFData.setWeekend(row.getAs("weekend"));
        RFData.setAmount(row.getAs("amount"));
        RFData.setPrediction(row.getAs("prediction"));

        return RFData;
    }
}
