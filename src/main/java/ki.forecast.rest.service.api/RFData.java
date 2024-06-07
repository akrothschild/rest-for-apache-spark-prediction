package ki.forecast.rest.service.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RFData implements Serializable {

    @JsonProperty("incomingDate")
    private List<String> incomingDate;

    @JsonProperty("incomingDateDouble")
    private List<String> incomingDateDouble;

    @JsonProperty("dayOfWeek")
    private List<Integer> dayOfWeek;

    @JsonProperty("dayOfYear")
    private List<Integer> dayOfYear;

    @JsonProperty("weekOfYear")
    private List<Integer> weekOfYear;

    @JsonProperty("month")
    private List<Integer> month;

    @JsonProperty("year")
    private List<Integer> year;

    @JsonProperty("weekend")
    private List<Integer> weekend;

    @JsonProperty("amount")
    private List<Integer> amount;

    @JsonProperty("prediction")
    private List<String> prediction = new ArrayList<>();

    @JsonProperty("predictionDate")
    private List<String> predictionDate;

    @JsonProperty("predictionDateDouble")
    private List<Double> predictionDateDouble;

    @JsonProperty("predictionDayOfWeek")
    private List<Integer> predictionDayOfWeek;

    @JsonProperty("predictionDayOfYear")
    private List<Integer> predictionDayOfYear;

    @JsonProperty("predictionWeekOfYear")
    private List<Integer> predictionWeekOfYear;

    @JsonProperty("predictionMonth")
    private List<Integer> predictionMonth;

    @JsonProperty("predictionYear")
    private List<Integer> predictionYear;

    @JsonProperty("predictionWeekend")
    private List<Integer> predictionWeekend;

    public RFData(List<String> incomingDate, List<String> incomingDateDouble, List<Integer> dayOfWeek, List<Integer> dayOfYear, List<Integer> weekOfYear, List<Integer> month,
                  List<Integer> year, List<Integer> weekend, List<Integer> amount) {
        this.incomingDate = incomingDate;
        this.incomingDateDouble = incomingDateDouble;
        this.dayOfWeek = dayOfWeek;
        this.dayOfYear = dayOfYear;
        this.weekOfYear = weekOfYear;
        this.month = month;
        this.year = year;
        this.weekend = weekend;
        this.amount = amount;
        this.prediction = prediction;
    }

    public RFData() {

    }

    public List<Integer> getAmount() {
        return amount;
    }

    public void setAmount(List<Integer> amount) {
        this.amount = amount;
    }

    public List<String> getPrediction() {
        return prediction;
    }

    public void setPrediction(List<String> prediction) {
        this.prediction = prediction;
    }

    public List<String> getIncomingDate() {
        return incomingDate;
    }

    public void setIncomingDate(List<String> incomingDate) {
        this.incomingDate = incomingDate;
    }

    public List<String> getIncomingDateDouble() {
        return incomingDateDouble;
    }

    public void setIncomingDateDouble(List<String> incomingDateInt) {
        this.incomingDateDouble = incomingDateDouble;
    }

    public List<Integer> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(List<Integer> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<Integer> getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(List<Integer> dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public List<Integer> getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(List<Integer> weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public List<Integer> getMonth() {
        return month;
    }

    public void setMonth(List<Integer> month) {
        this.month = month;
    }

    public List<Integer> getYear() {
        return year;
    }

    public void setYear(List<Integer> year) {
        this.year = year;
    }

    public List<Integer> getWeekend() {
        return weekend;
    }

    public void setWeekend(List<Integer> weekend) {
        this.weekend = weekend;
    }

    public List<String> getPredictionDate() {
        return predictionDate;
    }

    public void setPredictionDate(List<String> predictionDate) {
        this.predictionDate = predictionDate;
    }

    public List<Double> getPredictionDateDouble() {
        return predictionDateDouble;
    }

    public void setPredictionDateDouble(List<Double> predictionDateDouble) {
        this.predictionDateDouble = predictionDateDouble;
    }

    public List<Integer> getPredictionDayOfWeek() {
        return predictionDayOfWeek;
    }

    public void setPredictionDayOfWeek(List<Integer> predictionDayOfWeek) {
        this.predictionDayOfWeek = predictionDayOfWeek;
    }

    public List<Integer> getPredictionDayOfYear() {
        return predictionDayOfYear;
    }

    public void setPredictionDayOfYear(List<Integer> predictionDayOfYear) {
        this.predictionDayOfYear = predictionDayOfYear;
    }

    public List<Integer> getPredictionWeekOfYear() {
        return predictionWeekOfYear;
    }

    public void setPredictionWeekOfYear(List<Integer> predictionWeekOfYear) {
        this.predictionWeekOfYear = predictionWeekOfYear;
    }

    public List<Integer> getPredictionMonth() {
        return predictionMonth;
    }

    public void setPredictionMonth(List<Integer> predictionMonth) {
        this.predictionMonth = predictionMonth;
    }

    public List<Integer> getPredictionYear() {
        return predictionYear;
    }

    public void setPredictionYear(List<Integer> predictionYear) {
        this.predictionYear = predictionYear;
    }

    public List<Integer> getPredictionWeekend() {
        return predictionWeekend;
    }

    public void setPredictionWeekend(List<Integer> predictionWeekend) {
        this.predictionWeekend = predictionWeekend;
    }
}
