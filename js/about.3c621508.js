"use strict";(self["webpackChunkvue_rea"]=self["webpackChunkvue_rea"]||[]).push([[594],{2668:function(t,e,i){i.r(e),i.d(e,{default:function(){return k}});var s=function(){var t=this,e=t._self._c;return e("div",{staticClass:"Newest"},t._l(t.Obj,(function(i,s){return e("div",{key:s,staticClass:"wrapper"},[e("div",{staticClass:"container"},[e("div",{on:{click:function(e){return t.handleClick(s)}}},[e("transition",{attrs:{name:"scale",mode:"out-in"}},[e(t.itemVisibility[s]?"ArticleList":"ContentList",{tag:"component",attrs:{Obj:i}})],1)],1),e("transition",{attrs:{name:"slide",mode:"out-in"}},[t.itemVisibility[s]?t._e():e("div",{staticClass:"button-container"},[e("button",{staticClass:"styled-button",on:{click:function(e){return t.openOriginal(i)}}},[t._v("跳转原文")]),e("button",{class:1==t.itemCollect[s]?"styled-button-another":"styled-button",on:{click:function(e){return t.collectItem(i,s)}}},[t._v("收藏")]),e("button",{staticClass:"styled-button",on:{click:function(e){return t.collapseContent(s)}}},[t._v("收起")])])])],1)])})),0)},l=[],n=function(){var t=this,e=t._self._c;return e("div",{staticClass:"ArticleList"},[e("div",{staticClass:"box"},[e("div",{staticClass:"box1"},[e("div",{staticClass:"str"},[t._v(t._s(t.title))])]),e("div",{staticClass:"box2"},[e("div",{staticClass:"province"},[t._v(t._s(t.author))]),e("div",{staticClass:"likes"},[t._v(t._s(t.time))])])])])},o=[],a={name:"ArticleList",props:{Obj:Object},data(){return{title:this.Obj.title,author:this.Obj.author,time:this.Obj.time.split(" ")[0]}},created(){},methods:{}},c=a,r=i(1656),h=(0,r.A)(c,n,o,!1,null,"db8ae9c6",null),u=h.exports,d=function(){var t=this,e=t._self._c;return e("div",{staticClass:"userlist-frame"},[e("div",{staticClass:"content",domProps:{innerHTML:t._s(t.HTMLContent)}})])},m=[],C=i(4373),b={name:"ContentList",props:{Obj:{type:Object,required:!0}},data(){return{HTMLContent:""}},methods:{getContent(){C.A.get(`http://localhost:8087/getContentById?id=${this.Obj.id}`).then((t=>{this.HTMLContent=t.data})).catch((t=>{this.$message({message:"获取内容失败！",type:"error",duration:2e3}),console.error("获取内容出错！",t)}))}},created(){this.getContent()}},p=b,v=(0,r.A)(p,d,m,!1,null,null,null),g=v.exports,y={name:"ArticleView",components:{ArticleList:u,ContentList:g},data(){return{Obj:[],itemVisibility:[],itemCollect:[]}},methods:{getList(){let t=[],e={};C.A.get("http://localhost:8087/getAllArticlesByTime").then((i=>{t=i.data;for(let s=0;s<t.length;s++)e[s]=t[s],this.itemVisibility[s]=!0,this.itemCollect[s]=t[s].collect;console.log(e),this.Obj=e})).catch((t=>{this.$message({message:"获取页面内容失败！",type:"error",duration:2e3}),console.log("获取排行出错！"),console.error(t)}))},trans(t){this.$set(this.itemVisibility,t,!this.itemVisibility[t])},handleClick(t){console.log("Clicked index:",t),this.itemVisibility[t]&&this.$set(this.itemVisibility,t,!this.itemVisibility[t])},handleCollapse(t){this.$set(this.itemVisibility,t,!0),console.log("shouqi")},openOriginal(t){window.open(t.url,"_blank")},collapseContent(t){this.$set(this.itemVisibility,t,!0)},collectItem(t,e){1==this.itemCollect[e]?(console.log("取消收藏"),this.$set(this.itemCollect,e,0),console.log("取消收藏成功")):(console.log("收藏"),this.$set(this.itemCollect,e,1)),C.A.get("http://localhost:8087/updateArticleCollect",{params:{id:t.id,collect:this.itemCollect[e]}}).then().catch((t=>{this.$message({message:"收藏设置失败！",type:"error",duration:2e3}),console.error("收藏设置出错！",t)}))}},created(){this.getList()}},_=y,f=(0,r.A)(_,s,l,!1,null,null,null),k=f.exports}}]);
//# sourceMappingURL=about.3c621508.js.map