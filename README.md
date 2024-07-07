# Apache Spark Future Ticket Prediction REST Service
## REST API on Spring Boot that gets JSON input to analyze using MLIB Apache Spark library to predict future outcome and returns JSON.


This repository contains the code for a RESTful service that uses Apache Spark to predict future ticket sales. The service leverages machine learning models to analyze historical data and make predictions. It is built using Java, Spring Boot, and Apache Spark.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Data Analysis and Preparation:** Processes historical ticket data to extract useful features.
- **Machine Learning:** Utilizes a Random Forest classifier for predictions.
- **RESTful API:** Provides endpoints to trigger data analysis and retrieve predictions.
- **Accuracy Metrics:** Computes various performance metrics for the model including accuracy, precision, recall, and F-measure.

## Requirements

- Java 8 or higher
- Apache Maven
- Apache Spark 3.x
- Spring Boot 2.x
- Hadoop (if running in a distributed environment)

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/akrothschild/rest-for-spark-prediction.git
   cd rest-for-spark-prediction
   ```

2. **Build the project:**

   ```bash
   mvn clean install
   ```
   

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

   ## Usage

1. **Start the Spark service:**
   Ensure that the Apache Spark service is running on your machine or cluster.

2. **Send a request to trigger data analysis:**
   Use a tool like `curl` or Postman to send a request to the API endpoint.

3. **Retrieve predictions:**
   Predictions can be retrieved from the designated API endpoint in JSON format.

## Endpoints

- **POST /analyze**
  - Description: Trigger data analysis and model training.
  - Request Body: JSON object containing historical data.

- **GET /results**
  - Description: Retrieve the results of the latest analysis and predictions.
  - Response: JSON object containing predictions and accuracy metrics.

## Configuration

Configure Spark settings and application properties in the `application.properties` file.

```properties
# Spark Configuration
spark.app.name=Forecast Spark
spark.master=local[*]
spark.config.windows.storage=your_storage_path
spark.config.windows.storage.directory=your_directory
spark.log.level=ERROR

# Random Forest Configuration
spark.model.rf.maxDepth=5
spark.model.rf.numTrees=20
spark.model.rf.impurity=gini
spark.model.rf.maxBins=32
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Create a new Pull Request.

## License

This project is licensed. See the [LICENSE](LICENSE) file for details.
