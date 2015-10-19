(ns fwpd.core
  (:require [clojure.string :as s]))

(def filename "suspects.csv")

(def headers->keywords {"Name" :name
                        "Glitter Index" :glitter-index})

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(def parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(s/split % #",")
       (s/split string #"\n")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
