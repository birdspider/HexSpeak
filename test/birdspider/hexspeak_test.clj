(ns birdspider.hexspeak-test
  (:require [clojure.test :refer :all]
            [birdspider.hexspeak :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]))

(defspec size-combination-sum 100
  (prop/for-all [length gen/s-pos-int
                 sizes (gen/vector-distinct gen/s-pos-int {:min-elements 1 :max-elements 8})]
                (every? #(= length %) (map #(apply + 0 %) (size-combinations length sizes)))))
