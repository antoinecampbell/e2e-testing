// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts

const {SpecReporter} = require('jasmine-spec-reporter');
const jasmineReporters = require('jasmine-reporters');
const {resolve} = require('path');

exports.config = {
  allScriptsTimeout: 11000,
  specs: [
    './src/**/*.e2e-spec.ts'
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
    print: function () {
    }
  },
  onPrepare() {
    // Delay each action by 100ms so it can be seen
    // const origFn = browser.driver.controlFlow().execute;
    // browser.driver.controlFlow().execute = function () {
    //   const args = arguments;
    //
    //   // queue 100ms wait
    //   origFn.call(browser.driver.controlFlow(), function () {
    //     return protractor.promise.delayed(100);
    //   });
    //
    //   return origFn.apply(browser.driver.controlFlow(), args);
    // };
    require('ts-node').register({
      project: resolve(__dirname, './tsconfig.json')
    });
    jasmine.getEnv().addReporter(new SpecReporter({spec: {displayStacktrace: true}}));
    jasmine.getEnv().addReporter(new jasmineReporters.JUnitXmlReporter({
      savePath: 'build/test-results/e2e'
    }));
  }
};
