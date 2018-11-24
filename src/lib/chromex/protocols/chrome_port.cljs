(ns chromex.protocols.chrome-port)

(defprotocol IChromePort
  "a wrapper for https://developer.chrome.com/extensions/runtime#type-Port"
  (get-native-port [this])
  (get-name [this])
  (get-sender [this])
  (post-message! [this message])
  (disconnect! [this])
  (on-disconnect! [this callback])
  (on-message! [this callback]))
