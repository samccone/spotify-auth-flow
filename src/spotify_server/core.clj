(ns spotify-server.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.util.response :refer [redirect]]
            [clojure.data.json :as json]
            [clj-http.client :as client]
            [clojure.data.codec.base64 :as b64]
            [environ.core :refer [env]])
  (:gen-class))

(defn get-access-token [code]
  (let [client-id (env :client-id)
        client-secret (env :client-secret)
        redirect-uri (env :redirect-uri)
        url "https://accounts.spotify.com/api/token"
        response (client/post "https://accounts.spotify.com/api/token"
                              {
                               :headers {"Authorization" (str "Basic " (String. (b64/encode (.getBytes (str  client-id ":" client-secret)))))}
                               :form-params {:grant_type "authorization_code"
                                             :redirect_uri redirect-uri
                                             :code code}
                               :throw-exceptions false})]

    (if-let [token (:access_token response)]
      (redirect (str "/authed.html?token=" token))
      {:status 500
       :body (:body response)})))

(defn auth-handler [request]
  (if-let [code (-> request :params :code)]
    (get-access-token code)
    {:status 500
     :body "error"}))

(defn unmatched-handler [request]
  {:status 404
   :body (str "404 Unknown Route " (:uri request))})

(defn handler [request]
  (case (:uri request)
    "/auth" (auth-handler request)
    (unmatched-handler request)))

(def app (-> handler
             (wrap-resource "web/public")
             wrap-keyword-params
             wrap-params))

(defn -main [& args]
  (jetty/run-jetty app {:port (Integer. (or (env :port) "3333"))}))
