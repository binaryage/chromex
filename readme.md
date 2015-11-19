# chromex [![Build Status](https://travis-ci.org/binaryage/chromex.svg)](https://travis-ci.org/binaryage/chromex)

### Chrome Extension APIs wrapper for ClojureScript.

##### NOT READY FOR USE, THIS IS A WORK IN PROGRESS

This library is auto-generated from [chromium sources](https://www.chromium.org/developers).
Chromex generator uses the same data source as the [developer.chrome.com/extensions](https://developer.chrome.com/extensions) web site.

This library is data-driven. Given API namespace, all API methods, properties and events are described in a Clojure map
along with their parameters, callbacks, versions and additional metadata. Chromex then provides a set of macros
which consume this table and generate actual ClojureScript code wrapping native APIs.

These macros can be further parametrized which allows for greater flexibility. Sane defaults
are provided with following goals:

  * API version checking and deprecation warnings at compile-time
  * flexible marshalling of Javascript values to ClojureScript and back
  * callbacks are converted to core.async channels
  * events are emitted into core.async channels

#### API versions and deprecation warnings

Chrome Extension API is evolving. You might want to target multiple Chrome versions with slightly
different APIs. Good news is that our API data contains also versioning and deprecation information.

By default you target the latest APIs. But you can pin-point older API version as your target and
we will warn you during compilation in case you were accessing any APIs not yet available in that particular version.

Additionally we are able to detect calls to deprecated APIs and warn you during compilation.

#### Flexible marshalling

Generated API data contains information about all parameters and their types. Chromex provides a pluggable system for you
to specify how particular types will be marshalled when crossing API boundary.

By default we marshall only a few types where it makes good sense. We don't want to blindly run all
parameters through `clj->js` and `js->clj` conversions. That could have unexpected consequences and maybe
performance implications. Instead we keep marshalling lean and give you an easy way how to provide your own macro which
can optionally generate required marshalling code (during compilation).

There is also a practical reason. This library is huge (auto-generated) and it would be too laborous to maintain
hairy marshalling conventions up-to-date with evolving Chrome API index. If you want to provide richer set of
marshalling for particular APIs you use in your project, you can do that consistently.

#### Callbacks as core.async channels

Many Chrome API calls are async and require you to specify a callback for receiving an answer alter. You might want to watch
this video explaining [API conventions](https://www.youtube.com/watch?v=bmxr75CV36A) in Chrome.

We automatically turn all functions with a callback parameter to a ClojureScript function returning a new core.async channel instead.

This mechanism is pluggable, so you can optionally implement your own mechanism of consuming callback calls.

#### Events are emitted into core.async channels

Chrome API namespaces usually provide multiple `event` objects, which you can subscribe with `.addListener`.
You provide a callback function which will get called with future events as they occur. Later you can call `.removeListener`
to unsubscribe from the event stream.

We think consuming events via core.async channel is more natural for ClojureScript developers.
In Chromex, you can request Chrome events to be emitted into a core.async channel provided by you.
And then implement a single loop to sequentially process events as they appear on the channel.

Again this mechanism is pluggable, so you can optionally implement a different mechanism for consuming event streams.

### Related projects

  * [suprematic/khroma](https://github.com/suprematic/khroma)

#### License [MIT](license.txt)