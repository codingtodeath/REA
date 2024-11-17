"use strict";(self["webpackChunkvue_rea"]=self["webpackChunkvue_rea"]||[]).push([[594],{9785:function(t,i,e){e.r(i),e.d(i,{default:function(){return D}});var s=function(){var t=this,i=t._self._c;return i("div",{staticClass:"Newest"},t._l(t.Obj,(function(e,s){return i("div",{key:s,staticClass:"wrapper"},[i("div",{staticClass:"container"},[i("div",{staticClass:"horizontal-container",on:{click:function(i){return t.handleClick(s)}}},[i("transition",{attrs:{name:"scale",mode:"out-in"}},[t.itemDescript[s]&&!t.itemVisibility[s]?i("DescriptionList",{attrs:{description:t.description[s]}}):t._e()],1),i("transition",{attrs:{name:"scale",mode:"out-in"}},[i(t.itemVisibility[s]?"ArticleList":"ContentList",{tag:"component",attrs:{Obj:e}})],1)],1),i("transition",{attrs:{name:"slide",mode:"out-in"}},[t.itemVisibility[s]?t._e():i("div",{staticClass:"button-container"},[i("button",{staticClass:"styled-button",on:{click:function(i){return t.openOriginal(e)}}},[t._v("跳转原文")]),i("button",{class:1==t.itemCollect[s]?"styled-button-another":"styled-button",on:{click:function(i){return t.collectItem(e,s)}}},[t._v("收藏")]),i("button",{staticClass:"styled-button",on:{click:function(i){return t.collapseContent(s)}}},[t._v("收起")]),i("button",{class:1==t.itemDescript[s]?"styled-button-another":"styled-button",on:{click:function(i){return t.llmDescription(s)}}},[t._v("摘要")])])])],1)])})),0)},o=[],n=function(){var t=this,i=t._self._c;return i("div",{staticClass:"ArticleList"},[i("div",{staticClass:"box"},[i("div",{staticClass:"box1"},[i("div",{staticClass:"str"},[t._v(t._s(t.title))])]),i("div",{staticClass:"box2"},[i("div",{staticClass:"province"},[t._v(t._s(t.author))]),i("div",{staticClass:"likes"},[t._v(t._s(t.time))])])])])},l=[],c={name:"ArticleList",props:{Obj:Object},data(){return{title:this.Obj.title,author:this.Obj.author,time:this.Obj.time.split(" ")[0]}},created(){},methods:{}},r=c,a=e(1656),h=(0,a.A)(r,n,l,!1,null,"db8ae9c6",null),d=h.exports,u=function(){var t=this,i=t._self._c;return i("div",{staticClass:"userlist-frame"},[i("div",{staticClass:"content",domProps:{innerHTML:t._s(t.HTMLContent)}})])},m=[],p=e(4373),C={name:"ContentList",props:{Obj:{type:Object,required:!0}},data(){return{HTMLContent:""}},methods:{getContent(){p.A.get(`http://localhost:8087/getContentById?id=${this.Obj.id}`).then((t=>{this.HTMLContent=t.data})).catch((t=>{this.$message({message:"获取内容失败！",type:"error",duration:2e3}),console.error("获取内容出错！",t)}))}},created(){this.getContent()}},b=C,g=(0,a.A)(b,u,m,!1,null,null,null),v=g.exports,y=function(){var t=this,i=t._self._c;return i("div",{staticClass:"summary-box"},[t.description?i("div",{staticClass:"summary-content"},[i("h3",[t._v("文章摘要")]),i("p",[t._v(t._s(t.description))])]):i("div",{staticClass:"summary-placeholder"},[i("p",[t._v("正在生成摘要，请稍候...")])])])},_=[],f={name:"DescriptionList",props:{description:{type:String,required:!0}},methods:{getContent(){},created(){console.log("666666"),this.fetchDescription&&(this.getContent(),console.log("111")),console.log("222")}}},L=f,A=(0,a.A)(L,y,_,!1,null,"1a48642d",null),k=A.exports,O={name:"ArticleView",components:{ArticleList:d,ContentList:v,DescriptionList:k},data(){return{Obj:[],itemVisibility:[],itemCollect:[],itemDescript:[],description:[]}},methods:{getList(){let t=[],i={};p.A.get("http://localhost:8087/getAllArticlesByTime").then((e=>{t=e.data;for(let s=0;s<t.length;s++)i[s]=t[s],this.itemVisibility[s]=!0,this.itemCollect[s]=t[s].collect,this.itemDescript[s]=0,this.description[s]="";console.log(i),this.Obj=i})).catch((t=>{this.$message({message:"获取页面内容失败！",type:"error",duration:2e3}),console.log("获取排行出错！"),console.error(t)}))},trans(t){this.$set(this.itemVisibility,t,!this.itemVisibility[t])},handleClick(t){console.log("Clicked index:",t),this.itemVisibility[t]&&this.$set(this.itemVisibility,t,!this.itemVisibility[t])},handleCollapse(t){this.$set(this.itemVisibility,t,!0),console.log("shouqi")},openOriginal(t){window.open(t.url,"_blank")},collapseContent(t){this.$set(this.itemVisibility,t,!0)},llmDescription(t){this.$set(this.itemDescript,t,!this.itemDescript[t]),this.itemDescript[t]&&p.A.get(`http://localhost:8087/getArticleLLMById?id=${this.Obj[t].id}`).then((i=>{this.$set(this.description,t,i.data),console.log("111",this.description)})).catch((t=>{this.$message({message:"获取内容失败！",type:"error",duration:2e3}),console.error("获取内容出错！",t)}))},collectItem(t,i){1==this.itemCollect[i]?(console.log("取消收藏"),this.$set(this.itemCollect,i,0),console.log("取消收藏成功")):(console.log("收藏"),this.$set(this.itemCollect,i,1)),p.A.get("http://localhost:8087/updateArticleCollect",{params:{id:t.id,collect:this.itemCollect[i]}}).then().catch((t=>{this.$message({message:"收藏设置失败！",type:"error",duration:2e3}),console.error("收藏设置出错！",t)}))}},created(){this.getList()}},$=O,j=(0,a.A)($,s,o,!1,null,null,null),D=j.exports}}]);
//# sourceMappingURL=about.7ea64cd2.js.map