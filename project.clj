(defproject vpncontrol "0.1.0-SNAPSHOT"
  :description "Control the two VPN clients in a Tomato Router via SSH"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-ssh "0.5.11"]
                 [clj-http "2.0.0"]]
  :main ^:skip-aot vpncontrol.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/tools.nrepl "0.2.7"]]
                   :plugins [[cider/cider-nrepl "0.10.0-SNAPSHOT"]]}})
