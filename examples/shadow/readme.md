# chromex-sample

### An example extension using Chromex library and shadow-cljs

This project acts as a code example for [chromex library](https://github.com/binaryage/chromex) but also as a skeleton
with project configuration based on shadow-cljs and deps.edn following best practices. We recommend using it as a starting 
point when starting development of your own extension.

#### **chromex-sample** has a minimalist **background page**, **popup button** and **content script**:

  * background page listens for connections from popup buttons and content scripts (there can be multiple of them)
  * popup button connects to the background page and sends a simple "HELLO" message after connection
  * content script connects to the background page and sends a simple "HELLO" message after connection
  * content script does a simple page analysis upon launch (it counts number of script tags) and sends an info message to 
  the background page
  * background page listens to tab creation events and notifies all connected clients about new tabs

#### **chromex-sample** project has following configuration:

  * uses [shadow-cljs](http://shadow-cljs.org)
  * integrates [cljs-devtools](https://github.com/binaryage/cljs-devtools) 
    * shadow-cljs automatically includes devtools in non-release builds

### Local setup

#### Extension development

We assume you are familiar with ClojureScript tooling:

  * clone this repo somewhere:
    ```bash
    git clone https://github.com/binaryage/chromex.git
    cd chromex/examples/shadow
    ```
  * If you have not already installed shadow-cljs
    ```bash
    npm install -g shadow-cljs
    ```
  * For the first run, load any npm libraries
    ```bash
    npm install
    ```
  * chromex shadow example is gets built into `resources/unpacked/out` folder.

    In one terminal session run (will build background, popup and content pages using shadow-cljs):
    ```bash
    shadow-cljs watch extension
    ```

  * use the latest Chrome Canary with [Custom Formatters][custom-formatters] enabled
  * in Chrome Canary, open `chrome://extensions` and add `resources/unpacked` via "Load unpacked extension..."

##### Debugging

Chrome extension development is more complex than regular ClojureScript front-end
work. You are writing (and debugging) multiple parallel communicating processes: your
background page, your popup, and all the browser pages running your content script.

Amazingly, the ClojureScript tooling and shadow-cljs live coding remain very usable in this
environment. You need to be aware of a few things, particularly in regard to
compiler warnings:

Most warnings will show up as output of the `shadow-cljs watch extension` But,
the exact behavior depends upon which part of your code has the error:

_Content script_: Warnings and errors will appear in the output of the `shadow-cljs watch extension`

_popup_: Chrome normally closes the popup anytime focus leaves Chrome. So, if you are
working in your editor, the popup is closed, and you will not see any error messages
anywhere. This can be very frustrating but is easy to fix. When you first open the
popup, right click on its icon and select `Inspect popup`. This opens the Chrome
inspector/console and keeps the popup open while the inspector remains open. Any errors
will appear in both the console and the output of the `shadow-cljs watch extension`. Also,
of course, this gives you niceties of shadow-cljs live coding. Your changes will appear
immediately, with no need to close and reopen the popup.

_background_: The background code is running under shadow-cljs, messages may
appear in the as output of the `shadow-cljs watch extension` command. It also
has no visible window. You will only see warnings in the Chrome console. You
can open the inspector/console from `chrome://extensions`. Under your extension,
click on the `Inspect Views background page` link.

In summary, effective live debugging requires up to five open windows on your screen:
- Your editor;
- The shell running `shadow-cljs watch extension**, if you are making changes to content script code;
- The web browser, with open popup and/or content page;
- A Chrome inspector console, watching the background page; and
- A Chrome inspector console, watching the popup page.

#### Release build

```bash
./scripts/release.sh
```

The files get built into `resources/unpacked`. You may open this folder as unpacked extension in Chrome and test it.

The output should be similar to this:

```text
> ./scripts/release.sh
+ ./scripts/clean.sh
+ shadow-cljs release extension
shadow-cljs - config: ...
shadow-cljs - starting via "clojure"
[:extension] Compiling ...
[:extension] Build completed. (101 files, 0 compiled, 0 warnings, 10.76s)
```

#### Extension packaging

```bash
./scripts/package.sh
```

This will produce a new zip file in `releases` folder named `chromex-sample-$VERSION.zip` 
and `VERSION` is taken from [package.json](package.json).

The output should look like:

```text
> ./scripts/package.sh
Archive:  releases/chromex-sample-1.0.0.zip
  Length      Date    Time    Name
---------  ---------- -----   ----
        0  02-11-2020 12:27   out/
     1301  02-11-2020 12:35   out/popup.js
    25000  02-11-2020 12:35   out/background.js
        0  02-11-2020 12:35   out/bg-shared.js
     1524  02-11-2020 12:35   out/content-script.js
   183102  02-11-2020 12:35   out/shared.js
        0  02-09-2020 18:55   images/
      350  02-09-2020 15:46   images/icon16.png
      780  02-09-2020 15:46   images/icon38.png
     1026  02-09-2020 15:46   images/icon48.png
     2438  02-09-2020 15:46   images/icon128.png
      374  02-09-2020 15:46   images/icon19.png
      252  02-09-2020 19:45   popup.html
      970  02-11-2020 12:35   manifest.json
---------                     -------
   217117                     14 files

'releases/chromex-sample-1.0.0.zip' is ready for upload
=> https://chrome.google.com/webstore/developer/dashboard
```

### Shadow-cljs configuration

The documentation for `:chrome-extension` target in shadow-cljs is not yet in existence other than some comments in
[thheller/shadow-cljs :chrome-extension target support #279][shadow-docs-comment]

The information below can help to get you started.

The main configuration files are
* `deps.edn`
* `shadow-cljs.edn`
* `package.json`
* `resources/unpacked/manifest.edn`

#### Deps.edn

This is the standard [`deps.edn`](https://www.clojure.org/guides/deps_and_cli)
file that works with Clojure Tools. Shadow-cljs can use it directly as well. It
contains all the Clojure dependencies (`:deps`). It also specifies the `:paths`
to the project source code.

#### Shadow-cljs.edn

This is the main [shadow-cljs config][shadow-docs-configuration] file. 

The main section is:
```clojure
 :builds   {:extension ; name of the build

            {:target           :chrome-extension ; Tells shadow-cljs that this is a chrome extension

             ; Where the unpacked built extension files will be, including the compiled output
             :extension-dir    "resources/unpacked" 

             ; The input file for driving the build 
             :manifest-file    "resources/unpacked/manifest.edn" 
             :compiler-options {:closure-output-charset "US-ASCII"
                                :source-map-inline  true}

             ; Describes the 3 compiled outputs, their entry points and any options
             :outputs          {:background     {:output-type :chrome/background
                                                 :entries     [chromex-sample.background]}
                                :content-script {:output-type    :chrome/content-script
                                                 :chrome/options {:matches ["<all_urls>"]
                                                                  :run-at  "document_end"}
                                                 :entries        [chromex-sample.content-script]}
                                :popup          {:entries [chromex-sample.popup]}}}}
```

The three outputs (`:background`, `:content-script` and `:popup`) are the 3
types of components in the chrome extension and map to the top level
directories in `src`.


#### package.json

This is the normal [package.json][shadow-docs-npm-install]
that you can use to add npm libraries to your extension. The one in this example
does not include anything other than shadow-cljs.

#### Manifest.edn

The
[manifest.edn][shadow-docs-manifest]
location is specified by `:manifest-file` value in `shadow-cljs.edn`. In this
case it is in `resource/unpacked/manifest.edn`

This file is used to build the final
[manifest.json][extension-docs-manifest] that will be
generated in `:extension-dir`.

* `:permissions`
  [Specifies][chrome-docs-permissions] which
  pages and chrome resources the extension can access. In this case, it's the
  `activeTab`, the browser storage mechanism and any website.
  
* `:browser-action` 
  [Browser action][extension-docs-browser-action] puts icons in
  the main Google Chrome toolbar, to the right of the address bar.

* `:content-security-policy` 
  [Content Security Policy][extension-docs-content-security-policy] is used
  by Chrome as a block/allowlisting mechanism for resources loaded or executed
  by your extension.

### Code discussion

Before reading the code below you should get familiar with [Chrome Extension System architecture][extension-docs-arch].

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
[shadow-docs-comment]: https://github.com/thheller/shadow-cljs/issues/279#issuecomment-392007641
[inlined-comment]: https://github.com/thheller/shadow-cljs/commit/94c99bd609bf674e24b47964d37c89e57e838414
[shadow-docs-configuration]: https://shadow-cljs.github.io/docs/UsersGuide.html#_configuration
[shadow-docs-npm-install]: https://shadow-cljs.github.io/docs/UsersGuide.html#npm-install
[shadow-docs-manifest]: https://shadow-cljs.github.io/docs/UsersGuide.html#BrowserManifest
[extension-docs-manifest]: https://developer.chrome.com/extensions/manifest
[chrome-docs-permissions]: https://developer.chrome.com/extensions/permission_warnings
[extension-docs-browser-action]: https://developer.chrome.com/extensions/browserAction
[extension-docs-content-security-policy]: https://developer.chrome.com/extensions/contentSecurityPolicy
[extension-docs-arch]: https://developer.chrome.com/extensions/overview#arch
[runtime-port-docs]: https://developer.chrome.com/extensions/runtime#type-Port
[chrome-port-src]: https://github.com/binaryage/chromex/blob/master/src/lib/chromex/chrome_port.cljs
[runtime-connect-docs]: https://developer.chrome.com/extensions/runtime#method-connect
[runtime-on-connect-docs]: https://developer.chrome.com/extensions/runtime#event-onConnect
[tabs-src]: https://github.com/binaryage/chromex/blob/master/src/exts/chromex/ext/tabs.clj
[marshalling-clj-src]: https://github.com/binaryage/chromex/blob/master/src/lib/chromex/marshalling.clj
[marshalling-cljs-src]: https://github.com/binaryage/chromex/blob/master/src/lib/chromex/marshalling.cljs
[core-async-docs]: https://clojure.github.io/core.async/#clojure.core.async/<!

