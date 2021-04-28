(ns anomaly-detection-clj.core
  (:gen-class)
  (:use clojure.pprint)
  (:use (incanter core stats charts)))

(require '[clojure.data.csv :as csv]
         '[clojure.java.io :as io]
         ;;'[clojure.math.numeric-tower :as math]
         )

(import (com.markwatson.anomaly_detection AnomalyDetection))
(import (com.markwatson.anomaly_detection PrintHistogram))



(def NUM_HISTOGRAM_BINS 5)

(defn to-bin [a-vector num-bins vmin vmax]
  (map
    (fn [x]
      (int (/ (* 0.99 (- x vmin) num-bins) vmax)))
    a-vector))

(defn print-histogram [title values-2d index-to-display]
  (println "** plotting:" title)
  (let [column (for [row values-2d]
                 (nth row index-to-display))]
    (view (histogram column :title title))))


(defn data->gausian [vector-of-numbers-as-strings]
  "separate labeled output, and then make the data look more like a Gausian (bell curve shaped) distribution"
  (let [v (map read-string vector-of-numbers-as-strings)
        training-data0 (map
                        (fn [x] (Math/log (+ (* 0.1 x) 1.2)))
                        (butlast v))
        target-output (* 0.5 (- (last v) 2)) ; make target output be [0,1] instead of [2,4]
        vmin (apply min training-data0)
        vmax (apply max training-data0)
        training-data (map
                        (fn [x] (/
                                  (- x vmin)
                                  (+ 0.0001 (- vmax vmin))))
                        training-data0)]
    (concat training-data [target-output])))

(defn testAD []
  (def ad (AnomalyDetection.))
  (println ad)
  (def cdata
      (map
        data->gausian
        (with-open [reader (io/reader "data/cleaned_wisconsin_cancer_data.csv")]
          (doall
            (csv/read-csv reader)))))
;;  (println cdata)
  ;;(def java-training-array (into-array (doall (map (fn [x] (into-array java.lang.Double x)) cdata))))
  ;;(pprint java-training-array)

  ;;(def csvdata (slurp "data/cleaned_wisconsin_cancer_data.csv"))
  ;;(PrintHistogram/historam "Clump Thickness"  cdata 0 0.0 1.0 NUM_HISTOGRAM_BINS )
  (print-histogram "Clump Thickness" cdata 0)
  (print-histogram "Uniformity of Cell Size" cdata 1)
;;      )PrintHistogram.historam("Clump Thickness", training_data, 0, 0.0, 1.0, NUM_HISTOGRAM_BINS;
  )
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (testAD))
