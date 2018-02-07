// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts

const { SpecReporter } = require('jasmine-spec-reporter');
const jasmineReporters = require('jasmine-reporters');

exports.config = {
  allScriptsTimeout: 11000,
  specs: [
    './e2e/**/*.e2e-spec.ts'
  ],
  capabilities: {
    'browserName': 'chrome'
  },
  directConnect: true,
  baseUrl: 'http://localhost:4200/',
  framework: 'jasmine',
  jasmineNodeOpts: {
    showColors: true,
    defaultTimeoutInterval: 30000,
    print: function() {}
  },
  onPrepare() {
    // Delay each action by 100ms so it can be seen
    const origFn = browser.driver.controlFlow().execute;
    browser.driver.controlFlow().execute = function () {
      const args = arguments;

      // queue 100ms wait
      origFn.call(browser.driver.controlFlow(), function () {
        return protractor.promise.delayed(100);
      });

      return origFn.apply(browser.driver.controlFlow(), args);
    };
    require('ts-node').register({
      project: 'e2e/tsconfig.e2e.json'
    });
    jasmine.getEnv().addReporter(new SpecReporter({ spec: { displayStacktrace: true } }));
    jasmine.getEnv().addReporter(new jasmineReporters.JUnitXmlReporter({
      savePath: 'build/test-results/e2e'
    }));
  }
};
