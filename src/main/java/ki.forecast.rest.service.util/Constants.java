package ki.forecast.rest.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constants {
    // APP CONFIG: SPARK CONFIGURATIONS
    public static final String APP_CONFIG_KEY_SPARK_APP_NAME = "sparkAppName";
    public static final String APP_CONFIG_KEY_SPARK_MASTER_CORES_TO_USE = "sparkCoresToUse";
    public static final String APP_CONFIG_KEY_SPARK_MODEL_RF_MAX_DEPTH = "sparkRfMaxDepth";
    public static final String APP_CONFIG_KEY_SPARK_MODEL_RF_NUM_TREES = "sparkRfNumTrees";
    public static final String APP_CONFIG_KEY_SPARK_MODEL_RF_MAX_BINS = "sparkRfMaxBins";
    public static final String APP_CONFIG_KEY_SPARK_MODEL_RF_IMPURITY = "sparkRfImpurity";

    //SPARK SESSION
    public static final String SPARK_APP_NAME = "Forecast Spark";
    public static final String SPARK_MASTER_CORES_TO_USE = "local[*]";
    public static final String SPARK_CONFIG_WINDOWS_STORAGE = "spark.sql.warehouse.dir";
    public static final String SPARK_CONFIG_WINDOWS_STORAGE_DIRECTORY = "file:///tmp/";
    public static final String SPARK_LOG_LEVEL_ERROR = "ERROR";
    public static final String SPARK_LOG_LEVEL_WARN = "WARN";
    public static final String SPARK_LOG_LEVEL_INFO = "INFO";
    public static final String SPARK_SAVE_FORMAT_EXCEL = "com.crealytics.spark.excel";
    public static final String SPARK_READ_WRITE_OPTION_HEADER = "header";
    public static final String SPARK_READ_OPTION_SCHEMA = "inferSchema";

    // SPARK MODEL
    public static final String SPARK_MODEL_FEATURES = "features";
    public static final String SPARK_MODEL_LABEL = "label";
    public static final String SPARK_MODEL_PREDICTION = "prediction";

    // SPARK RF MODEL
    public static final int SPARK_MODEL_RF_MAX_DEPTH = 4;
    public static final int SPARK_MODEL_RF_NUM_TREES = 100;
    public static final int SPARK_MODEL_RF_MAX_BINS = 10;
    public static final String SPARK_MODEL_RF_IMPURITY = "gini";

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String ERRORMESSAGE_DUPLICATE = "Duplicate";

    private Constants() {
    }
}
