(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0b383e"],{2967:function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("page-view",[n("a-row",[n("a-col",{attrs:{span:24}},[n("a-card",{attrs:{bordered:!1,bodyStyle:{padding:"16px"}}},[n("a-card",{staticClass:"environment-info",attrs:{bordered:!1,bodyStyle:{padding:"16px"}}},[n("template",{slot:"title"},[t._v(" 环境信息 "),n("a",{attrs:{href:"javascript:void(0);"},on:{click:t.handleCopyEnvironments}},[n("a-icon",{attrs:{type:"copy"}})],1)]),n("a-popover",{attrs:{slot:"extra",placement:"left",title:t.isLatest?"当前为最新版本":"有新版本"},slot:"extra"},[n("template",{slot:"content"},[n("p",[t._v(t._s(t.versionMessage))]),n("a-button",{attrs:{type:"dashed"},on:{click:t.handleShowVersionContent}},[t._v("查看详情")])],1),n("a-button",{attrs:{loading:t.checking,type:"dashed",shape:"circle",icon:t.isLatest?"check-circle":"exclamation-circle"}})],2),n("ul",{staticClass:"p-0 m-0 list-none"},[n("li",[t._v("版本："+t._s(t.environments.version))]),n("li",[t._v("数据库："+t._s(t.environments.database))]),n("li",[t._v("运行模式："+t._s(t.environments.mode))]),n("li",[t._v("启动时间："+t._s(t._f("moment")(t.environments.startTime)))])]),n("a",{staticClass:"mr-3",attrs:{href:"https://halo.run",target:"_blank"}},[t._v("官网 "),n("a-icon",{attrs:{type:"link"}})],1),n("a",{staticClass:"mr-3",attrs:{href:"https://docs.halo.run",target:"_blank"}},[t._v("文档 "),n("a-icon",{attrs:{type:"link"}})],1),n("a",{staticClass:"mr-3",attrs:{href:"https://github.com/halo-dev",target:"_blank"}},[t._v("开源组织 "),n("a-icon",{attrs:{type:"link"}})],1),n("a",{staticClass:"mr-3",attrs:{href:"https://bbs.halo.run",target:"_blank"}},[t._v("在线社区 "),n("a-icon",{attrs:{type:"link"}})],1)],2),n("a-card",{attrs:{title:"开发者",bordered:!1,bodyStyle:{padding:"16px"},loading:t.contributorsLoading}},t._l(t.contributors,(function(t,e){return n("a",{key:e,attrs:{href:t.html_url,target:"_blank"}},[n("a-tooltip",{attrs:{placement:"top",title:t.login}},[n("a-avatar",{style:{marginRight:"10px",marginBottom:"10px"},attrs:{size:"large",src:t.avatar_url}})],1)],1)})),0)],1)],1),n("a-col",{attrs:{span:24}})],1),n("a-modal",{attrs:{title:t.versionContentModalTitle,visible:t.versionContentVisible,"ok-text":"查看更多",width:620},on:{cancel:function(e){t.versionContentVisible=!1},ok:t.handleOpenVersionUrl}},[n("div",{domProps:{innerHTML:t._s(t.versionContent)}})])],1)},r=[],o=(n("28a5"),n("a481"),n("96cf"),n("3b8d")),i=(n("7f7f"),n("50fc")),s=n("bc3a"),c=n.n(s),l=n("0e54"),u=n.n(l),h=n("680a"),p={components:{PageView:h["c"]},data:function(){return{environments:{},contributors:[{login:"",id:0,node_id:"",avatar_url:"",gravatar_id:"",url:"",html_url:"",followers_url:"",following_url:"",gists_url:"",starred_url:"",subscriptions_url:"",organizations_url:"",repos_url:"",events_url:"",received_events_url:"",type:"",site_admin:!1,contributions:0}],contributorsLoading:!0,checking:!1,isLatest:!1,latestData:{},versionContentVisible:!1}},computed:{versionMessage:function(){return"当前版本：".concat(this.environments.version,"，").concat(this.isLatest?"已经是最新版本。":"新版本：".concat(this.latestData.name,"，你可以点击下方按钮查看详情。"))},versionContent:function(){return this.latestData&&this.latestData.body?u()(this.latestData.body):"暂无内容"},versionContentModalTitle:function(){return"".concat(this.latestData.name," 更新内容")}},created:function(){this.getEnvironments(),this.fetchContributors()},methods:{getEnvironments:function(){var t=Object(o["a"])(regeneratorRuntime.mark((function t(){var e=this;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,i["a"].environments().then((function(t){e.environments=t.data.data}));case 2:this.checkServerUpdate();case 3:case"end":return t.stop()}}),t,this)})));function e(){return t.apply(this,arguments)}return e}(),handleCopyEnvironments:function(){var t=this,e="版本：".concat(this.environments.version,"\n数据库：").concat(this.environments.database,"\n运行模式：").concat(this.environments.mode,"\nUser Agent：").concat(navigator.userAgent);this.$copyText(e).then((function(e){t.$log.debug("copy",e),t.$message.success("复制成功！")})).catch((function(e){t.$log.debug("copy.err",e),t.$message.error("复制失败！")}))},fetchContributors:function(){var t=this;t.contributorsLoading=!0,c.a.get("https://api.github.com/repos/halo-dev/halo/contributors").then((function(e){t.contributors=e.data})).catch((function(e){t.$log.error("Fetch contributors error",e)})).finally((function(){setTimeout((function(){t.contributorsLoading=!1}),200)}))},checkServerUpdate:function(){var t=this,e=this.$createElement,n=this;n.checking=!0,c.a.get("https://api.github.com/repos/halo-dev/halo/releases/latest").then((function(a){var r=a.data;if(n.latestData=r,!r.draft&&!r.prerelease){var o=n.calculateIntValue(n.environments.version),i=n.calculateIntValue(r.name);if(o>=i)n.isLatest=!0;else{var s="新版本提醒",c="检测到 Halo 新版本："+r.name+"，点击下方按钮查看最新版本。";n.$notification.open({message:s,description:c,icon:e("a-icon",{attrs:{type:"smile"},style:"color: #108ee9"}),btn:function(e){return e("a-button",{props:{type:"primary",size:"small"},on:{click:function(){return t.handleShowVersionContent()}}},"去看看")}})}}})).catch((function(t){this.$log.error("Check update fail",t)})).finally((function(){setTimeout((function(){t.checking=!1}),200)}))},handleShowVersionContent:function(){this.versionContentVisible=!0},handleOpenVersionUrl:function(){window.open(this.latestData.html_url,"_blank")},calculateIntValue:function(t){t=t.replace(/v/g,"");var e=t.split(".");if(null==e||3!==e.length)return-1;var n=parseInt(e[0]),a=parseInt(e[1]),r=parseInt(e[2]);return isNaN(n)||isNaN(a)||isNaN(r)?-1:1e6*n+1e3*a+r}}},d=p,v=n("2877"),m=Object(v["a"])(d,a,r,!1,null,null,null);e["default"]=m.exports}}]);