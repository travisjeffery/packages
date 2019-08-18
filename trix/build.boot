(def +lib-version+ "1.2.0")
(def +version+ (str +lib-version+ "-0"))

(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.10.4" :scope "test"]
                 [javax.xml.bind/jaxb-api "2.4.0-b180830.0359" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(task-options!
 pom  {:project     'cljsjs/trix
       :version     +version+
       :description "A rich text editor for everyday writing."
       :url         "https://trix-editor.org"
       :scm         {:url "https://github.com/basecamp/trix"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package  []
  (comp
   (download :url (format "https://unpkg.com/trix@%s/dist/trix.js" +lib-version+)
             :target "cljsjs/trix/production/trix.js")
   (download :url (format "https://unpkg.com/trix@%s/dist/trix.css" +lib-version+)
             :target "cljsjs/trix/production/trix.css")
   (download :url (format "https://unpkg.com/trix@%s/dist/trix-core.js" +lib-version+)
             :target "cljsjs/trix/production/trix-core.js")
   (sift :include #{#"^cljsjs"})
   (pom)
   (jar)
   (validate)))
