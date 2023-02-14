<#macro comment post,type>
    <#if !post.disallowComment!false>
        <script src="${theme_base!}/source/npm/vue@2.6.10/dist/vue.min.js"></script>
        <script src="${theme_base!}/source/npm/halo-dev/halo-comment@latest/dist/halo-comment.min.js"></script>
        <halo-comment id="${post.id}" type="${type}"/>
    </#if>
    <style>
        .halo-comment .comment-placeholder {
            border: none !important;
        }
        .comment-item-content img {
            width: 100%;
        }
    </style>
</#macro>