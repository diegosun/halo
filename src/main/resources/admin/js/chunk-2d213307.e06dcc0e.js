(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d213307"],{ac2a:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"container-wrapper"},[a("div",{staticClass:"halo-logo animated fadeInUp"},[a("span",[e._v("Halo "),e.apiForm.visible?a("small",[e._v("API 设置")]):e._e()])]),a("div",{directives:[{name:"show",rawName:"v-show",value:!e.apiForm.visible,expression:"!apiForm.visible"}],staticClass:"login-form animated"},[a("LoginForm",{on:{success:e.onLoginSucceed}}),a("a-row",[a("a-col",{attrs:{span:24}},[a("router-link",{attrs:{to:{name:"ResetPassword"}}},[e.resetPasswordButtonVisible?a("a",{staticClass:"tip animated fadeInRight",attrs:{href:"javascript:void(0);"}},[e._v(" 找回密码 ")]):e._e()])],1)],1)],1),a("div",{directives:[{name:"show",rawName:"v-show",value:e.apiForm.visible,expression:"apiForm.visible"}],staticClass:"api-form animated"},[a("a-form",{attrs:{layout:"vertical"}},[a("a-form-item",{staticClass:"animated fadeInUp",style:{"animation-delay":"0.1s"}},[a("a-tooltip",{attrs:{placement:"top",title:"如果 Admin 不是独立部署，请不要更改此 API",trigger:"click"}},[a("a-input",{attrs:{placeholder:"API 地址"},model:{value:e.apiForm.apiUrl,callback:function(t){e.$set(e.apiForm,"apiUrl",t)},expression:"apiForm.apiUrl"}},[a("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"api"},slot:"prefix"})],1)],1)],1),a("a-form-item",{staticClass:"animated fadeInUp",style:{"animation-delay":"0.2s"}},[a("a-button",{attrs:{block:!0},on:{click:e.handleRestoreApiUrl}},[e._v("恢复默认")])],1),a("a-form-item",{staticClass:"animated fadeInUp",style:{"animation-delay":"0.3s"}},[a("a-button",{attrs:{type:"primary",block:!0},on:{click:e.handleModifyApiUrl}},[e._v("保存设置")])],1),a("a-row",[a("a",{staticClass:"tip animated fadeInUp",style:{"animation-delay":"0.4s"},on:{click:e.handleToggleShowApiForm}},[a("a-icon",{attrs:{type:"rollback"}})],1)])],1)],1)])},r=[],s=(a("8e6e"),a("ac6a"),a("456d"),a("a481"),a("96cf"),a("3b8d")),o=a("bd86"),n=a("50fc"),l=a("2f62"),c=a("fe86");function p(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);t&&(i=i.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,i)}return a}function d(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?p(Object(a),!0).forEach((function(t){Object(o["a"])(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):p(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}var u={components:{LoginForm:c["a"]},data:function(){return{resetPasswordButtonVisible:!1,apiForm:{apiUrl:window.location.host,visible:!1}}},computed:d({},Object(l["c"])({defaultApiUrl:"apiUrl"})),beforeMount:function(){var e=this;e.handleVerifyIsInstall(),document.addEventListener("keydown",(function(t){72===t.keyCode&&t.altKey&&t.shiftKey&&(e.resetPasswordButtonVisible=!e.resetPasswordButtonVisible)}))},methods:d(d(d({},Object(l["b"])(["refreshUserCache","refreshOptionsCache"])),Object(l["d"])({setApiUrl:"SET_API_URL",restoreApiUrl:"RESTORE_API_URL"})),{},{handleVerifyIsInstall:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,n["a"].isInstalled();case 2:t=e.sent,t.data.data||this.$router.push({name:"Install"});case 4:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}(),onLoginSucceed:function(){this.refreshUserCache(),this.refreshOptionsCache(),this.$route.query.redirect?this.$router.replace(this.$route.query.redirect):this.$router.replace({name:"Dashboard"})},handleModifyApiUrl:function(){this.setApiUrl(this.apiForm.apiUrl),this.apiForm.visible=!1},handleRestoreApiUrl:function(){this.restoreApiUrl(),this.apiForm.apiUrl=this.defaultApiUrl},handleToggleShowApiForm:function(){this.apiForm.visible=!this.apiForm.visible,this.apiForm.visible&&(this.apiForm.apiUrl=this.defaultApiUrl)}})},m=u,h=a("2877"),f=Object(h["a"])(m,i,r,!1,null,null,null);t["default"]=f.exports}}]);