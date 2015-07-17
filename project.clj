(defproject spotify-server "0.1.0-SNAPSHOT"
  :description "a spotify oauth server"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.8.11"]
            [lein-environ "1.0.0"]]
  :min-lein-version "2.5.0"
  :ring {:handler spotify-server.core/app}
  :main ^:skip-aot spotify-server.core
  :uberjar-name "spotify-server-standalone.jar"
  :profiles {:uberjar {:aot :all}}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-core "1.3.2"]
                 [org.clojure/data.codec "0.1.0"]
                 [clj-http "1.1.2"]
                 [org.clojure/data.json "0.2.6"]
                 [environ "1.0.0"]
                 [ring/ring-jetty-adapter "1.3.2"]])
