(ns deeplearning-dl4j-clj.wisconsin-data
  (:import [org.datavec.api.split FileSplit]
           [org.datavec.api.util ClassPathResource]
           [org.datavec.api.io.labels ParentPathLabelGenerator]
           [org.datavec.image.recordreader ImageRecordReader]
           [org.nd4j.linalg.dataset.api.preprocessor ImagePreProcessingScaler]
           [org.datavec.image.loader NativeImageLoader]
           [org.deeplearning4j.datasets.datavec
            RecordReaderDataSetIterator
            SequenceRecordReaderDataSetIterator RecordReaderDataSetIterator$Builder]
           [org.datavec.api.records.reader.impl.csv
            CSVRecordReader
            CSVSequenceRecordReader]
           [org.deeplearning4j.nn.conf
            NeuralNetConfiguration$Builder
            GradientNormalization
            ]
           [org.deeplearning4j.nn.api OptimizationAlgorithm]
           [org.deeplearning4j.nn.weights WeightInit]
           [org.nd4j.linalg.activations Activation]
           [org.nd4j.linalg.lossfunctions LossFunctions$LossFunction]
           [org.deeplearning4j.optimize.listeners ScoreIterationListener]
           [org.deeplearning4j.nn.multilayer MultiLayerNetwork]
           [org.deeplearning4j.util ModelSerializer]
           [org.deeplearning4j.nn.conf
            BackpropType
            WorkspaceMode]
           [org.deeplearning4j.nn.conf.layers
            SubsamplingLayer$Builder
            SubsamplingLayer$PoolingType
            ConvolutionLayer$Builder
            RnnOutputLayer$Builder
            GravesLSTM$Builder
            LSTM$Builder
            DropoutLayer$Builder
            OutputLayer$Builder
            DenseLayer$Builder
            LocalResponseNormalization$Builder GravesBidirectionalLSTM$Builder]
           [org.deeplearning4j.nn.conf.inputs InputType]
           [org.deeplearning4j.nn.conf.distribution
            NormalDistribution
            GaussianDistribution]
           [java.io File]
           [java.util Random]
           [org.nd4j.linalg.schedule StepSchedule MapSchedule ScheduleType ExponentialSchedule InverseSchedule PolySchedule SigmoidSchedule]
           [org.deeplearning4j.nn.conf.layers.variational VariationalAutoencoder VariationalAutoencoder$Builder]
           [org.nd4j.linalg.learning.config Nesterovs Adam RmsProp Sgd AdaDelta AdaGrad AdaMax Nadam NoOp]))

;; https://github.com/hswick/jutsu.ai/blob/master/src/jutsu/ai/core.clj

(def numHidden 3)
(def numOutputs 1)
(def batchSize 64)

(def initial-seed (long 33117))

(def numInputs 9)
(def labelIndex 9)
(def numClasses 2)


(defn -main
  "Using DL4J with Wisconsin data"
  [& args]
  (let [recordReader (new CSVRecordReader)
        _ (. recordReader initialize (new FileSplit (new File "data/","training.csv")))
        trainIter (new RecordReaderDataSetIterator recordReader batchSize labelIndex numClasses)
        recordReaderTest (new CSVRecordReader)
        _ (. recordReaderTest initialize (new FileSplit (new File "data/","testing.csv")))
        testIter (new RecordReaderDataSetIterator recordReaderTest batchSize labelIndex numClasses)
        conf (->
               (new NeuralNetConfiguration$Builder)
               (.seed initial-seed)
               (.activation Activation/TANH)
               (.weightInit( WeightInit/XAVIER))
        ;;TBD finish above on Intel Mac (no ARM DL4J backend
        ]
    ;;(. conf  (activation Activation/TANH))
    ;;(-> conf  (.activation Activation/TANH))
    (println trainIter)
    (println testIter)
    (println conf)
    ))

