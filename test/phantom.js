var system = require('system');

if (system.args.length != 2) {
    console.log('Expected a target URL parameter.');
    phantom.exit(1);
}

var page = require('webpage').create();
var url = system.args[1];

page.onConsoleMessage = function (message) {
  if (message == "TESTS DONE") {
    var failures = page.evaluate(function(){
        return window["test-failures"];
    });
    phantom.exit(failures?100:0);
  } else {
    console.log(message);
  }
};

console.log("Loading URL: " + url);

page.open(url, function (status) {
    if (status != "success") {
        console.log('Failed to open ' + url);
        phantom.exit(1);
    }
});