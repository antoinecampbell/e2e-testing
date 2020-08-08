// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

const path = require('path');

module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
    plugins: [
      'karma-jasmine',
      'karma-chrome-launcher',
      'karma-jasmine-html-reporter',
      'karma-coverage-istanbul-reporter',
      'karma-junit-reporter',
      'karma-mocha-reporter',
      '@angular-devkit/build-angular/plugins/karma'
    ],
    files: [
      {pattern: './src/styles.css', watched: false},
      {pattern: './node_modules/@angular/material/prebuilt-themes/indigo-pink.css', watched: false}
    ],
    client: {
      captureConsole: false,
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    coverageIstanbulReporter: {
      reports: ['html', 'lcovonly'],
      fixWebpackSourcePaths: true,
      dir: path.join(__dirname, 'build/test-results/coverage'),
      skipFilesWithNoCoverage: false
    },
    junitReporter: {
      outputDir: 'build/test-results/unit'
    },
    reporters: ['kjhtml', 'coverage-istanbul', 'junit', 'mocha'],
    customLaunchers: {
      Chrome_headless: {
        base: 'Chrome',
        flags: [
          '--no-sandbox',
          '--headless',
          '--disable-gpu',
          '--remote-debugging-port=9222'
        ]
      }
    },
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'],
    singleRun: false
  });
};
