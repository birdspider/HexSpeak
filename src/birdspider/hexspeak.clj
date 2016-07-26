(ns birdspider.hexspeak
  (:require [clojure.math.combinatorics :as combo])
  (:use [medley.core :only [map-vals]])
  (:gen-class))

;(set! *warn-on-reflection* true)
;(set! *unchecked-math* :warn-on-boxed)

;FIXME: does not work
(defn size-combinations
  "Given an array of possible string lengths, i.e. [2 3 4] computes all possible variations where the sum of the array
  equals 'length'. i.e. with length 4,  #{[2 2] [4]}"
  [length sizes]
  (set (mapcat combo/permutations
               (filter #(= length (apply + %))
                       (mapcat (partial combo/selections sizes) (range 1 6))))))

;(size-combinations 14 [1 4 6 7 3 5])
;(combo/subsets [1 4 6 7 3 5])

(defn load-words [& {:keys [dict chars] :or {dict "contrib/words" chars "abcdef"}}]
  (let [forbidden #{"aaa" "aba" "abc"}
        m (re-pattern (str "^[" chars "]*$"))
        f #(and (> (.length ^String %) 2)
                (not (forbidden %))
                (re-matches m %))]
   (->> (slurp dict)
        (clojure.string/split-lines)
        (filter f)
        (group-by count)
        (#(conj % [1 ["a"]])))))

;(load-words)

(defn count-for-size
  "returns the number of possible word combinations for a given size"
    [size wordcount]
  (apply * (map #(get wordcount % 1) size)))

(defn- count-only [ws l]
  (let [sizes (keys ws)
        wordcount (map-vals count ws)
        sx (size-combinations l sizes)]
    (apply + (map #(count-for-size % wordcount) sx))))

(defn solve
  "Using the list of valid options from our list of words,
  recurse to form complete phrases of the desired target-length,
  and count them all up to see how many there are."

  [words len counter mode]
  (vreset! counter
           (case mode
             (count-only words len))))

(defn -main [& args]
  "Expects as cmd-line arguments:

  - Desired length of phrases (e.g. 8)
  - Letters to search for     (e.g. abcdef)
  - Dictionary file to use    (e.g. /usr/share/dict/words)

  Prints the number of such HexSpeak phrases
  (e.g. 0xADEADBEE - a dead bee - is one of them)"

  (let [phrase-length (Integer. ^String (re-find #"\d+" (nth args 0 4)))
        letters (nth args 1 "abcdef")
        dictionary-file (nth args 2 "/usr/share/dict/words")
        counter (volatile! 0) ; faster than atom
        words (load-words :dict dictionary-file :chars letters)]
    (dotimes [n 10]
      (do
        (vreset! counter 0)
        (time (solve words phrase-length counter :count-only))
        (printf "Total: %d\n" @counter)))
    (flush)))

(-main "14" "abcdef" "contrib/words")
