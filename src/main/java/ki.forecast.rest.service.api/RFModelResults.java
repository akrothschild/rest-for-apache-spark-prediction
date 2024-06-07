package ki.forecast.rest.service.api;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RFModelResults implements Serializable {

    @JsonProperty("data")
    private RFData data;

    @JsonProperty("accuracy")
    private double accuracy;

    @JsonProperty("lossPerIteration")
    private double[] lossPerIteration;

    @JsonProperty("fpr")
    private double fpr;

    @JsonProperty("tpr")
    private double tpr;

    @JsonProperty("precision")
    private double precision;

    @JsonProperty("recall")
    private double recall;

    @JsonProperty("weightedF")
    private double weightedF;

    @JsonProperty("prediction")
    private List<String> prediction;

    public RFModelResults(List<String> prediction, double accuracy, double[] lossPerIteration, double fpr, double tpr, double precision, double recall, double weightedF) {
        this.prediction = prediction;
        this.accuracy = accuracy;
        this.lossPerIteration = lossPerIteration;
        this.fpr = fpr;
        this.tpr = tpr;
        this.precision = precision;
        this.recall = recall;
        this.weightedF = weightedF;
    }

    public RFData getData() {
        return data;
    }

    public void setData(RFData data) {
        this.data = data;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double[] getLossPerIteration() {
        return lossPerIteration;
    }

    public void setLossPerIteration(double[] lossPerIteration) {
        this.lossPerIteration = lossPerIteration;
    }

    public double getFpr() {
        return fpr;
    }

    public void setFpr(double fpr) {
        this.fpr = fpr;
    }

    public double getTpr() {
        return tpr;
    }

    public void setTpr(double tpr) {
        this.tpr = tpr;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getRecall() {
        return recall;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public double getWeightedF() {
        return weightedF;
    }

    public void setWeightedF(double weightedF) {
        this.weightedF = weightedF;
    }
}
