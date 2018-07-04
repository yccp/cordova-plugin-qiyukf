'use strict';

module.exports = {
  /**
   * @param {Function} successCallback ['success']
   * @param {Function} errorCallback ['fail'|'cancel'|'invalid']
   */
  open: function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "Qiyukf", "open");
  },
};