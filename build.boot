(def project 'provisdom/gitlab-tools)
(def version "0.1-alpha1")

(set-env! :resource-paths #{"src"}
          :source-paths #{"test"}
          :dependencies '[[provisdom/boot-tasks "1.4" :scope "test"]
                          [adzerk/boot-test "1.2.0" :scope "test"]

                          [org.clojure/clojure "1.8.0"]
                          [http-kit "2.2.0"]
                          [cheshire "5.7.1"]])

(require '[adzerk.boot-test :refer [test]]
         '[provisdom.boot-tasks.core :refer [build push-jar]])

(task-options!
  pom {:project     project
       :version     version
       :description "FIXME: write description"
       :url         "http://example/FIXME"
       :scm         {:url "https://github.com/yourname/gitlab-tools"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}}
  jar {:main 'provisdom.gitlab-tools.core
       :file (str "gitlab-tools-" version "-standalone.jar")})
