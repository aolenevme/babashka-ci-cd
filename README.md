# babashka-ci-cd
A template of a CI/CD for frontend applications

This is my experiment to build a fully featured CI/CD using Clojure scripting.

* Git branching model: https://nvie.com/posts/a-successful-git-branching-model/
* CI/CD runner: [Github Actions](https://github.com/features/actions) with a self-hosted runner on [Google Cloud](https://cloud.google.com/) to run [babashka](https://github.com/borkdude/babashka) scripts
* Units: [clojure.test](https://clojure.github.io/clojure/clojure.test-api.html)
* Linters: [clj-kondo](https://github.com/borkdude/clj-kondo), [kibit](https://github.com/jonase/kibit), [cljstyle](https://github.com/greglook/cljstyle)
* Building: [shadow-cljs](https://github.com/thheller/shadow-cljs)
* Git hooks: A simple call of common-scripts like this: 
~~~
#!/bin/bash

bb -f ./.github/hooks/commit-msg.clj`
~~~
