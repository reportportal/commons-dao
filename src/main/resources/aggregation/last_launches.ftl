[
    {$match: {"projectRef":"${project}"}},
    {$lookup: {from:"launchMetaInfo", localField:"name", foreignField: "_id", as:"meta"}},
    {$unwind:"$meta"},
    {$addFields:{"last":{$eq:['$meta.projects.${project}','$number']}}},
    {$match:{"last":true}}
]