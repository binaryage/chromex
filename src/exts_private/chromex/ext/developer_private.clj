(ns chromex.ext.developer-private
  "developerPrivate API.
   This is a private API exposing developing and debugging functionalities for
   apps and extensions.

     * available since Chrome 38"

  (:refer-clojure :only [defmacro defn apply declare meta let partial])
  (:require [chromex.wrapgen :refer [gen-wrap-helper]]
            [chromex.callgen :refer [gen-call-helper gen-tap-all-events-call]]))

(declare api-table)
(declare gen-call)

; -- functions --------------------------------------------------------------------------------------------------------------

(defmacro auto-update
  "Runs auto update for extensions and apps immediately.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::auto-update &form)))

(defmacro get-extensions-info
  "Returns information of all the extensions and apps installed.

     |options| - Options to restrict the items returned.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([options] (gen-call :function ::get-extensions-info &form options))
  ([] `(get-extensions-info :omit)))

(defmacro get-extension-info
  "Returns information of a particular extension.

     |id| - The id of the extension.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::get-extension-info &form id)))

(defmacro get-extension-size
  "Returns the size of a particular extension on disk (already formatted).

     |id| - The id of the extension.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [string] where:

     |string| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id] (gen-call :function ::get-extension-size &form id)))

(defmacro get-items-info
  "Returns information of all the extensions and apps installed.

     |include-disabled| - include disabled items.
     |include-terminated| - include terminated items.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([include-disabled include-terminated] (gen-call :function ::get-items-info &form include-disabled include-terminated)))

(defmacro get-profile-configuration
  "Returns the current profile's configuration.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [info] where:

     |info| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::get-profile-configuration &form)))

(defmacro update-profile-configuration
  "Updates the active profile.

     |update| - The parameters for updating the profile's configuration.  Any     properties omitted from |update| will not
                be changed.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([update] (gen-call :function ::update-profile-configuration &form update)))

(defmacro show-permissions-dialog
  "Opens a permissions dialog.

     |extension-id| - The id of the extension to show permissions for.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id] (gen-call :function ::show-permissions-dialog &form extension-id)))

(defmacro reload
  "Reloads a given extension.

     |extension-id| - The id of the extension to reload.
     |options| - Additional configuration parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [error] where:

     |error| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id options] (gen-call :function ::reload &form extension-id options))
  ([extension-id] `(reload ~extension-id :omit)))

(defmacro update-extension-configuration
  "Modifies an extension's current configuration.

     |update| - The parameters for updating the extension's configuration.     Any properties omitted from |update| will not
                be changed.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([update] (gen-call :function ::update-extension-configuration &form update)))

(defmacro load-unpacked
  "Loads a user-selected unpacked item.

     |options| - Additional configuration parameters.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [error] where:

     |error| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([options] (gen-call :function ::load-unpacked &form options))
  ([] `(load-unpacked :omit)))

(defmacro install-dropped-file
  "Installs the file that was dragged and dropped onto the associated page.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::install-dropped-file &form)))

(defmacro notify-drag-install-in-progress
  "Notifies the browser that a user began a drag in order to install an extension."
  ([] (gen-call :function ::notify-drag-install-in-progress &form)))

(defmacro load-directory
  "Loads an extension / app.

     |directory| - The directory to load the extension from.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [string] where:

     |string| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([directory] (gen-call :function ::load-directory &form directory)))

(defmacro choose-path
  "Open Dialog to browse to an entry.

     |select-type| - Select a file or a folder.
     |file-type| - Required file type. For example, pem type is for private key and load type is for an unpacked item.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [string] where:

     |string| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([select-type file-type] (gen-call :function ::choose-path &form select-type file-type)))

(defmacro pack-directory
  "Pack an extension.

     |path| - ?
     |private-key-path| - The path of the private key, if one is given.
     |flags| - Special flags to apply to the loading process, if any.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([path private-key-path flags] (gen-call :function ::pack-directory &form path private-key-path flags))
  ([path private-key-path] `(pack-directory ~path ~private-key-path :omit))
  ([path] `(pack-directory ~path :omit :omit)))

(defmacro is-profile-managed
  "Returns true if the profile is managed.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [result] where:

     |result| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([] (gen-call :function ::is-profile-managed &form)))

(defmacro request-file-source
  "Reads and returns the contents of a file related to an extension which caused an error.

     |properties| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [response] where:

     |response| - ?

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([properties] (gen-call :function ::request-file-source &form properties)))

(defmacro open-dev-tools
  "Open the developer tools to focus on a particular error.

     |properties| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([properties] (gen-call :function ::open-dev-tools &form properties)))

(defmacro delete-extension-errors
  "Delete reported extension errors.

     |properties| - The properties specifying the errors to remove.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([properties] (gen-call :function ::delete-extension-errors &form properties)))

(defmacro repair-extension
  "Repairs the extension specified.

     |extension-id| - The id of the extension to repair.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id] (gen-call :function ::repair-extension &form extension-id)))

(defmacro show-options
  "Shows the options page for the extension specified.

     |extension-id| - The id of the extension to show the options page for.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id] (gen-call :function ::show-options &form extension-id)))

(defmacro show-path
  "Shows the path of the extension specified.

     |extension-id| - The id of the extension to show the path for.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id] (gen-call :function ::show-path &form extension-id)))

(defmacro set-shortcut-handling-suspended
  "(Un)suspends global shortcut handling.

     |is-suspended| - Whether or not shortcut handling should be suspended.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([is-suspended] (gen-call :function ::set-shortcut-handling-suspended &form is-suspended)))

(defmacro update-extension-command
  "Updates an extension command.

     |update| - The parameters for updating the extension command.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([update] (gen-call :function ::update-extension-command &form update)))

(defmacro add-host-permission
  "Adds a new host permission to the extension. The extension will only have access to the host if it is within the requested
   permissions.

     |extension-id| - The id of the extension to modify.
     |host| - The host to add.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id host] (gen-call :function ::add-host-permission &form extension-id host)))

(defmacro remove-host-permission
  "Removes a host permission from the extension. This should only be called with a host that the extension has access to.

     |extension-id| - The id of the extension to modify.
     |host| - The host to remove.

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id host] (gen-call :function ::remove-host-permission &form extension-id host)))

(defmacro enable
  "  |id| - ?
     |enabled| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([id enabled] (gen-call :function ::enable &form id enabled)))

(defmacro allow-incognito
  "  |extension-id| - ?
     |allow| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id allow] (gen-call :function ::allow-incognito &form extension-id allow)))

(defmacro allow-file-access
  "  |extension-id| - ?
     |allow| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([extension-id allow] (gen-call :function ::allow-file-access &form extension-id allow)))

(defmacro inspect
  "  |options| - ?

   This function returns a core.async channel of type `promise-chan` which eventually receives a result value.
   Signature of the result value put on the channel is [].

   In case of an error the channel closes without receiving any value and relevant error object can be obtained via
   chromex.error/get-last-error."
  ([options] (gen-call :function ::inspect &form options)))

; -- events -----------------------------------------------------------------------------------------------------------------
;
; docs: https://github.com/binaryage/chromex/#tapping-events

(defmacro tap-on-item-state-changed-events
  "Fired when a item state is changed.

   Events will be put on the |channel| with signature [::on-item-state-changed [response]] where:

     |response| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-item-state-changed &form channel args)))

(defmacro tap-on-profile-state-changed-events
  "Fired when the profile's state has changed.

   Events will be put on the |channel| with signature [::on-profile-state-changed [info]] where:

     |info| - ?

   Note: |args| will be passed as additional parameters into Chrome event's .addListener call."
  ([channel & args] (apply gen-call :event ::on-profile-state-changed &form channel args)))

; -- convenience ------------------------------------------------------------------------------------------------------------

(defmacro tap-all-events
  "Taps all valid non-deprecated events in chromex.ext.developer-private namespace."
  [chan]
  (gen-tap-all-events-call api-table (meta &form) chan))

; ---------------------------------------------------------------------------------------------------------------------------
; -- API TABLE --------------------------------------------------------------------------------------------------------------
; ---------------------------------------------------------------------------------------------------------------------------

(def api-table
  {:namespace "chrome.developerPrivate",
   :since "38",
   :functions
   [{:id ::auto-update,
     :name "autoUpdate",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::get-extensions-info,
     :name "getExtensionsInfo",
     :since "43",
     :callback? true,
     :params
     [{:name "options", :optional? true, :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "[array-of-developerPrivate.ExtensionInfos]"}]}}]}
    {:id ::get-extension-info,
     :name "getExtensionInfo",
     :since "43",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "result", :type "developerPrivate.ExtensionInfo"}]}}]}
    {:id ::get-extension-size,
     :name "getExtensionSize",
     :since "64",
     :callback? true,
     :params
     [{:name "id", :type "string"}
      {:name "callback", :type :callback, :callback {:params [{:name "string", :type "string"}]}}]}
    {:id ::get-items-info,
     :name "getItemsInfo",
     :since "43",
     :deprecated "Use getExtensionsInfo",
     :callback? true,
     :params
     [{:name "include-disabled", :since "43", :type "boolean"}
      {:name "include-terminated", :since "43", :type "boolean"}
      {:name "callback", :type :callback, :callback {:params [{:name "result", :type "[array-of-objects]"}]}}]}
    {:id ::get-profile-configuration,
     :name "getProfileConfiguration",
     :since "44",
     :callback? true,
     :params
     [{:name "callback", :type :callback, :callback {:params [{:name "info", :type "developerPrivate.ProfileInfo"}]}}]}
    {:id ::update-profile-configuration,
     :name "updateProfileConfiguration",
     :since "44",
     :callback? true,
     :params [{:name "update", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::show-permissions-dialog,
     :name "showPermissionsDialog",
     :callback? true,
     :params
     [{:name "extension-id", :since "43", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::reload,
     :name "reload",
     :callback? true,
     :params
     [{:name "extension-id", :since "43", :type "string"}
      {:name "options", :optional? true, :since "43", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "error", :optional? true, :type "developerPrivate.LoadError"}]}}]}
    {:id ::update-extension-configuration,
     :name "updateExtensionConfiguration",
     :since "43",
     :callback? true,
     :params [{:name "update", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::load-unpacked,
     :name "loadUnpacked",
     :callback? true,
     :params
     [{:name "options", :optional? true, :since "43", :type "object"}
      {:name "callback",
       :optional? true,
       :type :callback,
       :callback {:params [{:name "error", :optional? true, :type "developerPrivate.LoadError"}]}}]}
    {:id ::install-dropped-file,
     :name "installDroppedFile",
     :since "67",
     :callback? true,
     :params [{:name "callback", :optional? true, :type :callback}]}
    {:id ::notify-drag-install-in-progress, :name "notifyDragInstallInProgress", :since "64"}
    {:id ::load-directory,
     :name "loadDirectory",
     :callback? true,
     :params
     [{:name "directory", :type "DirectoryEntry"}
      {:name "callback", :type :callback, :callback {:params [{:name "string", :type "string"}]}}]}
    {:id ::choose-path,
     :name "choosePath",
     :callback? true,
     :params
     [{:name "select-type", :since "43", :type "unknown-type"}
      {:name "file-type", :since "43", :type "unknown-type"}
      {:name "callback", :type :callback, :callback {:params [{:name "string", :type "string"}]}}]}
    {:id ::pack-directory,
     :name "packDirectory",
     :callback? true,
     :params
     [{:name "path", :type "string"}
      {:name "private-key-path", :optional? true, :since "43", :type "string"}
      {:name "flags", :optional? true, :type "integer"}
      {:name "callback", :optional? true, :type :callback, :callback {:params [{:name "response", :type "object"}]}}]}
    {:id ::is-profile-managed,
     :name "isProfileManaged",
     :callback? true,
     :params [{:name "callback", :type :callback, :callback {:params [{:name "result", :type "boolean"}]}}]}
    {:id ::request-file-source,
     :name "requestFileSource",
     :callback? true,
     :params
     [{:name "properties", :since "43", :type "object"}
      {:name "callback", :type :callback, :callback {:params [{:name "response", :type "object"}]}}]}
    {:id ::open-dev-tools,
     :name "openDevTools",
     :callback? true,
     :params [{:name "properties", :since "43", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::delete-extension-errors,
     :name "deleteExtensionErrors",
     :since "43",
     :callback? true,
     :params [{:name "properties", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::repair-extension,
     :name "repairExtension",
     :since "44",
     :callback? true,
     :params [{:name "extension-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::show-options,
     :name "showOptions",
     :since "44",
     :callback? true,
     :params [{:name "extension-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::show-path,
     :name "showPath",
     :since "44",
     :callback? true,
     :params [{:name "extension-id", :type "string"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::set-shortcut-handling-suspended,
     :name "setShortcutHandlingSuspended",
     :since "45",
     :callback? true,
     :params [{:name "is-suspended", :type "boolean"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::update-extension-command,
     :name "updateExtensionCommand",
     :since "45",
     :callback? true,
     :params [{:name "update", :type "object"} {:name "callback", :optional? true, :type :callback}]}
    {:id ::add-host-permission,
     :name "addHostPermission",
     :since "68",
     :callback? true,
     :params
     [{:name "extension-id", :type "string"}
      {:name "host", :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::remove-host-permission,
     :name "removeHostPermission",
     :since "68",
     :callback? true,
     :params
     [{:name "extension-id", :type "string"}
      {:name "host", :type "string"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::enable,
     :name "enable",
     :since "43",
     :deprecated "Use management.setEnabled",
     :callback? true,
     :params
     [{:name "id", :since "43", :type "string"}
      {:name "enabled", :since "43", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::allow-incognito,
     :name "allowIncognito",
     :since "43",
     :deprecated "Use updateExtensionConfiguration",
     :callback? true,
     :params
     [{:name "extension-id", :since "43", :type "string"}
      {:name "allow", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::allow-file-access,
     :name "allowFileAccess",
     :since "43",
     :deprecated "Use updateExtensionConfiguration",
     :callback? true,
     :params
     [{:name "extension-id", :since "43", :type "string"}
      {:name "allow", :type "boolean"}
      {:name "callback", :optional? true, :type :callback}]}
    {:id ::inspect,
     :name "inspect",
     :since "43",
     :deprecated "Use openDevTools",
     :callback? true,
     :params [{:name "options", :type "object"} {:name "callback", :optional? true, :type :callback}]}],
   :events
   [{:id ::on-item-state-changed, :name "onItemStateChanged", :params [{:name "response", :type "object"}]}
    {:id ::on-profile-state-changed,
     :name "onProfileStateChanged",
     :since "45",
     :params [{:name "info", :type "developerPrivate.ProfileInfo"}]}]})

; -- helpers ----------------------------------------------------------------------------------------------------------------

; code generation for native API wrapper
(defmacro gen-wrap [kind item-id config & args]
  (apply gen-wrap-helper api-table kind item-id config args))

; code generation for API call-site
(def gen-call (partial gen-call-helper api-table))