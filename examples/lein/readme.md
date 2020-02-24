# chromex-sample

### An example extension using Chromex library

This project acts as a code example for [chromex library](https://github.com/binaryage/chromex) but also as a skeleton
with project configuration following best practices. We recommend using it as a starting point when starting development
of your own extension.

#### **chromex-sample** has a minimalist **background page**, **popup button** and **content script**:

  * background page listens for connections from popup buttons and content scripts (there can be multiple of them)
  * popup button connects to the background page and sends a simple "HELLO" message after connection
  * content script connects to the background page and sends a simple "HELLO" message after connection
  * content script does a simple page analysis upon launch (it counts number of script tags) and sends an info message to the 
  background page
  * background page listens to tab creation events and notifies all connected clients about new tabs

#### **chromex-sample** project has following configuration:

  * uses [leiningen](http://leiningen.org) + [lein-cljsbuild](https://github.com/emezeske/lein-cljsbuild)
  * integrates [cljs-devtools](https://github.com/binaryage/cljs-devtools)
  * integrates [figwheel](https://github.com/bhauman/lein-figwheel) (for background page and popup buttons)
  * under `:unpacked` profile (development)
    * background page and popup button
      * compiles with `optimizations :none`
      * namespaces are included as individual files and source maps work as expected
      * figwheel works
    * content script
      * due to [security restrictions](https://github.com/binaryage/chromex-sample/issues/2), content script has to be 
      provided as a single file
      * compiles with `:optimizations :whitespace` and `:pretty-print true`
      * figwheel cannot be used in this context (eval is not allowed)
  * under `:release` profile
    * background page, popup button and content script compile with `optimizations :advanced`
    * elides asserts
    * no figwheel support
    * no cljs-devtools support
    * `./scripts/package.sh` builds extension package for release

### Local setup

#### Extension development

We assume you are familiar with ClojureScript tooling:

  * clone this repo somewhere:
    ```bash
    git clone https://github.com/binaryage/chromex.git
    cd chromex/examples/lein
    ```
  * chromex example is gets built into `resources/unpacked/compiled` folder.

    In one terminal session run (will build background and popup pages using figwheel):
    ```bash
    lein fig
    ```
    In a second terminal session run (will auto-build content-script):
    ```bash
    lein content
    ```
  * use the latest Chrome Canary with [Custom Formatters][custom-formatters] enabled
  * In Chrome Canary, open `chrome://extensions` and add `resources/unpacked` via "Load unpacked extension..."

##### Debugging

Chrome extension development is more complex than regular ClojureScript front-end
work. You are writing (and debugging) multiple parallel communicating processes: your
background page, your popup, and all the browser pages running your content script.

Amazingly, the ClojureScript tooling and Figwheel live coding remain very usable in this
environment. You need to be aware of a few things, particularly in regard to
compiler warnings:

Most warnings do not appear in the REPLs. Figwheel intercepts them for display in the
browser. Warning will appear in the Chrome console and, when possible, as an overlay in
the browser window. The exact behavior depends upon which part of your code has the
error:

_Content script_: Warnings and errors will appear in the repl running `lein content`.

_popup_: Chrome normally closes the popup anytime focus leaves Chrome. So, if you are
working in your editor, the popup is closed, and you will not see any error messages
anywhere. This can be very frustrating but is easy to fix. When you first open the
popup, right click on its icon and select `Inspect popup`. This opens the Chrome
inspector/console and keeps the popup open while the inspector remains open. Any errors
will appear in both the console and as the Figwheel overlay in your popup window. Also,
of course, this gives you niceties of Figwheel live coding. Your changes will appear
immediately, with no need to close and reopen the popup.

_background_: The background code is running under Figwheel, so no messages will appear
in the repl. It also has no visible window, so no Figwheel overlay can appear. You will
only see warnings in the Chrome console. You can open the inspector/console from
`chrome://extensions`. Under your extension, click on the `Inspect Views` line.

In summary, effective live debugging requires up to five open windows on your screen:
- Your editor;
- The shell running `lein content`, if you are making changes to content script code;
- The web browser, with open popup and/or content page;
- A Chrome inspector console, watching the background page; and
- A Chrome inspector console, watching the popup page.



#### Extension packaging

[Leiningen project](project.clj) has defined "release" profile for compilation in the advanced mode. Run:
```bash
lein release
```

This will build an optimized build into [resources/release](resources/release). You can add this folder via 
"Load unpacked extension..." to test it.

When satisfied, you can run:
```bash
./scripts/package.sh
```

This will create a folder `releases/chromex-sample-0.1.0` where 0.1.0 will be the current version from [project.clj](project.clj).
This folder will contain only files meant to be packaged.

Finally, you can use Chrome's "Pack extension" tool to prepare the final package (.crx and .pem files).

### Code discussion

Before reading the code below you should get familiar with [Chrome Extension System architecture][chrome-extensions-docs].

#### Popup page

Let's start with [popup button code](src/popup/chromex_sample/popup/core.cljs):

```clojure
; -- a message loop -------------------------------------------------------------------------------

(defn process-message! [message]
  (log "POPUP: got message:" message))

(defn run-message-loop! [message-channel]
  (log "POPUP: starting message loop...")
  (go-loop []
    (when-some [message (<! message-channel)]
      (process-message! message)
      (recur))
    (log "POPUP: leaving message loop")))

(defn connect-to-background-page! []
  (let [background-port (runtime/connect)]
    (post-message! background-port "hello from POPUP!")
    (run-message-loop! background-port)))

; -- main entry point -----------------------------------------------------------------------------

(defn init! []
  (log "POPUP: init")
  (connect-to-background-page!))
```

When you click the popup button, Chrome creates a new javascript context and runs our code by calling `init!`.
At this point we call [`runtime/connect`][runtime-connect-docs] to connect to our 
background page. We get a `background-port` back which is a wrapper of [`runtime.Port`][runtime-port-docs].
`background-port` implements chromex protocol [`IChromePort`][chrome-port-src]
which we can use to `post-message!` to our background page. `background-port` implements `core-async/ReadPort` so we 
can treat it as a core.async channel for reading incoming messages sent by our background page. You can see that implemented 
in `run-message-loop!` which takes messages off the channel and simply prints them into the console (in `process-message!`).

##### Marshalling

At this point you might ask. How is it possible that we called API method `runtime/connect` and got back `background-port` 
implementing `IChromePort`? That is not documented behaviour described in [Chrome's extension APIs docs][runtime-connect-docs].
We would expect a native javascript object of type `runtime.Port`.

This transformation was done by [marshalling subsystem](https://github.com/binaryage/chromex/#flexible-marshalling) 
implemented in Chromex library. Marshalling is responsible for converting parameter values when crossing API boundary. 
Parameter values can be automatically converted to ClojureScript values when returned from native Javascript API calls and
in the other direction parameters can be converted to native Javascript values when passed into API calls. This is a way how 
to ease extension development and promote idiomatic ClojureScript patterns.

Chromex library does not try to do heavy marshalling. You should review marshalling logic in 
[marshalling.clj][marshalling-clj-src] and [marshalling.cljs][marshalling-cljs-src] files to understand which parameter types 
get converted and how. You can also later use this subsystem to marshall additional parameter types of your own interest. 
For example automatic calling of `js->clj` and `clj->js` would come handy at many places.

##### Message loop

It is worth noting that core.async channel [returns `nil` when closed][core-async-docs].
That is why we leave the message loop after receiving a `nil` message. If you wanted to terminate the message channel from 
popup side, you could call core.async's `close!` on the message-channel (it implements [`core-async/Channel`][chrome-port-src] 
and will properly disconnect `runtime.Port`).

As a consequence you cannot send a `nil` message through our channel.

#### Background page

Let's take a look at [background page](src/background/chromex_sample/background/core.cljs) which is also pretty simple. 
It just has to handle multiple clients with their individual message loops. Also, it maintains one main event loop for 
receiving events from Chrome. With core.async channels the code reads quite well:

```clojure
(def clients (atom []))

; -- clients manipulation -------------------------------------------------------------------------

(defn add-client! [client]
  (log "BACKGROUND: client connected" (get-sender client))
  (swap! clients conj client))

(defn remove-client! [client]
  (log "BACKGROUND: client disconnected" (get-sender client))
  (let [remove-item (fn [coll item] (remove #(identical? item %) coll))]
    (swap! clients remove-item client)))

; -- client event loop ----------------------------------------------------------------------------

(defn run-client-message-loop! [client]
  (log "BACKGROUND: starting event loop for client:" (get-sender client))
  (go-loop []
    (when-some [message (<! client)]
      (log "BACKGROUND: got client message:" message "from" (get-sender client))
      (recur))
    (log "BACKGROUND: leaving event loop for client:" (get-sender client))
    (remove-client! client)))

; -- event handlers -------------------------------------------------------------------------------

(defn handle-client-connection! [client]
  (add-client! client)
  (post-message! client "hello from BACKGROUND PAGE!")
  (run-client-message-loop! client))

(defn tell-clients-about-new-tab! []
  (doseq [client @clients]
    (post-message! client "a new tab was created")))

; -- main event loop ------------------------------------------------------------------------------

(defn process-chrome-event [event-num event]
  (log (gstring/format "BACKGROUND: got chrome event (%05d)" event-num) event)
  (let [[event-id event-args] event]
    (case event-id
      ::runtime/on-connect (apply handle-client-connection! event-args)
      ::tabs/on-created (tell-clients-about-new-tab!)
      nil)))

(defn run-chrome-event-loop! [chrome-event-channel]
  (log "BACKGROUND: starting main event loop...")
  (go-loop [event-num 1]
    (when-some [event (<! chrome-event-channel)]
      (process-chrome-event event-num event)
      (recur (inc event-num)))
    (log "BACKGROUND: leaving main event loop")))

(defn boot-chrome-event-loop! []
  (let [chrome-event-channel (make-chrome-event-channel (chan))]
    (tabs/tap-all-events chrome-event-channel)
    (runtime/tap-all-events chrome-event-channel)
    (run-chrome-event-loop! chrome-event-channel)))

; -- main entry point -----------------------------------------------------------------------------

(defn init! []
  (log "BACKGROUND: init")
  (boot-chrome-event-loop!))
```

Again, main entry point for background page is our `init!` function. We start by running main event loop by subscribing
to some Chrome events. `tabs/tap-all-events` is a convenience method which subscribes to all events defined in 
[tabs namespace][tabs-src] to be delivered into provided channel. Similarly `runtime/tap-all-events` subscribes all runtime 
events. We could as well subscribe individual events for example by calling `tabs/tap-on-created-events`, but subscribing 
in bulk is more convenient in this case. As you can see we create our own ordinary core.async channel and wrap it 
in `make-chrome-event-channel` call. This is an optional step, but convenient. `make-chrome-event-channel` returns a channel 
which is aware of Chrome event subscriptions and is able to unsubscribe them when the channel is about to be closed 
(for whatever reason). This way we don't have to do any book keeping for future cleanup.

Events delivered into the channel are in a form `[event-id event-args]` where event-args is a vector of parameters which were 
passed into event's callback function (after marshalling). So you can read Chrome documentation to figure out what to expect 
there. For example our `:chromex.ext.runtime/on-connect` event-id is documented under 
[runtime/on-connect event][runtime-on-connect-docs] and claims that the callback has a single parameter `port` of 
type `runtime.Port`. Se we get `IChromePort` wrapper, because marshalling converted native `runtime.Port` into 
ClojureScript-friendly `IChromePort` on the way out.

Ok, when anything connects to our background page, we receive an event with `::runtime/on-connect` id. We call 
`handle-client-connection!` with event-args. Here we have to do some client-specific work. First, add this new client into 
a collection of active clients. Second, send a hello message to the client and finally run client-specific event loop for 
receiving messages from this client. We don't do anything with received messages, we just print them into the console with 
a bit of information about the sender.When our client message channel gets terminated (for whatever reason), we remove 
the client from active clients and forget about it.

##### Notifying clients about interesting events

We provide an additional separate functionality from maintaining client message loops.
When Chrome notifies us about a new tab being created. We simply send a message to all our connected clients by 
calling `tell-clients-about-new-tab!`.

##### More on cleanup

You might be asking why there is no explicit cleanup code here? There should be some `.removeListener` calls when we are
leaving message loops, no?

This cleanup is done under the hood because we are using Chromex wrappers here. Wrappers act as core.async channels but know
how to gracefully disconnect when channel is closed (or close channel when client disconnected). In case of client connections 
you get a `runtime.Port` wrapper automatically thanks to marshalling. In case of main event loop you created a wrapper 
explicitly by calling `make-chrome-event-channel`.

Please keep in mind that you can always access underlying objects and talk to them directly if needed.

#### Content Script

Our [content script](src/content-script/chromex_sample/content_script/core.cljs) is almost copy&paste of popup page code:

```clojure
; -- a message loop -------------------------------------------------------------------------------

(defn process-message! [message]
  (log "CONTENT SCRIPT: got message:" message))

(defn run-message-loop! [message-channel]
  (log "CONTENT SCRIPT: starting message loop...")
  (go-loop []
    (when-some [message (<! message-channel)]
      (process-message! message)
      (recur))
    (log "CONTENT SCRIPT: leaving message loop")))

; -- a simple page analysis  ----------------------------------------------------------------------

(defn do-page-analysis! [background-port]
  (let [script-elements (.getElementsByTagName js/document "script")
        script-count (.-length script-elements)
        title (.-title js/document)
        msg (str "CONTENT SCRIPT: document '" title "' contains " script-count " script tags.")]
    (log msg)
    (post-message! background-port msg)))

(defn connect-to-background-page! []
  (let [background-port (runtime/connect)]
    (post-message! background-port "hello from CONTENT SCRIPT!")
    (run-message-loop! background-port)
    (do-page-analysis! background-port)))

; -- main entry point -----------------------------------------------------------------------------

(defn init! []
  (log "CONTENT SCRIPT: init")
  (connect-to-background-page!))
```

Upon launch, we connect to the background page, send hello message and start a message loop with background page.

Additionally, we call `do-page-analysis!` which does some simple DOM access, counts
number of script tags and sends a reporting message to the background page.

##### Receiving messages from background page

As you can see, we don't have any interesting logic here for processing messages from background page. In `process-message!`
we simply print the received message into the console. It works! You can test it by creating new tabs. Background page 
should be sending notifications about new tabs.

[custom-formatters]: https://github.com/binaryage/cljs-devtools#enable-custom-formatters-in-your-chrome-canary
[chrome-extensions-docs]: https://developer.chrome.com/extensions/overview
[runtime-port-docs]: https://developer.chrome.com/extensions/runtime#type-Port
[chrome-port-src]: https://github.com/binaryage/chromex/blob/master/src/lib/chromex/chrome_port.cljs
[runtime-connect-docs]: https://developer.chrome.com/extensions/runtime#method-connect
[runtime-on-connect-docs]: https://developer.chrome.com/extensions/runtime#event-onConnect
[tabs-src]: https://github.com/binaryage/chromex/blob/master/src/exts/chromex/ext/tabs.clj
[marshalling-clj-src]: https://github.com/binaryage/chromex/blob/master/src/lib/chromex/marshalling.clj
[marshalling-cljs-src]: https://github.com/binaryage/chromex/blob/master/src/lib/chromex/marshalling.cljs
[core-async-docs]: https://clojure.github.io/core.async/#clojure.core.async/<!
