(ns vpncontrol.core
  (:require [clj-http.client :as client]
            [clj-ssh.ssh :as ssh])
  (:gen-class))

(def config (read-string (slurp "./config.clj")))

(def router-ssh-key (:ssh-key config))
(def router-ssh-host (:ssh-host config))
(def router-ssh-user (:ssh-user config))
(def command-base "service vpnclient")

(def vpn-api (str "http://" router-ssh-host "/vpnstatus.cgi"))
(def vpn-api-user (:api-user config))
(def vpn-api-pass (:api-password config))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn vpn-req
  "Turns a client ID into a request body string"
  [client]
  (str "client=" client "&_http_id=TID81019f3acb0fdc5f"))

(defn vpn-map
  "Returns a map for use with clj-http.client/post"
  [client]
  {:basic-auth [vpn-api-user vpn-api-pass]
   :body (vpn-req client)})

(defn get-vpn-status-body
  "Get body from vpnstatus.cgi for a client"
  [client]
  (:body (client/post vpn-api (vpn-map client))))

(defn vpn-on?
  "Returns true if the given vpn client is turned on"
  [client]
  (let [resp-body (get-vpn-status-body client)]
    (if (= resp-body "")
      false
      true)))

(defn vpn-client-command
  "Creates the command to turn on or off the VPN"
  [client on-or-off]
  (if (= on-or-off :on)
    (str command-base client " start")
    (str command-base client " stop")))

(defn vpn-command
  "Turns on or off the specified VPN"
  [client on-or-off]
  (let [agent (ssh/ssh-agent {:use-system-ssh-agent false})]
    (ssh/add-identity agent {:private-key-path router-ssh-key})
    (let [session (ssh/session agent router-ssh-host {:strict_host-key-checking :no :username router-ssh-user})]
      (ssh/with-connection session
        (let [result (ssh/ssh session {:cmd (vpn-client-command client on-or-off)})]
          (println (result :out))
          result)))))

(defn turn-on-vpn
  "Turns on the specified VPN"
  [client]
  (vpn-command client :on))

(defn turn-off-vpn
  "Turns off the specified VPN"
  [client]
  (vpn-command client :off))
