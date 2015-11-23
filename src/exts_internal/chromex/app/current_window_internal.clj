(ns chromex.app.current-window-internal
  "This is used by the app window API internally to pass through messages to
   the shell window.
   
     * available since Chrome 23
     * https://developer.chrome.com/extensions/app.currentWindowInternal"

  (:refer-clojure :only [defmacro defn apply declare meta let])
  (:require [chromex-lib.wrapgen :refer [gen-wrap-from-table]]
            [chromex-lib.callgen :refer [gen-call-from-table gen-tap-all-call]]
            [chromex-lib.config :refer [get-static-config gen-active-config]]))

(declare api-table)
(declare gen-call)

; -- functions ------------------------------------------------------------------------------------------------------

(defmacro focus ([] (gen-call :function ::focus (meta &form))))

(defmacro fullscreen ([] (gen-call :function ::fullscreen (meta &form))))

(defmacro minimize ([] (gen-call :function ::minimize (meta &form))))

(defmacro maximize ([] (gen-call :function ::maximize (meta &form))))

(defmacro restore ([] (gen-call :function ::restore (meta &form))))

(defmacro draw-attention ([] (gen-call :function ::draw-attention (meta &form))))

(defmacro clear-attention ([] (gen-call :function ::clear-attention (meta &form))))

(defmacro show ([focused] (gen-call :function ::show (meta &form) focused))
  ([] `(show :omit)))

(defmacro hide ([] (gen-call :function ::hide (meta &form))))

(defmacro set-bounds ([bounds-type bounds] (gen-call :function ::set-bounds (meta &form) bounds-type bounds)))

(defmacro set-size-constraints ([bounds-type constraints] (gen-call :function ::set-size-constraints (meta &form) bounds-type constraints)))

(defmacro set-icon ([icon-url] (gen-call :function ::set-icon (meta &form) icon-url)))

(defmacro set-shape ([region] (gen-call :function ::set-shape (meta &form) region)))

(defmacro set-always-on-top ([always-on-top] (gen-call :function ::set-always-on-top (meta &form) always-on-top)))

(defmacro set-visible-on-all-workspaces ([always-visible] (gen-call :function ::set-visible-on-all-workspaces (meta &form) always-visible)))

; -- events ---------------------------------------------------------------------------------------------------------

(defmacro tap-on-closed-events ([channel] (gen-call :event ::on-closed (meta &form) channel)))

(defmacro tap-on-bounds-changed-events ([channel] (gen-call :event ::on-bounds-changed (meta &form) channel)))

(defmacro tap-on-fullscreened-events ([channel] (gen-call :event ::on-fullscreened (meta &form) channel)))

(defmacro tap-on-minimized-events ([channel] (gen-call :event ::on-minimized (meta &form) channel)))

(defmacro tap-on-maximized-events ([channel] (gen-call :event ::on-maximized (meta &form) channel)))

(defmacro tap-on-restored-events ([channel] (gen-call :event ::on-restored (meta &form) channel)))

(defmacro tap-on-alpha-enabled-changed-events ([channel] (gen-call :event ::on-alpha-enabled-changed (meta &form) channel)))

(defmacro tap-on-window-shown-for-tests-events
  "Only sent in tests."
  ([channel] (gen-call :event ::on-window-shown-for-tests (meta &form) channel)))

; -- convenience ----------------------------------------------------------------------------------------------------

(defmacro tap-all-events [chan]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (gen-tap-all-call static-config api-table (meta &form) config chan)))

; -------------------------------------------------------------------------------------------------------------------
; -- API TABLE ------------------------------------------------------------------------------------------------------
; -------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.app.currentWindowInternal",
   :since "23",
   :functions
   [{:id ::focus, :name "focus"}
    {:id ::fullscreen, :name "fullscreen"}
    {:id ::minimize, :name "minimize"}
    {:id ::maximize, :name "maximize"}
    {:id ::restore, :name "restore"}
    {:id ::draw-attention, :name "drawAttention"}
    {:id ::clear-attention, :name "clearAttention"}
    {:id ::show, :name "show", :params [{:name "focused", :optional? true, :type "boolean"}]}
    {:id ::hide, :name "hide"}
    {:id ::set-bounds,
     :name "setBounds",
     :params [{:name "bounds-type", :type "string"} {:name "bounds", :type "object"}]}
    {:id ::set-size-constraints,
     :name "setSizeConstraints",
     :params [{:name "bounds-type", :type "string"} {:name "constraints", :type "object"}]}
    {:id ::set-icon, :name "setIcon", :params [{:name "icon-url", :type "string"}]}
    {:id ::set-shape, :name "setShape", :params [{:name "region", :type "object"}]}
    {:id ::set-always-on-top, :name "setAlwaysOnTop", :params [{:name "always-on-top", :type "boolean"}]}
    {:id ::set-visible-on-all-workspaces,
     :name "setVisibleOnAllWorkspaces",
     :params [{:name "always-visible", :type "boolean"}]}],
   :events
   [{:id ::on-closed, :name "onClosed"}
    {:id ::on-bounds-changed, :name "onBoundsChanged"}
    {:id ::on-fullscreened, :name "onFullscreened"}
    {:id ::on-minimized, :name "onMinimized"}
    {:id ::on-maximized, :name "onMaximized"}
    {:id ::on-restored, :name "onRestored"}
    {:id ::on-alpha-enabled-changed, :name "onAlphaEnabledChanged"}
    {:id ::on-window-shown-for-tests, :name "onWindowShownForTests"}]})

; -- helpers --------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (let [static-config (get-static-config)]
    (apply gen-wrap-from-table static-config api-table kind item-id config args)))

; code generation for API call-site
(defn gen-call [kind item src-info & args]
  (let [static-config (get-static-config)
        config (gen-active-config static-config)]
    (apply gen-call-from-table static-config api-table kind item src-info config args)))