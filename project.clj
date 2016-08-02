(defproject thanassis "0.1.0-SNAPSHOT"
  :description "HexSpeak - playing with Clojure and Python"
  :url "https://github.com/ttsiodras/HexSpeak"
  :license {:name "GNU Public License"
            :url "http://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/math.combinatorics "0.1.3"]
                 [medley "0.8.2"]
                 [org.clojure/test.check "0.9.0"]]
  :aot [thanassis.hexspeak]
  ; :main ^:skip-aot thanassis.hexspeak
  :main thanassis.hexspeak
  :target-path "target/%s"
  :jvm-opts ^:replace []
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[com.taoensso/tufte "1.0.0"]]}})
