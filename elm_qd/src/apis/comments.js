import http from "@/utils/http";

export const getRemarkedListAPI=(data)=>{
    return http({
        url:'/api/comments',
        method:'POST',
        data
    })
}

// export const publishRemarkAPI=(data)=>{
//     return http({
//         url:'/api/comments',
//         method:'PUT',
//         data
//     })
// }