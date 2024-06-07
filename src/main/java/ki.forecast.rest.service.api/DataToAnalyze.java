package ki.forecast.rest.service.api;

import java.io.Serializable;

import com.example.restapi.RFData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataToAnalyze implements Serializable {
    @JsonProperty("data")
    private RFData data;

    @JsonProperty("appName")
    private String appName;

    @JsonProperty("appCores")
    private String appCores;

    @JsonProperty("rfImpurity")
    private String rfImpurity;

    @JsonProperty("rfDepth")
    private Long rfDepth;

    @JsonProperty("rfBins")
    private Long rfBins;

    @JsonProperty("rfTrees")
    private Long rfTrees;

    public DataToAnalyze(RFData data, String appName, String appCores, String rfImpurity, Long rfDepth, Long rfBins, Long rfTrees) {
        this.data = data;
        this.appName = appName;
        this.appCores = appCores;
        this.rfImpurity = rfImpurity;
        this.rfDepth = rfDepth;
        this.rfBins = rfBins;
        this.rfTrees = rfTrees;
    }

    public DataToAnalyze() {

    }

    public RFData getData() {
        return data;
    }

    public void setData(RFData data) {
        this.data = data;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCores() {
        return appCores;
    }

    public void setAppCores(String appCores) {
        this.appCores = appCores;
    }

    public String getRfImpurity() {
        return rfImpurity;
    }

    public void setRfImpurity(String rfImpurity) {
        this.rfImpurity = rfImpurity;
    }

    public Long getRfDepth() {
        return rfDepth;
    }

    public void setRfDepth(Long rfDepth) {
        this.rfDepth = rfDepth;
    }

    public Long getRfBins() {
        return rfBins;
    }

    public void setRfBins(Long rfBins) {
        this.rfBins = rfBins;
    }

    public Long getRfTrees() {
        return rfTrees;
    }

    public void setRfTrees(Long rfTrees) {
        this.rfTrees = rfTrees;
    }
}
