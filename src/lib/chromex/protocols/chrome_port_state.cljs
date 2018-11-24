(ns chromex.protocols.chrome-port-state)

(defprotocol IChromePortState
  (set-connected! [this val])
  (put-message! [this message])
  (close-resources! [this]))
