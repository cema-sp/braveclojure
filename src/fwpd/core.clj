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

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(s/split % #",")
       (s/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (let [headers (map #(get headers->keywords %) (first rows))
        unmapped-rows (rest rows)]
    (map (fn [unmapped-row]
           (into {}
                 (map (fn [header column]
                        [header ((get conversions header) column)])
                      headers
                      unmapped-row)))
         unmapped-rows)))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn names-for
  [records]
  (map #(:name %) records))

(defn prepend
  [name glitter-index list]
  (cons {:name name :glitter-index glitter-index} list))

(def not-nil? (complement nil?))

(def validations {:name not-nil?
                  :glitter-index not-nil?})

(defn validate
  [record]
  (every? true? (map #((% validations) (% record)) (into [] (keys validations)))))

(defn csvfy
  "Convert map to CSV"
  [records]
  (let [records-w-headers (prepend "Name" "Glitter Index" records)]
    (s/join "\n"
            (map #(s/join "," (vals %)) records-w-headers))))

;;(defn -main
;;  "I don't do a whole lot ... yet."
;;  [& args]
;;  (println "Hello, World!"))
