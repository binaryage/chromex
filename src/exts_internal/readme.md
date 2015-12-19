#### DO NOT MODIFY THIS FOLDER

# This is generated API

### Files

| JS namespace | CLJS wrapper |
| --- | --- |
| [chrome.app.currentWindowInternal](https://developer.chrome.com/extensions/app.currentWindowInternal) | [chromex/ext/app/current_window_internal.clj](chromex/ext/app/current_window_internal.clj) |
| [chrome.appViewGuestInternal](https://developer.chrome.com/extensions/appViewGuestInternal) | [chromex/ext/app_view_guest_internal.clj](chromex/ext/app_view_guest_internal.clj) |
| [chrome.automationInternal](https://developer.chrome.com/extensions/automationInternal) | [chromex/ext/automation_internal.clj](chromex/ext/automation_internal.clj) |
| [chrome.certificateProviderInternal](https://developer.chrome.com/extensions/certificateProviderInternal) | [chromex/ext/certificate_provider_internal.clj](chromex/ext/certificate_provider_internal.clj) |
| [chrome.chromeWebViewInternal](https://developer.chrome.com/extensions/chromeWebViewInternal) | [chromex/ext/chrome_web_view_internal.clj](chromex/ext/chrome_web_view_internal.clj) |
| [chrome.contextMenusInternal](https://developer.chrome.com/extensions/contextMenusInternal) | [chromex/ext/context_menus_internal.clj](chromex/ext/context_menus_internal.clj) |
| [chrome.enterprise.platformKeysInternal](https://developer.chrome.com/extensions/enterprise.platformKeysInternal) | [chromex/ext/enterprise/platform_keys_internal.clj](chromex/ext/enterprise/platform_keys_internal.clj) |
| [chrome.extensionOptionsInternal](https://developer.chrome.com/extensions/extensionOptionsInternal) | [chromex/ext/extension_options_internal.clj](chromex/ext/extension_options_internal.clj) |
| [chrome.extensionViewInternal](https://developer.chrome.com/extensions/extensionViewInternal) | [chromex/ext/extension_view_internal.clj](chromex/ext/extension_view_internal.clj) |
| [chrome.fileBrowserHandlerInternal](https://developer.chrome.com/extensions/fileBrowserHandlerInternal) | [chromex/ext/file_browser_handler_internal.clj](chromex/ext/file_browser_handler_internal.clj) |
| [chrome.fileManagerPrivateInternal](https://developer.chrome.com/extensions/fileManagerPrivateInternal) | [chromex/ext/file_manager_private_internal.clj](chromex/ext/file_manager_private_internal.clj) |
| [chrome.guestViewInternal](https://developer.chrome.com/extensions/guestViewInternal) | [chromex/ext/guest_view_internal.clj](chromex/ext/guest_view_internal.clj) |
| [chrome.platformKeysInternal](https://developer.chrome.com/extensions/platformKeysInternal) | [chromex/ext/platform_keys_internal.clj](chromex/ext/platform_keys_internal.clj) |
| [chrome.printerProviderInternal](https://developer.chrome.com/extensions/printerProviderInternal) | [chromex/ext/printer_provider_internal.clj](chromex/ext/printer_provider_internal.clj) |
| [chrome.webRequestInternal](https://developer.chrome.com/extensions/webRequestInternal) | [chromex/ext/web_request_internal.clj](chromex/ext/web_request_internal.clj) |
| [chrome.webViewInternal](https://developer.chrome.com/extensions/webViewInternal) | [chromex/ext/web_view_internal.clj](chromex/ext/web_view_internal.clj) |


### API stats

Generated 16 namespaces containing 0 properties, 89 functions and 17 events:


    |                             :namespace | :properties | :functions | :events |
    |----------------------------------------+-------------+------------+---------|
    |       chrome.app.currentWindowInternal |           0 |         15 |       8 |
    |            chrome.appViewGuestInternal |           0 |          2 |       0 |
    |              chrome.automationInternal |           0 |          5 |       5 |
    |     chrome.certificateProviderInternal |           0 |          2 |       0 |
    |           chrome.chromeWebViewInternal |           0 |          5 |       0 |
    |            chrome.contextMenusInternal |           0 |          0 |       1 |
    | chrome.enterprise.platformKeysInternal |           0 |          2 |       0 |
    |        chrome.extensionOptionsInternal |           0 |          0 |       3 |
    |           chrome.extensionViewInternal |           0 |          2 |       0 |
    |      chrome.fileBrowserHandlerInternal |           0 |          1 |       0 |
    |      chrome.fileManagerPrivateInternal |           0 |         20 |       0 |
    |               chrome.guestViewInternal |           0 |          3 |       0 |
    |            chrome.platformKeysInternal |           0 |          3 |       0 |
    |         chrome.printerProviderInternal |           0 |          5 |       0 |
    |              chrome.webRequestInternal |           0 |          2 |       0 |
    |                 chrome.webViewInternal |           0 |         22 |       0 |

### Requires

```
(ns your.project
  (:require
    [chromex.ext.app.current-window-internal refer-macros:[
      focus fullscreen minimize maximize restore draw-attention clear-attention show hide set-bounds
      set-size-constraints set-icon set-shape set-always-on-top set-visible-on-all-workspaces
      tap-on-closed tap-on-bounds-changed tap-on-fullscreened tap-on-minimized tap-on-maximized
      tap-on-restored tap-on-alpha-enabled-changed tap-on-window-shown-for-tests tap-all-events]]
    
    [chromex.ext.app-view-guest-internal refer-macros:[
      attach-frame deny-request tap-all-events]]
    
    [chromex.ext.automation-internal refer-macros:[
      enable-tab enable-frame enable-desktop perform-action query-selector tap-on-accessibility-event
      tap-on-accessibility-tree-destroyed tap-on-tree-change tap-on-child-tree-id tap-on-nodes-removed
      tap-all-events]]
    
    [chromex.ext.certificate-provider-internal refer-macros:[
      report-signature report-certificates tap-all-events]]
    
    [chromex.ext.chrome-web-view-internal refer-macros:[
      context-menus-create context-menus-update context-menus-remove context-menus-remove-all
      show-context-menu tap-all-events]]
    
    [chromex.ext.context-menus-internal refer-macros:[
      tap-on-clicked tap-all-events]]
    
    [chromex.ext.enterprise.platform-keys-internal refer-macros:[
      get-tokens generate-key tap-all-events]]
    
    [chromex.ext.extension-options-internal refer-macros:[
      tap-on-close tap-on-load tap-on-preferred-size-changed tap-all-events]]
    
    [chromex.ext.extension-view-internal refer-macros:[
      load-src parse-src tap-all-events]]
    
    [chromex.ext.file-browser-handler-internal refer-macros:[
      select-file tap-all-events]]
    
    [chromex.ext.file-manager-private-internal refer-macros:[
      resolve-isolated-entries get-entry-properties add-file-watch remove-file-watch get-custom-actions
      execute-custom-action compute-checksum get-mime-type pin-drive-file execute-task set-default-task
      get-file-tasks get-share-url get-download-url request-drive-share set-entry-tag
      cancel-file-transfers start-copy zip-selection validate-path-name-length tap-all-events]]
    
    [chromex.ext.guest-view-internal refer-macros:[
      create-guest destroy-guest set-size tap-all-events]]
    
    [chromex.ext.platform-keys-internal refer-macros:[
      select-client-certificates sign get-public-key tap-all-events]]
    
    [chromex.ext.printer-provider-internal refer-macros:[
      report-printers report-usb-printer-info report-printer-capability report-print-result
      get-print-data tap-all-events]]
    
    [chromex.ext.web-request-internal refer-macros:[
      add-event-listener event-handled tap-all-events]]
    
    [chromex.ext.web-view-internal refer-macros:[
      execute-script insert-css add-content-scripts remove-content-scripts set-zoom get-zoom
      set-zoom-mode get-zoom-mode find stop-finding load-data-with-base-url go override-user-agent reload
      set-allow-transparency set-allow-scaling set-name set-permission navigate stop terminate clear-data
      tap-all-events]]))
```