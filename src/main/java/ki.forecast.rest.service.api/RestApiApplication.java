package ki.forecast.rest.service.api;

import static org.apache.spark.sql.functions.dayofmonth;
import static org.apache.spark.sql.functions.dayofweek;
import static org.apache.spark.sql.functions.dayofyear;
import static org.apache.spark.sql.functions.month;
import static org.apache.spark.sql.functions.weekofyear;
import static org.apache.spark.sql.functions.year;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdk.nashorn.api.scripting.JSObject;
import org.apache.hadoop.shaded.com.nimbusds.jose.shaded.json.JSONObject;
import org.apache.spark.ml.classification.RandomForestClassificationModel;
import org.apache.spark.ml.classification.RandomForestClassificationTrainingSummary;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.json4s.jackson.Json;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.Constants;

@SpringBootApplication
public class RestApiApplication implements CommandLineRunner {

    public static List<LocalDate> totalDates = new ArrayList<>();
    private double accuracy;
    private double[] lossPerIteration;
    private double fpr;
    private double tpr;
    private double precision;
    private double recall;
    private double weightedF;
    private RFData rfData;
    private DataToAnalyze dataToAnalyze;
    private Dataset<String> stringDataset;
    private RFModelResults results;
    private List<String> jsonString;

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (dataToAnalyze != null) {
            analyze();
            results = new RFModelResults(jsonString, accuracy, lossPerIteration, fpr, tpr, precision, recall, weightedF);
        }
    }

    public void analyze() {

        SparkSession spark = SparkSession.builder()
                .appName("Forecast Spark") // dataToAnalyze.getAppName()
                .master("local[*]") // dataToAnalyze.getAppCores()
                .config(Constants.SPARK_CONFIG_WINDOWS_STORAGE, Constants.SPARK_CONFIG_WINDOWS_STORAGE_DIRECTORY)
                .getOrCreate();

        spark.sparkContext().setLogLevel(Constants.SPARK_LOG_LEVEL_ERROR);
        System.out.println("Spark Version: " + spark.version());

        System.out.println(dataToAnalyze.getData().getIncomingDateDouble());
        System.out.println(dataToAnalyze.getData().getIncomingDate());


        Dataset<org.apache.spark.sql.Row> date = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getIncomingDate(), Encoders.STRING()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.STRING(), Encoders.LONG())).toDF("date", "index");

        Dataset<org.apache.spark.sql.Row> dayOfWeek = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getDayOfWeek(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("dayOfWeek", "index");

        Dataset<org.apache.spark.sql.Row> month = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getMonth(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("month", "index");

        Dataset<org.apache.spark.sql.Row> weekOfYear = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getWeekOfYear(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("weekOfYear", "index");

        Dataset<org.apache.spark.sql.Row> year = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getYear(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("year", "index");

        Dataset<org.apache.spark.sql.Row> weekend = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getWeekend(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("weekend", "index");

        Dataset<org.apache.spark.sql.Row> dayOfYear = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getDayOfYear(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("dayOfYear", "index");

        Dataset<org.apache.spark.sql.Row> amount = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getAmount(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("amount", "index");

        String s = "1900-01-01";
        String e = "2099-12-31";
        LocalDate start = LocalDate.parse(s);
        LocalDate end = LocalDate.parse(e);

        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }

        Dataset<LocalDate> dates = spark.createDataset(totalDates, Encoders.LOCALDATE());

        Dataset<org.apache.spark.sql.Row> datesToPrognose = dates
                .select(dates.col("value").as("dateReceived"),
                        year(dates.col("value")).as("year"),
                        month(dates.col("value")).as("month"),
                        weekofyear(dates.col("value")).as("weekOfYear"),
                        dayofmonth(dates.col("value")).as("dayOfMonth"),
                        dayofweek(dates.col("value")).as("dayOfWeek"),
                        dayofyear(dates.col("value")).as("dayOfYear"));
        datesToPrognose.show();

        datesToPrognose.printSchema();

        Dataset<org.apache.spark.sql.Row> ds = date
                .join(month, "index")
                .join(weekOfYear, "index")
                .join(year, "index")
                .join(weekend, "index")
                .join(dayOfYear, "index")
                .join(dayOfWeek, "index")
                .join(amount, "index");

        ds.show();
        ds.printSchema();

        ds.show();
        ds.printSchema();

        Dataset<org.apache.spark.sql.Row> datePrediction = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getPredictionDate(), Encoders.STRING()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.STRING(), Encoders.LONG())).toDF("date", "index");

        Dataset<org.apache.spark.sql.Row> dayOfWeekPrediction = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getPredictionDayOfWeek(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("dayOfWeek", "index");

        Dataset<org.apache.spark.sql.Row> monthPrediction = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getPredictionMonth(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("month", "index");

        Dataset<org.apache.spark.sql.Row> weekOfYearPrediction = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getPredictionWeekOfYear(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("weekOfYear", "index");

        Dataset<org.apache.spark.sql.Row> yearPrediction = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getPredictionYear(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("year", "index");

        Dataset<org.apache.spark.sql.Row> weekendPrediction = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getPredictionWeekend(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("weekend", "index");

        Dataset<org.apache.spark.sql.Row> dayOfYearPrediction = spark.createDataset(
                spark.createDataset(dataToAnalyze.getData().getPredictionDayOfYear(), Encoders.INT()).toJavaRDD().zipWithIndex().collect(),
                Encoders.tuple(Encoders.INT(), Encoders.LONG())).toDF("dayOfYear", "index");


        Dataset<org.apache.spark.sql.Row> dsPrediction = datePrediction
                .join(monthPrediction, "index")
                .join(weekOfYearPrediction, "index")
                .join(yearPrediction, "index")
                .join(weekendPrediction, "index")
                .join(dayOfYearPrediction, "index")
                .join(dayOfWeekPrediction, "index");

        Dataset<org.apache.spark.sql.Row> datasetWithFeatures = new VectorAssembler().setInputCols(new String[]{"dayOfWeek", "month", "weekOfYear", "year", "dayOfYear", "weekend"})
                .setOutputCol(Constants.SPARK_MODEL_FEATURES)
                .transform(ds);
        datasetWithFeatures.show();

        Dataset<org.apache.spark.sql.Row>[] dataSplits = datasetWithFeatures.withColumnRenamed("amount", Constants.SPARK_MODEL_LABEL)
                .randomSplit(new double[]{0.8, 0.2});

        Dataset<org.apache.spark.sql.Row> trainingData = dataSplits[0];
        Dataset<org.apache.spark.sql.Row> testData = dataSplits[1];
        Dataset<org.apache.spark.sql.Row> holdOutData = new VectorAssembler().setInputCols(new String[]{"dayOfWeek", "month", "weekOfYear", "year", "dayOfYear", "weekend"})
                .setOutputCol(Constants.SPARK_MODEL_FEATURES)
                .transform(dsPrediction);

        RandomForestClassifier rfClassifier = new RandomForestClassifier();
        rfClassifier.setMaxDepth(Constants.SPARK_MODEL_RF_MAX_DEPTH)
                .setNumTrees(Constants.SPARK_MODEL_RF_NUM_TREES)
                .setImpurity(Constants.SPARK_MODEL_RF_IMPURITY)
                .setMaxBins(Constants.SPARK_MODEL_RF_MAX_BINS);
        RandomForestClassificationModel rfModel = rfClassifier.fit(trainingData);
        Dataset<org.apache.spark.sql.Row> rfPredictionsOnTestData = rfModel.transform(testData);
        rfPredictionsOnTestData.show();

        System.out.println("Predicting: ...");
        Dataset<org.apache.spark.sql.Row> rfPredictionsOnHoldOutData = rfModel.transform(holdOutData);


        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator();
        evaluator.setMetricName("accuracy");
        System.out.println("The accuracy of the random forest model is " + evaluator.evaluate(rfPredictionsOnTestData));

        RandomForestClassificationTrainingSummary trainingSummary = rfModel.summary();

        // Obtain the loss per iteration.
        System.out.println("Loss per iteration");
        double[] objectiveHistory = trainingSummary.objectiveHistory();
        lossPerIteration = trainingSummary.objectiveHistory();
        for (double lossPerIteration : objectiveHistory) {
            System.out.println(lossPerIteration);
        }

// for multiclass, we can inspect metrics on a per-label basis
        System.out.println("False positive rate by label:");
        int i = 0;
        double[] fprLabel = trainingSummary.falsePositiveRateByLabel();
        for (double fpr : fprLabel) {
            System.out.println("label " + i + ": " + fpr);
            i++;
        }

        System.out.println("True positive rate by label:");
        i = 0;
        double[] tprLabel = trainingSummary.truePositiveRateByLabel();
        for (double tpr : tprLabel) {
            System.out.println("label " + i + ": " + tpr);
            i++;
        }

        System.out.println("Precision by label:");
        i = 0;
        double[] precLabel = trainingSummary.precisionByLabel();
        for (double prec : precLabel) {
            System.out.println("label " + i + ": " + prec);
            i++;
        }

        System.out.println("Recall by label:");
        i = 0;
        double[] recLabel = trainingSummary.recallByLabel();
        for (double rec : recLabel) {
            System.out.println("label " + i + ": " + rec);
            i++;
        }

        System.out.println("F-measure by label:");
        i = 0;
        double[] fLabel = trainingSummary.fMeasureByLabel();
        for (double f : fLabel) {
            System.out.println("label " + i + ": " + f);
            i++;
        }

        accuracy = trainingSummary.accuracy();
        fpr = trainingSummary.weightedFalsePositiveRate();
        tpr = trainingSummary.weightedTruePositiveRate();
        precision = trainingSummary.weightedPrecision();
        recall = trainingSummary.weightedRecall();
        weightedF = trainingSummary.weightedFMeasure();

        System.out.println("Accuracy: " + accuracy);
        System.out.println("FPR: " + fpr);
        System.out.println("TPR: " + tpr);
        System.out.println("F-measure: " + weightedF);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);

        rfPredictionsOnTestData.select("date", "label", "prediction").withColumnRenamed("label", "amount").show();

        Dataset<Row> rfResults = rfPredictionsOnHoldOutData.select("date", "prediction");
        System.out.println("Show rfResults: ...");
        rfResults.show();
        jsonString = rfResults.toJSON().collectAsList();

        System.out.println("JSON String: " + jsonString);
        results = new RFModelResults(jsonString, accuracy, lossPerIteration, fpr, tpr, precision, recall, weightedF);
        String resultString = rfPredictionsOnTestData.select("date", "label", "prediction").withColumnRenamed("label", "amount").toJSON().toString();

    }

    public RFData getRfData() {
        return rfData;
    }

    public void setRfData(RFData rfData) {
        this.rfData = rfData;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double[] getLossPerIteration() {
        return lossPerIteration;
    }

    public double getFpr() {
        return fpr;
    }

    public double getTpr() {
        return tpr;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }

    public double getWeightedF() {
        return weightedF;
    }

    public void setDataToAnalyze(DataToAnalyze dataToAnalyze) {
        this.dataToAnalyze = dataToAnalyze;
    }

    public Dataset<String> getStringDataset() {
        return stringDataset;
    }

    public RFModelResults getResults() {
        return results;
    }

    public List<String> getJsonString() {
        return jsonString;
    }

    public void setJsonString(List<String> jsonString) {
        this.jsonString = jsonString;
    }
}
