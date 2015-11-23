# chromex [![Build Status](https://travis-ci.org/binaryage/chromex.svg)](https://travis-ci.org/binaryage/chromex)

### Chrome Extension APIs wrapper for ClojureScript.

This library is auto-generated from [chromium sources](https://www.chromium.org/developers).

Currently Chromex provides following wrappers:

| API family | namespaces | properties | functions | events |
| --- | --- | --- | --- | --- |
| [Public Chrome Extension APIs](src/exts) | 82 | 44 | 354 | 185 |
| [Private Chrome Extension APIs](src/exts_private) | 45 | 3 | 315 | 67 |
| [Internal Chrome Extension APIs](src/exts_internal) | 16 | 0 | 89 | 15 |

Note: Chromex generator uses the same data source as the [developer.chrome.com/extensions/api_index](https://developer.chrome.com/extensions/api_index) web site.

This library is data-driven. Given API namespace, all API methods, properties and events are described in a Clojure map
along with their parameters, callbacks, versions and additional metadata ([see a simple example here](src/exts/chromex/context_menus.clj#L71)).
Chromex then provides a set of macros which consume this table and generate actual ClojureScript code wrapping native APIs.

These macros can be further parametrized which allows for greater flexibility. Sane defaults
are provided with following goals:

  * API version checking and deprecation warnings at compile-time
  * flexible marshalling of Javascript values to ClojureScript and back
  * callbacks are converted to core.async channels
  * events are emitted into core.async channels

#### API versions and deprecation warnings

Chrome Extension API is evolving. You might want to target multiple Chrome versions with slightly
different APIs. Good news is that our API data map contains full versioning and deprecation information.

By default you target the latest APIs. But you can target older API version instead and
we will warn you during compilation in case you were accessing any APIs not yet available in that particular version.

Additionally we are able to detect calls to deprecated APIs and warn you during compilation.

#### Flexible marshalling

Generated API data map contains information about all parameters and their types. Chromex provides a pluggable system
to specify how particular types are marshalled when crossing API boundary.

By default we marshall only a few types where it makes good sense. We don't want to blindly run all
parameters through `clj->js` and `js->clj` conversions. That could have unexpected consequences and maybe
performance implications. Instead we keep marshalling lean and give you an easy way how to provide your own macro which
can optionally generate required marshalling code (during compilation).

There is also a practical reason. This library is auto-generated and quite large - it would be too laborious to maintain
hairy marshalling conventions up-to-date with evolving Chrome API index. If you want to provide richer set of
marshalling for particular APIs you care about, you [can do that consistently](src/lib/chromex_lib/marshalling.clj).

#### Callbacks as core.async channels

Many Chrome API calls are async in nature and require you to specify a callback (for receiving an answer later).
You might want to watch this video explaining [API conventions](https://www.youtube.com/watch?v=bmxr75CV36A) in Chrome.

We automatically turn all API functions with a callback parameter to a ClojureScript API function returning a new core.async channel instead.

This mechanism is pluggable, so you can optionally implement your own mechanism of consuming callback calls.

#### Events are emitted into core.async channels

Chrome API namespaces usually provide multiple `event` objects, which you can subscribe with `.addListener`.
You provide a callback function which will get called with future events as they occur. Later you can call `.removeListener`
to unsubscribe from the event stream.

We think consuming events via core.async channel is more natural for ClojureScript developers.
In Chromex, you can request Chrome events to be emitted into a core.async channel provided by you.
And then implement a single loop to sequentially process events as they appear on the channel.

Again this mechanism is pluggable, so you can optionally implement a different mechanism for consuming event streams.

### Similar projects

  * [suprematic/khroma](https://github.com/suprematic/khroma)

#### License [MIT](license.txt)
