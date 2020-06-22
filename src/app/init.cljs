(ns app.init
  (:require
    ["http" :as http]))


(def port
  (if-let [env-port (. js/process.env -PORT)]
    env-port
    8080))


(defn request-handler
  [req res]
  (.end res "foo"))


(defonce server-refs
  (volatile! nil))


(defn main
  [& args]
  (js/console.log "Starting server")
  (let [server (http/createServer #(request-handler %1 %2))]

    (.listen server port
             (fn [err]
               (if err
                 (js/console.error "Server start failed")
                 (js/console.info "Http server running"))))

    (vreset! server-refs server)))
