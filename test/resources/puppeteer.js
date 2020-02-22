const puppeteer = require('puppeteer');
const fs = require('fs');

const path = process.argv[2];
const page = process.argv[3];

const httpServer = require('http-server');

const host = '127.0.0.1';
const port = 3000;

const url = `http://${host}:${port}/${page}`;

const serverOptions = {root: path};

const server = httpServer.createServer(serverOptions);

server.listen(port, host, function () {
  console.log(`[puppeteer] Server running at http://${host}:${port}/`);

  (async () => {
    const browser = await puppeteer.launch();
    const page = await browser.newPage();

    async function testCompilerOutput() {
      const compilerOutput = fs.readFileSync("test/.compiler-out.txt", {encoding: "utf8"});
      return page.evaluate(output => chromex.test.compiler.warnings(output), compilerOutput);
    }

    async function checkResults() {
      let failures = await page.evaluate('window["test-failures"]');
      failures += await testCompilerOutput();

      // give node some time to flush event loop before exiting
      // we might lose some logMsg calls which are async
      setTimeout(() => {
        process.exit(failures ? 100 : 0);
      }, 1000);
    }

    // see https://github.com/puppeteer/puppeteer/issues/3397#issuecomment-434970058
    async function logMsg(msg) {
      // serialize my args the way I want
      const args = await Promise.all(msg.args().map(arg => arg.executionContext().evaluate(arg => {
        // I'm in a page context now. If my arg is an error - get me its message.
        if (arg instanceof Error)
          return arg.message;
        // return arg right away. since we use `executionContext.evaluate`, it'll return JSON value of
        // the argument if possible, or `undefined` if it fails to stringify it.
        return arg;
      }, arg)));
      console.log(...args);
    }

    page.on('console', async msg => {
      if (msg.text() === "TESTS DONE") {
        await checkResults();
      } else {
        await logMsg(msg);
      }
    });

    console.log("[puppeteer] Navigating to: ", url);
    await page.goto(url);
  })();
});
