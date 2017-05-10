(ns provisdom.gitlab-tools.core
  (:require
    [org.httpkit.client :as http]
    [cheshire.core :as json]))

(def access-token (System/getenv "GITLAB_ACCESS_TOKEN"))
(def base-url "https://gitlab.com/api/v4")

(defn request
  [opts]
  (http/request (assoc-in opts [:headers "PRIVATE-TOKEN"] access-token)))

(defn projects-for-group
  [group-id]
  (-> @(request {:method :get
                 :url    (str base-url "/groups/" group-id)})
      :body
      (json/decode true)
      :projects))

(defn create-variable
  [project-id key value]
  @(request {:method  :post
             :url     (str base-url "/projects/" project-id "/variables")
             :headers {"Content-Type" "application/json"}
             :body    (json/encode
                        {:key   key
                         :value value})}))

(defn add-var-to-projects
  [projects key value]
  (let [results (pmap (fn [{:keys [id]}]
                        (create-variable id key value))
                      projects)]
    (filter #(not= (:status %) 201) results)))